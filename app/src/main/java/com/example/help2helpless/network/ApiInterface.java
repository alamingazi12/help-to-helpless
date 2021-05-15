package com.example.help2helpless.network;

import com.example.help2helpless.model.AddDealerList;
import com.example.help2helpless.model.AdminResponse;
import com.example.help2helpless.model.AllDonar;
import com.example.help2helpless.model.Amount;
import com.example.help2helpless.model.AvgDealerReceived;
import com.example.help2helpless.model.CategoryResponse;
import com.example.help2helpless.model.ClientResponse;
import com.example.help2helpless.model.DealerAvgPaid;
import com.example.help2helpless.model.DealerBalance;
import com.example.help2helpless.model.DealerReciveResponse;
import com.example.help2helpless.model.DealerResponse;
import com.example.help2helpless.model.DealerSendResponse;
import com.example.help2helpless.model.DealerTDonation;
import com.example.help2helpless.model.DealersRequest;
import com.example.help2helpless.model.Donar;
import com.example.help2helpless.model.DonarAllDonation;
import com.example.help2helpless.model.DonarBalance;
import com.example.help2helpless.model.DonarResponse;
import com.example.help2helpless.model.DonarsAvgDonation;
import com.example.help2helpless.model.RecordResponse;
import com.example.help2helpless.model.Responses;
import com.example.help2helpless.model.Student;
import com.example.help2helpless.model.TotalClients;
import com.example.help2helpless.model.TotalDealer;
import com.example.help2helpless.model.TotalDonars;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public  interface ApiInterface {

    @GET("adminlogin.php")
    Call<AdminResponse> getAdminResponse(@Query("username") String username,@Query("password") String  password);
    @GET("donar_login.php")
    Call<DonarResponse> getDonarResponse(@Query("phone") String phone, @Query("password") String  password);
    @GET("dealer_login.php")
    Call<DealerResponse> getDealerResponse(@Query("phone") String phone, @Query("password") String  password);

    @GET("temp_dealer_fetch.php")
    Call<DealerResponse> getAlldealers(@Query("page") int page,@Query("row_per_page") int row_per_page,@Query("search_text") String search_text);

    @GET("temp_dealer_fetch.php")
    Observable<DealerResponse> getAlldealer();

    @GET("fetchdealer.php")
    Call<DealerResponse> fetchAllDealers(@Query("page") int page,@Query("row_per_page") int row_per_page,@Query("donar_contact") String donar_contact);
    //search_client.php

    @GET("search_client.php")
    Call<ClientResponse> fetchClient(@Query("page") int page,@Query("row_per_page") int row_per_page,@Query("dnumber") String dnumber,@Query("client_phone") String client_phone);

    @GET("fetchamount.php")
    Call<Amount> getAmount();

    @GET("get_categories.php")
    Call<CategoryResponse> get_clientCategory();

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

   // @GET("total_dealer_donations.php")
   // Observable<List<Donar>> getAllDonars();
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
    // paging api ok
    @GET("fetchdonar_item.php")
    Call<AllDonar> getTotalDonar(@Query("page") int page,@Query("row_per_page") int row_per_page,@Query("dlr_contact") String dlr_contact);

    @GET("fetch_request.php")
    Call<DealersRequest> getAllDealerRequest(@Query("dnr_contact") String dnr_contact);
   ///AddDealerList
    //pagination api ok
    @GET("total_dealers_list.php")
    Call<AddDealerList> getAllAddedDealers(@Query("page") int page, @Query("row_per_page") int row_per_page, @Query("dnr_contact") String dnr_contact);
   // pagination api ok total complete
    @GET("send_money_donar.php")
    Call<DonarResponse> getAllDonars(@Query("page") int page,@Query("row_per_page") int row_per_page,@Query("phone") String phone);

    @FormUrlEncoded
    @POST("donarinsert.php")
     Call<Responses> donarSignResponses(
            @Field("name") String name,
            @Field("contact") String contact,
            @Field("passwrd") String password
            );

    @FormUrlEncoded
    @POST("temp_dealerinsert.php")
    Call<Responses> dealerSignResponse(
            @Field("name") String name,
            @Field("phone") String contact,
            @Field("password") String password,
            @Field("profile_pic") String profile_pic,
            @Field("address") String address,
            @Field("zilla") String zilla,
            @Field("thana") String thana,
            @Field("email") String email,
            @Field("shop_pic") String shop_pic
            );


    @FormUrlEncoded
    @POST("insertclient.php")
    Call<Responses> clientSignResponse(
            @Field("name") String name,
            @Field("profile_pic") String profile_pic,
            @Field("caddress") String address,//presentaddr
            @Field("thana") String thana,
            @Field("district") String district,
            @Field("phone") String phone,
            @Field("client_type") String client_type,
            @Field("document_pic") String document_pic,
            @Field("dcontact") String dealer_contact);


    @FormUrlEncoded
    @POST("update_dealer.php")
    Call<Responses> dealerUpdateResponse(
            @Field("profile_pic") String profile_pic,
            @Field("address") String address,
            @Field("zilla") String zilla,
            @Field("thana") String thana,
            @Field("phone") String phone,
            @Field("mail") String mail,
            @Field("document_pic") String document_pic
    );

    @FormUrlEncoded
    @POST("update_donar.php")
    Call<Responses> donarUpdateResponse(
            @Field("profile_pic") String profile_pic,
            @Field("address") String address,
            @Field("zilla") String zilla,
            @Field("thana") String thana,
            @Field("phone") String phone,
            @Field("mail") String mail,
            @Field("document_pic") String document_pic,
            @Field("donation") String donation
    );


    ///donation history record
    @GET("get_donar_send_record_today.php")
    Call<RecordResponse> getDonarSendRecordToday(@Query("contact") String contact, @Query("date") String  date);

    @GET("get_donar_send_record_older.php")
    Call<RecordResponse> getDonarSendRecordOlder(@Query("contact") String contact, @Query("date") String  date,@Query("page") int  page,@Query("row_per_page") int  row_per_page,@Query("search_text") String  search_text);

    ///donation history send
    @GET("get_donar_recive_record_today.php")
    Call<RecordResponse> getDonarReceiveRecordToday(@Query("contact") String contact, @Query("date") String  date);

    @GET("get_donar_receive_record_older.php")
    Call<RecordResponse> getDonarReceiveRecordOlder(@Query("contact") String contact, @Query("date") String  date,@Query("search_text") String  search_text);



    //dealer history record
    @GET("get_dealer_send_record_today.php")
    Call<DealerSendResponse> getDealerSendRecordToday(@Query("contact") String contact, @Query("date") String  date);

    @GET("get_dealer_send_record_older.php")
    Call<DealerSendResponse> getDealerSendRecordOlder(@Query("contact") String contact, @Query("date") String date,@Query("search_text") String  search_text);
    
    @GET("get_dealer_recive_record_today.php")
    Call<DealerReciveResponse> getDealerReceiveRecordToday(@Query("contact") String contact, @Query("date") String  date);

    @GET("get_dealer_receive_record_older.php")
    Call<DealerReciveResponse> getDealerReceiveRecordOlder(@Query("contact") String contact, @Query("date") String  date,@Query("search_text") String  search_text);

}
