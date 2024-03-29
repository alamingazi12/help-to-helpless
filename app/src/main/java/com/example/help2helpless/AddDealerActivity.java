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
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.help2helpless.adapter.AddedDealerAdapter;
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
    LinearLayoutManager linearLayoutManager;
    ImageButton btn_image_back;
    EditText search_edit_text;


    int page=1,row_per_page=5;
    public  boolean has_more;
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
        btn_image_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        search_edit_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d("text",s.toString());
                if(TextUtils.isEmpty(s.toString()) || s.toString().equals("0") ){
                    Log.d("text","Empty");
                    row_per_page=4;
                    page=1;

                    pastVisibleItems=0;
                    totalItemcount=0;
                    previous_total=0;
                    visible_item_count=0;
                    fetchAllDealer();
                }
                filter(s.toString());
            }
        });


    }

    private void filter(String text) {
        ArrayList<Dealer> filteredList = new ArrayList<>();

        for (Dealer item : DealerAdapter.dealers) {
            if ((item.getName().toLowerCase().contains(text.toLowerCase())) ||(item.getPhone().toLowerCase().contains(text.toLowerCase())) || (item.getShpnmthana().toLowerCase().contains(text.toLowerCase())) || (item.getShpnmzilla().toLowerCase().contains(text.toLowerCase()))) {
                filteredList.add(item);
            }
        }
        dealerAdapter.filterList(filteredList);
    }


    public void initRecycler() {
        donarsharedpreference= getSharedPreferences("donarinfo",0);
        donar_contact= donarsharedpreference.getString("contact",null);
        dealer_add_container=findViewById(R.id.dealer_container);
        dealer_add_container.setHasFixedSize(true);
        linearLayoutManager=new LinearLayoutManager(this);
        dealer_add_container.setLayoutManager(linearLayoutManager);
        progressBar=findViewById(R.id._progress);
        btn_image_back=findViewById(R.id.btn_back);

        search_edit_text=findViewById(R.id.search_text);

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
                   // Toast.makeText(AddDealerActivity.this,"data not null"+response.body().getDealers().size(),Toast.LENGTH_LONG).show();
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