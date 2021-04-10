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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_added_dealer);
        setFontToActionBar();
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
       Call<AddDealerList> addDealerListCall=apiInterface.getAllAddedDealers(dcontact);
       addDealerListCall.enqueue(new Callback<AddDealerList>() {
           @Override
           public void onResponse(Call<AddDealerList> call, Response<AddDealerList> response) {
             dealerslist=  response.body().getDealerlist();
             if(dealerslist.size()>0){
                 AddedDealerAdapter addedDealerAdapter=new AddedDealerAdapter(dealerslist,AddedDealerActivity.this);
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

    }

    private void initAll() {
        added_dealers=findViewById(R.id.added_dealer_container);
        added_dealers.setHasFixedSize(true);
        added_dealers.setLayoutManager(new LinearLayoutManager(this));
    }
}