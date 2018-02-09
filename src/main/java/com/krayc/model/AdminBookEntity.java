package com.krayc.model;

import javax.persistence.*;

@Entity
@Table(name = "AdminBook", schema = "Bilet")
public class AdminBookEntity {

    private int abid;
    private double amount;
    private byte isConfirmed;
    private VenueEntity venue;
    private EventEntity event;

    @Id
    @Column(name = "abid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getAbid() {
        return abid;
    }

    public void setAbid(int abid) {
        this.abid = abid;
    }

    @Basic
    @Column(name = "amount")
    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Basic
    @Column(name = "isConfirmed")
    public byte getIsConfirmed() {
        return isConfirmed;
    }

    public void setIsConfirmed(byte isConfirmed) {
        this.isConfirmed = isConfirmed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AdminBookEntity that = (AdminBookEntity) o;

        if (abid != that.abid) return false;
        if (Double.compare(that.amount, amount) != 0) return false;
        if (isConfirmed != that.isConfirmed) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = abid;
        temp = Double.doubleToLongBits(amount);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (int) isConfirmed;
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "vid", referencedColumnName = "vid", nullable = false)
    public VenueEntity getVenue() {
        return venue;
    }

    public void setVenue(VenueEntity venue) {
        this.venue = venue;
    }

    @OneToOne
    @JoinColumn(name = "eid", referencedColumnName = "eid", nullable = false)
    public EventEntity getEvent() {
        return event;
    }

    public void setEvent(EventEntity event) {
        this.event = event;
    }

}
