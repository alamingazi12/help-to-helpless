package com.example.help2helpless;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.help2helpless.adapter.MenuAdapter;
import com.example.help2helpless.model.MenuItem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.muddzdev.styleabletoast.StyleableToast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    FloatingActionButton floatingActionButton;
    Toolbar toolbar;
    ArrayList<MenuItem> items;
    public static DrawerLayout drawer;
    RecyclerView menucontentitems;
    SharedPreferences usertype;
    SharedPreferences.Editor editor;

    Button btn_dealer, btn_donar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

          inItAll();

       btn_dealer.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               SharedPreferences    adminSharedPreference=getSharedPreferences("admininfo",0);
               String user= adminSharedPreference.getString("adminuser","");
               SharedPreferences   donarinfo=getSharedPreferences("donarinfo",0);
               if(!donarinfo.getString("contact","").equals("")){

                   StyleableToast.makeText(MainActivity.this,"You Have Logged in as Donar",R.style.mytoast).show();
               }

                   else if(!TextUtils.isEmpty(user)){
                       StyleableToast.makeText(MainActivity.this,"You Have Logged in as Admin",R.style.mytoast).show();
                   }

               else {
                   SharedPreferences dealerlogininfo=getSharedPreferences("dealerinfo",0);
                   if(dealerlogininfo.getString("contact","").equals("")){
                       editor.putString("type","dealer");
                       editor.apply();
                       Intent intent=new Intent(MainActivity.this, DonarLogin.class);
                       startActivity(intent);
                   }else{
                       Intent intent=new Intent(MainActivity.this, DealerActivity.class);
                       startActivity(intent);

                   }
               }

           }
       });

       btn_donar.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               SharedPreferences    adminSharedPreference=getSharedPreferences("admininfo",0);
               String user= adminSharedPreference.getString("adminuser","");


               SharedPreferences dealerlogininfo=getSharedPreferences("dealerinfo",0);
               if(!dealerlogininfo.getString("contact","").equals("")){

                    StyleableToast.makeText(MainActivity.this,"You Have Logged in as Dealer",R.style.mytoast).show();
               }
               else if(!TextUtils.isEmpty(user)){

                   StyleableToast.makeText(MainActivity.this,"You Have Logged in as Admin",R.style.mytoast).show();
               }
               else{
                   SharedPreferences   donarinfo=getSharedPreferences("donarinfo",0);
                   if(donarinfo.getString("contact","").equals("")){

                       editor.putString("type","donar");
                       editor.apply();
                       Intent intent=new Intent(MainActivity.this, DonarLogin.class);
                       startActivity(intent);
                   }else{
                       Intent intent=new Intent(MainActivity.this, DonarDashBoardActivity.class);
                       startActivity(intent);
                   }
               }



           }
       });

    }

    private void inItAll() {
         if(usertype==null){

             usertype=getSharedPreferences("typedata",0);
             editor= usertype.edit();
         }
        btn_dealer=findViewById(R.id.btn_dealer);
        btn_donar=findViewById(R.id.btn_donar);
        floatingActionButton=findViewById(R.id.orderPlus);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle=new Bundle();
                bundle.putString("main","data");
                Intent intent=new Intent(MainActivity.this,SettingActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }









    @Override
    public void onBackPressed() {

            super.onBackPressed();

    }


    }

