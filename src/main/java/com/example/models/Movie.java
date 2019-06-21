package com.example.models;


//import org.bson.types.ObjectId;
//import org.springframework.data.annotation.Id;

import java.util.ArrayList;

public class Movie {
//    @Id
//    private ObjectId _id;
    private String url;
    private String name;
    private ArrayList<String> genre;
    private ArrayList<String> actor;
    private ArrayList<String> director;
    private String description;
    private String datePublished;
    private String duration;
    private String keywords;
    private String image;
    private Review review;
    private AggregateRating aggregateRating;
    private String trailer;

    public Movie() {}


    public Movie(String url, String name, ArrayList<String> genre, ArrayList<String> actor, ArrayList<String> director, String description, String datePublished, String duration, String keywords, String image, Review review, AggregateRating aggregateRating, String trailer) {
//        this._id = _id;
        this.url = url;
        this.name = name;
        this.genre = genre;
        this.actor = actor;
        this.director = director;
        this.description = description;
        this.datePublished = datePublished;
        this.duration = duration;
        this.keywords = keywords;
        this.image = image;
        this.review = review;
        this.aggregateRating = aggregateRating;
        this.trailer = trailer;
    }

    public String getUrl() {
        return url;
    }

    public String getName() {
        return name;
    }

    public ArrayList<String> getGenre() {
        return genre;
    }

    public String getDescription() {
        return description;
    }

    public String getDatePublished() {
        return datePublished;
    }

    public String getDuration() {
        return duration;
    }

    public String getKeywords() {
        return keywords;
    }

    public String getImage() {
        return image;
    }

    public Review getReview() {
        return review;
    }

    public String getTrailer() {
        return trailer;
    }

    public AggregateRating getAggregateRating() {
        return aggregateRating;
    }

    public ArrayList<String> getActor() {
        return actor;
    }

    public ArrayList<String> getDirector() {
        return director;
    }



    public void setUrl(String url) {
        this.url = url;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGenre(ArrayList<String> genre) {
        this.genre = genre;
    }

    public void setActor(ArrayList<String> actor) {
        this.actor = actor;
    }

    public void setDirector(ArrayList<String> director) {
        this.director = director;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDatePublished(String datePublished) {
        this.datePublished = datePublished;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setReview(Review review) {
        this.review = review;
    }

    public void setAggregateRating(AggregateRating aggregateRating) {
        this.aggregateRating = aggregateRating;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }

}
