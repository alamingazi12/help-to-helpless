package com.example.help2helpless.network;

import com.example.help2helpless.model.AdminResponse;
import com.example.help2helpless.model.Amount;
import com.example.help2helpless.model.ClientResponse;
import com.example.help2helpless.model.DealerResponse;
import com.example.help2helpless.model.DonarResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public  interface ApiInterface {

    @GET("adminlogin.php")
    Call<AdminResponse> getAdminResponse(@Query("username") String username,@Query("password") String  password);

    @GET("donar_login.php")
    Call<DonarResponse> getDonarResponse(@Query("username") String username, @Query("password") String  password);
    @GET("dealer_login.php")
    Call<DealerResponse> getDealerResponse(@Query("username") String username, @Query("password") String  password);

    @GET("temp_dealer_fetch.php")
    Call<DealerResponse> getAlldealers();

    @GET("fetchdealer.php")
    Call<DealerResponse> fetchAllDealers(@Query("zilla") String zilla);
    //search_client.php

    @GET("search_client.php")
    Call<ClientResponse> fetchClient(@Query("cnumber") String cnumber,@Query("dnumber") String dnumber);

    @GET("fetchamount.php")
    Call<Amount> getAmount();

}
