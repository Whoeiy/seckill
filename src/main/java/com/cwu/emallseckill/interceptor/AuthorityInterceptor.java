package com.cwu.emallseckill.interceptor;

import com.alibaba.fastjson.JSON;
import com.cwu.emallseckill.annotations.AccessLimit;
import com.cwu.emallseckill.entity.User;
import com.cwu.emallseckill.redis.AccessKey;
import com.cwu.emallseckill.redis.RedisService;
import com.cwu.emallseckill.redis.UserKey;
import com.cwu.emallseckill.result.CodeMsg;
import com.cwu.emallseckill.result.Result;
import com.cwu.emallseckill.util.CookieUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;

/**
 * 使用拦截器统一校验用户权限
 */
@Component
public class AuthorityInterceptor implements HandlerInterceptor {
    @Autowired
    private RedisService redisService;

    private Logger logger = LoggerFactory.getLogger(AuthorityInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 请求Controller中的方法名
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;

            // 解析HandlerMethod
            String methodName = handlerMethod.getMethod().getName();
            String className = handlerMethod.getBean().getClass().getSimpleName();

            StringBuffer requestParamBuffer = new StringBuffer();
            Map parmaMap = request.getParameterMap();
            Iterator iterator = parmaMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry entry = (Map.Entry) iterator.next();
                String mapKey = (String) entry.getKey();
                String mapValue = "";

                // request的这个参数map的value返回的是一个String[]
                Object object = entry.getValue();
                if (object instanceof String[]) {
                    String[] strs = (String[]) object;
                    mapValue = Arrays.toString(strs);
                }
                requestParamBuffer.append(mapKey).append("=").append(mapValue);
            }

            // 接口限流
            AccessLimit accessLimit = handlerMethod.getMethodAnnotation(AccessLimit.class);
            if (ObjectUtils.isEmpty(accessLimit)) {
                return true;
            }
            int seconds = accessLimit.seconds();
            int maxCount = accessLimit.maxCount();
            boolean needLogin = accessLimit.needLogin();
            String key = request.getRequestURI();

            // 对于拦截其中拦截login登录的处理，对于登录不拦截，直接放行
            if (!StringUtils.equals(className, "SeckillController")) {
                // 如果是拦截到登录请求，不打印参数，因为参数里面有密码，全部会打印到日志中，防止日志泄露
                logger.info("权限拦截器拦截到请求 SeckillController, className:{}, methodName:{}", className, methodName);
                return true;
            }

            logger.info("--> 权限拦截器拦截到请求，className:{}, methodName:{}, parma:{}", className, methodName, requestParamBuffer);

            User user = null;
            String loginToken = CookieUtil.readLoginToken(request);
            if (StringUtils.isNotEmpty(loginToken)) {
                user = this.redisService.get(UserKey.getByName, loginToken, User.class);
            }

            if (needLogin) {
                if (ObjectUtils.isEmpty(user)) {
                    render(response, CodeMsg.USER_NO_LOGIN);
                    return false;
                }
                key += "_" + user.getId();
            }

            AccessKey accessKey = AccessKey.withExpire;
            Integer count = this.redisService.get(accessKey, key, Integer.class);
            if (ObjectUtils.isEmpty(count)) {
                this.redisService.set(accessKey, key, 1, seconds);
            } else if (count < maxCount) {
                this.redisService.incr(accessKey, key);
            } else {
                render(response, CodeMsg.ACCESS_LIMIT_REACHED);
                return false;
            }
        }

        return true;
    }

    private void render(HttpServletResponse response, CodeMsg codeMsg) throws Exception {
        response.setContentType("application/json;charset=UTF-8");
        OutputStream out = response.getOutputStream();  // 输出流
        String str = JSON.toJSONString(Result.error(codeMsg));
        out.write(str.getBytes("UTF-8"));
        out.flush();
        out.close();
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
