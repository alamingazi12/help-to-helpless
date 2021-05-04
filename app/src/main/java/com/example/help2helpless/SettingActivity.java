package com.example.help2helpless;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.muddzdev.styleabletoast.StyleableToast;

public class SettingActivity extends AppCompatActivity {
    CardView admin_cardview;
    String type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initAll();



        admin_cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences   donarinfo=getSharedPreferences("donarinfo",0);
                SharedPreferences dealerlogininfo=getSharedPreferences("dealerinfo",0);
                if(!dealerlogininfo.getString("contact","").equals("")){
                    StyleableToast.makeText(SettingActivity.this,"You Have Logged in as Dealer",R.style.mytoast).show();
                       // Intent intent=new Intent(SettingActivity.this, DonarLogin.class);
                      //  startActivity(intent);
                    }

                   else if(!donarinfo.getString("contact","").equals("")){
                    StyleableToast.makeText(SettingActivity.this,"You Have Logged in as Donar",R.style.mytoast).show();
                        }
                   else {

                    SharedPreferences    adminSharedPreference=getSharedPreferences("admininfo",0);
                    String user=    adminSharedPreference.getString("adminuser","");
                    if(TextUtils.isEmpty(user)){
                        Intent intent=new Intent(SettingActivity.this,AdminLogin.class);
                        startActivity(intent);
                    }else{
                        Intent intent=new Intent(SettingActivity.this,AdminDashBoardActivity.class);
                        startActivity(intent);
                    }
                }

/*

*/
            }
        });

        Button button=findViewById(R.id.btn_logout);
        Bundle bundle= getIntent().getExtras();
        String data_from_main=bundle.getString("main");
        if(TextUtils.isEmpty(data_from_main)){


        }else {
            if(data_from_main.equals("data")){
                button.setVisibility(View.INVISIBLE);
            }

        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
         Bundle bundle= getIntent().getExtras();
         String type= bundle.getString("user_type");
             if(TextUtils.isEmpty(type)){
                 StyleableToast.makeText(SettingActivity.this,"its Empty",R.style.mytoast).show();
             }else{
                 if(type.equals("donar")){
                     logout();
                 }
                 else if(type.equals("dealer")){
                     dealerLogout();
                 }else{
                     adminLogout();
                 }
             }




            }
        });
    }

    private void adminLogout() {
        SharedPreferences adminSharedPreference = getSharedPreferences("admininfo", 0);
        SharedPreferences.Editor editor = adminSharedPreference.edit();
        editor.remove("adminuser");
        editor.commit();
        Intent intent = new Intent(SettingActivity.this, MainActivity.class);
        startActivity(intent);
    }


    private void logout() {
        SharedPreferences dealerlogininfo = getSharedPreferences("donarinfo", 0);
        SharedPreferences.Editor dealer_editor = dealerlogininfo.edit();
        dealer_editor.remove("contact");
        dealer_editor.commit();
        Intent intent = new Intent(SettingActivity.this, MainActivity.class);
        startActivity(intent);
    }

    private void dealerLogout() {
        SharedPreferences   dealerlogininfo=getSharedPreferences("dealerinfo",0);
        SharedPreferences.Editor   dealer_editor=dealerlogininfo.edit();
        dealer_editor.remove("contact");
        dealer_editor.commit();
        Intent intent=new Intent(SettingActivity.this,MainActivity.class);
        startActivity(intent);
    }

    private void initAll() {

        admin_cardview=findViewById(R.id.card_admin);
    }
}