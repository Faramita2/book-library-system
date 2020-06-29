package app.admin.admin.service;

import app.admin.admin.domain.Admin;
import app.api.admin.admin.BOCreateAdminRequest;
import app.api.admin.admin.BOLoginAdminRequest;
import app.api.admin.admin.BOLoginAdminResponse;
import app.api.admin.admin.BOSearchAdminRequest;
import app.api.admin.admin.BOSearchAdminResponse;
import core.framework.db.Database;
import core.framework.db.Query;
import core.framework.db.Repository;
import core.framework.db.Transaction;
import core.framework.inject.Inject;
import core.framework.util.Strings;
import core.framework.web.WebContext;
import core.framework.web.exception.BadRequestException;
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
import java.util.stream.Collectors;

/**
 * @author zoo
 */
public class BOAdminService {
    private final Logger logger = LoggerFactory.getLogger(BOAdminService.class);
    @Inject
    Repository<Admin> repository;
    @Inject
    Database database;
    @Inject
    WebContext webContext;

    public void create(BOCreateAdminRequest request) {
        repository.selectOne("account = ?", request.account).ifPresent(admin -> {
            throw new ConflictException(Strings.format("admin exists, account = {}", admin.account));
        });

        Admin admin = new Admin();
        admin.account = request.account;
        LocalDateTime now = LocalDateTime.now();
        admin.createdAt = now;
        admin.updatedAt = now;
        admin.createdBy = request.createdBy;
        admin.updatedBy = request.createdBy;

        try (Transaction transaction = database.beginTransaction()) {
            logger.warn("==== start create admin ====");
            hashPassword(admin, request.password);
            repository.insert(admin);
            transaction.commit();
            logger.warn("==== start create admin ====");
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            logger.error("create admin failed: {}", e.getMessage());
        }
    }

    public BOSearchAdminResponse search(BOSearchAdminRequest request) {
        BOSearchAdminResponse response = new BOSearchAdminResponse();
        Query<Admin> query = repository.select();

        if (request.account != null) {
            query.where("account like ?", request.account + "%");
        }

        response.total = query.count();

        query.skip(request.skip);
        query.limit(request.limit);
        response.admins = query.fetch().stream().map(user -> {
            BOSearchAdminResponse.Admin a = new BOSearchAdminResponse.Admin();
            a.id = user.id;
            a.account = user.account;
            a.createdAt = user.createdAt;
            a.updatedAt = user.updatedAt;

            return a;
        }).collect(Collectors.toList());

        return response;
    }

    public BOLoginAdminResponse login(BOLoginAdminRequest request) {
        Admin admin = repository.selectOne("account = ?", request.account).orElseThrow(()
            -> new BadRequestException("account or password incorrect"));

        BOLoginAdminResponse response = new BOLoginAdminResponse();

        try {
            if (admin.password.equals(getPasswordHash(request, admin.salt))) {
                response.id = admin.id;
                response.account = admin.account;
                return response;
            }
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            logger.error("login admin failed: {}", e.getMessage());
        }

        throw new BadRequestException("account or password incorrect");
    }

    private byte[] generateSalt() {
        byte[] salt = new byte[16];
        SecureRandom random = new SecureRandom();
        random.nextBytes(salt);

        return salt;
    }

    private void hashPassword(Admin admin, String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] salt = generateSalt();
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
        SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] hash = f.generateSecret(spec).getEncoded();
        Base64.Encoder enc = Base64.getEncoder();

        logger.info("salt: {}", enc.encodeToString(salt));
        logger.info("hash: {}", enc.encodeToString(hash));

        admin.password = enc.encodeToString(hash);
        admin.salt = enc.encodeToString(salt);
    }

    private String getPasswordHash(BOLoginAdminRequest request, String salt)
        throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] saltBytes = Base64.getDecoder().decode(salt);
        KeySpec spec = new PBEKeySpec(request.password.toCharArray(), saltBytes, 65536, 128);
        SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

        return Base64.getEncoder().encodeToString(f.generateSecret(spec).getEncoded());
    }
}
