package com.cwu.emallseckill;

import com.cwu.emallseckill.dao.GoodsMapper;
import com.cwu.emallseckill.dao.OrderInfoMapper;
import com.cwu.emallseckill.dao.SeckillOrderMapper;
import com.cwu.emallseckill.dao.UserMapper;
import com.cwu.emallseckill.entity.Goods;
import com.cwu.emallseckill.entity.OrderInfo;
import com.cwu.emallseckill.entity.SeckillOrder;
import com.cwu.emallseckill.param.LoginParam;
import com.cwu.emallseckill.result.Result;
import com.cwu.emallseckill.service.IUserService;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;

@SpringBootTest
@MapperScan("com.cwu.emallseckill.dao")
class EmallSeckillApplicationTests {

//    @Autowired
//    private UserMapper userMapper;

    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private SeckillOrderMapper seckillOrderMapper;

    @Autowired
    private OrderInfoMapper orderInfoMapper;

//    @Autowired
//    private IUserService userServiceImpl;

    @Test
    void contextLoads() {
    }


    @Test
    void testSeckillOrderByPrimaryKey(){
        System.out.println(this.seckillOrderMapper.selectByPrimaryKey(51L));
    }

    @Test
    void testSeckillOrderListByUserId(){
        System.out.println(this.seckillOrderMapper.selectByUserId(1410080408L));
    }

    @Test
    void testInsertSelective(){
        SeckillOrder order = new SeckillOrder();
        order.setUserId(1410080408L);
        order.setGoodsId(1L);
        order.setOrderId(2L);
        this.seckillOrderMapper.insertSelective(order);
    }

    @Test
    void testQueryOrderInfo(){
        System.out.println(this.orderInfoMapper.selectByPrimaryKey(48L));
    }

    @Test
    void testOrderInsert(){
        OrderInfo orderInfo = new OrderInfo();
        this.orderInfoMapper.insert(orderInfo);
    }

    @Test
    void testSeckillOrderByUserIdAndGoodsId(){
        System.out.println(this.seckillOrderMapper.selectByUserIdAndGoodsId(1410080408, 2));
    }

    @Test
    void testSeckillGoodsBo(){
        System.out.println(this.goodsMapper.getSeckillGoodsBoByGoodsId(1L));
    }

    @Test
    void testUpdateStock(){
        System.out.println(this.goodsMapper.updateStock(1L));
    }

    @Test
    void testInsert(){
        Goods goods = new Goods();
        goods.setCreateDate(new Date());
        goods.setGoodsDetail("戴尔电脑总部设在美国德克萨斯州奥丝登(Austin)，戴尔公司是世界排名第一的计算机系统公司、计算机产品及服务的首要提供商，其业务包括帮助客户建立自己的信息技术及互联网基础架构。戴尔公司成为市场领导者的根本原因是：通过直接向客户提供符合行业标准技术的电脑产品和服务，不断地致力于提供最佳的客户体验。戴尔公司目前在全球共有34,400个雇员，在过去的四个财季中，戴尔公司的总营业额达到318亿美元。");
        goods.setGoodsImg("https://bkimg.cdn.bcebos.com/pic/4d086e061d950a7bd78e30cd0bd162d9f2d3c904?x-bce-process=image/resize,m_lfit,w_268,limit_1/format,f_jpg");
        goods.setGoodsName("戴尔电脑");
        goods.setGoodsPrice(new BigDecimal(2899.00));
        goods.setGoodsStock(10);
        goods.setGoodsTitle("戴尔电脑成就3681英特尔酷睿");
        goods.setUpdateDate(new Date());
        this.goodsMapper.insert(goods);
    }

    @Test
    void testSelect(){
        System.out.println(this.goodsMapper.selectByPrimaryKey(6L));
    }

    @Test
    void testSelectAllGoods(){
        System.out.println(this.goodsMapper.selectAllGoods());
    }

    @Test
    void testUpdate(){
        Goods goods = new Goods();
        goods.setId(6L);
        goods.setCreateDate(new Date());
        goods.setGoodsDetail("戴尔电脑总部设在美国德克萨斯州奥丝登(Austin)，戴尔公司是世界排名第一的计算机系统公司、计算机产品及服务的首要提供商，其业务包括帮助客户建立自己的信息技术及互联网基础架构。戴尔公司成为市场领导者的根本原因是：通过直接向客户提供符合行业标准技术的电脑产品和服务，不断地致力于提供最佳的客户体验。戴尔公司目前在全球共有34,400个雇员，在过去的四个财季中，戴尔公司的总营业额达到318亿美元。");
        goods.setGoodsImg("https://bkimg.cdn.bcebos.com/pic/4d086e061d950a7bd78e30cd0bd162d9f2d3c904?x-bce-process=image/resize,m_lfit,w_268,limit_1/format,f_jpg");
        goods.setGoodsName("戴尔电脑");
        goods.setGoodsPrice(new BigDecimal(2899.00));
        goods.setGoodsStock(5);
        goods.setGoodsTitle("戴尔电脑成就3681英特尔酷睿");
        goods.setUpdateDate(new Date());
        this.goodsMapper.updateByPrimaryKey(goods);
    }

    @Test
    void testUpdateDetail(){
        Goods goods = new Goods();
        goods.setId(6L);
        goods.setCreateDate(new Date());
        goods.setGoodsDetail("戴尔电脑总部设在美国德克萨斯州奥丝登(Austin)，戴尔公司是世界排名第一的计算机系统公司、计算机产品及服务的首要提供商，其业务包括帮助客户建立自己的信息技术及互联网基础架构。戴尔公司成为市场领导者的根本原因是：通过直接向客户提供符合行业标准技术的电脑产品和服务，不断地致力于提供最佳的客户体验。");
        goods.setGoodsImg("https://bkimg.cdn.bcebos.com/pic/4d086e061d950a7bd78e30cd0bd162d9f2d3c904?x-bce-process=image/resize,m_lfit,w_268,limit_1/format,f_jpg");
        goods.setGoodsName("戴尔电脑");
        goods.setGoodsPrice(new BigDecimal(2899.00));
        goods.setGoodsStock(5);
        goods.setGoodsTitle("戴尔电脑成就3681英特尔酷睿");
        goods.setUpdateDate(new Date());
        System.out.println(this.goodsMapper.updateByPrimaryKeyWithBLOBs(goods));
    }


    @Test
    void testDelete(){
        this.goodsMapper.deleteByPrimaryKey(6L);
    }

//    @Test
//    void checkPhone() {
//        System.out.println(this.userMapper.checkPhone("18077200000"));
//    }
//
//    @Test
//    void selectByPhoneAndPassword() {
//        System.out.println(
//                this.userMapper.selectByPhoneAndPassword("18077200000", "123456")
//        );
//    }
//
//    @Test
//    void login() {
//        LoginParam param = new LoginParam();
//        param.setMobile("18077200000");
//        param.setPassword("123456");
//        Result result = this.userServiceImpl.login(param);
//        System.out.println("[Code]" + result.getCode());
//        System.out.println("[Msg]" + result.getMsg());
//        System.out.println("[Data]" + result.getData());
//    }
}