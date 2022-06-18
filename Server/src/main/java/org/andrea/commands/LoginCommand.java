package org.andrea.commands;

import org.andrea.auth.User;
import org.andrea.auth.UserManager;
import org.andrea.connection.AnswerMsg;
import org.andrea.connection.Response;
import org.andrea.exceptions.AuthException;

public class LoginCommand extends CommandImpl {
    private final UserManager userManager;

    public LoginCommand(UserManager manager) {
        super("login", CommandType.AUTH);
        userManager = manager;
    }

    @Override
    public Response run() throws AuthException {

        User user = getArgument().getUser();
        if (user != null && user.getLogin() != null && user.getPassword() != null) {
            if (userManager.isValid(user)) {
                return new AnswerMsg().info("login successful").setStatus(Response.Status.AUTH_SUCCESS);
            }
        }
        throw new AuthException("login or password is incorrect");

    }
}
