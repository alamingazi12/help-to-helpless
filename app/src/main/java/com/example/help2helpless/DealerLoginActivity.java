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
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.help2helpless.model.Dealer;
import com.example.help2helpless.model.DealerResponse;
import com.example.help2helpless.network.ApiClient;
import com.example.help2helpless.network.ApiInterface;
import com.google.gson.Gson;
import com.muddzdev.styleabletoast.StyleableToast;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DealerLoginActivity extends AppCompatActivity {
    ProgressDialog dialogue;
    SharedPreferences dealerlogininfo;
    SharedPreferences.Editor dealer_editor;
    ArrayList<Dealer> dealers;
    Button dealer_login;
    EditText dlrphone, dlr_pass;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dealer_login);
        //setFontToActionBar();
        initAll();
        dealer_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(dlrphone.getText().toString().trim()) || TextUtils.isEmpty(dlr_pass.getText().toString().trim())){
                    StyleableToast.makeText(DealerLoginActivity.this,"One or More Fields Empty", R.style.mytoast).show();
                }else{
                    dealerLogins();
                }
            }
        });
        
    }

    private void setFontToActionBar() {
        TextView tv = new TextView(DealerLoginActivity.this);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        tv.setLayoutParams(lp);
        tv.setText("Dealer Login");
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

    private void dealerLogin() {
        showProgress();
        ApiInterface apiInterface= ApiClient.getApiClient(DealerLoginActivity.this).create(ApiInterface.class);
        Call<DealerResponse> dealerResponseCall=apiInterface.getDealerResponse(dlrphone.getText().toString().trim(), dlr_pass.getText().toString().trim());
        dealerResponseCall.enqueue(new Callback<DealerResponse>() {
        @Override
        public void onResponse(Call<DealerResponse> call, Response<DealerResponse> response) {
            dealers=response.body().getDealers();

            if(dealers.size()>0){
              dialogue.cancel();
                Dealer dealer=  dealers.get(0);

                Gson gson = new Gson();
                String json = gson.toJson(dealer); // myObject - instance of MyObject
                dealer_editor.putString("MyObject", json);
                //getting object
              //  Gson gson = new Gson();
              //  String json = mPrefs.getString("MyObject", "");
               // MyObject obj = gson.fromJson(json, MyObject.class);

                dealer_editor.putString("dname", dealer.getName());
                dealer_editor.putString("contact", dealer.getPhone());
                dealer_editor.putString("Zilla", dealer.getShpnmzilla());

                dealer_editor.apply();
                dlrphone.setText("");
                dlr_pass.setText("");
               // Toast.makeText(DealerLoginActivity.this," You Loged in Successfully",Toast.LENGTH_LONG).show();
                Intent intent=new Intent(DealerLoginActivity.this,DealerActivity.class);
                startActivity(intent);
            }else {
                dialogue.cancel();
                StyleableToast.makeText(DealerLoginActivity.this,"Wrong Username and Password",R.style.mytoast).show();
            }
        }

        @Override
        public void onFailure(Call<DealerResponse> call, Throwable t) {
            dialogue.cancel();
            StyleableToast.makeText(DealerLoginActivity.this,"Network Error",R.style.mytoast).show();
        }
    });


    }


    private void dealerLogins() {
        showProgress();
        ApiInterface apiInterface= ApiClient.getApiClient(DealerLoginActivity.this).create(ApiInterface.class);
        Call<DealerResponse> dealerResponseCall=apiInterface.getDealerResponse(dlrphone.getText().toString().trim(), dlr_pass.getText().toString().trim());
        dealerResponseCall.enqueue(new Callback<DealerResponse>() {
            @Override
            public void onResponse(Call<DealerResponse> call, Response<DealerResponse> response) {
                dealers=response.body().getDealers();

                if(dealers.size()>0){
                    dialogue.cancel();
                    Dealer dealer=  dealers.get(0);
                    Gson gson = new Gson();
                    String json = gson.toJson(dealer); // myObject - instance of MyObject
                    dealer_editor.putString("MyObject", json);
                    //getting object
                    //  Gson gson = new Gson();
                    //  String json = mPrefs.getString("MyObject", "");
                    // MyObject obj = gson.fromJson(json, MyObject.class);
                    dealer_editor.putString("dname", dealer.getName());
                    dealer_editor.putString("contact", dealer.getPhone());
                    dealer_editor.putString("Zilla", dealer.getShpnmzilla());
                    dealer_editor.putString("dealer_pic", dealer.getProfile_pic());

                    dealer_editor.apply();
                    dlrphone.setText("");
                    dlr_pass.setText("");
                    // Toast.makeText(DealerLoginActivity.this," You Loged in Successfully",Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(DealerLoginActivity.this,DealerActivity.class);
                    startActivity(intent);
                }else {
                    dialogue.cancel();
                    StyleableToast.makeText(DealerLoginActivity.this,""+response.body().getMessage(),R.style.mytoast).show();
                }
            }

            @Override
            public void onFailure(Call<DealerResponse> call, Throwable t) {
                dialogue.cancel();
                StyleableToast.makeText(DealerLoginActivity.this,"Network Error",R.style.mytoast).show();
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
    private void initAll() {
        if(dealerlogininfo==null){
            dealerlogininfo=getSharedPreferences("dealerinfo",0);
            dealer_editor=dealerlogininfo.edit();
        }

        dlrphone =findViewById(R.id.dlphone);
        dlr_pass =findViewById(R.id.dpass);
        dealer_login=findViewById(R.id.d_login);
    }
}
