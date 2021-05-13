package com.example.help2helpless;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
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
     EditText client_search_text;
     Button btn_search_client;
     ImageButton btn_image_back;
     ClientAdapter clientAdapter;
     LinearLayoutManager linearLayoutManager;
     ProgressBar progressBar;


    String   text;
    boolean has_more;
    int page=1,row_per_page=5;
    private boolean isloading=true;
    int pastVisibleItems,totalItemcount,previous_total,visible_item_count=0;
    private  int view_thresold=5;
    String search_text;
    String url="https://apps.help2helpless.com/search_client.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discount);
        //setFontToActionBar();
        initAll();
        getAllContact();
        btn_image_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        btn_search_client.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             text= client_search_text.getText().toString().trim();
                if(!TextUtils.isEmpty(text)){

                    isloading=true;
                    pastVisibleItems=0;
                    totalItemcount=0;
                    previous_total=0;
                    visible_item_count=0;
                    // dSendMoneyAdapter=null;

                    page=1;
                    clients.clear();
                    clientAdapter.notifyDataSetChanged();
                    getAllContact();
                }
            }
        });
        if(TextUtils.isEmpty(client_search_text.getText().toString().trim())){
            getAllContact();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(DiscountActivity.this,DealerActivity.class);
        startActivity(intent);
    }

    private void getAllContact() {
        SharedPreferences dealerlogininfo=getSharedPreferences("dealerinfo",0);
        ApiInterface apiInterface= ApiClient.getApiClient(DiscountActivity.this).create(ApiInterface.class);
        Call<ClientResponse> clientResponseCall=apiInterface.fetchClient(page,row_per_page,dealerlogininfo.getString("contact",null),text);
        clientResponseCall.enqueue(new Callback<ClientResponse>() {
            @Override
            public void onResponse(Call<ClientResponse> call, Response<ClientResponse> response) {
               clients= response.body().getResult();
                if(clients.size()>0){
                     clientAdapter=new ClientAdapter(clients,DiscountActivity.this);
                     clientItem_container.setAdapter(clientAdapter);
                }

            }

            @Override
            public void onFailure(Call<ClientResponse> call, Throwable t) {

            }
        });
        clientItem_container.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if(!has_more){
                    progressBar.setVisibility(View.GONE);
                }


                visible_item_count=linearLayoutManager.getChildCount();
                totalItemcount=linearLayoutManager.getItemCount();
                pastVisibleItems=linearLayoutManager.findFirstVisibleItemPosition();


                if(dy>0){
                    if(isloading) {

                        if(totalItemcount>previous_total){
                            isloading=false;
                            previous_total=totalItemcount;
                        }

                    }
                    if(!isloading && (totalItemcount-visible_item_count)<=(pastVisibleItems+view_thresold)){
                        Log.d("page","called");
                        performPagination();
                        isloading=true;
                    }


                }
            }
        });

    }

    private void performPagination() {
        progressBar.setVisibility(View.VISIBLE);
        page++;
        Log.d("page",String.valueOf(page));

        SharedPreferences dealerlogininfo=getSharedPreferences("dealerinfo",0);
        ApiInterface apiInterface= ApiClient.getApiClient(DiscountActivity.this).create(ApiInterface.class);
        Call<ClientResponse> clientResponseCall=apiInterface.fetchClient(page,row_per_page,dealerlogininfo.getString("contact",null),text);
        clientResponseCall.enqueue(new Callback<ClientResponse>() {
            @Override
            public void onResponse(Call<ClientResponse> call, Response<ClientResponse> response) {

                //has_more= response.body().isHas_more();
                if(response.body().getResult().size()>0){
                    clientAdapter.addList(response.body().getResult());
                    progressBar.setVisibility(View.GONE);
                }else{
                    progressBar.setVisibility(View.GONE);
                }
                progressBar.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<ClientResponse> call, Throwable t) {

            }
        });


    }

    private void initAll() {
        btn_image_back=findViewById(R.id.btn_back);
        progressBar=findViewById(R.id._progress);
        client_search_text=findViewById(R.id.client_search_input);
        clientItem_container=findViewById(R.id.client_container);
        clientItem_container.setHasFixedSize(true);
        linearLayoutManager=new LinearLayoutManager(this);
        clientItem_container.setLayoutManager(linearLayoutManager);
        btn_search_client=findViewById(R.id.btn_client_search);
    }
}