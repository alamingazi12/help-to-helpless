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
     DonarAdapter donarAdapter;
     ProgressBar progressBar;
     LinearLayoutManager linearLayoutManager;


     boolean has_more;
    int page=1,row_per_page=5;
    private boolean isloading=true;
    int pastVisibleItems,totalItemcount,previous_total,visible_item_count=0;
    private  int view_thresold=5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_donar);
       // setFontToActionBar();
        initAll();
        getAllDonar();
    }



    private void initAll() {

      donar_item_container=findViewById(R.id.donar_item_container);
      donar_item_container.setHasFixedSize(true);
      linearLayoutManager=new LinearLayoutManager(this);
      donar_item_container.setLayoutManager(linearLayoutManager);
      progressBar=findViewById(R.id._progress);
    }

    private void getAllDonar() {
        SharedPreferences dealerharedpreference= getSharedPreferences("dealerinfo",0);
        ApiInterface apiInterface= ApiClient.getApiClient(AddDonarActivity.this).create(ApiInterface.class);
        Call<AllDonar> allDonarCall= apiInterface.getTotalDonar(page,row_per_page,dealerharedpreference.getString("contact",null));
        allDonarCall.enqueue(new Callback<AllDonar>() {
            @Override
            public void onResponse(Call<AllDonar> call, Response<AllDonar> response) {
             donarList= response.body().getDealers();
             if(donarList.size()>0){
                 donarAdapter=new DonarAdapter(donarList,AddDonarActivity.this);
                 donar_item_container.setAdapter(donarAdapter);
                 progressBar.setVisibility(View.GONE);
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

        donar_item_container.addOnScrollListener(new RecyclerView.OnScrollListener() {
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


   public void performPagination(){

       progressBar.setVisibility(View.VISIBLE);
       page++;
       Log.d("page",String.valueOf(page));

       SharedPreferences dealerharedpreference= getSharedPreferences("dealerinfo",0);
       ApiInterface apiInterface= ApiClient.getApiClient(AddDonarActivity.this).create(ApiInterface.class);
       Call<AllDonar> allDonarCall= apiInterface.getTotalDonar(page,row_per_page,dealerharedpreference.getString("contact",null));
       allDonarCall.enqueue(new Callback<AllDonar>() {
           @Override
           public void onResponse(Call<AllDonar> call, Response<AllDonar> response) {
               has_more= response.body().isHas_more();
               if(has_more){
                   donarAdapter.addList(response.body().getDealers());
                   progressBar.setVisibility(View.GONE);
               }else{
                   progressBar.setVisibility(View.GONE);
               }
               progressBar.setVisibility(View.GONE);
           }

           @Override
           public void onFailure(Call<AllDonar> call, Throwable t) {
               Toast.makeText(AddDonarActivity.this,"Network Error",Toast.LENGTH_LONG).show();
           }


       });
    }

}