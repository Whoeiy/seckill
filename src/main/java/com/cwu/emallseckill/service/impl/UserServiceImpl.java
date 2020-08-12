package com.cwu.emallseckill.service.impl;

import com.cwu.emallseckill.dao.UserMapper;
import com.cwu.emallseckill.entity.User;
import com.cwu.emallseckill.param.LoginParam;
import com.cwu.emallseckill.result.CodeMsg;
import com.cwu.emallseckill.result.Result;
import com.cwu.emallseckill.service.IUserService;
import com.cwu.emallseckill.util.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
public class UserServiceImpl implements IUserService {


    @Autowired
    private UserMapper userMapper;

    @Override
    public Result<User> login(LoginParam loginParam) {
        User user = this.userMapper.checkPhone(loginParam.getMobile());
        if(ObjectUtils.isEmpty(user)) {
            return Result.error(CodeMsg.MOBILE_NOT_EXIST);
        }
        String dbPwd = user.getPassword();
        String saltDB = user.getSalt();
        String calaPass = MD5Util.inputPassToDBPass(loginParam.getPassword(), saltDB);
        if(!StringUtils.equals(calaPass, dbPwd)){
            return Result.error(CodeMsg.PASSWORD_ERROR);
        }
        user.setPassword(StringUtils.EMPTY);    // 空值回填

        return Result.success(user);
    }
}
