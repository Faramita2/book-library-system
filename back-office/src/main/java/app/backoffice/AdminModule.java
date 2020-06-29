package app.backoffice;

import app.api.backoffice.AdminAJAXWebService;
import app.backoffice.admin.service.AdminService;
import app.backoffice.admin.web.AdminAJAXWebServiceImpl;
import app.backoffice.admin.web.AuthInterceptor;
import core.framework.module.Module;

/**
 * @author zoo
 */
public class AdminModule extends Module {
    @Override
    protected void initialize() {
        bind(AdminService.class);
        http().intercept(bind(AuthInterceptor.class));
        api().service(AdminAJAXWebService.class, bind(AdminAJAXWebServiceImpl.class));
    }
}
