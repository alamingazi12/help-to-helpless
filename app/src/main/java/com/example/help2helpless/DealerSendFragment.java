package com.example.help2helpless;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.help2helpless.dealeradapter.DealerReceiveAdapterToday;
import com.example.help2helpless.dealeradapter.DealerSendAdapterOlder;
import com.example.help2helpless.dealeradapter.DealerSendAdapterToday;
import com.example.help2helpless.model.DealerReceiveRecord;
import com.example.help2helpless.model.DealerReciveResponse;
import com.example.help2helpless.model.DealerSendRecord;
import com.example.help2helpless.model.DealerSendResponse;
import com.example.help2helpless.network.ApiClient;
import com.example.help2helpless.network.ApiInterface;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DealerSendFragment extends Fragment {
    ProgressBar progressBar;
    RecyclerView today_record_container;
    RecyclerView yesterday_record_container;
    RecyclerView older_record_container;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.dealer_send_fragment, container, false);
        initAll(view);
        showTodayRecords();
        showYesterdayRecords();
        showOlderRecords();
        return view;
    }

    private void initAll(View view) {
        progressBar=view.findViewById(R.id._progress);

        //today container;
        today_record_container=view.findViewById(R.id.donar_today_container);
        today_record_container.setHasFixedSize(true);
        today_record_container.setLayoutManager(new LinearLayoutManager(getContext()));
        // yesterday container
        yesterday_record_container=view.findViewById(R.id.donar_yesterday_container);
        yesterday_record_container.setHasFixedSize(true);
        yesterday_record_container.setLayoutManager(new LinearLayoutManager(getContext()));
        //older container
        older_record_container=view.findViewById(R.id.donar_old_container);
        older_record_container.setHasFixedSize(true);
        older_record_container.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void showYesterdayRecords() {
        Date date=new Date();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        String today=sdf.format(date);

        String pdatarr[]=today.split("-");
        int days=(Integer.parseInt(pdatarr[2]))-1;
        String day= String.valueOf(days);
        String yesterday=pdatarr[0]+"-"+pdatarr[1]+"-"+day;
        Log.d("yesterday",yesterday);
        SharedPreferences sharedPreferences=getContext().getSharedPreferences("dealerinfo",0);
        String contact=  sharedPreferences.getString("contact","") ;
        ApiInterface apiInterface= ApiClient.getApiClient(getContext()).create(ApiInterface.class);
        apiInterface.getDealerSendRecordToday(contact,yesterday).enqueue(new Callback<DealerSendResponse>() {
            @Override
            public void onResponse(Call<DealerSendResponse> call, Response<DealerSendResponse> response) {
                ArrayList<DealerSendRecord> records= response.body().getRecords();

                if(records.size()>0){
                    DealerSendAdapterToday recordTodayAdapter=new DealerSendAdapterToday(records,getContext(),2);
                    yesterday_record_container.setAdapter(recordTodayAdapter);
                }
            }

            @Override
            public void onFailure(Call<DealerSendResponse> call, Throwable t) {

            }
        });

    }

    private void showOlderRecords() {
        Date date=new Date();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        String today=sdf.format(date);

        String pdatarr[]=today.split("-");
        int days=(Integer.parseInt(pdatarr[2]))-1;
        String day= String.valueOf(days);
        String yesterday=pdatarr[0]+"-"+pdatarr[1]+"-"+day;
        Log.d("yesterday",yesterday);
        SharedPreferences sharedPreferences=getContext().getSharedPreferences("dealerinfo",0);
        String contact=  sharedPreferences.getString("contact","") ;
        ApiInterface apiInterface= ApiClient.getApiClient(getContext()).create(ApiInterface.class);
        apiInterface.getDealerSendRecordOlder(contact,yesterday).enqueue(new Callback<DealerSendResponse>() {
            @Override
            public void onResponse(Call<DealerSendResponse> call, Response<DealerSendResponse> response) {
                ArrayList<DealerSendRecord> records= response.body().getRecords();

                if(records.size()>0){
                    progressBar.setVisibility(View.GONE);
                    DealerSendAdapterOlder recordTodayAdapter=new DealerSendAdapterOlder(records,getContext(),2);
                    older_record_container.setAdapter(recordTodayAdapter);
                }
            }

            @Override
            public void onFailure(Call<DealerSendResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });

    }

    private void showTodayRecords() {
        Date date=new Date();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        String today=sdf.format(date);

        String pdatarr[]=today.split("-");
        SharedPreferences sharedPreferences=getContext().getSharedPreferences("dealerinfo",0);
        String contact=  sharedPreferences.getString("contact","") ;
        ApiInterface apiInterface= ApiClient.getApiClient(getContext()).create(ApiInterface.class);
        apiInterface.getDealerSendRecordToday(contact,today).enqueue(new Callback<DealerSendResponse>() {
            @Override
            public void onResponse(Call<DealerSendResponse> call, Response<DealerSendResponse> response) {
                ArrayList<DealerSendRecord> records= response.body().getRecords();

                if(records.size()>0){
                    DealerSendAdapterToday recordTodayAdapter=new DealerSendAdapterToday(records,getContext(),2);
                    today_record_container.setAdapter(recordTodayAdapter);
                }
            }

            @Override
            public void onFailure(Call<DealerSendResponse> call, Throwable t) {

            }
        });

    }
}
