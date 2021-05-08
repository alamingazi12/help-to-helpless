package com.example.help2helpless;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.help2helpless.adapter.RequestAdapter;
import com.example.help2helpless.model.Dealer;
import com.example.help2helpless.model.DealerResponse;
import com.example.help2helpless.network.ApiClient;
import com.example.help2helpless.network.ApiInterface;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DealerRequestActivity extends AppCompatActivity {
    RecyclerView requst_container;
    ArrayList<Dealer> allDealers;
    RequestAdapter requestAdapter;
    LinearLayoutManager linearLayoutManager;
    ProgressBar progressBar;
    Button button_search;
    EditText edit_input_search;
    int page=1,row_per_page=5;
    public  boolean has_more;
    String search_text="";

    private boolean isloading=true;
    int pastVisibleItems,totalItemcount,previous_total,visible_item_count=0;
    private  int view_thresold=5;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dealer_request_activity);
        initAll();


        button_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search_text= edit_input_search.getText().toString().trim();
                if(!TextUtils.isEmpty(search_text)){

                    isloading=true;
                    pastVisibleItems=0;
                    totalItemcount=0;
                    previous_total=0;
                    visible_item_count=0;
                    // dSendMoneyAdapter=null;

                    page=1;
                    allDealers.clear();
                    requestAdapter.notifyDataSetChanged();
                    fetchAllDealer();
                }
            }
        });

        if(TextUtils.isEmpty(search_text)){
            fetchAllDealer();
        }
        edit_input_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(TextUtils.isEmpty(s) ||  s.toString().equals("0")){
                    Log.d("empty","empty");
                    isloading=true;
                    pastVisibleItems=0;
                    totalItemcount=0;
                    previous_total=0;
                    visible_item_count=0;
                    search_text="";
                    row_per_page=20;
                    // dSendMoneyAdapter=null;

                    page=1;
                    allDealers.clear();
                    requestAdapter.notifyDataSetChanged();
                    fetchAllDealer();
                }
            }
        });
    }




    private void fetchAllDealer() {

        ApiInterface apiInterface= ApiClient.getApiClient(DealerRequestActivity.this).create(ApiInterface.class);
        Call<DealerResponse> dealerResponseCall=apiInterface.getAlldealers(page,row_per_page,search_text);
        dealerResponseCall.enqueue(new Callback<DealerResponse>() {
            @Override
            public void onResponse(Call<DealerResponse> call, Response<DealerResponse> response) {
                allDealers=response.body().getDealers();
                if(allDealers.size()>0){
                     requestAdapter=new RequestAdapter(allDealers,DealerRequestActivity.this);
                    requst_container.setAdapter(requestAdapter);
                }
            }

            @Override
            public void onFailure(Call<DealerResponse> call, Throwable t) {

            }
        });
       requst_container.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
        ApiInterface apiInterface= ApiClient.getApiClient(DealerRequestActivity.this).create(ApiInterface.class);
        Call<DealerResponse> dealerResponseCall=apiInterface.getAlldealers(page,row_per_page,search_text);
        dealerResponseCall.enqueue(new Callback<DealerResponse>() {
            @Override
            public void onResponse(Call<DealerResponse> call, Response<DealerResponse> response) {
                has_more= response.body().isHas_more();
                if(has_more){
                    requestAdapter.addList(response.body().getDealers());
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

    @Override
    protected void onResume() {
        super.onResume();
        //fetchAllDealer();
    }

    private void initAll() {
        edit_input_search=findViewById(R.id.search_edit_text);
        button_search=findViewById(R.id.btn_search);
        progressBar=findViewById(R.id._progress);
        requst_container=findViewById(R.id.req_container);
        requst_container.setHasFixedSize(true);
        linearLayoutManager=new LinearLayoutManager(this);
        requst_container.setLayoutManager(linearLayoutManager);
    }
}
