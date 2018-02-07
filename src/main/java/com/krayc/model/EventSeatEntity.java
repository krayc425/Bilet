package com.krayc.model;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "EventSeat", schema = "Bilet")
public class EventSeatEntity {

    private int number;
    private int price;
    private int esid;
    private EventEntity event;
    private SeatEntity seat;
    private Collection<OrderEventSeatEntity> orderEventSeats;

    @Basic
    @Column(name = "number")
    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Basic
    @Column(name = "price")
    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Id
    @Column(name = "esid")
    public int getEsid() {
        return esid;
    }

    public void setEsid(int esid) {
        this.esid = esid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EventSeatEntity that = (EventSeatEntity) o;

        if (number != that.number) return false;
        if (price != that.price) return false;
        if (esid != that.esid) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = number;
        result = 31 * result + price;
        result = 31 * result + esid;
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "eid", referencedColumnName = "eid", nullable = false)
    public EventEntity getEvent() {
        return event;
    }

    public void setEvent(EventEntity event) {
        this.event = event;
    }

    @ManyToOne
    @JoinColumn(name = "sid", referencedColumnName = "sid", nullable = false)
    public SeatEntity getSeat() {
        return seat;
    }

    public void setSeat(SeatEntity seat) {
        this.seat = seat;
    }

    @OneToMany(mappedBy = "eventSeatByEsid")
    @Fetch(value = FetchMode.SUBSELECT)
    public Collection<OrderEventSeatEntity> getOrderEventSeats() {
        return orderEventSeats;
    }

    public void setOrderEventSeats(Collection<OrderEventSeatEntity> orderEventSeats) {
        this.orderEventSeats = orderEventSeats;
    }
}
