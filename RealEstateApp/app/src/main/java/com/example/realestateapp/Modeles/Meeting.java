package com.example.realestateapp.Modeles;

public class Meeting {
    private String date;
    private String info;

    public Meeting() {
    }
    public Meeting setDate(String date) {
        this.date = date;
        return this;
    }

    public Meeting setInfo(String info) {
        this.info = info;
        return this;
    }
    public String getDate() {
        return date;
    }

    public String getInfo() {
        return info;
    }

    @Override
    public String toString() {
        return "Meeting{" +
                "date='" + date + '\'' +
                ", info='" + info + '\'' +
                '}';
    }
}
