package com.pucit.hostelhubupdated.Adapters;

import java.util.ArrayList;
import java.util.List;

public class Notification{
    private String name;
    private String date;
    private String detail;

    public Notification() {
    }

    public void setName(String n) {
        this.name = n;
    }

    public String getName() {
        return name;
    }

    public void setDate(String d) {
        this.date = d;
    }

    public String getDate() {
        return date;
    }

    public void setDetail(String det) {
        this.detail = det;
    }

    public String getDetail() {
        return detail;
    }

    Notification(String n, String d, String det) {
        name = n;
        date = d;
        detail = det;
    }


    public static List<Notification> getData() {
        List<Notification> data = new ArrayList<>();
        Notification i = new Notification("name1","date/time1","text1");
        data.add(i);

        i = new Notification("name2","date/time2","text2");
        data.add(i);

        i = new Notification("name3","date/time3","text3");
        data.add(i);

        i = new Notification("name4","date/time4","text4");
        data.add(i);

        i = new Notification("name5","date/time5","text5");
        data.add(i);

        i = new Notification("name6","date/time6","text6");
        data.add(i);

        i = new Notification("name7","date/time7","text7");
        data.add(i);

        i = new Notification("name8","date/time8","text8");
        data.add(i);

        i = new Notification("name9","date/time9","text9");
        data.add(i);

        i = new Notification("name10","date/time10","text10");
        data.add(i);

        i = new Notification("name11","date/time11","text11");
        data.add(i);

        i = new Notification("name12","date/time12","text12");
        data.add(i);

        i = new Notification("name13","date/time13","text13");
        data.add(i);

        i = new Notification("name14","date/time14","text14");
        data.add(i);

        i = new Notification("name15","date/time15","text15");
        data.add(i);

        i = new Notification("name16","date/time16","text16");
        data.add(i);


        return data;
    }
}

