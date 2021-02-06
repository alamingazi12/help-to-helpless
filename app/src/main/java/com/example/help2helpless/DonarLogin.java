package com.example.help2helpless;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.help2helpless.model.Admin;
import com.example.help2helpless.model.AdminResponse;
import com.example.help2helpless.model.Donar;
import com.example.help2helpless.model.DonarResponse;
import com.example.help2helpless.network.ApiClient;
import com.example.help2helpless.network.ApiInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DonarLogin extends AppCompatActivity {
    ArrayList<Donar> donars;
    Button donar_login;
    EditText dusernme,dpasswrd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donar_login);
        initAll();
        donar_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
    }

    private void initAll() {
        dusernme=findViewById(R.id.dsername);
        dpasswrd=findViewById(R.id.dpasswrd);
        donar_login=findViewById(R.id.dbutton_login);
    }

    private void login() {
        ApiInterface apiInterface= ApiClient.getApiClient(DonarLogin.this).create(ApiInterface.class);
        Call<DonarResponse> donarResponseCall=apiInterface.getDonarResponse(dusernme.getText().toString(),dpasswrd.getText().toString());
        donarResponseCall.enqueue(new Callback<DonarResponse>() {
          @Override
          public void onResponse(Call<DonarResponse> call, Response<DonarResponse> response) {
              donars=response.body().getUsers();
             if(donars.size()>0){


                 Toast.makeText(DonarLogin.this,"Loged in Successfully",Toast.LENGTH_LONG).show();
             }else{
                 Toast.makeText(DonarLogin.this,"Wrong Username and Password",Toast.LENGTH_LONG).show();


             }
          }

          @Override
          public void onFailure(Call<DonarResponse> call, Throwable t) {

          }
      });


    }
}
