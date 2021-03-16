package com.example.help2helpless.network;

import com.example.help2helpless.model.AdminResponse;
import com.example.help2helpless.model.Amount;
import com.example.help2helpless.model.AvgDealerReceived;
import com.example.help2helpless.model.ClientResponse;
import com.example.help2helpless.model.DealerAvgPaid;
import com.example.help2helpless.model.DealerBalance;
import com.example.help2helpless.model.DealerResponse;
import com.example.help2helpless.model.DealerTDonation;
import com.example.help2helpless.model.DonarAllDonation;
import com.example.help2helpless.model.DonarBalance;
import com.example.help2helpless.model.DonarResponse;
import com.example.help2helpless.model.DonarsAvgDonation;
import com.example.help2helpless.model.TotalClients;
import com.example.help2helpless.model.TotalDealer;
import com.example.help2helpless.model.TotalDonars;

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
    Call<DealerResponse> fetchAllDealers(@Query("donar_contact") String donar_contact);
    //search_client.php

    @GET("search_client.php")
    Call<ClientResponse> fetchClient(@Query("dnumber") String dnumber);

    @GET("fetchamount.php")
    Call<Amount> getAmount();

   // donar Dashbord Api
   @GET("donar_tamount.php")
   Call<DonarBalance> getDonarBalance(@Query("dcontact") String dcontact);

    @GET("avg_mdonation.php")
    Call<DonarsAvgDonation> getDonaravgDonations(@Query("dcontact") String dcontact);

    @GET("total_donation.php")
    Call<DonarAllDonation> getDonarsAllDonations(@Query("dcontact") String dcontact);


    //Dealer Dashboard Api
    //dlr_contact
    @GET("avg_dlrdonation.php")
    Call<DealerAvgPaid> getDealerAvgDonation(@Query("dlrcontact") String dlrcontact);

    @GET("dealer_balance.php")
    Call<DealerBalance> getDealerBalance(@Query("dlr_contact") String dlr_contact);

    @GET("total_dealer_donation.php")
    Call<DealerTDonation> getTotalDealerDonation(@Query("dlr_contact") String dlr_contact);
    ///avg_dealer_received.php
   // total_donar.php

    @GET("avg_dealer_received.php")
    Call<AvgDealerReceived> avgDealerReceived(@Query("dlr_contact") String dlr_contact);

    @GET("total_donar.php")
    Call<TotalDonars> getTotalDonars(@Query("dlr_contact") String dlr_contact);

    @GET("total_clients.php")
    Call<TotalClients> getTotalClients(@Query("dlr_contact") String dlr_contact);

    @GET("total_dealers.php")
    Call<TotalDealer> getTotalDealer(@Query("dnr_contact") String dnr_contact);

}
