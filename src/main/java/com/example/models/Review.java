package com.example.models;

//import org.bson.types.ObjectId;

import java.io.Serializable;

public class Review implements Serializable {
//    private ObjectId _id;
    private String authorName;
    private String dateCreated;
    private String inLanguage;
    private String reviewName;
    private String reviewBody;
    private String ratingValue;

    public Review() {}

    public Review(String authorName, String dateCreated, String reviewName, String reviewBody,String ratingValue)
    {
        this.authorName = authorName;
        this.dateCreated = dateCreated;
        this.reviewName = reviewName;
        this.reviewBody = reviewBody;
        this.ratingValue = ratingValue;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public void setReviewName(String reviewName) {
        this.reviewName = reviewName;
    }

    public void setReviewBody(String reviewBody) {
        this.reviewBody = reviewBody;
    }

    public void setRatingValue(String ratingValue) {
        this.ratingValue = ratingValue;
    }


    public String getAuthorName() {
        return this.authorName;
    }
    public String getDateCreated() {
        return this.dateCreated;
    }
    public String getReviewName() {
        return this.reviewName;
    }
    public String getReviewBody() {
        return this.reviewBody;
    }
    public String getRatingValue() {
        return this.ratingValue;
    }


}
