package com.example.feelsbook;

import java.util.Date;

public class Feeling {
    protected String mood;
    protected Date date;
    protected String comment;

    public Feeling(String mood){
        this.mood = mood;
        this.date = new Date();
    }
    public Feeling(String mood,String text) {
        this.mood = mood;
        this.date = new Date();
        this.comment = text;

    }
}
