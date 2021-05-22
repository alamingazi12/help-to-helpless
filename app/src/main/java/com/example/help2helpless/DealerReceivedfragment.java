package com.example.help2helpless;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.help2helpless.adapter.RecordTodayAdapter;
import com.example.help2helpless.dealeradapter.DealerReceiveAdapterOlder;
import com.example.help2helpless.dealeradapter.DealerReceiveAdapterToday;
import com.example.help2helpless.model.DealerReceiveRecord;
import com.example.help2helpless.model.DealerReciveResponse;
import com.example.help2helpless.model.RecordResponse;
import com.example.help2helpless.network.ApiClient;
import com.example.help2helpless.network.ApiInterface;
import com.muddzdev.styleabletoast.StyleableToast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DealerReceivedfragment extends Fragment {

    EditText editText_input_search;
    Button btn_seach_history;

    String search_text="";
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
        View view= inflater.inflate(R.layout.dealer_fragment_received, container, false);
        initAll(view);
        showTodayRecords();
        showYesterdayRecords();
        showOlderRecords();
       // showOlderRecords();
        if(TextUtils.isEmpty(search_text)){
            showOlderRecords();
        }

        btn_seach_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search_text=editText_input_search.getText().toString();
                if(TextUtils.isEmpty(search_text)){
                    StyleableToast.makeText(getContext(),"Search Box Empty",R.style.mytoast).show();
                }else{
                    showOlderRecords();
                }
            }
        });
        editText_input_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String data= editable.toString();
                search_text=editText_input_search.getText().toString();
                if(TextUtils.isEmpty(data) || TextUtils.isEmpty(search_text)){
                  //  StyleableToast.makeText(getContext(),"Search Box Empty",R.style.mytoast).show();
                    showOlderRecords();
                }
            }
        });
        return view;
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
        apiInterface.getDealerReceiveRecordOlder(contact,yesterday,search_text).enqueue(new Callback<DealerReciveResponse>() {
            @Override
            public void onResponse(Call<DealerReciveResponse> call, Response<DealerReciveResponse> response) {
                ArrayList<DealerReceiveRecord> records= response.body().getRecords();

                if(records.size()>0){
                    progressBar.setVisibility(View.GONE);
                    progressBar.setVisibility(View.GONE);
                    DealerReceiveAdapterOlder recordTodayAdapter=new DealerReceiveAdapterOlder(records,getContext(),2);
                    older_record_container.setAdapter(recordTodayAdapter);
                }
            }

            @Override
            public void onFailure(Call<DealerReciveResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });

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
        apiInterface.getDealerReceiveRecordToday(contact,yesterday).enqueue(new Callback<DealerReciveResponse>() {
            @Override
            public void onResponse(Call<DealerReciveResponse> call, Response<DealerReciveResponse> response) {
          ArrayList<DealerReceiveRecord> records= response.body().getRecords();

                if(records.size()>0){

                    DealerReceiveAdapterToday recordTodayAdapter=new DealerReceiveAdapterToday(records,getContext(),2);
                    yesterday_record_container.setAdapter(recordTodayAdapter);
                }
            }

            @Override
            public void onFailure(Call<DealerReciveResponse> call, Throwable t) {

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
        apiInterface.getDealerReceiveRecordToday(contact,today).enqueue(new Callback<DealerReciveResponse>() {
            @Override
            public void onResponse(Call<DealerReciveResponse> call, Response<DealerReciveResponse> response) {
                ArrayList<DealerReceiveRecord> records= response.body().getRecords();

                if(records.size()>0){
                    DealerReceiveAdapterToday recordTodayAdapter=new DealerReceiveAdapterToday(records,getContext(),2);
                    today_record_container.setAdapter(recordTodayAdapter);
                }
            }

            @Override
            public void onFailure(Call<DealerReciveResponse> call, Throwable t) {

            }
        });

    }

    private void initAll(View view) {
        progressBar=view.findViewById(R.id._progress);
        editText_input_search=view.findViewById(R.id.edit_search_history);
        btn_seach_history=view.findViewById(R.id.btn_search_history);
        //today container;
        today_record_container=view.findViewById(R.id.donar_today_container);
        today_record_container.setHasFixedSize(true);
        today_record_container.setLayoutManager(new LinearLayoutManager(getContext()));
        today_record_container.getRecycledViewPool().setMaxRecycledViews(0,20);
        // yesterday container
        yesterday_record_container=view.findViewById(R.id.donar_yesterday_container);
        yesterday_record_container.setHasFixedSize(true);
        yesterday_record_container.setLayoutManager(new LinearLayoutManager(getContext()));
        yesterday_record_container.getRecycledViewPool().setMaxRecycledViews(0,20);
        //older container
        older_record_container=view.findViewById(R.id.donar_old_container);
        older_record_container.setHasFixedSize(true);
        older_record_container.setLayoutManager(new LinearLayoutManager(getContext()));
        older_record_container.getRecycledViewPool().setMaxRecycledViews(0,20);
    }
}
