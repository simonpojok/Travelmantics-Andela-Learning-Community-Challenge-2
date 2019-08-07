package com.example.travelmantics.Utilities;

import android.util.Log;

import java.io.Serializable;

public class TravelDeal implements Serializable {
    private String travelDealId;
    private String travelDealTitle;
    private String travelDealDescription;
    private String travelDealPrice;
    private String travelDealImageUrl;
    private String travelDealImageName;


    public TravelDeal() {}

    public TravelDeal(String travelDealId, String travelDealTitle, String travelDealdescription, String travelDealPrice, String travelDealImageUrl) {
        this.travelDealId = travelDealId;
        this.travelDealTitle = travelDealTitle;
        this.travelDealDescription = travelDealdescription;
        this.travelDealPrice = travelDealPrice;
        this.travelDealImageUrl = travelDealImageUrl;
    }

    public String getTravelDealId() {
        return travelDealId;
    }

    public void setTravelDealId(String travelDealId) {
        this.travelDealId = travelDealId;
    }

    public String getTravelDealTitle() {
        return travelDealTitle;
    }

    public void setTravelDealTitle(String travelDealTitle) {
        this.travelDealTitle = travelDealTitle;
    }

    public String getTravelDealDescription() {
        return travelDealDescription;
    }

    public void setTravelDealDescription(String travelDealDescription) {
        this.travelDealDescription = travelDealDescription;
    }

    public String getTravelDealPrice() {
        return travelDealPrice;
    }

    public void setTravelDealPrice(String travelDealPrice) {
        this.travelDealPrice = travelDealPrice;
    }

    public String getTravelDealImageUrl() {
        return travelDealImageUrl;
    }

    public void setTravelDealImageUrl(String travelDealImageUrl) {
        this.travelDealImageUrl = travelDealImageUrl;
    }

    public void setTravelDealImageName(String pictureName) {
        this.travelDealImageName = pictureName;
    }

    public String getTravelDealImageName(){
        Log.d(getClass().getSimpleName(), "<<<<<<<<<<< image link visted ");
        return travelDealImageName;
    }
}
