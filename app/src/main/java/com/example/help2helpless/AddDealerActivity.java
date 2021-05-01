package com.example.help2helpless;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.help2helpless.adapter.DealerAdapter;
import com.example.help2helpless.model.Dealer;
import com.example.help2helpless.model.DealerResponse;
import com.example.help2helpless.model.Donar;
import com.example.help2helpless.network.ApiClient;
import com.example.help2helpless.network.ApiInterface;
import com.muddzdev.styleabletoast.StyleableToast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddDealerActivity extends AppCompatActivity {
    ArrayList<Dealer> dealerslist;
    String donar_contact;
    SharedPreferences  donarsharedpreference;
    RecyclerView dealer_add_container;
    ProgressBar progressBar;
    DealerAdapter dealerAdapter;
    int page=1,row_per_page=5;
    public  boolean has_more;
    LinearLayoutManager linearLayoutManager;

    //for pagination
    private boolean isloading=true;
    int pastVisibleItems,totalItemcount,previous_total,visible_item_count=0;
    private  int view_thresold=5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dealer);
        //setFontToActionBar();
        initRecycler();
        fetchAllDealer();
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
        linearLayoutManager=new LinearLayoutManager(this);
        dealer_add_container.setLayoutManager(linearLayoutManager);
        progressBar=findViewById(R.id._progress);

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    public  void fetchAllDealer() {

        ApiInterface apiInterface= ApiClient.getApiClient(AddDealerActivity.this).create(ApiInterface.class);
        Call<DealerResponse> dealerResponseCall=apiInterface.fetchAllDealers(page,row_per_page,donar_contact);
         dealerResponseCall.enqueue(new Callback<DealerResponse>() {
             @Override
             public void onResponse(Call<DealerResponse> call, Response<DealerResponse> response) {
                dealerslist= response.body().getDealers();
                if(dealerslist.size()>0){
                     dealerAdapter=new DealerAdapter(dealerslist,AddDealerActivity.this,donar_contact);
                     dealer_add_container.setAdapter(dealerAdapter);
                }else{


                }
             }

             @Override
             public void onFailure(Call<DealerResponse> call, Throwable t) {

             }
         });


         dealer_add_container.addOnScrollListener(new RecyclerView.OnScrollListener() {
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

        ApiInterface apiInterface= ApiClient.getApiClient(AddDealerActivity.this).create(ApiInterface.class);
        Call<DealerResponse> dealerResponseCall=apiInterface.fetchAllDealers(page,row_per_page,donar_contact);
        dealerResponseCall.enqueue(new Callback<DealerResponse>() {
            @Override
            public void onResponse(Call<DealerResponse> call, Response<DealerResponse> response) {

                has_more= response.body().isHas_more();
                if(has_more && response.body().getDealers().size()>0){
                    Toast.makeText(AddDealerActivity.this,"data not null"+response.body().getDealers().size(),Toast.LENGTH_LONG).show();
                    dealerAdapter.addLists(response.body().getDealers());
                    progressBar.setVisibility(View.GONE);
                }else{
                    progressBar.setVisibility(View.GONE);
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<DealerResponse> call, Throwable t) {

            }
        });
    }
}