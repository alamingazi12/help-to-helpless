package com.example.help2helpless;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.help2helpless.model.Admin;
import com.example.help2helpless.model.AdminResponse;
import com.example.help2helpless.network.ApiClient;
import com.example.help2helpless.network.ApiInterface;
import com.muddzdev.styleabletoast.StyleableToast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminLogin extends AppCompatActivity {
    Button admin_login;
    EditText adusernme,adpasswrd;
    ArrayList<Admin> admins;
    ProgressBar progressBar;
    ProgressDialog dialogue;
    SharedPreferences adminSharedPreference;
    SharedPreferences.Editor editor;
    @Override

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminlogin);
        //setFontToActionBar();
        initAll();
        admin_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(adusernme.getText().toString().trim()) || TextUtils.isEmpty(adpasswrd.getText().toString().trim())){
                    StyleableToast.makeText(AdminLogin.this,"Fields Empty", R.style.mytoast).show();
                }else{
                    login();
                }
            }
        });
    }

    public  void showProgress(){
        dialogue=new ProgressDialog(this);
        dialogue.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialogue.setTitle("Processing");
        dialogue.setMessage("Please Wait...");
        dialogue.setCanceledOnTouchOutside(false);
        dialogue.show();

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
        showProgress();
        ApiInterface apiInterface= ApiClient.getApiClient(AdminLogin.this).create(ApiInterface.class);
        Call<AdminResponse> adminResponseCall=apiInterface.getAdminResponse(adusernme.getText().toString().trim(),adpasswrd.getText().toString().trim());
        adminResponseCall.enqueue(new Callback<AdminResponse>() {
            @Override
            public void onResponse(Call<AdminResponse> call, Response<AdminResponse> response) {
               admins=response.body().getResult();
               if(admins.size()>0){
                   dialogue.cancel();
                   Admin admin=admins.get(0);

                   editor.putString("adminuser",admin.getAname());
                   //StyleableToast.makeText(AdminLogin.this,"Usernames"+adminSharedPreference.getString("adminuser",""),R.style.mytoast).show();
                 //  StyleableToast.makeText(AdminLogin.this,"Usernames"+admin.getAname(),R.style.mytoast).show();
                   editor.apply();
                   adusernme.setText("");
                   adpasswrd.setText("");

                   Intent intent=new Intent(AdminLogin.this,AdminDashBoardActivity.class);
                   startActivity(intent);
               }else{
                   dialogue.cancel();

                   StyleableToast.makeText(AdminLogin.this,"Wrong Username and Password",R.style.mytoast).show();
                }
            }

            @Override
            public void onFailure(Call<AdminResponse> call, Throwable t) {
                dialogue.cancel();
                StyleableToast.makeText(AdminLogin.this,"Network Error",R.style.mytoast).show();
            }
        });


    }

    private void initAll() {
       if(adminSharedPreference==null){
          adminSharedPreference=getSharedPreferences("admininfo",0);
          editor=adminSharedPreference.edit();

       }
        adusernme=findViewById(R.id.admin_username);
        adpasswrd=findViewById(R.id.admin_password);
        admin_login=findViewById(R.id.admin_login);
    }
}
