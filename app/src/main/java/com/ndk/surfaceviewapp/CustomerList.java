package com.ndk.surfaceviewapp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CustomerList {
    @SerializedName("customeR_ID")
    @Expose
    private String customeRID;
    @SerializedName("cusT_GUARANT")
    @Expose
    private Integer cusTGUARANT;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("loaN_ID")
    @Expose
    private String loaNID;
    @SerializedName("maiN_JOIN")
    @Expose
    private Integer maiNJOIN;

    /**
     * No args constructor for use in serialization
     *
     */
    public CustomerList() {
    }

    /**
     *
     * @param maiNJOIN
     * @param cusTGUARANT
     * @param name
     * @param customeRID
     * @param loaNID
     */
    public CustomerList(String customeRID, Integer cusTGUARANT, String name, String loaNID, Integer maiNJOIN) {
        super();
        this.customeRID = customeRID;
        this.cusTGUARANT = cusTGUARANT;
        this.name = name;
        this.loaNID = loaNID;
        this.maiNJOIN = maiNJOIN;
    }

    public String getCustomeRID() {
        return customeRID;
    }

    public void setCustomeRID(String customeRID) {
        this.customeRID = customeRID;
    }

    public Integer getCusTGUARANT() {
        return cusTGUARANT;
    }

    public void setCusTGUARANT(Integer cusTGUARANT) {
        this.cusTGUARANT = cusTGUARANT;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLoaNID() {
        return loaNID;
    }

    public void setLoaNID(String loaNID) {
        this.loaNID = loaNID;
    }

    public Integer getMaiNJOIN() {
        return maiNJOIN;
    }

    public void setMaiNJOIN(Integer maiNJOIN) {
        this.maiNJOIN = maiNJOIN;
    }
}
