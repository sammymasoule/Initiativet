package com.example.jespe.initiativiet;

import android.support.annotation.NonNull;

/**
 * Created by Gustav Petersen on 13-01-2018.
 */

public class CommentMade implements Comparable {

    private String content, date, author;

    CommentMade(String content, String date, String author){

        this.content = content;
        this.author = author;
        this.date = date;

    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public int compareTo(@NonNull Object o) {

        //"HH:mm - dd/MM/yyyy"
        String thisDate = this.getDate();
        CommentMade that = (CommentMade) o;
        String thatDate = that.getDate();

        //Initialize string variables
        String year1 = thisDate.substring(14,18);
        String month1 = thisDate.substring(11,13);
        String day1 = thisDate.substring(8,10);
        String min1 = thisDate.substring(3,5);
        String hour1 = thisDate.substring(0,2);

        String year2 = thatDate.substring(14,18);
        String month2 = thatDate.substring(11,13);
        String day2 = thatDate.substring(8,10);
        String min2 = thatDate.substring(3,5);
        String hour2 = thatDate.substring(0,2);

        //Convert string to int
        int year_1 = Integer.parseInt(year1);
        int month_1 = Integer.parseInt(month1);
        int day_1 = Integer.parseInt(day1);
        int min_1 = Integer.parseInt(min1);
        int hour_1 = Integer.parseInt(hour1);

        int year_2 = Integer.parseInt(year2);
        int month_2 = Integer.parseInt(month2);
        int day_2 = Integer.parseInt(day2);
        int min_2 = Integer.parseInt(min2);
        int hour_2 = Integer.parseInt(hour2);

        //Compare integers
        //Years
        if (year_1 > year_2)
            return 1;
        else if (year_2 > year_1)
            return -1;

        //Months
        if (month_1 > month_2)
            return 1; //this.getDate().compareTo(that.getDate());
        else if (month_2 > month_1)
            return -1; // that.getDate().compareTo(this.getDate());

        //Days
        if (day_1 > day_2)
            return 1; //this.compareTo(that);
        else if (day_2 > day_1)
            return -1; //that.compareTo(this);

        //Hours
        if (hour_1 > hour_2)
            return 1; //this.compareTo(that);
        else if (hour_2 > hour_1)
            return -1; //that.compareTo(this);

        //Minutes
        if (min_1 > min_2)
            return 1; //this.compareTo(that);
        else if (min_2 > min_1)
            return -1; //that.compareTo(this);

        return 0;
    }
}