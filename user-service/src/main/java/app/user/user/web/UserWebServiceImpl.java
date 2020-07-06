package app.user.user.web;

import app.user.api.UserWebService;
import app.user.api.user.GetUserByUsernameRequest;
import app.user.api.user.GetUserByUsernameResponse;
import app.user.api.user.GetUserResponse;
import app.user.user.service.UserService;
import core.framework.inject.Inject;

/**
 * @author zoo
 */
public class UserWebServiceImpl implements UserWebService {
    @Inject
    UserService service;

    @Override
    public GetUserResponse get(Long id) {
        return service.get(id);
    }

    @Override
    public GetUserByUsernameResponse getUserByUsername(GetUserByUsernameRequest request) {
        return service.getUserByUsername(request);
    }
}
