package com.krayc.model;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;

@Entity
@Table(name = "Event", schema = "Bilet")
public class EventEntity {

    private int eid;
    private String name;
    private String description;
    private Timestamp time;
    private VenueEntity venueId;
    private EventTypeEntity eventTypeEntity;
    private Collection<EventSeatEntity> eventSeats;
    private Collection<OrderEntity> orders;
    private VenueBookEntity venueBook;
    private AdminBookEntity adminBook;

    @Id
    @Column(name = "eid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getEid() {
        return eid;
    }

    public void setEid(int eid) {
        this.eid = eid;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "time")
    public Timestamp getTime() {
        return time;
    }

    public void setTime(String timeString) {
        this.time = Timestamp.valueOf(timeString);
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EventEntity that = (EventEntity) o;

        if (eid != that.eid) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (time != null ? !time.equals(that.time) : that.time != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = eid;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (time != null ? time.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "type", referencedColumnName = "etid", nullable = false)
    public EventTypeEntity getEventTypeEntity() {
        return eventTypeEntity;
    }

    public void setEventTypeEntity(EventTypeEntity eventTypeEntity) {
        this.eventTypeEntity = eventTypeEntity;
    }

    @ManyToOne
    @JoinColumn(name = "vid", referencedColumnName = "vid", nullable = false)
    public VenueEntity getVenueId() {
        return venueId;
    }

    public void setVenueId(VenueEntity venueId) {
        this.venueId = venueId;
    }

    @OneToMany(mappedBy = "event")
    @Fetch(value = FetchMode.SUBSELECT)
    public Collection<EventSeatEntity> getEventSeats() {
        return eventSeats;
    }

    public void setEventSeats(Collection<EventSeatEntity> eventSeats) {
        this.eventSeats = eventSeats;
    }

    @OneToMany(mappedBy = "eventByEid")
    @Fetch(value = FetchMode.SUBSELECT)
    public Collection<OrderEntity> getOrders() {
        return orders;
    }

    public void setOrders(Collection<OrderEntity> orders) {
        this.orders = orders;
    }

    @OneToOne(mappedBy = "event")
    public VenueBookEntity getVenueBook() {
        return venueBook;
    }

    public void setVenueBook(VenueBookEntity venueBook) {
        this.venueBook = venueBook;
    }

    @OneToOne(mappedBy = "event")
    public AdminBookEntity getAdminBook() {
        return adminBook;
    }

    public void setAdminBook(AdminBookEntity adminBook) {
        this.adminBook = adminBook;
    }

}
