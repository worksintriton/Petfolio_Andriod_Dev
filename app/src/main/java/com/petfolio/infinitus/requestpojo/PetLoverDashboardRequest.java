package com.petfolio.infinitus.requestpojo;

import com.google.gson.annotations.SerializedName;

public class PetLoverDashboardRequest {


    /**
     * user_id : 5fb22773e70b0d3cc5b2c19a
     * lat : 12.0909
     * long : 80.09093
     * user_type : 1
     * address : Muthamil nager, Kodugaiyur, Chennai - 600 118
     */

    private String user_id;
    private double lat;
    @SerializedName("long")
    private double longX;
    private int user_type;
    private String address;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLongX() {
        return longX;
    }

    public void setLongX(double longX) {
        this.longX = longX;
    }

    public int getUser_type() {
        return user_type;
    }

    public void setUser_type(int user_type) {
        this.user_type = user_type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
