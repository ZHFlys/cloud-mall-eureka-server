package com.zh.cloud.mall.practice.zuul;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;


/**
 * @author ：郑小浩
 * @description：网关启动类
 * @date ：2021/11/12 下午 16:09
 */

@EnableZuulProxy
@EnableFeignClients
@SpringCloudApplication
@EnableRedisHttpSession
public class ZuulGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZuulGatewayApplication.class, args);
    }
}
