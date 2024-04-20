package com.example.realestateapp.Modeles;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.HashMap;

public class Asset {
    private String assetID;
    private String city = "";
    private String street = "";
    private int streetNumber;
    private int floor;
    private RentOrSale rentOrSale = RentOrSale.NA;
    private String moreInfo = "";
    public enum RentOrSale {
        NA,
        RENT,
        SALE
    }
    private long price;
    private String ownerFirstName;
    private String ownerLastName;
    private String ownerPhoneNumber;
    public final static int MAX_LINES_COLLAPSED = 1;
    private boolean isCollapsed = true;
    private MeetingList allMeetings;

    public Asset() {
    }
    public String getOwnerFirstName() {
        return ownerFirstName;
    }

    public Asset setOwnerFirstName(String ownerFirstName) {
        this.ownerFirstName = ownerFirstName;
        return this;
    }

    public String getOwnerLastName() {
        return ownerLastName;
    }

    public Asset setOwnerLastName(String ownreLastName) {
        this.ownerLastName = ownreLastName;
        return this;
    }
    public String getOwnerPhoneNumber() {
        return ownerPhoneNumber;
    }

    public Asset setOwnerPhoneNumber(String ownerPhoneNumber) {
        this.ownerPhoneNumber = ownerPhoneNumber;
        return this;
    }
    public String getMoreInfo() {
        return moreInfo;
    }

    public Asset setMoreInfo(String moreInfo) {
        this.moreInfo = moreInfo;
        return this;
    }

    public String getAssetid() {
        return assetID;
    }

    public Asset setAssetID(String assetID) {
        this.assetID = assetID;
        return this;
    }

    public RentOrSale getRentOrSale() {
        return rentOrSale;
    }

    public Asset setRentOrSale(RentOrSale rentOrSale) {
        this.rentOrSale = rentOrSale;
        return this;
    }
    public String getCity() {
        return city;
    }

    public Asset setCity(String city) {
        this.city = city;
        return this;
    }

    public String getStreet() {
        return street;
    }

    public Asset setStreet(String street) {
        this.street = street;
        return this;
    }

    public int getStreetNumber() {
        return streetNumber;
    }

    public Asset setStreetNumber(int streetNumber) {
        this.streetNumber = streetNumber;
        return this;
    }

    public int getFloor() {
        return floor;
    }

    public Asset setFloor(int floor) {
        this.floor = floor;
        return this;
    }

    public long getPrice() {
        return price;
    }

    public Asset setPrice(long price) {
        this.price = price;
        return this;
    }
    public boolean isCollapsed() {
        return isCollapsed;
    }

    public Asset setCollapsed(boolean collapsed) {
        isCollapsed = collapsed;
        return this;
    }

    public MeetingList getAllMeetings() {
        return allMeetings;
    }

    public Asset setAllMeetings(MeetingList allMeetings) {
        this.allMeetings = allMeetings;
        return this;
    }

    @NonNull
    public String toString() {
        return "Asset{" +
                "assetID='" + assetID + '\'' +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", streetNumber=" + streetNumber +
                ", floor=" + floor +
                ", rentOrSale=" + rentOrSale +
                ", moreInfo='" + moreInfo + '\'' +
                ", price=" + price +
                ", ownerFirstName='" + ownerFirstName + '\'' +
                ", ownerLastName='" + ownerLastName + '\'' +
                ", ownerPhoneNumber='" + ownerPhoneNumber + '\'' +
                ", isCollapsed=" + isCollapsed +
                '}';
    }
    public String ownerDetailsToString(){
        return "Owner details" +"\n"+
                "Name: "+ownerFirstName+" "+ownerLastName +"\n"
                +"Phone: "+ownerPhoneNumber;
    }
}
