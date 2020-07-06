package app.user.user.service;

import app.user.api.user.BOCreateUserRequest;
import app.user.api.user.BOGetUserResponse;
import app.user.api.user.BOResetUserPasswordRequest;
import app.user.api.user.BOSearchUserRequest;
import app.user.api.user.BOSearchUserResponse;
import app.user.api.user.BOUpdateUserRequest;
import app.user.api.user.UserStatusView;
import app.user.user.domain.User;
import app.user.user.domain.UserStatus;
import core.framework.db.Database;
import core.framework.db.Query;
import core.framework.db.Repository;
import core.framework.db.Transaction;
import core.framework.inject.Inject;
import core.framework.util.Strings;
import core.framework.web.exception.BadRequestException;
import core.framework.web.exception.ConflictException;
import core.framework.web.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.stream.Collectors;

/**
 * @author zoo
 */
public class BOUserService {
    private final Logger logger = LoggerFactory.getLogger(BOUserService.class);
    @Inject
    Repository<User> repository;
    @Inject
    Database database;

    public void create(BOCreateUserRequest request) {
        repository.selectOne("username = ?", request.username).ifPresent(user -> {
            throw new ConflictException(Strings.format("user already exists, username = {}", user.username), "USER_USERNAME_EXISTS");
        });

        repository.selectOne("email = ?", request.email).ifPresent(user -> {
            throw new ConflictException(Strings.format("user already exists, email = {}", user.email), "USER_EMAIL_EXISTS");
        });

        User user = new User();
        user.username = request.username;
        user.email = request.email;
        user.status = UserStatus.valueOf(request.status.name());
        LocalDateTime now = LocalDateTime.now();
        user.createdTime = now;
        user.updatedTime = now;
        user.createdBy = request.operator;
        user.updatedBy = request.operator;

        try (Transaction transaction = database.beginTransaction()) {
            logger.warn("==== start create user ====");
            hashPassword(user, request.password);
            repository.insert(user).orElseThrow();
            transaction.commit();
            logger.warn("==== end create user ====");
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            logger.error("create user failed: {}", e.getMessage());
        }
    }

    public BOSearchUserResponse search(BOSearchUserRequest request) {
        BOSearchUserResponse response = new BOSearchUserResponse();
        Query<User> query = repository.select();

        if (request.ids != null && !request.ids.isEmpty()) {
            query.in("id", request.ids);
        }

        if (!Strings.isBlank(request.username)) {
            query.where("username LIKE ?", Strings.format("{}%", request.username));
        }

        if (!Strings.isBlank(request.email)) {
            query.where("email LIKE ?", Strings.format("{}%", request.email));
        }

        if (request.status != null) {
            query.where("status = ?", request.status.name());
        }

        query.skip(request.skip);
        query.limit(request.limit);

        response.total = query.count();
        response.users = query.fetch().stream().map(user -> {
            BOSearchUserResponse.User view = new BOSearchUserResponse.User();
            view.id = user.id;
            view.status = UserStatusView.valueOf(user.status.name());
            view.username = user.username;
            view.email = user.email;
            view.createdTime = user.createdTime;
            view.updatedTime = user.updatedTime;
            return view;
        }).collect(Collectors.toList());

        return response;
    }

    public void update(Long id, BOUpdateUserRequest request) {
        User user = repository.get(id).orElseThrow(() ->
            new NotFoundException(Strings.format("user not found, id = {}", id), "USER_NOT_FOUND"));
        user.status = UserStatus.valueOf(request.status.name());
        user.updatedTime = LocalDateTime.now();
        user.updatedBy = request.operator;

        repository.partialUpdate(user);
    }

    private byte[] generateSalt() {
        byte[] salt = new byte[16];
        SecureRandom random = new SecureRandom();
        random.nextBytes(salt);

        return salt;
    }

    private void hashPassword(User user, String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] salt = generateSalt();
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
        SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] hash = f.generateSecret(spec).getEncoded();
        Base64.Encoder enc = Base64.getEncoder();

        user.password = enc.encodeToString(hash);
        user.salt = enc.encodeToString(salt);
    }

    public void resetPassword(Long id, BOResetUserPasswordRequest request) {
        User user = repository.get(id).orElseThrow(() ->
            new NotFoundException(Strings.format("user not found, id = {}", id), "USER_NOT_FOUND"));

        if (!request.password.equals(request.passwordConfirm)) {
            throw new BadRequestException("Please make sure both passwords match.", "USER_PASSWORD_INCORRECT");
        }

        user.updatedTime = LocalDateTime.now();
        user.updatedBy = request.operator;
        try (Transaction transaction = database.beginTransaction()) {
            logger.warn("==== start reset user password ====");
            hashPassword(user, request.password);
            repository.partialUpdate(user);
            transaction.commit();
            logger.warn("==== end reset user password ====");
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            logger.error("reset user password failed: {}", e.getMessage());
        }

    }

    public BOGetUserResponse get(Long id) {
        User user = repository.get(id).orElseThrow(() ->
            new NotFoundException(Strings.format("user not found, id = {}", id), "USER_NOT_FOUND"));

        BOGetUserResponse response = new BOGetUserResponse();
        response.id = user.id;
        response.username = user.username;
        response.email = user.email;
        response.status = UserStatusView.valueOf(user.status.name());

        return response;
    }
}
