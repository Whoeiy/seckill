package com.cwu.emallseckill.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Goods implements Serializable {

    private Long id;            // 数据行序号
    private String goodsName;   // 商品名称
    private String goodsTitle;  // 商品标题
    private String goodsImg;    // 商品图片
    private BigDecimal goodsPrice;  // 商品价格
    private Integer goodsStock;     // 商品购买数
    private Date createDate;        // 商品创建日期
    private Date updateDate;        // 商品更新日期
    private String goodsDetail;     // 商品详情


    public Goods() {
    }

    public Goods(Long id, String goodsName, String goodsTitle, String goodsImg, BigDecimal goodsPrice, Integer goodsStock, Date createDate, Date updateDate, String goodsDetail) {
        this.id = id;
        this.goodsName = goodsName;
        this.goodsTitle = goodsTitle;
        this.goodsImg = goodsImg;
        this.goodsPrice = goodsPrice;
        this.goodsStock = goodsStock;
        this.createDate = createDate;
        this.updateDate = updateDate;
        this.goodsDetail = goodsDetail;
    }

    public Goods(Long id, String goodsName, String goodsTitle, String goodsImg, BigDecimal goodsPrice, Integer goodsStock, Date createDate, Date updateDate) {
        this.id = id;
        this.goodsName = goodsName;
        this.goodsTitle = goodsTitle;
        this.goodsImg = goodsImg;
        this.goodsPrice = goodsPrice;
        this.goodsStock = goodsStock;
        this.createDate = createDate;
        this.updateDate = updateDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsTitle() {
        return goodsTitle;
    }

    public void setGoodsTitle(String goodsTitle) {
        this.goodsTitle = goodsTitle;
    }

    public String getGoodsImg() {
        return goodsImg;
    }

    public void setGoodsImg(String goodsImg) {
        this.goodsImg = goodsImg;
    }

    public BigDecimal getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(BigDecimal goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public Integer getGoodsStock() {
        return goodsStock;
    }

    public void setGoodsStock(Integer goodsStock) {
        this.goodsStock = goodsStock;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getGoodsDetail() {
        return goodsDetail;
    }

    public void setGoodsDetail(String goodsDetail) {
        this.goodsDetail = goodsDetail;
    }

    @Override
    public String toString() {
        return "Goods{" +
                "id=" + id +
                ", goodsName='" + goodsName + '\'' +
                ", goodsTitle='" + goodsTitle + '\'' +
                ", goodsImg='" + goodsImg + '\'' +
                ", goodsPrice=" + goodsPrice +
                ", goodsStock=" + goodsStock +
                ", createDate=" + createDate +
                ", updateDate=" + updateDate +
                ", goodsDetail='" + goodsDetail + '\'' +
                '}';
    }
}
