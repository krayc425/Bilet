package com.krayc.vo;

import com.krayc.model.EventEntity;
import com.krayc.model.OrderEntity;
import com.krayc.util.DateFormatter;

import java.sql.Date;

public class OrderVO {

    private int oid;
    private String orderTime;
    private String status;
    private EventEntity eventByEid;
    private int seatNumber;

    public int getOid() {
        return oid;
    }

    public void setOid(int oid) {
        this.oid = oid;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public EventEntity getEventByEid() {
        return eventByEid;
    }

    public void setEventByEid(EventEntity eventByEid) {
        this.eventByEid = eventByEid;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    public OrderVO(int oid, String orderTime, String status, EventEntity eventByEid, int seatNumber) {
        this.oid = oid;
        this.orderTime = orderTime;
        this.status = status;
        this.eventByEid = eventByEid;
        this.seatNumber = seatNumber;
    }

    public OrderVO(OrderEntity orderEntity) {
        Date date = new Date(orderEntity.getOrderTime().getTime());
        String status = "";
        switch (orderEntity.getStatus()) {
            case 0:
                status = "等待付款";
                break;
            case 1:
                status = "等待演出";
                break;
            case 2:
                status = "已取消";
                break;
            case 3:
                status = "已退款";
                break;
            default:
                break;
        }
        this.oid = orderEntity.getOid();
        this.status = status;
        this.orderTime = DateFormatter.getDateFormatter().stringFromDate(date);
        this.eventByEid = orderEntity.getEventByEid();
        this.seatNumber = orderEntity.getOrderEventSeats().size();
    }

}
