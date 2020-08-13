package com.cwu.emallseckill.service.impl;

import com.cwu.emallseckill.bo.GoodsBo;
import com.cwu.emallseckill.consts.Const;
import com.cwu.emallseckill.dao.GoodsMapper;
import com.cwu.emallseckill.dao.OrderInfoMapper;
import com.cwu.emallseckill.dao.SeckillOrderMapper;
import com.cwu.emallseckill.entity.OrderInfo;
import com.cwu.emallseckill.entity.SeckillOrder;
import com.cwu.emallseckill.entity.User;
import com.cwu.emallseckill.redis.RedisService;
import com.cwu.emallseckill.redis.SeckillKey;
import com.cwu.emallseckill.service.ISeckillOrderService;
import com.cwu.emallseckill.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.UUID;

@Service
public class SeckillOrderServiceImpl implements ISeckillOrderService {

    @Autowired
    private RedisService redisService;

    @Autowired
    private SeckillOrderMapper seckillOrderMapper;

    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private OrderInfoMapper orderInfoMapper;


    @Override
    public boolean checkPath(User user, Long goodsId, String path) {
        if(ObjectUtils.isEmpty(user) || StringUtils.isEmpty(path)){
            return false;
        }

        String pathOld = this.redisService.get(
                SeckillKey.getSeckillPath,
                "" + user.getId() + "_" + goodsId,
                String.class
        );
        return path.equals(pathOld);
    }

    @Override
    public SeckillOrder getSeckillOrderByUserIdAndGoodsId(Long userId, Long goodsId) {
        return this.seckillOrderMapper.selectByUserIdAndGoodsId(userId, goodsId);
    }

    @Transactional
    @Override
    public OrderInfo insert(User user, GoodsBo goodsBo) {
        // 秒杀商品库存减一
        int success = this.goodsMapper.updateStock(goodsBo.getId());
        if(success == 1){   // 更新成功
            OrderInfo orderinfo = new OrderInfo();
            orderinfo.setCreatDate(new Date());
            orderinfo.setAddrId(0L);
            orderinfo.setGoodsCount(1);
            orderinfo.setGoodsId(goodsBo.getId());
            orderinfo.setGoodsName(goodsBo.getGoodsName());
            orderinfo.setGoodsPrice(goodsBo.getGoodsPrice());
            orderinfo.setOrderChannel(1);
            orderinfo.setStatus(0);
            orderinfo.setUserId((long)user.getId());

            // 添加信息到订单
            long orderId = this.orderInfoMapper.insert(orderinfo);
            SeckillOrder seckillOrder = new SeckillOrder();
            seckillOrder.setGoodsId(goodsBo.getId());
            seckillOrder.setOrderId(orderinfo.getId());
            seckillOrder.setUserId((long)user.getId());

            // 秒杀表中添加数据
            this.seckillOrderMapper.insertSelective(seckillOrder);
            return orderinfo;
        }else{      // 秒杀商品结束
            setGoodsOver(goodsBo.getId());
            return null;
        }
    }

    @Override
    public String createSeckillPath(User user, long goodsId) {
        if(ObjectUtils.isEmpty(user) || goodsId <= 0){
            return null;
        }
        String str = MD5Util.md5(UUID.randomUUID() + "123456");
        this.redisService.set(SeckillKey.getSeckillPath, "" + user.getId() + "_" + goodsId,
                str, Const.RedisCacheExtime.GOODS_ID);
        return str;
    }

    private void setGoodsOver(Long goodsId){
        this.redisService.set(SeckillKey.isGoodsOver, "" + goodsId, true,
                Const.RedisCacheExtime.GOODS_ID);
    }
}
