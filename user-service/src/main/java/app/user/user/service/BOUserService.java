package app.user.user.service;

import app.user.api.user.BOCreateUserRequest;
import app.user.user.domain.User;
import app.user.user.domain.UserStatus;
import core.framework.db.Database;
import core.framework.db.Repository;
import core.framework.db.Transaction;
import core.framework.inject.Inject;
import core.framework.util.Strings;
import core.framework.web.exception.ConflictException;
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

        User user = new User();
        user.username = request.username;
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
