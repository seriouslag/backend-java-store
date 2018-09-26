package com.chiefsretro.entities;

import javax.persistence.*;

@Entity
@Table(name = "products")
public class Rating {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int ratingId;
    private String ratingTitle;
    private String ratingMessage;
    private double ratingValue;
    private User user;

    public int getRatingId() {
        return ratingId;
    }

    public void setRatingId(int ratingId) {
        this.ratingId = ratingId;
    }

    public String getRatingTitle() {
        return ratingTitle;
    }

    public void setRatingTitle(String ratingTitle) {
        this.ratingTitle = ratingTitle;
    }

    public String getRatingMessage() {
        return ratingMessage;
    }

    public void setRatingMessage(String ratingMessage) {
        this.ratingMessage = ratingMessage;
    }

    public double getRatingValue() {
        return ratingValue;
    }

    public void setRatingValue(double ratingValue) {
        this.ratingValue = ratingValue;
    }
}
