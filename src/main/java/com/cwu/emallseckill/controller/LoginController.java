package com.cwu.emallseckill.controller;

import com.cwu.emallseckill.consts.Const;
import com.cwu.emallseckill.entity.User;
import com.cwu.emallseckill.param.LoginParam;
import com.cwu.emallseckill.redis.RedisService;
import com.cwu.emallseckill.redis.UserKey;
import com.cwu.emallseckill.result.Result;
import com.cwu.emallseckill.service.IUserService;
import com.cwu.emallseckill.util.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class LoginController {

    @Autowired
    private RedisService redisService;

    @Autowired
    private IUserService userService;

    /** 联调前端前 **/
    @RequestMapping("/login")
    @ResponseBody
    public Result<User> login(@Valid LoginParam param, HttpServletRequest request, HttpServletResponse response){
        Result<User> login = this.userService.login(param);
        if(login.isSuccess()){
            CookieUtil.writeLoginToken(response, request.getSession().getId());

            /** 放入redis中 **/
            this.redisService.set(UserKey.getByName, request.getSession().getId(), login.getData(), Const.RedisCacheExtime.REDIS_SESSION_EXTIME);
        }

        return login;
    }

    /** 联调前端后 **/
//    @RequestMapping("/login")
//    @ResponseBody
//    public Result<User> login(@RequestBody User user, HttpServletRequest request, HttpServletResponse response){
//
//        LoginParam loginParam = new LoginParam();
//        loginParam.setMobile(user.getUserName());
//        loginParam.setPassword(user.getPassword());
//
//        Result<User> login = this.userService.login(loginParam);
//        if(login.isSuccess()){
//            CookieUtil.writeLoginToken(response, request.getSession().getId());
//
//            /** 放入redis中 **/
//            this.redisService.set(UserKey.getByName, request.getSession().getId(), login.getData(), Const.RedisCacheExtime.REDIS_SESSION_EXTIME);
//        }
//
//        return login;
//    }

    @RequestMapping("/logout")
    @ResponseBody
    public String logout(HttpServletRequest request, HttpServletResponse response){
        String token = CookieUtil.readLoginToken(request);
        CookieUtil.delLoginToken(request, response);

        /** 从redis中删除 **/
        this.redisService.del(UserKey.getByName, token);

        return "login";
    }
}
