package app.user.user.service;

import app.user.api.user.GetUserByUsernameRequest;
import app.user.api.user.GetUserByUsernameResponse;
import app.user.api.user.GetUserResponse;
import app.user.api.user.UserStatusView;
import app.user.user.domain.User;
import core.framework.db.Repository;
import core.framework.inject.Inject;
import core.framework.util.Strings;
import core.framework.web.exception.BadRequestException;
import core.framework.web.exception.NotFoundException;

/**
 * @author zoo
 */
public class UserService {
    @Inject
    Repository<User> repository;

    public GetUserResponse get(Long id) {
        User user = repository.get(id).orElseThrow(() ->
            new NotFoundException(Strings.format("user not found, id = {}", id), "USER_NOT_FOUND"));

        GetUserResponse response = new GetUserResponse();
        response.id = user.id;
        response.username = user.username;
        response.email = user.email;
        response.status = UserStatusView.valueOf(user.status.name());

        return response;
    }

    public GetUserByUsernameResponse getUserByUsername(GetUserByUsernameRequest request) {
        User user = repository.selectOne("username = ?", request.username).orElseThrow(() ->
            new BadRequestException(Strings.format("user not exists, username = {}", request.username), "USER_NOT_FOUND"));

        GetUserByUsernameResponse response = new GetUserByUsernameResponse();
        response.id = user.id;
        response.username = user.username;
        response.email = user.email;
        response.salt = user.salt;
        response.password = user.password;
        response.status = UserStatusView.valueOf(user.status.name());

        return response;
    }
}
