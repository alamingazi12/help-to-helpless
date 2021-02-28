package com.example.help2helpless;

import android.content.Intent;
import android.content.SharedPreferences;
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

import com.example.help2helpless.model.Donar;
import com.example.help2helpless.model.DonarResponse;
import com.example.help2helpless.network.ApiClient;
import com.example.help2helpless.network.ApiInterface;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DonarLogin extends AppCompatActivity {
    ArrayList<Donar> donars;
    Button donar_login;
    TextInputLayout dusernme,dpasswrd;

    SharedPreferences donarsharedpreference;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donar_login);
        setFontToActionBar();
        initAll();
        donar_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
    }
    private void setFontToActionBar() {
        TextView tv = new TextView(DonarLogin.this);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        tv.setLayoutParams(lp);
        tv.setText("Donar Login");
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

    private void initAll() {
        dusernme=findViewById(R.id.dsername);
        dpasswrd=findViewById(R.id.dpasswrd);
        donar_login=findViewById(R.id.dbutton_login);

        donarsharedpreference=  this.getSharedPreferences("donarinfo",0);
        editor=donarsharedpreference.edit();
    }

    private void login() {
        ApiInterface apiInterface= ApiClient.getApiClient(DonarLogin.this).create(ApiInterface.class);
        Call<DonarResponse> donarResponseCall=apiInterface.getDonarResponse(dusernme.getEditText().getText().toString(),dpasswrd.getEditText().getText().toString());
        donarResponseCall.enqueue(new Callback<DonarResponse>() {
          @Override
          public void onResponse(Call<DonarResponse> call, Response<DonarResponse> response) {
              donars=response.body().getUsers();
             if(donars.size()>0){
                 Donar donar=donars.get(0);
                  editor.putString("uname",donar.getUsernm());
                  editor.putString("contact",donar.getDcontact());
                  editor.putString("zilla",donar.getZilla());
                  editor.putBoolean("login",true);
                  editor.apply();
                 // Toast.makeText(AdminLogin.this,"Loged in Successfully",Toast.LENGTH_LONG).show();
                  Intent intent=new Intent(DonarLogin.this,DonarDashBoardActivity.class);
                  startActivity(intent);
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
