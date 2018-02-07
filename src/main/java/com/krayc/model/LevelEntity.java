package com.krayc.model;

import javax.persistence.*;

@Entity
@Table(name = "Level", schema = "Bilet")
public class LevelEntity {

    private int lid;
    private int minimumPoint;
    private String description;
    private double discount;

    @Id
    @Column(name = "lid")
    public int getLid() {
        return lid;
    }

    public void setLid(int lid) {
        this.lid = lid;
    }

    @Basic
    @Column(name = "minimumPoint")
    public int getMinimumPoint() {
        return minimumPoint;
    }

    public void setMinimumPoint(int minimumPoint) {
        this.minimumPoint = minimumPoint;
    }

    @Basic
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LevelEntity that = (LevelEntity) o;

        if (lid != that.lid) return false;
        if (minimumPoint != that.minimumPoint) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = lid;
        result = 31 * result + minimumPoint;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

    @Basic
    @Column(name = "discount")
    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

}
