package app.admin;

import app.admin.admin.domain.Admin;
import app.admin.admin.service.BOAdminService;
import app.admin.admin.web.BOAdminWebServiceImpl;
import app.api.admin.BOAdminWebService;
import core.framework.module.Module;

/**
 * @author zoo
 */
public class AdminModule extends Module {
    @Override
    protected void initialize() {
        db().repository(Admin.class);

        bind(BOAdminService.class);

        api().service(BOAdminWebService.class, bind(BOAdminWebServiceImpl.class));
    }
}
