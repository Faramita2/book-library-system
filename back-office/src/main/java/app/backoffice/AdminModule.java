package app.backoffice;

import app.api.backoffice.AdminAJAXWebService;
import app.backoffice.service.AdminService;
import app.backoffice.api.AdminAJAXWebServiceImpl;
import app.backoffice.web.interceptor.AuthInterceptor;
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
        api().service(AdminAJAXWebService.class, bind(AdminAJAXWebServiceImpl.class));
    }

    private void services() {
        bind(AdminService.class);
    }
}
