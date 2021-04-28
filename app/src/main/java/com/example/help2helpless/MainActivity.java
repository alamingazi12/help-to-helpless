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

               SharedPreferences   donarinfo=getSharedPreferences("donarinfo",0);
               if(donarinfo.getString("contact","").equals("")){
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

               }else {
                   StyleableToast.makeText(MainActivity.this,"You Have Logged in as Donar",R.style.mytoast).show();
               }

           }
       });

       btn_donar.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               SharedPreferences dealerlogininfo=getSharedPreferences("dealerinfo",0);
               if(dealerlogininfo.getString("contact","").equals("")){
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

               }else {

                   StyleableToast.makeText(MainActivity.this,"You Have Logged in as Dealer",R.style.mytoast).show();
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
                Intent intent=new Intent(MainActivity.this,SettingActivity.class);
                startActivity(intent);
            }
        });
    }









    @Override
    public void onBackPressed() {

            super.onBackPressed();

    }


    }

