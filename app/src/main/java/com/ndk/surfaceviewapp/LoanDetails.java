package com.ndk.surfaceviewapp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class LoanDetails {
    @SerializedName("customerList")
    @Expose
    private List<CustomerList> customerList = new ArrayList<CustomerList>();
    @SerializedName("status")
    @Expose
    private Status status;

    /**
     * No args constructor for use in serialization
     *
     */
    public LoanDetails() {
    }

    /**
     *
     * @param customerList
     * @param status
     */
    public LoanDetails(List<CustomerList> customerList, Status status) {
        super();
        this.customerList = customerList;
        this.status = status;
    }

    public List<CustomerList> getCustomerList() {
        return customerList;
    }

    public void setCustomerList(List<CustomerList> customerList) {
        this.customerList = customerList;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
