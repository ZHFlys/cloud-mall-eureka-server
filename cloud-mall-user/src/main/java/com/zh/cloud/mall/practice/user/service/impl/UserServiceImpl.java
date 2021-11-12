package com.zh.cloud.mall.practice.user.service.impl;

import com.zh.cloud.mall.practice.common.common.MD5Utils;
import com.zh.cloud.mall.practice.common.exception.ZhMallException;
import com.zh.cloud.mall.practice.common.exception.ZhMallExceptionEnum;
import com.zh.cloud.mall.practice.user.service.UserService;
import com.zh.cloud.mall.practice.user.model.dao.UserMapper;
import com.zh.cloud.mall.practice.user.model.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;

/**
 * UserSerivice实现类
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public void register(String username, String password) throws ZhMallException {
        //查询是否存在，不允许重名
        User result = userMapper.selectByName(username);
        if(result != null){
            throw new ZhMallException(ZhMallExceptionEnum.NAME_EXISTED);
        }
        User user = new User();
        user.setUsername(username);
        try {
            user.setPassword(MD5Utils.getMD5Str(password));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        int count = userMapper.insertSelective(user);
        if(count == 0){
            throw new ZhMallException(ZhMallExceptionEnum.INSERT_FAILED);
        }
    }

    @Override
    public User login(String userName, String password) throws ZhMallException {
        String md5Passwor = null;

        try {
            md5Passwor = MD5Utils.getMD5Str(password);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        User user = userMapper.selectLogin(userName, md5Passwor);
        if(user == null){
            throw new ZhMallException(ZhMallExceptionEnum.WRONG_PASSWORD);
        }
        return user;

    }

    @Override
    public void updateInfomation(User user) throws ZhMallException {
        int count = userMapper.updateByPrimaryKeySelective(user);
        if( count > 1){
            throw new ZhMallException(ZhMallExceptionEnum.UPDATE_FAILED);
        }
    }

    @Override
    public boolean checkAdminRole(User user){
        //1是普通用户，2是管理员
        return user.getRole().equals(2);
    }
}
