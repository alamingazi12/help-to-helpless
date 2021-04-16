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
import android.widget.Toast;
import com.example.help2helpless.adapter.DonarAdapter;
import com.example.help2helpless.model.AllDonar;
import com.example.help2helpless.model.Donar;
import com.example.help2helpless.model.TotalDonars;
import com.example.help2helpless.network.ApiClient;
import com.example.help2helpless.network.ApiInterface;
import com.muddzdev.styleabletoast.StyleableToast;

import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddDonarActivity extends AppCompatActivity {
     ArrayList<Donar> donarList;
     RecyclerView donar_item_container;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_donar);
        setFontToActionBar();
        initAll();
        getAllDonar();
    }

    private void setFontToActionBar() {
        TextView tv = new TextView(AddDonarActivity.this);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        tv.setLayoutParams(lp);
        tv.setText("Donars");
        tv.setTextSize(22);
        tv.setGravity(Gravity.CENTER);
        tv.setTextColor(Color.parseColor("#ffffff"));
        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/relway_regular.ttf");
        tv.setTypeface(tf);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(tv);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    private void initAll() {
      donar_item_container=findViewById(R.id.donar_item_container);
      donar_item_container.setHasFixedSize(true);
      donar_item_container.setLayoutManager(new LinearLayoutManager(this));
    }

    private void getAllDonar() {
        SharedPreferences dealerharedpreference= getSharedPreferences("dealerinfo",0);
        ApiInterface apiInterface= ApiClient.getApiClient(AddDonarActivity.this).create(ApiInterface.class);
        Call<AllDonar> allDonarCall= apiInterface.getTotalDonar(dealerharedpreference.getString("contact",null));
        allDonarCall.enqueue(new Callback<AllDonar>() {
            @Override
            public void onResponse(Call<AllDonar> call, Response<AllDonar> response) {
             donarList= response.body().getDealers();
             if(donarList.size()>0){
                 DonarAdapter donarAdapter=new DonarAdapter(donarList,AddDonarActivity.this);
                 donar_item_container.setAdapter(donarAdapter);
                // Toast.makeText(AddDonarActivity.this,"gets data",Toast.LENGTH_LONG).show();
             }
             else{
                 StyleableToast.makeText(AddDonarActivity.this,"NO Donars To Add",R.style.mytoast).show();
                 //Toast.makeText(AddDonarActivity.this,"NO Donars To Add",Toast.LENGTH_LONG).show();
             }
            }

            @Override
            public void onFailure(Call<AllDonar> call, Throwable t) {
                Toast.makeText(AddDonarActivity.this,"Network Error",Toast.LENGTH_LONG).show();
            }
        });

    }

}