package app.authentication.authentication.service;

import app.api.admin.BOAdminWebService;
import app.api.admin.admin.BOGetAdminByAccountRequest;
import app.api.admin.admin.BOGetAdminByAccountResponse;
import app.api.authentication.authentication.BOLoginRequest;
import app.api.authentication.authentication.BOLoginResponse;
import core.framework.inject.Inject;
import core.framework.web.exception.BadRequestException;

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
    @Inject
    BOAdminWebService boAdminWebService;
    private final String secretKey;

    public BOAuthenticationService(String secretKey) {
        this.secretKey = secretKey;
    }

    public BOLoginResponse login(BOLoginRequest request) throws InvalidKeySpecException, NoSuchAlgorithmException {
        BOGetAdminByAccountRequest boGetAdminByAccountRequest = new BOGetAdminByAccountRequest();
        boGetAdminByAccountRequest.account = request.account;
        BOGetAdminByAccountResponse boGetAdminByAccountResponse = boAdminWebService.getByAccount(boGetAdminByAccountRequest);

        if (!boGetAdminByAccountResponse.password.equals(getPasswordHash(request.password, boGetAdminByAccountResponse.salt))) {
            throw new BadRequestException("account or password incorrect", "ADMIN_PASSWORD_INCORRECT");
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
