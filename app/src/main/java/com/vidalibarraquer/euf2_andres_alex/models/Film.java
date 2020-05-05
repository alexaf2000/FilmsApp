package com.vidalibarraquer.euf2_andres_alex.models;

import java.net.URL;

public class Film {
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
