package com.cwu.emallseckill.param;

import com.cwu.emallseckill.validator.IsMobile;

import javax.validation.constraints.NotNull;

public class RegisterParam {

    @NotNull(message = "用户名不能为空")
    private String userName;

    @NotNull(message = "手机号不能为空")
    @IsMobile
    private String mobile;

    @NotNull(message = "密码不能为空")
    private String password;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "RegisterParam{" +
                "userName='" + userName + '\'' +
                ", mobile='" + mobile + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
