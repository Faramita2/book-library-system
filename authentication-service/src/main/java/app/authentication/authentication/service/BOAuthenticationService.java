package app.authentication.authentication.service;

import app.api.admin.BOAdminWebService;
import app.api.admin.admin.BOGetAdminByAccountRequest;
import app.api.admin.admin.BOGetAdminByAccountResponse;
import app.api.authentication.authentication.BOLoginRequest;
import app.api.authentication.authentication.BOLoginResponse;
import core.framework.inject.Inject;
import core.framework.web.exception.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

/**
 * @author zoo
 */
public class BOAuthenticationService {
    private final Logger logger = LoggerFactory.getLogger(BOAuthenticationService.class);
    @Inject
    BOAdminWebService boAdminWebService;

    public BOLoginResponse login(BOLoginRequest request) {
        BOGetAdminByAccountRequest boGetAdminByAccountRequest = new BOGetAdminByAccountRequest();
        BOGetAdminByAccountResponse boGetAdminByAccountResponse = boAdminWebService.getByAccount(boGetAdminByAccountRequest);

        if (!boGetAdminByAccountResponse.password.equals(getPasswordHash(request.password, boGetAdminByAccountResponse.salt))) {
            throw new BadRequestException("account or password incorrect", "ADMIN_PASSWORD_INCORRECT");
        }

        BOLoginResponse response = new BOLoginResponse();
        response.id = boGetAdminByAccountResponse.id;

        return response;
    }


    private String getPasswordHash(String password, String salt) {
        String passwordHash = "";
        byte[] saltBytes = Base64.getDecoder().decode(salt);
        KeySpec spec = new PBEKeySpec(password.toCharArray(), saltBytes, 65536, 128);
        try {
            SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] encoded = secretKeyFactory.generateSecret(spec).getEncoded();
            passwordHash = Base64.getEncoder().encodeToString(encoded);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            logger.error("hash password error, message = {}", e.getMessage());
        }

        return passwordHash;
    }
}
