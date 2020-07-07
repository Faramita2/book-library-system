package app.booksite;

import app.api.backoffice.AdminAJAXWebService;
import app.booksite.service.AdminService;
import app.booksite.api.AdminAJAXWebServiceImpl;
import app.booksite.web.interceptor.AuthInterceptor;
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
