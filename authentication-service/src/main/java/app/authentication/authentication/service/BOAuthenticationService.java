package app.authentication.authentication.service;

import app.api.admin.BOAdminWebService;
import app.api.admin.admin.BOGetAdminByAccountRequest;
import app.api.admin.admin.BOGetAdminByAccountResponse;
import app.authentication.authentication.exception.AuthenticationException;
import app.api.authentication.authentication.BOLoginRequest;
import app.api.authentication.authentication.BOLoginResponse;
import core.framework.inject.Inject;
import core.framework.log.Markers;
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
    private final String secretKey;

    public BOAuthenticationService(String secretKey) {
        this.secretKey = secretKey;
    }

    public BOLoginResponse login(BOLoginRequest request) {
        BOGetAdminByAccountRequest boGetAdminByAccountRequest = new BOGetAdminByAccountRequest();
        boGetAdminByAccountRequest.account = request.account;
        BOGetAdminByAccountResponse boGetAdminByAccountResponse = boAdminWebService.getByAccount(boGetAdminByAccountRequest);

        try {
            if (!boGetAdminByAccountResponse.password.equals(getPasswordHash(request.password, boGetAdminByAccountResponse.salt))) {
                throw new BadRequestException("account or password incorrect", "ADMIN_PASSWORD_INCORRECT");
            }
        } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
            logger.error(Markers.errorCode("HASH_PASSWORD_ERROR"), e.getMessage());
            throw new AuthenticationException("login failed.", e, "HASH_PASSWORD_ERROR");
        }

        BOLoginResponse response = new BOLoginResponse();
        response.id = boGetAdminByAccountResponse.id;

        return response;
    }


    private String getPasswordHash(String password, String salt) throws InvalidKeySpecException, NoSuchAlgorithmException {
        byte[] saltBytes = Base64.getDecoder().decode(salt);
        KeySpec spec = new PBEKeySpec(password.toCharArray(), saltBytes, 65536, 128);
        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(secretKey);
        byte[] encoded = secretKeyFactory.generateSecret(spec).getEncoded();

        return Base64.getEncoder().encodeToString(encoded);
    }
}
