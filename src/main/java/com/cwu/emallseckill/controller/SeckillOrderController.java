package com.cwu.emallseckill.controller;

import com.cwu.emallseckill.bo.GoodsBo;
import com.cwu.emallseckill.entity.OrderInfo;
import com.cwu.emallseckill.entity.User;
import com.cwu.emallseckill.redis.RedisService;
import com.cwu.emallseckill.redis.UserKey;
import com.cwu.emallseckill.result.CodeMsg;
import com.cwu.emallseckill.result.Result;
import com.cwu.emallseckill.service.ISeckillGoodsService;
import com.cwu.emallseckill.service.ISeckillOrderService;
import com.cwu.emallseckill.util.CookieUtil;
import com.cwu.emallseckill.vo.OrderDetailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/order")
public class SeckillOrderController {
    @Autowired
    private RedisService redisService;

    @Autowired
    private ISeckillOrderService seckillOrderService;

    @Autowired
    private ISeckillGoodsService seckillGoodsService;

    @RequestMapping("/list")
    @ResponseBody
    public Result<List<OrderDetailVo>> list(HttpServletRequest request){
        String loginToken = CookieUtil.readLoginToken(request);
        User user = this.redisService.get(UserKey.getByName, loginToken, User.class);
        if(ObjectUtils.isEmpty(user)){
            return Result.error(CodeMsg.USER_NO_LOGIN);
        }

        List<OrderInfo> orderInfoList = this.seckillOrderService.getOrderList(user);
        if(ObjectUtils.isEmpty(orderInfoList)){
            return Result.error(CodeMsg.ORDER_NOT_EXIST);
        }

        List<OrderDetailVo> orderDetailVoList = new ArrayList<>();
        for(OrderInfo order : orderInfoList){
            long goodsId = order.getGoodsId();
            GoodsBo goods = this.seckillGoodsService.getSeckillGoodsBoByGoodsId(goodsId);
            OrderDetailVo vo = new OrderDetailVo();
            // 获取日期格式器
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String format_1 = formatter.format(order.getCreatDate());
            goods.setCreateDateStr(format_1);

            vo.setOrder(order);
            vo.setGoods(goods);

            orderDetailVoList.add(vo);
        }

        return Result.success(orderDetailVoList);
    }

    @RequestMapping("/detail")
    @ResponseBody
    public Result<OrderDetailVo> orderDetailInfo(Model model,
                                                 @RequestParam("orderId") long orderId,
                                                 HttpServletRequest request){
        String loginToken = CookieUtil.readLoginToken(request);
        User user = this.redisService.get(UserKey.getByName, loginToken, User.class);
        if(ObjectUtils.isEmpty(user)){
            return Result.error(CodeMsg.USER_NO_LOGIN);
        }

        OrderInfo order = this.seckillOrderService.getOrderInfo(orderId);
        if(ObjectUtils.isEmpty(orderId)){
            return Result.error(CodeMsg.ORDER_NOT_EXIST);
        }

        long goodsId = order.getGoodsId();
        GoodsBo goods = this.seckillGoodsService.getSeckillGoodsBoByGoodsId(goodsId);
        OrderDetailVo vo = new OrderDetailVo();
        // 日期转换
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format_1 = formatter.format(goods.getCreateDate());
        vo.setOrder(order);
        vo.setGoods(goods);

        return Result.success(vo);
    }
}














