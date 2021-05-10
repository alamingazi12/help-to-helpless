package com.example.help2helpless;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.help2helpless.adapter.RecordOlderAdapter;
import com.example.help2helpless.adapter.RecordReceiveOlderAdapter;
import com.example.help2helpless.adapter.RecordTodayAdapter;
import com.example.help2helpless.model.DonarSendRecord;
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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentReceived#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentReceived extends Fragment {
    RecyclerView today_record_container;
    RecyclerView yesterday_record_container;
    RecyclerView older_record_container;
    ArrayList<DonarSendRecord> records;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentReceived() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentReceived.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentReceived newInstance(String param1, String param2) {
        FragmentReceived fragment = new FragmentReceived();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_received, container, false);
        initAll(view);
        showTodayRecords();
        showYesterdayRecords();
        showOlderRecords();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void showTodayRecords() {
        Date date=new Date();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        String today=sdf.format(date);

        String pdatarr[]=today.split("-");
        SharedPreferences sharedPreferences=getContext().getSharedPreferences("donarinfo",0);
        String contact=  sharedPreferences.getString("contact","") ;
        ApiInterface apiInterface= ApiClient.getApiClient(getContext()).create(ApiInterface.class);
        apiInterface.getDonarReceiveRecordToday(contact,today).enqueue(new Callback<RecordResponse>() {
            @Override
            public void onResponse(Call<RecordResponse> call, Response<RecordResponse> response) {
                records= response.body().getRecordsList();

                if(records.size()>0){
                    RecordTodayAdapter recordTodayAdapter=new RecordTodayAdapter(records,getContext(),2);
                    today_record_container.setAdapter(recordTodayAdapter);
                }
            }

            @Override
            public void onFailure(Call<RecordResponse> call, Throwable t) {

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
        SharedPreferences sharedPreferences=getContext().getSharedPreferences("donarinfo",0);
        String contact=  sharedPreferences.getString("contact","") ;
        ApiInterface apiInterface= ApiClient.getApiClient(getContext()).create(ApiInterface.class);
        apiInterface.getDonarReceiveRecordToday(contact,yesterday).enqueue(new Callback<RecordResponse>() {
            @Override
            public void onResponse(Call<RecordResponse> call, Response<RecordResponse> response) {
                records= response.body().getRecordsList();

                if(records.size()>0){
                    RecordTodayAdapter recordTodayAdapter=new RecordTodayAdapter(records,getContext(),2);
                    yesterday_record_container.setAdapter(recordTodayAdapter);
                }
            }

            @Override
            public void onFailure(Call<RecordResponse> call, Throwable t) {

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
        SharedPreferences sharedPreferences=getContext().getSharedPreferences("donarinfo",0);
        String contact=  sharedPreferences.getString("contact","") ;
        ApiInterface apiInterface= ApiClient.getApiClient(getContext()).create(ApiInterface.class);
        apiInterface.getDonarReceiveRecordOlder(contact,yesterday).enqueue(new Callback<RecordResponse>() {
            @Override
            public void onResponse(Call<RecordResponse> call, Response<RecordResponse> response) {
                records= response.body().getRecordsList();
                if(records.size()>0){
                    RecordReceiveOlderAdapter Adapter=new RecordReceiveOlderAdapter(records,getContext(),2);
                    older_record_container.setAdapter(Adapter);
                }else{
                    StyleableToast.makeText(getContext(),"No Data Here",R.style.mytoast).show();
                }
            }

            @Override
            public void onFailure(Call<RecordResponse> call, Throwable t) {

            }
        });

    }

    private void initAll(View view) {
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
}