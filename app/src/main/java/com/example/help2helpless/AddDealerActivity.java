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

import com.example.help2helpless.adapter.DealerAdapter;
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
   // Donar donar;
    String donar_contact;
    SharedPreferences  donarsharedpreference;
    RecyclerView dealer_add_container;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dealer);
        setFontToActionBar();
        initRecycler();

    }

    private void setFontToActionBar() {
        TextView tv = new TextView(AddDealerActivity.this);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        tv.setLayoutParams(lp);
        tv.setText("Add Dealer");
        tv.setTextSize(24);
        tv.setGravity(Gravity.CENTER);
        tv.setTextColor(Color.parseColor("#ffffff"));
        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/regular.otf");
        tv.setTypeface(tf);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(tv);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    public void initRecycler() {
        donarsharedpreference= getSharedPreferences("donarinfo",0);
        donar_contact= donarsharedpreference.getString("contact",null);
        dealer_add_container=findViewById(R.id.dealer_container);
        dealer_add_container.setHasFixedSize(true);
        dealer_add_container.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchAllDealer();
    }

    public  void fetchAllDealer() {


        ApiInterface apiInterface= ApiClient.getApiClient(AddDealerActivity.this).create(ApiInterface.class);
        Call<DealerResponse> dealerResponseCall=apiInterface.fetchAllDealers(donar_contact);
         dealerResponseCall.enqueue(new Callback<DealerResponse>() {
             @Override
             public void onResponse(Call<DealerResponse> call, Response<DealerResponse> response) {
                dealerslist= response.body().getDealers();
                if(dealerslist.size()>0){
                    DealerAdapter dealerAdapter=new DealerAdapter(dealerslist,AddDealerActivity.this,donar_contact);
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