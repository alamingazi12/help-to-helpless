package com.example.help2helpless;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.example.help2helpless.adapter.ClientAdapter;
import com.example.help2helpless.model.Client;
import com.example.help2helpless.model.ClientResponse;
import com.example.help2helpless.network.ApiClient;
import com.example.help2helpless.network.ApiInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DiscountActivity extends AppCompatActivity {
     public RecyclerView clientItem_container;
     ArrayList<Client> clients;
     EditText client_contact;
    
    String url="https://apps.help2helpless.com/search_client.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discount);
        setFontToActionBar();
        initAll();
        getAllContact();

    }


    private void setFontToActionBar() {
        TextView tv = new TextView(DiscountActivity.this);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        tv.setLayoutParams(lp);
        tv.setText("Clients");
        tv.setTextSize(24);
        tv.setGravity(Gravity.CENTER);
        tv.setTextColor(Color.parseColor("#ffffff"));
        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/regular.otf");
        tv.setTypeface(tf);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(tv);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#8ecae6")));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    private void getAllContact() {
        SharedPreferences dealerlogininfo=getSharedPreferences("dealerinfo",0);
        ApiInterface apiInterface= ApiClient.getApiClient(DiscountActivity.this).create(ApiInterface.class);
        Call<ClientResponse> clientResponseCall=apiInterface.fetchClient(dealerlogininfo.getString("contact",null));
        clientResponseCall.enqueue(new Callback<ClientResponse>() {
            @Override
            public void onResponse(Call<ClientResponse> call, Response<ClientResponse> response) {
               clients= response.body().getResult();
                if(clients.size()>0){
                    ClientAdapter clientAdapter=new ClientAdapter(clients,DiscountActivity.this);
                    clientItem_container.setAdapter(clientAdapter);
                }

            }

            @Override
            public void onFailure(Call<ClientResponse> call, Throwable t) {

            }
        });
    }

    private void initAll() {
   
     
        clientItem_container=findViewById(R.id.client_container);
        clientItem_container.setHasFixedSize(true);
        clientItem_container.setLayoutManager(new LinearLayoutManager(this));
    }
}