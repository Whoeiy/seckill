package com.cwu.emallseckill.vo;

import com.cwu.emallseckill.bo.GoodsBo;
import com.cwu.emallseckill.entity.OrderInfo;

/** 订单详情 **/
public class OrderDetailVo {
    private GoodsBo goods;
    private OrderInfo order;

    public GoodsBo getGoods() {
        return goods;
    }

    public void setGoods(GoodsBo goods) {
        this.goods = goods;
    }

    public OrderInfo getOrder() {
        return order;
    }

    public void setOrder(OrderInfo order) {
        this.order = order;
    }
}
