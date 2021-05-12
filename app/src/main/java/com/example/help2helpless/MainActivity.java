package com.example.help2helpless;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;
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

    SharedPreferences dealerlogininfo;
    SharedPreferences    adminSharedPreference;
    SharedPreferences   donarinfo;

    Button btn_dealer, btn_donar;
    ImageButton menu;

    String admin_user;
    String dealer_contact;
    String donar_contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

          inItAll();

       btn_dealer.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
              // SharedPreferences    adminSharedPreference=getSharedPreferences("admininfo",0);
              // String user= adminSharedPreference.getString("adminuser","");
                 // donarinfo=getSharedPreferences("donarinfo",0);
               if(!TextUtils.isEmpty(donar_contact)){
                   StyleableToast.makeText(MainActivity.this,"You Have Logged in as Donar",R.style.mytoast).show();
               }

                   else if(!TextUtils.isEmpty(admin_user)){
                       StyleableToast.makeText(MainActivity.this,"You Have Logged in as Admin",R.style.mytoast).show();
                   }

               else {
                  // SharedPreferences dealerlogininfo=getSharedPreferences("dealerinfo",0);
                   if(TextUtils.isEmpty(dealer_contact)){
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


        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(MainActivity.this, menu);
                //inflating menu from xml resource
                popup.inflate(R.menu.admin_menu_item);
                //adding click listener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(android.view.MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.logout:
                                logout();
                                break;
                            case R.id.settings:
                                goSettings();
                                break;
                        }
                        return false;
                    }
                });
                popup.show();
            }
        });

       btn_donar.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
                // adminSharedPreference=getSharedPreferences("admininfo",0);
                //admin_user= adminSharedPreference.getString("adminuser","");


              // dealerlogininfo=getSharedPreferences("dealerinfo",0);
               if(!TextUtils.isEmpty(dealer_contact)){

                    StyleableToast.makeText(MainActivity.this,"You Have Logged in as Dealer",R.style.mytoast).show();
               }
               else if(!TextUtils.isEmpty(admin_user)){

                   StyleableToast.makeText(MainActivity.this,"You Have Logged in as Admin",R.style.mytoast).show();
               }
               else{
                 //  SharedPreferences   donarinfo=getSharedPreferences("donarinfo",0);
                   if(TextUtils.isEmpty(donar_contact)){

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

    private void logout() {

        SharedPreferences    adminSharedPreference=getSharedPreferences("admininfo",0);
        String user= adminSharedPreference.getString("adminuser","");
        SharedPreferences   donarinfo=getSharedPreferences("donarinfo",0);

        SharedPreferences dealerlogininfo=getSharedPreferences("dealerinfo",0);
        if(!dealerlogininfo.getString("contact","").equals("")){
            SharedPreferences.Editor   dealer_editor=dealerlogininfo.edit();
            dealer_editor.remove("contact");
            dealer_editor.commit();
            StyleableToast.makeText(MainActivity.this,"You Logged out Successfully",R.style.greentoast).show();
        }
        else if(!TextUtils.isEmpty(user)){
            SharedPreferences.Editor editor = adminSharedPreference.edit();
            editor.remove("adminuser");
            editor.commit();
            StyleableToast.makeText(MainActivity.this,"You Logged out Successfully",R.style.greentoast).show();
        }
        else if(!donarinfo.getString("contact","").equals("")){
            SharedPreferences.Editor donar_editor = donarinfo.edit();
            donar_editor.remove("contact");
            donar_editor.commit();
            StyleableToast.makeText(MainActivity.this,"You Logged out Successfully",R.style.greentoast).show();
        }else {
           StyleableToast.makeText(MainActivity.this,"You Did,nt Login",R.style.mytoast).show();
        }

    }

    public void goSettings(){
        Bundle bundle=new Bundle();
        bundle.putString("main","");
        Intent intent=new Intent(MainActivity.this,SettingActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void inItAll() {
     //   init sharedPreference
        dealerlogininfo=getSharedPreferences("dealerinfo",0);
        dealer_contact=  dealerlogininfo.getString("contact","");
        adminSharedPreference=getSharedPreferences("admininfo",0);
        donarinfo=getSharedPreferences("donarinfo",0);
        donar_contact=  donarinfo.getString("contact","");

        admin_user= adminSharedPreference.getString("adminuser","");

         if(usertype==null){

             usertype=getSharedPreferences("typedata",0);
             editor= usertype.edit();
         }
        menu=findViewById(R.id.admin_menu_icon);
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
            finishAffinity();
    }


    }

