package com.cwu.emallseckill.dao;

import com.cwu.emallseckill.entity.OrderInfo;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderInfoMapper {

    /** 添加普通订单信息 **/
    int insert(OrderInfo record);

    OrderInfo selectByPrimaryKey(Long id);

    /** 添加秒杀订单信息 **/
    int insertSelective(OrderInfo record);


}
