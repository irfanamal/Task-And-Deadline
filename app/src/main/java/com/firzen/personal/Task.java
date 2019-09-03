package com.firzen.personal;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Task {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String day;
    private int date;
    private int monthNum;
    private int year;
    private String description;

    public Task(String name, String day, int date, int monthNum, int year, String description) {
        this.name = name;
        this.day = day;
        this.date = date;
        this.monthNum = monthNum;
        this.year = year;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDay() {
        return day;
    }

    public int getDate() {
        return date;
    }

    public int getMonthNum() {
        return monthNum;
    }

    public int getYear() {
        return year;
    }

    public String getDescription() {
        return description;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public void setMonthNum(int monthNum) {
        this.monthNum = monthNum;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
