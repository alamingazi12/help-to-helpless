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
import com.example.help2helpless.model.Donar;
import com.example.help2helpless.model.DonarResponse;
import com.example.help2helpless.network.ApiClient;
import com.example.help2helpless.network.ApiInterface;
import com.muddzdev.styleabletoast.StyleableToast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DonarLogin extends AppCompatActivity {
    //ProgressBar progressBar;
    SharedPreferences dealerlogininfo;
    SharedPreferences.Editor dealer_editor;
    ArrayList<Dealer> dealers;

    ArrayList<Donar> donars;
    Button donar_login,go_sign_up;
    EditText donar_phone,dpasswrd;
    View DialogueView;

    ProgressDialog dialogue;

    SharedPreferences donarsharedpreference;
    SharedPreferences.Editor editor;
    String type;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donar_login);
       // setFontToActionBar();
        initAll();

        donar_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              // createDialoge();


                if(TextUtils.isEmpty(donar_phone.getText().toString().trim()) || TextUtils.isEmpty(dpasswrd.getText().toString().trim())){
                    StyleableToast.makeText(DonarLogin.this,"Fields Empty", R.style.mytoast).show();
                }else{
                SharedPreferences    usertype=getSharedPreferences("typedata",0);
                    if(usertype.getString("type","").equals("donar")){
                        login();
                    }else {
                        dealerLogin();
                    }

                }
            }
        });
        go_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(DonarLogin.this,DonarRegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private void dealerLogin() {
        showProgress();
        ApiInterface apiInterface= ApiClient.getApiClient(DonarLogin.this).create(ApiInterface.class);
        Call<DealerResponse> dealerResponseCall=apiInterface.getDealerResponse(donar_phone.getText().toString().trim(),dpasswrd.getText().toString().trim());
        dealerResponseCall.enqueue(new Callback<DealerResponse>() {
            @Override
            public void onResponse(Call<DealerResponse> call, Response<DealerResponse> response) {
                dealers=response.body().getDealers();

                if(dealers.size()>0){
                    dialogue.cancel();
                    Dealer dealer=  dealers.get(0);
                    dealer_editor.putString("dname", dealer.getName());
                    dealer_editor.putString("contact", dealer.getPhone());
                    dealer_editor.putString("uname", dealer.getUsernm());
                    dealer_editor.putString("Zilla", dealer.getShpnmzilla());
                    dealer_editor.apply();
                    donar_phone.setText("");
                    dpasswrd.setText("");
                    // Toast.makeText(DealerLoginActivity.this," You Loged in Successfully",Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(DonarLogin.this,DealerActivity.class);
                    startActivity(intent);
                }else {
                    dialogue.cancel();
                    StyleableToast.makeText(DonarLogin.this,"Wrong Username and Password",R.style.mytoast).show();
                }
            }

            @Override
            public void onFailure(Call<DealerResponse> call, Throwable t) {
                dialogue.cancel();
                StyleableToast.makeText(DonarLogin.this,"Network Error",R.style.mytoast).show();
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

        //progressBar=findViewById(R.id.progrss_login);

        donar_phone =findViewById(R.id.donar_phone);
        dpasswrd=findViewById(R.id.donar_pass);

        //Button
        go_sign_up=findViewById(R.id.go_donar_signup);
        donar_login=findViewById(R.id.donar_login);
        if(donarsharedpreference==null){

            donarsharedpreference=  this.getSharedPreferences("donarinfo",0);
            editor=donarsharedpreference.edit();
        }
        if(dealerlogininfo==null){
            dealerlogininfo=getSharedPreferences("dealerinfo",0);
            dealer_editor=dealerlogininfo.edit();
        }

    }
    public  void showProgress(){
        dialogue=new ProgressDialog(this);
        dialogue.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialogue.setTitle("Processing");
        dialogue.setMessage("Please Wait...");
        dialogue.setCanceledOnTouchOutside(false);
        dialogue.show();

    }

    private void login() {
         showProgress();
        ApiInterface apiInterface= ApiClient.getApiClient(DonarLogin.this).create(ApiInterface.class);
        Call<DonarResponse> donarResponseCall=apiInterface.getDonarResponse(donar_phone.getText().toString().trim(),dpasswrd.getText().toString().trim());
        donarResponseCall.enqueue(new Callback<DonarResponse>() {
          @Override
          public void onResponse(Call<DonarResponse> call, Response<DonarResponse> response) {
              donars=response.body().getUsers();
              if(donars.size()>0){
                 dialogue.cancel();
                  Donar donar=donars.get(0);
                  editor.putString("name",donar.getDname());
                  editor.putString("uname",donar.getUsernm());
                  editor.putString("contact",donar.getDcontact());
                  editor.putString("zilla",donar.getZilla());
                  editor.putString("donar_pic",donar.getDonar_photo());
                  editor.putBoolean("login",true);
                  editor.apply();
                 // dialog.dismiss();
                  donar_phone.setText("");
                  dpasswrd.setText("");
                 // Toast.makeText(AdminLogin.this,"Loged in Successfully",Toast.LENGTH_LONG).show();
                  Intent intent=new Intent(DonarLogin.this,DonarDashBoardActivity.class);
                  startActivity(intent);
             }else{
                 dialogue.cancel();
                // dialog.dismiss();
                 StyleableToast.makeText(DonarLogin.this,"Wrong Username and Password",R.style.mytoast).show();
                // Toast.makeText(DonarLogin.this,"Wrong Username and Password",Toast.LENGTH_LONG).show();

             }
          }

          @Override
          public void onFailure(Call<DonarResponse> call, Throwable t) {

              dialogue.cancel();
              StyleableToast.makeText(DonarLogin.this,"Network Error",R.style.mytoast).show();
//              dialog.dismiss();
          }
      });


    }
}
