package com.cwu.emallseckill.dao;

import com.cwu.emallseckill.bo.GoodsBo;
import com.cwu.emallseckill.entity.Goods;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GoodsMapper {
    /** 添加商品 **/
    int insert(Goods goods);

    /** 根据id删除商品 **/
    int deleteByPrimaryKey(Long id);

    /** 根据id修改商品基本信息 **/
    int updateByPrimaryKey(Goods goods);

    /** 根据id修改商品详细信息 **/
    int updateByPrimaryKeyWithBLOBs(Goods goods);

    /** 根据id查询商品 **/
    Goods selectByPrimaryKey(Long id);

    /** 查询所有参与秒杀活动的商品 **/
    List<GoodsBo> selectAllGoods();

    /** 查询秒杀商品详情 **/
    GoodsBo getSeckillGoodsBoByGoodsId(Long id);

    /** 减少商品库存数量 **/
    int updateStock(long goodsId);

}
