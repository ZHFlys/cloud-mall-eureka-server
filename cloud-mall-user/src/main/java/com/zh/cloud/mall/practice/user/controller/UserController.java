package com.zh.cloud.mall.practice.user.controller;

import com.zh.cloud.mall.practice.common.common.ApiRestResponse;
import com.zh.cloud.mall.practice.common.common.Constant;
import com.zh.cloud.mall.practice.common.exception.ZhMallException;
import com.zh.cloud.mall.practice.common.exception.ZhMallExceptionEnum;
import com.zh.cloud.mall.practice.user.model.pojo.User;
import com.zh.cloud.mall.practice.user.service.UserService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * 用户控制器
 */
@Controller
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/register")
    @ResponseBody
    public ApiRestResponse register(String userName, String password) throws ZhMallException {
        if(StringUtils.isEmpty(userName)){
            return ApiRestResponse.error(ZhMallExceptionEnum.NEED_USER_NAME);
        }
        if(StringUtils.isEmpty(password)){
            return ApiRestResponse.error(ZhMallExceptionEnum.NEED_PASSWORD);
        }
        if(password.length() < 8){
            return ApiRestResponse.error(ZhMallExceptionEnum.PASSWORD_TOO_SHORT);
        }
        userService.register(userName, password);
        return ApiRestResponse.success();
    }

    @PostMapping("/login")
    @ResponseBody
    public ApiRestResponse login(@RequestParam("userName") String
     userName, @RequestParam("password") String password, HttpSession session) throws ZhMallException {
        if(StringUtils.isEmpty(userName)){
            return ApiRestResponse.error(ZhMallExceptionEnum.NEED_USER_NAME);
        }
        if(StringUtils.isEmpty(password)){
            return ApiRestResponse.error(ZhMallExceptionEnum.NEED_PASSWORD);
        }
        User user = userService.login(userName, password);
        session.setAttribute(Constant.ZH_MALL_USER, user);
        user.setPassword(null);//防止对方分析密码
        return ApiRestResponse.success(user);
    }

    @PostMapping("/user/update")
    @ResponseBody
    public ApiRestResponse updateUserInfo(HttpSession session,
    @RequestParam String signature) throws ZhMallException {
        User currentUser = (User) session.getAttribute(Constant.ZH_MALL_USER);
        if(currentUser == null){
            return ApiRestResponse.error(ZhMallExceptionEnum.NEED_LOGIN);
        }
        User user = new User();
        user.setId(currentUser.getId());
        user.setPersonalizedSignature(signature);
        userService.updateInfomation(user);

        return ApiRestResponse.success();

    }

    @PostMapping("/user/logout")
    @ResponseBody
    public ApiRestResponse logout(HttpSession session) throws ZhMallException {
        session.removeAttribute(Constant.ZH_MALL_USER);
        return ApiRestResponse.success();
    }

    /**
     * 管理员登录
     * @param userName 用户名
     * @param password 密码
     * @param session
     * @return
     * @throws ZhMallException
     */
    @PostMapping("/adminLogin")
    @ResponseBody
    public ApiRestResponse adminLogin(@RequestParam("userName") String
        userName, @RequestParam("password") String password, HttpSession session) throws ZhMallException {
        if(StringUtils.isEmpty(userName)){
            return ApiRestResponse.error(ZhMallExceptionEnum.NEED_USER_NAME);
        }
        if(StringUtils.isEmpty(password)){
            return ApiRestResponse.error(ZhMallExceptionEnum.NEED_PASSWORD);
        }
        User user = userService.login(userName, password);
        if (!userService.checkAdminRole(user)) {
            return ApiRestResponse.error(ZhMallExceptionEnum.NEED_ADMIN);
        }
        session.setAttribute(Constant.ZH_MALL_USER, user);
        user.setPassword(null);//防止对方分析密码
        return ApiRestResponse.success(user);
    }
}
