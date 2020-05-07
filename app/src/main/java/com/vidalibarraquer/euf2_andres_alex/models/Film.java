package com.vidalibarraquer.euf2_andres_alex.models;

import java.net.URL;
import java.util.UUID;

public class Film {

    String id;
    String title;
    String genre;
    int duration; // In minutes
    int puntuation; // From 0 to 10
    URL cover; // Film cover as URL

    public Film(String title, String genre, int duration, int puntuation, URL cover) {
        this.title = title;
        this.genre = genre;
        this.duration = duration;
        this.puntuation = puntuation;
        this.cover = cover;

        // This will generate a random ID for our film
        this.id = UUID.randomUUID().toString();
    }

    public Film() {
        this.id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getPuntuation() {
        return puntuation;
    }

    public void setPuntuation(int puntuation) {
        this.puntuation = puntuation;
    }

    public URL getCover() {
        return cover;
    }

    public void setCover(URL cover) {
        this.cover = cover;
    }
}
