package com.example.help2helpless;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.help2helpless.adapter.DealerAdapter;
import com.example.help2helpless.adapter.RequestAdapter;
import com.example.help2helpless.model.Dealer;
import com.example.help2helpless.model.DealerResponse;
import com.example.help2helpless.model.Donar;
import com.example.help2helpless.network.ApiClient;
import com.example.help2helpless.network.ApiInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddDealerActivity extends AppCompatActivity {
    ArrayList<Dealer> dealerslist;
    Donar donar;
   RecyclerView dealer_add_container;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dealer);
        Bundle bundle=getIntent().getExtras();
        donar=bundle.getParcelable("dobj");
        initRecycler();
        fetchAllDealer();
    }

    private void initRecycler() {
        dealer_add_container=findViewById(R.id.dealer_container);
        dealer_add_container.setHasFixedSize(true);
        dealer_add_container.setLayoutManager(new LinearLayoutManager(this));

    }
    private void fetchAllDealer() {

        ApiInterface apiInterface= ApiClient.getApiClient(AddDealerActivity.this).create(ApiInterface.class);
        Call<DealerResponse> dealerResponseCall=apiInterface.fetchAllDealers(donar.getZilla());
         dealerResponseCall.enqueue(new Callback<DealerResponse>() {
             @Override
             public void onResponse(Call<DealerResponse> call, Response<DealerResponse> response) {
                dealerslist= response.body().getDealers();
                if(dealerslist.size()>0){
                    DealerAdapter dealerAdapter=new DealerAdapter(dealerslist,AddDealerActivity.this);
                    dealer_add_container.setAdapter(dealerAdapter);
                }else{


                }
             }

             @Override
             public void onFailure(Call<DealerResponse> call, Throwable t) {

             }
         });


    }
}