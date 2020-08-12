package com.cwu.emallseckill.filter;

import com.cwu.emallseckill.consts.Const;
import com.cwu.emallseckill.entity.User;
import com.cwu.emallseckill.redis.RedisService;
import com.cwu.emallseckill.redis.UserKey;
import com.cwu.emallseckill.util.CookieUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class LoginFilter implements Filter {

    @Autowired
    private RedisService redisService;


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String loginToken = CookieUtil.readLoginToken(request);

        if(StringUtils.isNotEmpty(loginToken)){
            User user = this.redisService.get(UserKey.getByName, loginToken, User.class);
            if(ObjectUtils.isEmpty(user)){
                // 如果user不为空，则重置session时间，即调用expire命令
                this.redisService.expice(UserKey.getByName, loginToken, Const.RedisCacheExtime.REDIS_SESSION_EXTIME);
            }
        }

        filterChain.doFilter(request, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
