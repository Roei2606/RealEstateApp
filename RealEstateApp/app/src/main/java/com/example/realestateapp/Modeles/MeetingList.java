package com.example.realestateapp.Modeles;

import java.util.ArrayList;

public class MeetingList {
    private ArrayList<Meeting> allMeetings;

    public MeetingList() {
    }

    public ArrayList<Meeting> getAllMeetings() {
        return allMeetings;
    }

    public MeetingList setAllMeetings(ArrayList<Meeting> allMeetings) {
        this.allMeetings = allMeetings;
        return this;
    }

    @Override
    public String toString() {
        return "MeetingList{" +
                "allMeetings=" + allMeetings +
                '}';
    }
}
