package app.user.user.service;

import app.user.api.user.GetUserResponse;
import app.user.api.user.UserStatusView;
import app.user.user.domain.User;
import core.framework.db.Repository;
import core.framework.inject.Inject;
import core.framework.util.Strings;
import core.framework.web.exception.NotFoundException;

/**
 * @author zoo
 */
public class UserService {
    @Inject
    Repository<User> repository;

    public GetUserResponse get(Long id) {
        User user = repository.get(id).orElseThrow(() -> new NotFoundException(Strings.format("user not found, id = {}", id)));

        GetUserResponse response = new GetUserResponse();
        response.id = user.id;
        response.username = user.username;
        response.status = UserStatusView.valueOf(user.status.name());

        return response;
    }
}
