package app.admin.admin.service;

import app.admin.admin.domain.Admin;
import app.api.admin.admin.BOGetAdminByAccountRequest;
import app.api.admin.admin.BOGetAdminByAccountResponse;
import core.framework.db.Repository;
import core.framework.inject.Inject;
import core.framework.util.Strings;
import core.framework.web.exception.BadRequestException;

/**
 * @author zoo
 */
public class BOAdminService {
    @Inject
    Repository<Admin> adminRepository;

    public BOGetAdminByAccountResponse getByAccount(BOGetAdminByAccountRequest request) {
        Admin admin = adminRepository.selectOne("account = ?", request.account).orElseThrow(() ->
            new BadRequestException(Strings.format("account not exists, account = {}", request.account), "ADMIN_NOT_FOUND"));

        BOGetAdminByAccountResponse response = new BOGetAdminByAccountResponse();
        response.id = admin.id;
        response.account = admin.account;
        response.salt = admin.salt;
        response.password = admin.password;

        return response;
    }
}
