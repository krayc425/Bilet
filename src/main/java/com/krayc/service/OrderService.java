package com.krayc.service;

import com.krayc.model.EventEntity;
import com.krayc.model.MemberEntity;
import com.krayc.model.OrderEntity;
import com.krayc.model.OrderEventSeatEntity;

import java.util.List;

public interface OrderService {

    public OrderEntity findByOid(Integer oid);

    public void createOrder(OrderEntity orderEntity, List<OrderEventSeatEntity> eventSeatEntityList);

    public Boolean payOrder(OrderEntity orderEntity, MemberEntity memberEntity);

    public void cancelOrder(OrderEntity orderEntity);

    public void refundOrder(OrderEntity orderEntity, MemberEntity memberEntity);

    public void confirmOrder(OrderEntity orderEntity);

    public List<OrderEntity> findOrderByEvent(EventEntity eventEntity);

}
