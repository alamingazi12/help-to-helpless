package com.example.help2helpless;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.help2helpless.model.Admin;
import com.example.help2helpless.model.AdminResponse;
import com.example.help2helpless.network.ApiClient;
import com.example.help2helpless.network.ApiInterface;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminLogin extends AppCompatActivity {
    Button admin_login;
    TextInputLayout adusernme,adpasswrd;
    ArrayList<Admin> admins;


    @Override

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminlogin);
        setFontToActionBar();
        initAll();
        admin_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
    }

    private void setFontToActionBar() {
        TextView tv = new TextView(AdminLogin.this);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        tv.setLayoutParams(lp);
        tv.setText("Admin Login");
        tv.setTextSize(24);
        tv.setGravity(Gravity.CENTER);
        tv.setTextColor(Color.parseColor("#ffffff"));
        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/regular.otf");
        tv.setTypeface(tf);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(tv);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    private void login() {
        ApiInterface apiInterface= ApiClient.getApiClient(AdminLogin.this).create(ApiInterface.class);
        Call<AdminResponse> adminResponseCall=apiInterface.getAdminResponse(adusernme.getEditText().getText().toString(),adpasswrd.getEditText().getText().toString());
        adminResponseCall.enqueue(new Callback<AdminResponse>() {
            @Override
            public void onResponse(Call<AdminResponse> call, Response<AdminResponse> response) {
               admins=response.body().getResult();
               if(admins.size()>0){
                   Admin admin=admins.get(0);

                  // Toast.makeText(AdminLogin.this,"Loged in Successfully",Toast.LENGTH_LONG).show();
                   Intent intent=new Intent(AdminLogin.this,AdminDashBoardActivity.class);
                 Bundle bundle=new Bundle();
                   bundle.putParcelable("obj",admin);
                  intent.putExtras(bundle);
                  startActivity(intent);
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
