package com.zh.cloud.mall.practice.user.service;


import com.zh.cloud.mall.practice.common.exception.ZhMallException;
import com.zh.cloud.mall.practice.user.model.pojo.User;

public interface UserService {
    void register(String username, String password) throws ZhMallException;

    User login(String userName, String password) throws ZhMallException;

    void updateInfomation(User user) throws ZhMallException;

    boolean checkAdminRole(User user);
}
