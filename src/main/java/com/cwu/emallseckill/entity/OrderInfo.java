package com.cwu.emallseckill.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class OrderInfo implements Serializable {

    private Long id;        // 数据行序号
    private Long userId;    // 用户id
    private Long goodsId;   // 商品id
    private Long addrId;    // 地址id

    private String goodsName;   // 商品名称
    private Integer goodsCount; // 商品数量
    private BigDecimal goodsPrice;      // 商品价格
    private Integer orderChannel;       // 订单渠道
    private Integer status;             // 订单状态
    private Date createDate;             // 创建时间
    private Date payDate;               // 支付时间

    public OrderInfo() {
    }

    public OrderInfo(Long id, Long userId, Long goodsId, Long addrId, String goodsName, Integer goodsCount, BigDecimal goodsPrice, Integer orderChannel, Integer status, Date creatDate, Date payDate) {
        this.id = id;
        this.userId = userId;
        this.goodsId = goodsId;
        this.addrId = addrId;
        this.goodsName = goodsName;
        this.goodsCount = goodsCount;
        this.goodsPrice = goodsPrice;
        this.orderChannel = orderChannel;
        this.status = status;
        this.createDate = creatDate;
        this.payDate = payDate;
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

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public Long getAddrId() {
        return addrId;
    }

    public void setAddrId(Long addrId) {
        this.addrId = addrId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public Integer getGoodsCount() {
        return goodsCount;
    }

    public void setGoodsCount(Integer goodsCount) {
        this.goodsCount = goodsCount;
    }

    public BigDecimal getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(BigDecimal goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public Integer getOrderChannel() {
        return orderChannel;
    }

    public void setOrderChannel(Integer orderChannel) {
        this.orderChannel = orderChannel;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreatDate() {
        return createDate;
    }

    public void setCreatDate(Date creatDate) {
        this.createDate = creatDate;
    }

    public Date getPayDate() {
        return payDate;
    }

    public void setPayDate(Date payDate) {
        this.payDate = payDate;
    }

    @Override
    public String toString() {
        return "OrderInfo{" +
                "id=" + id +
                ", userId=" + userId +
                ", goodsId=" + goodsId +
                ", addrId=" + addrId +
                ", goodsName='" + goodsName + '\'' +
                ", goodsCount=" + goodsCount +
                ", goodsPrice=" + goodsPrice +
                ", orderChannel=" + orderChannel +
                ", status=" + status +
                ", creatDate=" + createDate +
                ", payDate=" + payDate +
                '}';
    }
}
