package com.example.help2helpless;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.help2helpless.adapter.DonarAdapter;
import com.example.help2helpless.model.AllDonar;
import com.example.help2helpless.model.Donar;
import com.example.help2helpless.model.TotalDonars;
import com.example.help2helpless.network.ApiClient;
import com.example.help2helpless.network.ApiInterface;

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
        initAll();
        getAllDonar();
    }

    private void initAll() {
      donar_item_container=findViewById(R.id.donar_item_container);
      donar_item_container.setHasFixedSize(true);
      donar_item_container.setLayoutManager(new LinearLayoutManager(this));
    }

    private void getAllDonar() {

        ApiInterface apiInterface= ApiClient.getApiClient(AddDonarActivity.this).create(ApiInterface.class);
        Call<AllDonar> allDonarCall= apiInterface.getTotalDonar("12335667");
        allDonarCall.enqueue(new Callback<AllDonar>() {
            @Override
            public void onResponse(Call<AllDonar> call, Response<AllDonar> response) {
             donarList= response.body().getDealers();
             if(donarList.size()>0){
                 DonarAdapter donarAdapter=new DonarAdapter(donarList,AddDonarActivity.this);
                 donar_item_container.setAdapter(donarAdapter);
                 Toast.makeText(AddDonarActivity.this,"gets data",Toast.LENGTH_LONG).show();
             }
            }

            @Override
            public void onFailure(Call<AllDonar> call, Throwable t) {
                Toast.makeText(AddDonarActivity.this,"Network Error",Toast.LENGTH_LONG).show();
            }
        });

    }

}