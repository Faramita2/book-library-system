package app.backoffice;

import app.api.backoffice.AdminAJAXWebService;
import app.backoffice.service.AdminService;
import app.backoffice.api.AdminAJAXWebServiceImpl;
import core.framework.module.Module;

/**
 * @author zoo
 */
public class AdminModule extends Module {
    @Override
    protected void initialize() {
        bind(AdminService.class);
        api().service(AdminAJAXWebService.class, bind(AdminAJAXWebServiceImpl.class));
    }
}
