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
import com.example.help2helpless.network.ApiClient;
import com.example.help2helpless.network.ApiInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminLogin extends AppCompatActivity {
    Button admin_login;
    EditText adusernme,adpasswrd;
    ArrayList<Admin> admins;


    @Override

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminlogin);
        initAll();
        admin_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
    }

    private void login() {
        ApiInterface apiInterface= ApiClient.getApiClient(AdminLogin.this).create(ApiInterface.class);
        Call<AdminResponse> adminResponseCall=apiInterface.getAdminResponse(adusernme.getText().toString(),adpasswrd.getText().toString());
        adminResponseCall.enqueue(new Callback<AdminResponse>() {
            @Override
            public void onResponse(Call<AdminResponse> call, Response<AdminResponse> response) {
               admins=response.body().getResult();
               if(admins.size()>0){

                   Toast.makeText(AdminLogin.this,"Loged in Successfully",Toast.LENGTH_LONG).show();
               }else{
                   Toast.makeText(AdminLogin.this,"Wrong Username and Password",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<AdminResponse> call, Throwable t) {

            }
        });


    }

    private void initAll() {
        adusernme=findViewById(R.id.adusername);
        adpasswrd=findViewById(R.id.adpasswrd);
        admin_login=findViewById(R.id.admbutton_login);
    }
}
