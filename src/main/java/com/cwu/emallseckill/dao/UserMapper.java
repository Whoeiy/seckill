package com.cwu.emallseckill.dao;

import com.cwu.emallseckill.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
    /** 通过手机号查询用户 */
    User checkPhone(@Param("phone") String phone);

    /** 通过手机号及密码查询用户 */
    User selectByPhoneAndPassword(@Param("phone") String phone, @Param("password") String password) ;
}
