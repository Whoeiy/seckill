package com.cwu.emallseckill.controller;


import com.cwu.emallseckill.annotations.AccessLimit;
import com.cwu.emallseckill.bo.GoodsBo;
import com.cwu.emallseckill.consts.Const;
import com.cwu.emallseckill.entity.OrderInfo;
import com.cwu.emallseckill.entity.SeckillOrder;
import com.cwu.emallseckill.entity.User;
import com.cwu.emallseckill.redis.GoodsKey;
import com.cwu.emallseckill.redis.RedisService;
import com.cwu.emallseckill.redis.UserKey;
import com.cwu.emallseckill.result.CodeMsg;
import com.cwu.emallseckill.result.Result;
import com.cwu.emallseckill.service.ISeckillGoodsService;
import com.cwu.emallseckill.service.ISeckillOrderService;
import com.cwu.emallseckill.util.CookieUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/seckill")
public class SeckillController implements InitializingBean {

    private Logger logger = LoggerFactory.getLogger(SeckillOrder.class);

    @Autowired
    private RedisService redisService;

    @Autowired
    private ISeckillGoodsService seckillGoodsService;

    @Autowired
    private ISeckillOrderService seckillOrderService;

    private Map<Long, Boolean> localOverMap = new HashMap<>();

    /** 初始化：秒杀商品的列表，并把秒杀商品数量存放到redis其设置缓存的有效时间 **/
    @Override
    public void afterPropertiesSet() throws Exception {
        List<GoodsBo> goodsBoList = this.seckillGoodsService.getSeckillGoodsList();
        if (ObjectUtils.isEmpty(goodsBoList)){
            return ;
        }
        for(GoodsBo goods : goodsBoList){
            this.redisService.set(GoodsKey.getSeckillGoodsStock, "" + goods.getId(),
                    goods.getStockCount(), Const.RedisCacheExtime.GOODS_LIST);
            localOverMap.put(goods.getId(), false);
        }
    }

    /** 隐藏秒杀路径后的请求地址 **/
    @RequestMapping(value = "/{path}/seckill", method = RequestMethod.POST)
    @ResponseBody
    public Result<Integer> list(Model model,
                                @RequestParam("goodsId") long goodsId,
                                @PathVariable("path") String path,
                                HttpServletRequest request){
        String loginTokin = CookieUtil.readLoginToken(request);
        User user = this.redisService.get(UserKey.getByName, loginTokin, User.class);
        if(ObjectUtils.isEmpty(user)){
            return Result.error(CodeMsg.USER_NO_LOGIN);
        }

        // 验证path
        boolean check = this.seckillOrderService.checkPath(user, goodsId, path);
        if(!check){     // 两个路径不一致
            return Result.error(CodeMsg.REQUEST_ILLEGAL);
        }
        // 内存标记，减少redis访问
        boolean over = localOverMap.get(goodsId);
        if (over){
            return Result.error(CodeMsg.SECKILL_OVER);
        }
        // 数量减少,即库存减少
        Long stock = this.redisService.decr(GoodsKey.getSeckillGoodsStock, "" + goodsId);
        if(stock < 0){  // 商品已经秒杀完了
            localOverMap.put(goodsId, true);
            return  Result.error(CodeMsg.SECKILL_OVER);
        }

        // 判断是否已经秒杀到了
        SeckillOrder order = this.seckillOrderService.getSeckillOrderByUserIdAndGoodsId(user.getId(), goodsId);
        if(!ObjectUtils.isEmpty(order)){
            return Result.error(CodeMsg.REPEATE_SECKILL);
        }

        // 还未下单，减库存，下订单，写入秒杀订单
        GoodsBo goodsBo = this.seckillGoodsService.getSeckillGoodsBoByGoodsId(goodsId);
        OrderInfo orderInfo = this.seckillOrderService.insert(user, goodsBo);

        return Result.success(0);
    }

    /** 生成随机路径，用于隐藏秒杀路径使用
     * 自定义一个注解AccessLimit seconds: 请求失效时间， maxCount: 失效时间内最大请求数， needLogin: 是否需要登录 **/
    @AccessLimit(seconds = 5, maxCount = 5, needLogin = true)
    @RequestMapping(value = "/path", method = RequestMethod.GET)
    @ResponseBody
    public Result<String> getSeckillPath(@RequestParam("goodsId") long goodsId, HttpServletRequest request){
        String loginToken = CookieUtil.readLoginToken(request);
        User user = this.redisService.get(UserKey.getByName, loginToken, User.class);
        if(ObjectUtils.isEmpty(user)){
            return Result.error(CodeMsg.USER_NO_LOGIN);
        }

        String path = this.seckillOrderService.createSeckillPath(user, goodsId);
        logger.info(path);

        return Result.success(path);
    }

    /** 客户端轮询查看是否下单成功
     * orderId: 成功
     * -1: 秒杀失败
     * 0: 排队中
     **/

    @RequestMapping(value = "/result", method = RequestMethod.GET)
    @ResponseBody
    public Result<Long> seckillResult(@RequestParam("goodsId") long goodsId, HttpServletRequest request){
        String loginToken = CookieUtil.readLoginToken(request);
        User user = this.redisService.get(UserKey.getByName, loginToken, User.class);
        if(ObjectUtils.isEmpty(user)){
            return Result.error(CodeMsg.USER_NO_LOGIN);
        }

        long result = this.seckillOrderService.getSeckillResult((long) user.getId(), goodsId);
        return Result.success(result);
    }

}
