package org.andrea.auth;



import org.andrea.exceptions.DatabaseException;

import java.util.List;

public interface UserManager{
    public void add(User user) throws DatabaseException;

    public boolean isValid(User user);

    public boolean isPresent(String username);

    public List<User> getUsers();
}