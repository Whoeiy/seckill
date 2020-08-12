package com.cwu.emallseckill.consts;

public class Const {

    public interface RedisCacheExtime {
        int REDIS_SESSION_EXTIME = 60 * 30; // 30分钟
        int GOODS_LIST = 60 * 30 * 24;      // 12小时
        int GOODS_ID = 60;                  // 1分钟
    }
}
