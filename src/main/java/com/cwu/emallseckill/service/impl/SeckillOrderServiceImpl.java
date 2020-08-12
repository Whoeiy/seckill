package com.cwu.emallseckill.service.impl;

import com.cwu.emallseckill.dao.SeckillOrderMapper;
import com.cwu.emallseckill.entity.SeckillOrder;
import com.cwu.emallseckill.entity.User;
import com.cwu.emallseckill.redis.RedisService;
import com.cwu.emallseckill.redis.SeckillKey;
import com.cwu.emallseckill.service.ISeckillOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

@Service
public class SeckillOrderServiceImpl implements ISeckillOrderService {

    @Autowired
    private RedisService redisService;

    @Autowired
    private SeckillOrderMapper seckillOrderMapper;


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
}
