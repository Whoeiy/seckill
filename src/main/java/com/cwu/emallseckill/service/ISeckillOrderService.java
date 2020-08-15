package com.cwu.emallseckill.service;

import com.cwu.emallseckill.bo.GoodsBo;
import com.cwu.emallseckill.entity.OrderInfo;
import com.cwu.emallseckill.entity.SeckillOrder;
import com.cwu.emallseckill.entity.User;

import java.util.List;

public interface ISeckillOrderService {

    /** 隐藏了秒杀的路径，验证路径 **/
    boolean checkPath(User user, long goodsId, String path);

    /** 根据用户id和商品id获取秒杀订单 **/
    SeckillOrder getSeckillOrderByUserIdAndGoodsId(long userId, long goodsId);

    /** 秒杀成功，保存订单 **/
    OrderInfo insert(User user, GoodsBo goodsBo);

    /** 根据用户和用户选择的秒杀商品id，获取秒杀链接中的路径 **/
    String createSeckillPath(User user, long goodsId);

    /** 根据用户id和商品id查看秒杀是否成功的结果 **/
    long getSeckillResult(long userId, long goodsId);

    /** 查询某个用户下的订单列表 **/
    List<OrderInfo> getOrderList(User user);

    /** 根据订单id查询订单信息 **/
    OrderInfo getOrderInfo(long orderId);
}
