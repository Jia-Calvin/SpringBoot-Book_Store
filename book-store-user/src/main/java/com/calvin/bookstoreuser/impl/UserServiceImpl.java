package com.calvin.bookstoreuser.impl;


import com.alibaba.dubbo.config.annotation.Service;
import com.calvin.bookstorebasis.entity.User;
import com.calvin.bookstoreuser.service.UserService;
import com.calvin.bookstoreuser.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;

@Component
@Service(
        version = "${demo.service.version}",
        application = "${dubbo.application.id}",
        protocol = "${dubbo.protocol.id}",
        registry = "${dubbo.registry.id}"
)
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean isExistUsername(String username) {
        return userMapper.findUserByUsername(username) != null;
    }

    @Override
    public User findUserByUsername(String username) {
        User user = userMapper.findUserByUsername(username);
        user.setAddress(userMapper.findAddressByUserId(user.getUserId()));
        return user;
    }

    @Override
    public boolean addUser(User user) {
        if (!isExistUsername(user.getUsername())) {
            userMapper.addUser(user);
            int userId = findUserByUsername(user.getUsername()).getUserId();
            user.getAddress().forEach(address -> userMapper.addAddress(userId, address));
            return true;
        }
        return false;
    }

    @Override
    public boolean addUser(String username, String password, boolean gender, String email, String address, String
            telephone) {
        User user = new User(username, password, gender, email, new ArrayList<>(Arrays.asList
                (address)), telephone);
        return addUser(user);
    }

    @Override
    public boolean addAddress(User user, String address) {
        int userId = findUserByUsername(user.getUsername()).getUserId();
        return userMapper.addAddress(userId, address);
    }

    @Override
    public boolean deleteUser(String username) {
        return userMapper.deleteUser(username);
    }

    @Override
    public boolean updatePassword(User user, String oldPassword, String newPassword) {
        if (user.getPassword().equals(oldPassword))
            return userMapper.updatePassword(user.getUsername(), newPassword);
        return false;
    }

    @Override
    public boolean updateAddress(User user, String newAddress, int addrId) {
        int userId = findUserByUsername(user.getUsername()).getUserId();
        return userMapper.updateAddress(newAddress, addrId, userId);
    }

    @Override
    public boolean updateTelephone(User user, String newTelephone) {
        return userMapper.updateTelephone(user.getUsername(), newTelephone);
    }

    @Override
    public boolean verityPassword(String username, String enterPassword) {
        if (isExistUsername(username)) {
            User user = findUserByUsername(username);
            return user.getPassword().equals(enterPassword);
        }
        else
            return false;
    }
}
