package com.krayc.vo;

import com.krayc.model.MemberBookEntity;
import com.krayc.model.OrderEntity;
import com.krayc.util.DateFormatter;

public class MemberBookVO {

    private String amount;
    private String type;
    private String time;
    private String orderId;

    public MemberBookVO(MemberBookEntity memberBookEntity) {
        this.amount = String.format("%.2f", memberBookEntity.getAmount());
        this.time = DateFormatter.getDateFormatter().stringFromDate(memberBookEntity.getTime());
        OrderEntity o = memberBookEntity.getOrder();
        if (o != null) {
            this.orderId = o.getOid() + "";
        } else {
            this.orderId = "无";
        }
        switch (memberBookEntity.getType()) {
            case 0:
                this.type = "线上购票";
                break;
            case 1:
                this.type = "现场购票";
                break;
            case 2:
                this.type = "线上退票";
                break;
            case 3:
                this.type = "线上充值";
                break;
            default:
                this.type = "未知";
                break;
        }
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

}
