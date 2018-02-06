package com.krayc.model;

import javax.persistence.*;

@Entity
@Table(name = "OrderEventSeat", schema = "Bilet")
public class OrderEventSeatEntity {

    private int oesid;
    private int seatNumber;
    private Integer isValid;
    private OrderEntity orderByOid;
    private EventSeatEntity eventSeatByEsid;

    @Id
    @Column(name = "oesid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getOesid() {
        return oesid;
    }

    public void setOesid(int oesid) {
        this.oesid = oesid;
    }

    @Basic
    @Column(name = "seatNumber")
    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    @Basic
    @Column(name = "isValid")
    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderEventSeatEntity that = (OrderEventSeatEntity) o;

        if (oesid != that.oesid) return false;
        if (seatNumber != that.seatNumber) return false;
        if (isValid != null ? !isValid.equals(that.isValid) : that.isValid != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = oesid;
        result = 31 * result + seatNumber;
        result = 31 * result + (isValid != null ? isValid.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "oid", referencedColumnName = "oid", nullable = false)
    public OrderEntity getOrderByOid() {
        return orderByOid;
    }

    public void setOrderByOid(OrderEntity orderByOid) {
        this.orderByOid = orderByOid;
    }

    @ManyToOne
    @JoinColumn(name = "esid", referencedColumnName = "esid", nullable = false)
    public EventSeatEntity getEventSeatByEsid() {
        return eventSeatByEsid;
    }

    public void setEventSeatByEsid(EventSeatEntity eventSeatByEsid) {
        this.eventSeatByEsid = eventSeatByEsid;
    }

}
