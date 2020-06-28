package app.user.user.service;

import app.user.api.user.GetUserResponse;
import app.user.api.user.LoginUserRequest;
import app.user.api.user.UserStatusView;
import app.user.user.domain.User;
import core.framework.db.Repository;
import core.framework.inject.Inject;
import core.framework.json.JSON;
import core.framework.util.Strings;
import core.framework.web.WebContext;
import core.framework.web.exception.BadRequestException;
import core.framework.web.exception.NotFoundException;
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
public class UserService {
    private final Logger logger = LoggerFactory.getLogger(UserService.class);
    @Inject
    Repository<User> repository;
    @Inject
    WebContext webContext;

    public GetUserResponse get(Long id) {
        User user = repository.get(id).orElseThrow(() -> new NotFoundException(Strings.format("user not found, id = {}", id)));

        GetUserResponse response = new GetUserResponse();
        response.id = user.id;
        response.username = user.username;
        response.email = user.email;
        response.status = UserStatusView.valueOf(user.status.name());

        webContext.request().session().get("user").ifPresent(u -> {
            logger.info("login user, username = {}", JSON.fromJSON(User.class, u).username);
        });
        return response;
    }

    public void login(LoginUserRequest request) {
        User user = repository.selectOne("username = ?", request.username).orElseThrow(
            () -> new BadRequestException("username or password incorrect"));

        try {
            byte[] saltBytes = Base64.getDecoder().decode(user.salt);
            KeySpec spec = new PBEKeySpec(request.password.toCharArray(), saltBytes, 65536, 128);
            SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] passwordHash = f.generateSecret(spec).getEncoded();
            if (user.password.equals(Base64.getEncoder().encodeToString(passwordHash))) {
                webContext.request().session().set("user", JSON.toJSON(user));
                logger.info("login success, username = {}, password = {}", request.username, request.password);
            } else {
                throw new BadRequestException("username or password incorrect");
            }
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            logger.error("user login error, message = {}", e.getMessage());
        }
    }
}
