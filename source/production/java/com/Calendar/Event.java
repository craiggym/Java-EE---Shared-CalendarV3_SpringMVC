package com.Calendar;


import java.util.Date;

/**
 * Created by BHARATH on 2/26/2016.
 */
public class Event{
    private Integer id;
    private String eventName;
    private Date eventDate;
    private String eventDescription;
    private String username;
    private String eventAuthor;
    private int monthWeight;
    private int dateWeight;
    private String monthWeightS;
    private String dateWeightS;

    private int yearWeight;
    private String yearWeightS;

    public Event(Integer id, String name, Date date, String desc, String uname, String author) {
        this.id = id;
        this.eventName = name;
        this.eventDate = date;
        this.eventDescription = desc;
        this.username = uname;
        this.eventAuthor = author;
    }

    //============== Getters ====================================
    public Date getEventDate() {
        return this.eventDate;
    }

    public String getEventName() {
        return eventName;
    }

    public String getDescription() {
        return eventDescription;
    }
    public String getUsername() {
        return username;
    }
    public int getMonthWeight() {return monthWeight;}
    public int getDateWeight() {return dateWeight;}
    public String getMonthWeightS() {return monthWeightS;}
    public String getDateWeightS() {return dateWeightS;}
    public int getYearWeight() {return yearWeight;}
    public String getYearWeightS() {return yearWeightS;}

    //============ Setters ==========================================
    public void setEventName(String name) {
        this.eventName = name;
    }

    public void setEventDate(Date date) {
        this.eventDate = date;
    }

    public void setDescription(String body) {
        this.eventDescription = body;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public void setMonthWeight(int m){
        this.monthWeight = m;
    }
    public void setDateWeight(int d){
        this.dateWeight = d;
    }
    public void setMonthWeightS(String m){
        this.monthWeightS = m;
    }
    public void setDateWeightS(String d){
        this.dateWeightS = d;
    }

    public void setYearWeight(int y){
        this.yearWeight = y;
    }
    public void setYearWeightS(String y){
        this.yearWeightS = y;
    }

    public String getEventAuthor() {
        return eventAuthor;
    }

    public void setEventAuthor(String eventAuthor) {
        this.eventAuthor = eventAuthor;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
