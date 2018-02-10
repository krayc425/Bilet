package com.krayc.vo;

import com.krayc.model.OrderEventSeatEntity;

public class OrderEventSeatVO {

    private String name;
    private Integer price;

    public OrderEventSeatVO(OrderEventSeatEntity orderEventSeatEntity) {
        this.price = orderEventSeatEntity.getEventSeatByEsid().getPrice();

        Integer num = orderEventSeatEntity.getSeatNumber();
        if (num < 0) {
            name = "等待配票";
        } else {
            name = orderEventSeatEntity.getEventSeatByEsid().getSeat().getName() + " " + num + " 座";
        }

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

}
