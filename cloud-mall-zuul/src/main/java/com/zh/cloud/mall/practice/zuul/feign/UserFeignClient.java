package com.zh.cloud.mall.practice.zuul.feign;

import com.zh.cloud.mall.practice.common.exception.ZhMallException;
import com.zh.cloud.mall.practice.user.model.pojo.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author ：郑小浩
 * @description：UserFeignClient
 * @date ：2021/11/12 下午 16:01
 */

@FeignClient(value = "cloud-mall-user")
public interface UserFeignClient {

    @PostMapping("/checkAdminRole")
    public Boolean checkAdminRole(@RequestBody User user);

}
