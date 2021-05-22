package com.example.help2helpless;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.help2helpless.model.Essentials;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.muddzdev.styleabletoast.StyleableToast;

public class SettingActivity extends AppCompatActivity {
    CardView admin_cardview;
    FloatingActionButton fleatin_button;
    ImageButton btn_image_back;
    String type;
    Button button_logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initAll();
        if(!Essentials.userExist(this)){
            button_logout.setText("Login");
        }



        btn_image_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

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


        Bundle bundle= getIntent().getExtras();
        String data_from_main=bundle.getString("main");
        if(TextUtils.isEmpty(data_from_main)){


        }else {
            if(data_from_main.equals("data")){
                button_logout.setVisibility(View.INVISIBLE);
            }

        }

        button_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(button_logout.getText().toString().equals("Login")){
                   Intent intent=new Intent(SettingActivity.this,MainActivity.class);
                   startActivity(intent);

                }else{

                    Essentials.logout(SettingActivity.this);
                    /*

                    Bundle bundle= getIntent().getExtras();
                    String type= bundle.getString("user_type");
                    if(TextUtils.isEmpty(type)){
                        adminLogout();
                        //StyleableToast.makeText(SettingActivity.this,"its Empty",R.style.mytoast).show();
                    }else{
                        if(type.equals("donar")){
                            logout();
                        }
                        else if(type.equals("dealer")){
                            dealerLogout();
                        }
                    }
*/
                }






            }
        });

        fleatin_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SettingActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
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
        button_logout=findViewById(R.id.btn_logout);
        btn_image_back=findViewById(R.id.btn_back);
        fleatin_button=findViewById(R.id.orderPlus);
        admin_cardview=findViewById(R.id.card_admin);
    }

}