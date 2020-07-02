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
        dbs();

        services();

        apiServices();
    }

    private void apiServices() {
        api().service(BOAdminWebService.class, bind(BOAdminWebServiceImpl.class));
    }

    private void services() {
        bind(BOAdminService.class);
    }

    private void dbs() {
        db().repository(Admin.class);
    }
}
