package com.example.models;

//import org.bson.types.ObjectId;

public class AggregateRating {
    private Integer ratingCount;
    private String ratingValue;

    public AggregateRating(Integer ratingCount, String ratingValue) {
        this.ratingCount = ratingCount;
        this.ratingValue = ratingValue;
    }


    public Integer getRatingCount() {
        return ratingCount;
    }

    public String getRatingValue() {
        return ratingValue;
    }


    public void setRatingCount(Integer ratingCount) {
        this.ratingCount = ratingCount;
    }

    public void setRatingValue(String ratingValue) {
        this.ratingValue = ratingValue;
    }
}
