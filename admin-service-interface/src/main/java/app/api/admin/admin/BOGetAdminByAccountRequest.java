package app.api.admin.admin;

import core.framework.api.validate.Length;
import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;
import core.framework.api.web.service.QueryParam;

/**
 * @author zoo
 */
public class BOGetAdminByAccountRequest {
    @NotNull
    @NotBlank
    @Length(max = 50)
    @QueryParam(name = "account")
    public String account;
}
