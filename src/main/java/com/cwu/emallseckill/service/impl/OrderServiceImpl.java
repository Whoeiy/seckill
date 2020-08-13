package com.cwu.emallseckill.service.impl;

import com.cwu.emallseckill.dao.OrderInfoMapper;
import com.cwu.emallseckill.entity.OrderInfo;
import com.cwu.emallseckill.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements IOrderService {


    @Autowired
    private OrderInfoMapper orderInfoMapper;

    @Override
    public long addOrder(OrderInfo orderInfo) {
        return this.orderInfoMapper.insert(orderInfo);
    }

    @Override
    public OrderInfo getOrderInfo(long orderId) {
        return this.orderInfoMapper.selectByPrimaryKey(orderId);
    }
}
