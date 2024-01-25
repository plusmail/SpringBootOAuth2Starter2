package com.yi.service;

import com.yi.entity.User;

import java.util.List;

public interface UserService {
    User createUser(User user);

    User getUserById(Long userId);

    List<User> getAllUsers();

    User updateUser(User user) throws Exception;

    void deleteUser(Long userId);
}
