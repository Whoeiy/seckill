package com.cwu.emallseckill.vo;

import com.cwu.emallseckill.bo.GoodsBo;
import com.cwu.emallseckill.entity.User;

/** 商品详情vo **/
public class GoodsDetailVo {
    private int seckillStatus = 0;  // 秒杀状态 0：秒杀未开始 1：秒杀中 2：秒杀结束
    private int remainSeconds = 0;  // 倒计时 >0：还没开始 0：秒杀中 -1：秒杀结束
    private GoodsBo goods;          // 秒杀商品
    private User user;              // 当前秒杀商品的用户

    public int getSeckillStatus() {
        return seckillStatus;
    }

    public void setSeckillStatus(int seckillStatus) {
        this.seckillStatus = seckillStatus;
    }

    public int getRemainSeconds() {
        return remainSeconds;
    }

    public void setRemainSeconds(int remainSeconds) {
        this.remainSeconds = remainSeconds;
    }

    public GoodsBo getGoods() {
        return goods;
    }

    public void setGoods(GoodsBo goods) {
        this.goods = goods;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
