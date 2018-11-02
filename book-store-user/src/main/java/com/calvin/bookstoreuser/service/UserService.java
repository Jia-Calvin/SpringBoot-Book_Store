package com.calvin.bookstoreuser.service;

import com.calvin.bookstorebasis.entity.User;

public interface UserService {

    // 查询用户名是否存在
    boolean isExistUsername(String username);

    // 增加用户
    boolean addUser(User user);

    // 增加用户
    boolean addUser(String username, String password, boolean gender,
                    String email, String address, String telephone);

    // 删除用户并且同步删除地址
    boolean deleteUser(String username);

    // 查询用户的完整信息
    User findUserByUsername(String username);

    // 新增收货地址
    boolean addAddress(User user, String address);

    // 修改密码，需要验证旧密码
    boolean updatePassword(User user, String oldPassword, String newPassword);

    // 修改收货地址，需要对应的收货地址id
    public boolean updateAddress(User user, String newAddress, int addrId);

    // 修改电话号码
    boolean updateTelephone(User user, String telephone);

    // 验证用户名与密码
    boolean verityPassword(String username, String enterPassword);

}
