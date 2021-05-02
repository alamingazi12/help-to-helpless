package com.example.help2helpless;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.help2helpless.adapter.AddedDealerAdapter;
import com.example.help2helpless.model.AddDealerList;
import com.example.help2helpless.model.Dealer;
import com.example.help2helpless.network.ApiClient;
import com.example.help2helpless.network.ApiInterface;
import com.muddzdev.styleabletoast.StyleableToast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddedDealerActivity extends AppCompatActivity {
     RecyclerView added_dealers;
     ArrayList<Dealer> dealerslist;

    int page=1,row_per_page=5;
    public  boolean has_more;
    LinearLayoutManager linearLayoutManager;
    ProgressBar progressBar;
    AddedDealerAdapter addedDealerAdapter;
    //for pagination
    private boolean isloading=true;
    int pastVisibleItems,totalItemcount,previous_total,visible_item_count=0;
    private  int view_thresold=5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_added_dealer);
        //setFontToActionBar();
        initAll();
        getAddedDealer();
    }
    private void setFontToActionBar() {
        TextView tv = new TextView(AddedDealerActivity.this);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        tv.setLayoutParams(lp);
        tv.setText("Your Dealers");
        tv.setTextSize(25);
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

    private void getAddedDealer() {
        SharedPreferences donarinfo=getSharedPreferences("donarinfo",0);
        String dcontact=donarinfo.getString("contact",null);
        ApiInterface apiInterface= ApiClient.getApiClient(AddedDealerActivity.this).create(ApiInterface.class);
        Call<AddDealerList> addDealerListCall=apiInterface.getAllAddedDealers(page,row_per_page,dcontact);
        addDealerListCall.enqueue(new Callback<AddDealerList>() {
           @Override
           public void onResponse(Call<AddDealerList> call, Response<AddDealerList> response) {
             dealerslist=  response.body().getDealerlist();
             if(dealerslist.size()>0){
                  addedDealerAdapter=new AddedDealerAdapter(dealerslist,AddedDealerActivity.this);
                  added_dealers.setAdapter(addedDealerAdapter);

             }else{
                 StyleableToast.makeText(AddedDealerActivity.this,"No Dealer Added",R.style.mytoast).show();

             }
           }

           @Override
           public void onFailure(Call<AddDealerList> call, Throwable t) {
               StyleableToast.makeText(AddedDealerActivity.this,"Netword Error",R.style.mytoast).show();
           }
       });

        added_dealers.addOnScrollListener(new RecyclerView.OnScrollListener() {
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

        SharedPreferences donarinfo=getSharedPreferences("donarinfo",0);
        String dcontact=donarinfo.getString("contact",null);
        ApiInterface apiInterface= ApiClient.getApiClient(AddedDealerActivity.this).create(ApiInterface.class);
        Call<AddDealerList> addDealerListCall=apiInterface.getAllAddedDealers(page,row_per_page,dcontact);
        addDealerListCall.enqueue(new Callback<AddDealerList>() {
            @Override
            public void onResponse(Call<AddDealerList> call, Response<AddDealerList> response) {


                has_more= response.body().isHas_more();
                if(response.body().getDealerlist().size()>0){
                    addedDealerAdapter.addLists(response.body().getDealerlist());
                   // addedDealerAdapter.addLists(response.body().getDealerlist());
                    progressBar.setVisibility(View.GONE);
                  //  StyleableToast.makeText(AddedDealerActivity.this,"data not null",R.style.mytoast).show();
                }
                else{
                    StyleableToast.makeText(AddedDealerActivity.this,"data null",R.style.mytoast).show();
                    progressBar.setVisibility(View.GONE);
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<AddDealerList> call, Throwable t) {
                StyleableToast.makeText(AddedDealerActivity.this,"Netword Error",R.style.mytoast).show();
            }
        });
    }

    private void initAll() {
        added_dealers=findViewById(R.id.added_dealer_container);
        added_dealers.setHasFixedSize(true);
        linearLayoutManager =new LinearLayoutManager(this);
        added_dealers.setLayoutManager(linearLayoutManager);
        progressBar=findViewById(R.id._progress);
    }
}