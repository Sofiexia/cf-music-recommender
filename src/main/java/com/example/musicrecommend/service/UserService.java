package com.example.musicrecommend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.musicrecommend.entity.User;

public interface UserService extends IService<User> {
    User login(String username, String password);
    void register(User user);
    User getUserByUsername(String username);
    User getUserByEmail(String email);
}
