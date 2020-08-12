package com.cwu.emallseckill.entity;

import java.io.Serializable;

public class SeckillOrder implements Serializable {

    private Long id;        // 数据行序号
    private Long userId;    // 用户id
    private Long orderId;   // 订单id
    private Long goodsId;   // 商品id

    public SeckillOrder() {
    }

    public SeckillOrder(Long id, Long userId, Long orderId, Long goodsId) {
        this.id = id;
        this.userId = userId;
        this.orderId = orderId;
        this.goodsId = goodsId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    @Override
    public String toString() {
        return "SeckillOrder{" +
                "id=" + id +
                ", userId=" + userId +
                ", orderId=" + orderId +
                ", goodsId=" + goodsId +
                '}';
    }
}
