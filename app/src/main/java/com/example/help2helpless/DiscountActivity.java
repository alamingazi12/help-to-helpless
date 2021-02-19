package com.example.help2helpless;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.help2helpless.adapter.ClientAdapter;
import com.example.help2helpless.model.Client;
import com.example.help2helpless.model.ClientResponse;
import com.example.help2helpless.model.DealerResponse;
import com.example.help2helpless.network.ApiClient;
import com.example.help2helpless.network.ApiInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DiscountActivity extends AppCompatActivity {
     public RecyclerView clientItem_container;
     ArrayList<Client> clients;
     EditText client_contact;
     Button cl_search;
    String url="https://apps.help2helpless.com/search_client.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discount);
        initAll();
        cl_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search_client();
            }
        });

    }

    private void search_client() {
        ApiInterface apiInterface= ApiClient.getApiClient(DiscountActivity.this).create(ApiInterface.class);
        Call<ClientResponse> clientResponseCall=apiInterface.fetchClient("567","1234");
        clientResponseCall.enqueue(new Callback<ClientResponse>() {
            @Override
            public void onResponse(Call<ClientResponse> call, Response<ClientResponse> response) {
               clients= response.body().getResult();
                if(clients.size()>0){
                    ClientAdapter clientAdapter=new ClientAdapter(clients,DiscountActivity.this);
                    clientItem_container.setAdapter(clientAdapter);
                }

            }

            @Override
            public void onFailure(Call<ClientResponse> call, Throwable t) {

            }
        });
    }

    private void initAll() {
        cl_search=findViewById(R.id.cl_searchs);
        client_contact=findViewById(R.id.cl_contact);
        clientItem_container=findViewById(R.id.client_container);
        clientItem_container.setHasFixedSize(true);
        clientItem_container.setLayoutManager(new LinearLayoutManager(this));
    }
}