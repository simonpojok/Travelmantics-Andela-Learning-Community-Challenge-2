package com.example.travelmantics.Utilities;

import java.io.Serializable;

public class TravelDeal implements Serializable {
    private String travelDealId;
    private String travelDealtitle;
    private String travelDealdescription;
    private String travelDealprice;
    private String travelDealimageUrl;


    public TravelDeal() {}

    public TravelDeal(String travelDealId, String travelDealtitle, String travelDealdescription, String travelDealprice, String travelDealimageUrl) {
        this.travelDealId = travelDealId;
        this.travelDealtitle = travelDealtitle;
        this.travelDealdescription = travelDealdescription;
        this.travelDealprice = travelDealprice;
        this.travelDealimageUrl = travelDealimageUrl;
    }

    public String getTravelDealId() {
        return travelDealId;
    }

    public void setTravelDealId(String travelDealId) {
        this.travelDealId = travelDealId;
    }

    public String getTravelDealTitle() {
        return travelDealtitle;
    }

    public void setTravelDealTitle(String travelDealtitle) {
        this.travelDealtitle = travelDealtitle;
    }

    public String getTravelDealdescription() {
        return travelDealdescription;
    }

    public void setTravelDealdescription(String travelDealdescription) {
        this.travelDealdescription = travelDealdescription;
    }

    public String getTravelDealprice() {
        return travelDealprice;
    }

    public void setTravelDealprice(String travelDealprice) {
        this.travelDealprice = travelDealprice;
    }

    public String getTravelDealimageUrl() {
        return travelDealimageUrl;
    }

    public void setTravelDealimageUrl(String travelDealimageUrl) {
        this.travelDealimageUrl = travelDealimageUrl;
    }
}
