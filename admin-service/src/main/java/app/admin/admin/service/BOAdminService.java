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

        hashPassword(admin, request.password);
        try (Transaction transaction = database.beginTransaction()) {
            logger.warn("==== start create admin ====");
            repository.insert(admin);
            transaction.commit();
            logger.warn("==== end create admin ====");
        }
    }

    public BOSearchAdminResponse search(BOSearchAdminRequest request) {
        BOSearchAdminResponse response = new BOSearchAdminResponse();
        Query<Admin> query = repository.select();

        if (!Strings.isBlank(request.account)) {
            query.where("account LIKE ?", request.account + "%");
        }

        query.skip(request.skip);
        query.limit(request.limit);

        response.total = query.count();
        response.admins = query.fetch().stream().map(user -> {
            BOSearchAdminResponse.Admin view = new BOSearchAdminResponse.Admin();
            view.id = user.id;
            view.account = user.account;
            view.createdAt = user.createdAt;
            view.updatedAt = user.updatedAt;
            return view;
        }).collect(Collectors.toList());

        return response;
    }

    public BOLoginAdminResponse login(BOLoginAdminRequest request) {
        Admin admin = repository.selectOne("account = ?", request.account).orElseThrow(() ->
            new BadRequestException("account or password incorrect"));


        if (admin.password.equals(getPasswordHash(request, admin.salt))) {
            BOLoginAdminResponse response = new BOLoginAdminResponse();
            response.id = admin.id;
            response.account = admin.account;
            return response;
        }

        throw new BadRequestException("account or password incorrect");
    }

    private byte[] generateSalt() {
        byte[] salt = new byte[16];
        SecureRandom random = new SecureRandom();
        random.nextBytes(salt);

        return salt;
    }

    private void hashPassword(Admin admin, String password) {
        byte[] salt = generateSalt();
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
        try {
            SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] hash = f.generateSecret(spec).getEncoded();
            Base64.Encoder enc = Base64.getEncoder();
            admin.password = enc.encodeToString(hash);
            admin.salt = enc.encodeToString(salt);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            logger.error("create admin failed: {}", e.getMessage());
        }
    }

    private String getPasswordHash(BOLoginAdminRequest request, String salt) {
        String passwordHash = "";
        byte[] saltBytes = Base64.getDecoder().decode(salt);
        KeySpec spec = new PBEKeySpec(request.password.toCharArray(), saltBytes, 65536, 128);
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
