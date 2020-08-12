package com.cwu.emallseckill.bo;

import java.math.BigDecimal;
import java.util.Date;

public class GoodsBo {
    private BigDecimal seckillPrice;    // 秒杀价格
    private Integer stockCount;         // 购买数据
    private Date startDate;             // 开始日期
    private String startDateStr;
    private Date endDate;               // 结束日期
    private String endDateStr;

    private Long id;            // 数据行序号
    private String goodsName;   // 商品名称
    private String goodsTitle;  // 商品标题
    private String goodsImg;    // 商品图片
    private BigDecimal goodsPrice;  // 商品价格
    private Integer goodsStock;     // 商品购买数
    private Date createDate;        // 商品创建日期
    private String createDateStr;
    private Date updateDate;        // 商品更新日期
    private String updateDateStr;
    private String goodsDetail;     // 商品详情

    public GoodsBo() {
    }

    public GoodsBo(BigDecimal seckillPrice, Integer stockCount, Date startDate, Date endDate, Long id, String goodsName, String goodsTitle, String goodsImg, BigDecimal goodsPrice, Integer goodsStock, Date createDate, Date updateDate, String goodsDetail) {
        this.seckillPrice = seckillPrice;
        this.stockCount = stockCount;
        this.startDate = startDate;
        this.endDate = endDate;
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

    public BigDecimal getSeckillPrice() {
        return seckillPrice;
    }

    public void setSeckillPrice(BigDecimal seckillPrice) {
        this.seckillPrice = seckillPrice;
    }

    public Integer getStockCount() {
        return stockCount;
    }

    public void setStockCount(Integer stockCount) {
        this.stockCount = stockCount;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getStartDateStr() {
        return startDateStr;
    }

    public void setStartDateStr(String startDateStr) {
        this.startDateStr = startDateStr;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getEndDateStr() {
        return endDateStr;
    }

    public void setEndDateStr(String endDateStr) {
        this.endDateStr = endDateStr;
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

    public String getCreateDateStr() {
        return createDateStr;
    }

    public void setCreateDateStr(String createDateStr) {
        this.createDateStr = createDateStr;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getUpdateDateStr() {
        return updateDateStr;
    }

    public void setUpdateDateStr(String updateDateStr) {
        this.updateDateStr = updateDateStr;
    }

    public String getGoodsDetail() {
        return goodsDetail;
    }

    public void setGoodsDetail(String goodsDetail) {
        this.goodsDetail = goodsDetail;
    }

    @Override
    public String toString() {
        return "GoodsBo{" +
                "seckillPrice=" + seckillPrice +
                ", stockCount=" + stockCount +
                ", startDate=" + startDate +
                ", startDateStr='" + startDateStr + '\'' +
                ", endDate=" + endDate +
                ", endDateStr='" + endDateStr + '\'' +
                ", id=" + id +
                ", goodsName='" + goodsName + '\'' +
                ", goodsTitle='" + goodsTitle + '\'' +
                ", goodsImg='" + goodsImg + '\'' +
                ", goodsPrice=" + goodsPrice +
                ", goodsStock=" + goodsStock +
                ", createDate=" + createDate +
                ", createDateStr='" + createDateStr + '\'' +
                ", updateDate=" + updateDate +
                ", updateDateStr='" + updateDateStr + '\'' +
                ", goodsDetail='" + goodsDetail + '\'' +
                '}';
    }
}
