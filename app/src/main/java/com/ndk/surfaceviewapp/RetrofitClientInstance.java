package com.ndk.surfaceviewapp;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientInstance {
    private static final String BASE_URL_LOS="https://mac.mactech.net.in/Maafin/LoanCollectionAPIGen/";
    private static Retrofit retrofitLOSApi;

    public static Retrofit getRetrofitLOSAPIInstance() {
        if (retrofitLOSApi == null) {
            retrofitLOSApi = new Retrofit.Builder()
                    .baseUrl(BASE_URL_LOS)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofitLOSApi;
    }
}
