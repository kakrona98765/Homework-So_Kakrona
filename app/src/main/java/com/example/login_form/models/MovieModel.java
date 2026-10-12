package com.example.login_form.models;
public class MovieModel {
    private double rating;
    private String type;
    private String title;
    private int year;
    private int duration;
    private String ageRating;
    private String genre;
    private int imageResId;

    public MovieModel(double rating,
                      String type,
                      String title,
                      int year,
                      int duration,
                      String ageRating,
                      String genre,
                      int imageResId) {
        this.rating = rating;
        this.type = type;
        this.title = title;
        this.year = year;
        this.duration = duration;
        this.ageRating = ageRating;
        this.genre = genre;
        this.imageResId = imageResId;
    }

    public double getRating() { return rating; }
    public String getType() { return type; }
    public String getTitle() { return title; }
    public int getYear() { return year; }
    public int getDuration() { return duration; }
    public String getAgeRating() { return ageRating; }
    public String getGenre() { return genre; }
    public int getImageResId() { return imageResId; }
}
