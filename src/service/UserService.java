package service;

import model.User;

/**
 * Created by Alican on 2016-11-21.
 */
public interface UserService {
    public User login(User user);
    public User register(User user);
    public User findUser(int id);
    public User findUserByUsername(String username);
}
