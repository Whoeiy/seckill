package com.cwu.emallseckill.dao;

import com.cwu.emallseckill.entity.SeckillOrder;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeckillOrderMapper {
    SeckillOrder selectByUserIdAndGoodsId(@Param("userId") long userId, @Param("goodsId") long goodsId);

    int insertSelective(SeckillOrder record);

    List<SeckillOrder> selectByUserId(@Param("userId") long userId);

    SeckillOrder selectByPrimaryKey(long orderId);
}
