package iot.service;

import iot.model.User;

public interface UserService {

    public User getUserById(Long userId);

    public void createUser(User user);

    public void updateUser(User user);

    public String verifyAccount(String email, String password);

}