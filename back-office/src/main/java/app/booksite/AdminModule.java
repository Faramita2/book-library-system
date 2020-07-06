package app.booksite;

import app.api.backoffice.AdminAJAXWebService;
import app.booksite.admin.service.AdminService;
import app.booksite.admin.web.AdminAJAXWebServiceImpl;
import app.booksite.admin.web.AuthInterceptor;
import core.framework.module.Module;

/**
 * @author zoo
 */
public class AdminModule extends Module {
    @Override
    protected void initialize() {
        services();

        apiServices();
    }

    private void apiServices() {
        http().intercept(bind(AuthInterceptor.class));
        api().service(AdminAJAXWebService.class, bind(AdminAJAXWebServiceImpl.class));
    }

    private void services() {
        bind(AdminService.class);
    }
}
