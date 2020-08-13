package com.cwu.emallseckill.service.impl;

import com.cwu.emallseckill.bo.GoodsBo;
import com.cwu.emallseckill.dao.GoodsMapper;
import com.cwu.emallseckill.service.ISeckillGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeckillGoodsServiceImpl implements ISeckillGoodsService {

    @Autowired
    private GoodsMapper goodsMapper;

    @Override
    public List<GoodsBo> getSeckillGoodsList() {
        return this.goodsMapper.selectAllGoods();
    }

    @Override
    public GoodsBo getSeckillGoodsBoByGoodsId(long id) {
        return this.goodsMapper.getSeckillGoodsBoByGoodsId(id);
    }

    @Override
    public int reduceStock(long goodsId) {

        return 0;
    }

}
