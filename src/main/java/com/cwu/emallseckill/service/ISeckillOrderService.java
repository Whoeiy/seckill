package com.cwu.emallseckill.service;

import com.cwu.emallseckill.entity.SeckillOrder;
import com.cwu.emallseckill.entity.User;

public interface ISeckillOrderService {

    /** 隐藏了秒杀的路径，验证路径 **/
    boolean checkPath(User user, Long goodsId, String path);

    /** 根据用户id和商品id获取秒杀订单 **/
    SeckillOrder getSeckillOrderByUserIdAndGoodsId(Long userId, Long goodsId);
}
