package com.example.userdetails.service;

import com.example.userdetails.dao.UserDao;
import com.example.userdetails.dao.UserData;
import com.example.userdetails.exception.DuplicateUserException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{

    private final UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public Collection<User> getUsers() {
        return userDao.findAll()
                .stream()
                .map(UserServiceImpl::convertUserData)
                .collect(Collectors.toList());
    }

    @Override
    public User addUser(User user) {
        final Optional<UserData> existingUser = userDao.findById(user.getId());
        if (existingUser.isPresent()) {
            throw new DuplicateUserException(user);
        }

        final UserData savedUser = userDao.add(convertUser(user));
        return convertUserData(savedUser);
    }

    private static User convertUserData(UserData userData) {
        return new User(userData.getId(), userData.getName(), userData.getEmail());
    }

    private static UserData convertUser(User user) {
        return new UserData(user.getId(), user.getName(), user.getEmail());
    }
}
