package com.shoppingmate.service;

import com.shoppingmate.exception.UserException;
import com.shoppingmate.model.User;

public interface UserService {
    public User findUserById(Long id) throws UserException;

    public User findUserProfileByJwt(String jwt) throws UserException;
}
