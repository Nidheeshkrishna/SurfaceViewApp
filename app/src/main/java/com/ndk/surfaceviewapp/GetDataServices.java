package com.ndk.surfaceviewapp;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GetDataServices {
    @GET("getLoanCustomerDetails")
    Call<CustomerList> getLoanList(@Query("loanID") String loanID);



}
