package com.example.help2helpless;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.help2helpless.adapter.DealerRequestAdapter;
import com.example.help2helpless.model.Dealer;
import com.example.help2helpless.model.DealersRequest;
import com.example.help2helpless.network.ApiClient;
import com.example.help2helpless.network.ApiInterface;
import com.muddzdev.styleabletoast.StyleableToast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RequestActivity extends AppCompatActivity {
       ArrayList<Dealer> dealerslist;
       RecyclerView dealer_request_containers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);
      //  setFontToActionBar();
       init();
        getDealers();
    }


    private void init() {
        dealer_request_containers=findViewById(R.id.dealer_request_container);
        dealer_request_containers.setHasFixedSize(true);
        dealer_request_containers.setLayoutManager(new LinearLayoutManager(this));

    }

    private void getDealers() {
    SharedPreferences donarinfo=this.getSharedPreferences("donarinfo",0);
    String   donar_contact=donarinfo.getString("contact",null);
        ApiInterface apiInterface= ApiClient.getApiClient(RequestActivity.this).create(ApiInterface.class);
        Call<DealersRequest>  dealersRequestCall=apiInterface.getAllDealerRequest(donar_contact);
        dealersRequestCall.enqueue(new Callback<DealersRequest>() {
            @Override
            public void onResponse(Call<DealersRequest> call, Response<DealersRequest> response) {
              dealerslist= response.body().getDealers();
              if(dealerslist.size()>0){

                  DealerRequestAdapter dealerRequestAdapter=new DealerRequestAdapter(RequestActivity.this,dealerslist);
                  dealer_request_containers.setAdapter(dealerRequestAdapter);
              }else {
                  StyleableToast.makeText(RequestActivity.this,"No Request Found",R.style.mytoast).show();
              }
            }

            @Override
            public void onFailure(Call<DealersRequest> call, Throwable t) {

            }
        });

    }
}