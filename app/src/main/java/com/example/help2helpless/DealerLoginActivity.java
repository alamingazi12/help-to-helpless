package com.example.help2helpless;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.help2helpless.model.Dealer;
import com.example.help2helpless.model.DealerResponse;
import com.example.help2helpless.model.Donar;
import com.example.help2helpless.model.DonarResponse;
import com.example.help2helpless.network.ApiClient;
import com.example.help2helpless.network.ApiInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DealerLoginActivity extends AppCompatActivity {

    ArrayList<Dealer> dealers;
    Button dealer_login;
    EditText dealeruname,dealerpass;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dealer_login);
        initAll();
        dealer_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
        
    }

    private void login() {
        ApiInterface apiInterface= ApiClient.getApiClient(DealerLoginActivity.this).create(ApiInterface.class);
        Call<DealerResponse> dealerResponseCall=apiInterface.getDealerResponse(dealeruname.getText().toString(),dealerpass.getText().toString());
    dealerResponseCall.enqueue(new Callback<DealerResponse>() {
        @Override
        public void onResponse(Call<DealerResponse> call, Response<DealerResponse> response) {
            dealers=response.body().getDealers();
            if(dealers.size()>0){
                Toast.makeText(DealerLoginActivity.this," You Loged in Successfully",Toast.LENGTH_LONG).show();
            }else {
                Toast.makeText(DealerLoginActivity.this,"Wrong Username and Password",Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onFailure(Call<DealerResponse> call, Throwable t) {

        }
    });


    }

    private void initAll() {
        dealeruname=findViewById(R.id.dlrname);
        dealerpass=findViewById(R.id.dlrpass);
        dealer_login=findViewById(R.id.dlr_login);
    }
}
