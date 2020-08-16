package com.cwu.emallseckill.service;

import com.cwu.emallseckill.entity.User;
import com.cwu.emallseckill.param.LoginParam;
import com.cwu.emallseckill.param.RegisterParam;
import com.cwu.emallseckill.result.Result;

public interface IUserService {
    /** 用户登录 **/
    Result<User> login(LoginParam loginParam);

    /** 用户注册 **/
    long register(RegisterParam registerParam);
}
