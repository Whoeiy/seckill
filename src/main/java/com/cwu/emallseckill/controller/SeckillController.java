package com.cwu.emallseckill.controller;


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
import com.cwu.emallseckill.service.impl.SeckillGoodsServiceImpl;
import com.cwu.emallseckill.util.CookieUtil;
import com.sun.org.apache.xpath.internal.operations.Bool;
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
    public Result<Integer> list(Model mode,
                                @RequestParam("goodsId") Long goodsId,
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

    /** 生成随机路径，用于隐藏秒杀路径使用 **/
    @RequestMapping("/path")
    @ResponseBody
    public Result<String> getSeckillPath(@RequestParam("goodsId") long goodsId, HttpServletRequest request){
        String loginToken = CookieUtil.readLoginToken(request);
        User user = this.redisService.get(UserKey.getByName, loginToken, User.class);
        if(ObjectUtils.isEmpty(user)){
            return Result.error(CodeMsg.USER_NO_LOGIN);
        }

        String path = this.seckillOrderService.createSeckillPath(user, goodsId);
        return Result.success(path);
    }
}
