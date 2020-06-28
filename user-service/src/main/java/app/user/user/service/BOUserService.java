package app.user.user.service;

import app.user.api.user.BOCreateUserRequest;
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
        repository.selectOne("username = ?", request.username).ifPresent(u -> {
            throw new ConflictException(Strings.format("user already exists, username = {}", u.username));
        });

        repository.selectOne("email = ?", request.email).ifPresent(u -> {
            throw new ConflictException(Strings.format("user already exists, email = {}", u.email));
        });

        User user = new User();
        user.username = request.username;
        user.email = request.email;
        user.status = UserStatus.valueOf(request.status.name());
        user.createdAt = LocalDateTime.now();
        user.updatedAt = LocalDateTime.now();
        user.createdBy = "UserService";
        user.updatedBy = "UserService";

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

        if (request.username != null) {
            query.where("username like ?", request.username + "%");
        }

        if (request.email != null) {
            query.where("email like ?", request.email + "%");
        }

        if (request.status != null) {
            query.where("status = ?", request.status.name());
        }

        response.total = query.count();

        query.skip(request.skip);
        query.limit(request.limit);
        response.users = query.fetch().stream().map(user -> {
            BOSearchUserResponse.User u = new BOSearchUserResponse.User();
            u.id = user.id;
            u.status = UserStatusView.valueOf(user.status.name());
            u.username = user.username;
            u.email = user.email;
            u.createdAt = user.createdAt;
            u.updatedAt = user.updatedAt;

            return u;
        }).collect(Collectors.toList());

        return response;
    }

    public void update(Long id, BOUpdateUserRequest request) {
        User user = repository.get(id).orElseThrow(() -> new NotFoundException(Strings.format("user not found, id = {}", id)));
        user.status = UserStatus.valueOf(request.status.name());

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

        logger.info("salt: {}", enc.encodeToString(salt));
        logger.info("hash: {}", enc.encodeToString(hash));

        user.password = enc.encodeToString(hash);
        user.salt = enc.encodeToString(salt);
    }
}
