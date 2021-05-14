package com.example.help2helpless.model;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;

import com.example.help2helpless.AdminDashBoardActivity;
import com.example.help2helpless.DealerActivity;
import com.example.help2helpless.DealerRequestActivity;
import com.example.help2helpless.DonarDashBoardActivity;
import com.example.help2helpless.DonarLogin;
import com.example.help2helpless.MainActivity;
import com.example.help2helpless.R;
import com.example.help2helpless.SettingActivity;
import com.example.help2helpless.network.ApiClient;
import com.example.help2helpless.network.ApiInterface;
import com.google.gson.Gson;
import com.muddzdev.styleabletoast.StyleableToast;

public class Essentials {
    static SharedPreferences donarlogininfo;
    static SharedPreferences dealerlogininfo;
    static   SharedPreferences.Editor donar_editor;
    static   SharedPreferences.Editor dealer_editor;
    static   ProgressDialog dialogue;


    public Essentials(Context context) {

        if(donarlogininfo==null){
            donarlogininfo=context.getSharedPreferences("donarinfo",0);
            donar_editor=donarlogininfo.edit();
        }

    }

    public static void goDonarHome(Context context) {
      Intent intent=new Intent(context, DonarDashBoardActivity.class);
      context.startActivity(intent);
    }
    public static void goDealerHome(Context context) {

        Intent intent=new Intent(context, DealerActivity.class);
        context.startActivity(intent);

    }
    public static void goAdminHome(Context context) {
        Intent intent=new Intent(context, AdminDashBoardActivity.class);
        context.startActivity(intent);
    }



   public static void dismissDialogue(){
       dialogue.cancel();

    }

    public static ApiInterface getApiInterface(Context context){
        ApiInterface apiInterface= ApiClient.getApiClient(context).create(ApiInterface.class);
        return apiInterface;
    }
    public static Donar getDonarData(Context context){
        donarlogininfo=context.getSharedPreferences("donarinfo",0);
        Gson gson = new Gson();
        String json = donarlogininfo.getString("MyObject", "");
        Donar   donar = gson.fromJson(json, Donar.class);
        return donar;
    }



    public static void dangerMessage(Context context,String message){
        StyleableToast.makeText(context,message,R.style.mytoast).show();
    }
    public static void dangerMessageValue(Context context,int value){
        StyleableToast.makeText(context,""+value,R.style.mytoast).show();
    }

    public static void SuccessMessage(Context context,String message){
        StyleableToast.makeText(context,message,R.style.greentoast).show();
    }

    public static void showProgress(Context context){
        dialogue=new ProgressDialog(context);
        dialogue.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialogue.setTitle("Processing");
        dialogue.setMessage("Please Wait...");
        dialogue.setCanceledOnTouchOutside(false);
        dialogue.show();
    }

    public static void storeDonarData(Context context,Donar donar){

        if(donarlogininfo==null){
            donarlogininfo=context.getSharedPreferences("donarinfo",0);
            donar_editor=donarlogininfo.edit();
        }

        Gson gson = new Gson();
        String json = gson.toJson(donar); // myObject - instance of MyObject
        donar_editor.putString("MyObject", json);
        donar_editor.apply();
    }

    public static void storeDealerData(Context context,Dealer dealer){

        if(donarlogininfo==null){
            dealerlogininfo=context.getSharedPreferences("dealerinfo",0);
            dealer_editor=dealerlogininfo.edit();
        }

        Gson gson = new Gson();
        String json = gson.toJson(dealer); // myObject - instance of MyObject
        dealer_editor.putString("MyObject", json);
        dealer_editor.apply();
    }

    public Dealer getDealerData(Context context){
        SharedPreferences dealerlogininfo=context.getSharedPreferences("dealerinfo",0);
        Gson gson = new Gson();
        String json = dealerlogininfo.getString("MyObject", "");
        Dealer dealers = gson.fromJson(json, Dealer.class);
        return dealers;
    }

    public  static void goHome(Context context){
        Intent intent=new Intent(context,MainActivity.class);
        context.startActivity(intent);
    }


    public static void  goSettings(Context context){
        Bundle bundle=new Bundle();
        bundle.putString("main","");
        Intent intent=new Intent(context, SettingActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
    private void Adminlogout (Context context) {
        SharedPreferences adminSharedPreference = context.getSharedPreferences("admininfo", 0);
        SharedPreferences.Editor editor = adminSharedPreference.edit();
        editor.remove("adminuser");
        editor.commit();
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }
    public static void logout(Context context) {
        SharedPreferences adminSharedPreference=context.getSharedPreferences("admininfo",0);
        String user= adminSharedPreference.getString("adminuser","");
        SharedPreferences   donarinfo=context.getSharedPreferences("donarinfo",0);

        SharedPreferences dealerlogininfo=context.getSharedPreferences("dealerinfo",0);
        if(!dealerlogininfo.getString("contact","").equals("")){
            SharedPreferences.Editor   dealer_editor=dealerlogininfo.edit();
            dealer_editor.remove("contact");
            dealer_editor.commit();
            StyleableToast.makeText(context,"You Logged out Successfully", R.style.greentoast).show();
            Intent intent=new Intent(context, MainActivity.class);
            context.startActivity(intent);
        }
        else if(!TextUtils.isEmpty(user)){
            SharedPreferences.Editor editor = adminSharedPreference.edit();
            editor.remove("adminuser");
            editor.commit();
            StyleableToast.makeText(context,"You Logged out Successfully",R.style.greentoast).show();
            Intent intent=new Intent(context,MainActivity.class);
            context.startActivity(intent);
        }
        else if(!donarinfo.getString("contact","").equals("")){
            SharedPreferences.Editor donar_editor = donarinfo.edit();
            donar_editor.remove("contact");
            donar_editor.commit();
            StyleableToast.makeText(context,"You Logged out Successfully",R.style.greentoast).show();
            Intent intent=new Intent(context,MainActivity.class);
            context.startActivity(intent);
        }else {
            StyleableToast.makeText(context,"You Did,nt Login",R.style.mytoast).show();
        }

    }
}
