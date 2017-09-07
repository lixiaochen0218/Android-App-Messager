package com.example.alex.reminder1;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Created by Alex on 2017/3/20.
 */

public class Message implements Serializable{
    private int id;
    private String title;
    private String text;
    private String time;
    private  int star=3;
    private Calendar calendar;
    //private String date;

    public Message(int id,String title,String text,int star){
        this.id=id;
        this.title=title;
        this.text=text;
        this.star=star;
        this.calendar=Calendar.getInstance();
    }

    public void setText(String text){
        this.text=text;
    }
    public String getText(){
        return text;
    }
    public void setTitle(String title){
        this.title=title;
    }
    public String getTitle(){
        return title;
    }
    public void setStar(int star){
        this.star=star;
    }
    public int getStar(){
        return star;
    }
    public void setCalendar(Calendar calendar){
        this.calendar=calendar;
    }
    public Calendar getCalendar(){
        return calendar;
    }
    public int getId(){return id;}
    public void updateTime(){this.calendar=Calendar.getInstance();}
}
