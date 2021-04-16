package com.example.help2helpless.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.recyclerview.widget.RecyclerView;


import com.example.help2helpless.AdminDashBoardActivity;
import com.example.help2helpless.AdminLogin;
import com.example.help2helpless.DealerActivity;
import com.example.help2helpless.DealerLoginActivity;
import com.example.help2helpless.DealerRegActivity;
import com.example.help2helpless.DonarDashBoardActivity;
import com.example.help2helpless.DonarLogin;
import com.example.help2helpless.DonarRegisterActivity;
import com.example.help2helpless.R;
import com.example.help2helpless.model.MenuItem;
import com.muddzdev.styleabletoast.StyleableToast;

import java.util.ArrayList;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuViewHolder> {

    public MenuAdapter(ArrayList<MenuItem> items, Context context) {
        this.items = items;
        this.context = context;
    }

    ArrayList<MenuItem> items;
    Context context;
    @NonNull
    @Override
    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.menu_drawer_items,parent,false);
        return new MenuViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MenuViewHolder holder, int position) {

         if(position==0){
             holder.itemlayout.setBackgroundColor(Color.parseColor("#84ffff"));
             holder.itemname.setTextColor(Color.parseColor("#004d40"));
             holder.view.setVisibility(View.INVISIBLE);
         }

        final MenuItem menuItem=items.get(position);

         holder.itemimage.setImageResource(menuItem.getImage());

         holder.itemname.setText(menuItem.getName());
         holder.itemView.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 if(menuItem.getName().equals("Admin Panel")){

                     SharedPreferences    adminSharedPreference=context.getSharedPreferences("admininfo",0);

                     if(adminSharedPreference.getString("adminuser","").equals("")){
                         Intent intent=new Intent(context, AdminLogin.class);
                         context.startActivity(intent);
                     }else {
                         Intent intent=new Intent(context, AdminDashBoardActivity.class);
                         context.startActivity(intent);
                     }

                 }
                 if(menuItem.getName().equals("Dealer")){
                     SharedPreferences     dealerlogininfo=context.getSharedPreferences("dealerinfo",0);

                     if(dealerlogininfo.getString("dname","").equals("")){

                         Intent intent=new Intent(context, DealerLoginActivity.class);
                         context.startActivity(intent);
                     }else {
                         Intent intent=new Intent(context, DealerActivity.class);
                         context.startActivity(intent);
                     }
                     
                 }
                 if(menuItem.getName().equals("Donar")){
                     SharedPreferences   donarinfo=context.getSharedPreferences("donarinfo",0);

                     if(donarinfo.getString("uname","").equals("")){
                         Intent intent=new Intent(context, DonarLogin.class);
                         context.startActivity(intent);
                     }else{
                         Intent intent=new Intent(context, DonarDashBoardActivity.class);
                         context.startActivity(intent);
                     }

                 }
                 if(menuItem.getName().equals("Dealer Registration")){
                     Intent intent=new Intent(context, DealerRegActivity.class);
                     context.startActivity(intent);
                 }
                 if(menuItem.getName().equals("Donar Registration")){
                     Intent intent=new Intent(context, DonarRegisterActivity.class);
                     context.startActivity(intent);
                 }
                 //Donar Registration

             }
         });



    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class MenuViewHolder extends RecyclerView.ViewHolder {
        ImageView itemimage;
        View view;
        RelativeLayout itemlayout;
        TextView itemname,currency;
        public MenuViewHolder(@NonNull View itemView) {
            super(itemView);
             itemimage= itemView.findViewById(R.id.itemimage);
             itemname=itemView.findViewById(R.id.itemname);
             itemlayout=itemView.findViewById(R.id.menulayot);
             currency=itemView.findViewById(R.id.currencymenu);
             view=itemView.findViewById(R.id.viewid);
        }
    }
}
