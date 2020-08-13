package com.cwu.emallseckill.util;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtil {
    private final static String COOKIE_DOMAIN = "localhost";
    private final static String COOKIE_NAME = "seckill_login_token";

    /** 将token写入cookie **/
    public static void writeLoginToken(HttpServletResponse response, String token){
        Cookie cookie = new Cookie(COOKIE_NAME, token);
        cookie.setDomain(COOKIE_DOMAIN);
        cookie.setPath("/");        // 设置根目录
        cookie.setHttpOnly(true);
        // 单位是秒，如果maxage不设置的话，cookie不会写入硬盘，而是写入内存，只在当前页面有效
        cookie.setMaxAge(60 * 60 * 24 * 30);    // 如果是-1，代表永久
        response.addCookie(cookie);
    }

    /** 从cookie中读取token **/
    public static String readLoginToken(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
//        if(cookies != null){
//            for(Cookie cookie : cookies){
//                if(StringUtils.equals(cookie.getName(), COOKIE_NAME)){
//                    return cookie.getValue();
//                }
//            }
//        }

        if(cookies != null){
            for(Cookie cookie : cookies){
                if(StringUtils.equals(cookie.getName(), "JSESSIONID")){
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    /** 从cookie里删除token **/
    public static void delLoginToken(HttpServletRequest request, HttpServletResponse response){
        Cookie[] cookies = request.getCookies();
        if(cookies != null){
            for(Cookie cookie : cookies){
                if(StringUtils.equals(cookie.getName(), COOKIE_NAME)){
                    cookie.setDomain(COOKIE_DOMAIN);
                    cookie.setPath("/");
                    cookie.setMaxAge(0);    // 设置为0，代表删除此cookie
                    response.addCookie(cookie);
                    return;
                }
            }
        }
    }

}
