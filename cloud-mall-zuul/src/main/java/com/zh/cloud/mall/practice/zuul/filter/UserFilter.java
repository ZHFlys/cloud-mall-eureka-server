package com.zh.cloud.mall.practice.zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.zh.cloud.mall.practice.common.common.Constant;
import com.zh.cloud.mall.practice.user.model.pojo.User;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author ：郑小浩
 * @description：用户鉴权控制器
 * @date ：2021/11/12 下午 15:30
 */

public class UserFilter extends ZuulFilter {

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();
        String requestURI = request.getRequestURI();

        if(requestURI.contains("images") || requestURI.contains("pay")){
            return false;
        }

        if(requestURI.contains("cart") || requestURI.contains("order")){
            return true;
        }
        return false;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Constant.ZH_MALL_USER);
        if(user == null){
            currentContext.setSendZuulResponse(false);
            currentContext.setResponseBody("{\n" +
                    "    \"status\": 10007,\n" +
                    "    \"msg\": \"用户未登录\",\n" +
                    "    \"data\": null\n" +
                    "}");
            currentContext.setResponseStatusCode(200);
        }
        return null;
    }
}
