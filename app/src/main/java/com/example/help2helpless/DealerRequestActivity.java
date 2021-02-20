package com.example.help2helpless;
import android.os.Bundle;
import androidx.annotation.Nullable;
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
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dealer_request_activity);
        initAll();
        //fetchAllDealer();
    }

    private void fetchAllDealer() {

        ApiInterface apiInterface= ApiClient.getApiClient(DealerRequestActivity.this).create(ApiInterface.class);
        Call<DealerResponse> dealerResponseCall=apiInterface.getAlldealers();
        dealerResponseCall.enqueue(new Callback<DealerResponse>() {
            @Override
            public void onResponse(Call<DealerResponse> call, Response<DealerResponse> response) {
                allDealers=response.body().getDealers();
                if(allDealers.size()>0){
                    RequestAdapter requestAdapter=new RequestAdapter(allDealers,DealerRequestActivity.this);
                    requst_container.setAdapter(requestAdapter);
                }
            }

            @Override
            public void onFailure(Call<DealerResponse> call, Throwable t) {

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchAllDealer();
    }

    private void initAll() {
        requst_container=findViewById(R.id.req_container);
        requst_container.setHasFixedSize(true);
        requst_container.setLayoutManager(new LinearLayoutManager(DealerRequestActivity.this));
    }
}
