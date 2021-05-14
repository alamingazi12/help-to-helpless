package com.example.help2helpless;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.ProgressBar;

import com.example.help2helpless.adapter.DSendMoneyAdapter;
import com.example.help2helpless.model.Donar;
import com.example.help2helpless.model.DonarResponse;
import com.example.help2helpless.model.Essentials;
import com.example.help2helpless.network.ApiClient;
import com.example.help2helpless.network.ApiInterface;
import com.muddzdev.styleabletoast.StyleableToast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllDonarActivity extends AppCompatActivity {
    RecyclerView donar_send_money_item_container;
    LinearLayoutManager linearLayoutManager;
    EditText search_text_donar;
    Button btn_search_donar;
    int page=1,row_per_page=5;
    public  boolean has_more;
    ProgressBar progressBar;
    DSendMoneyAdapter dSendMoneyAdapter;
    ArrayList<Donar> donars;
    //pagination variable
    String text;
    ImageButton btn_back_image,menu;


    private boolean isloading=true;
    int pastVisibleItems,totalItemcount,previous_total,visible_item_count=0;
    private  int view_thresold=5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_donar);
        initAll();
        showDonar();

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(AllDonarActivity.this, menu);
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
                            case R.id.go_dash:
                                Essentials.goAdminHome(AllDonarActivity.this);
                                break;
                        }
                        return false;
                    }
                });
                popup.show();
            }
        });


        btn_back_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        btn_search_donar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text= search_text_donar.getText().toString().trim();
                if(!TextUtils.isEmpty(text)){

                    isloading=true;
                    pastVisibleItems=0;
                    totalItemcount=0;
                    previous_total=0;
                    visible_item_count=0;
                   // dSendMoneyAdapter=null;

                    page=1;
                    donars.clear();
                    dSendMoneyAdapter.notifyDataSetChanged();
                    showDonar();
                }

            }
        });

        if(TextUtils.isEmpty(search_text_donar.getText().toString().trim())){
            showDonar();
        }

        search_text_donar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if(TextUtils.isEmpty(s) ||  s.toString().equals("0")){
                    Log.d("empty","empty");
                    isloading=true;
                    pastVisibleItems=0;
                    totalItemcount=0;
                    previous_total=0;
                    visible_item_count=0;
                    text="";
                    row_per_page=20;
                    // dSendMoneyAdapter=null;

                    page=1;
                    donars.clear();
                    dSendMoneyAdapter.notifyDataSetChanged();
                    showDonar();
                }
            }
        });

    }

    private void showDonar() {

        ApiInterface apiInterface=ApiClient.getApiClient(this).create(ApiInterface.class);
        Call<DonarResponse> donarResponseCall=apiInterface.getAllDonars(page,row_per_page,text);
        donarResponseCall.enqueue(new Callback<DonarResponse>() {
           @Override
           public void onResponse(Call<DonarResponse> call, Response<DonarResponse> response) {

               if (response.body().getUsers().size()>0){
                    donars= response.body().getUsers();
                    dSendMoneyAdapter=new DSendMoneyAdapter(donars,AllDonarActivity.this);
                    donar_send_money_item_container.setAdapter(dSendMoneyAdapter);
               }else{
                   StyleableToast.makeText(AllDonarActivity.this,"Empty",R.style.mytoast).show();
               }



           }

           @Override
           public void onFailure(Call<DonarResponse> call, Throwable t) {

           }
       });

        donar_send_money_item_container.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(!has_more){
                    progressBar.setVisibility(View.GONE);
                }





                visible_item_count=linearLayoutManager.getChildCount();
                totalItemcount=linearLayoutManager.getItemCount();
                pastVisibleItems=linearLayoutManager.findFirstVisibleItemPosition();


                if(dy>0){
                    if(isloading) {

                        if(totalItemcount>previous_total){

                            isloading=false;
                            previous_total=totalItemcount;
                        }

                    }
                    if(!isloading && (totalItemcount-visible_item_count)<=(pastVisibleItems+view_thresold)){
                       Log.d("page","called");
                        performPagination();
                        isloading=true;
                    }


                }

            }
        });
    }


    public void  performPagination(){
        progressBar.setVisibility(View.VISIBLE);
        page++;
        Log.d("page",String.valueOf(page));
        ApiInterface apiInterface=  ApiClient.getApiClient(this).create(ApiInterface.class);
        Call<DonarResponse> dealerResponseCall=apiInterface.getAllDonars(page,row_per_page,text);
        dealerResponseCall.enqueue(new Callback<DonarResponse>() {
            @Override
            public void onResponse(Call<DonarResponse> call, Response<DonarResponse> response) {
                has_more= response.body().isHas_more();
                if(has_more){
                    dSendMoneyAdapter.addList(response.body().getUsers());
                    progressBar.setVisibility(View.GONE);
                }else{
                    progressBar.setVisibility(View.GONE);
                }
                progressBar.setVisibility(View.GONE);
                // RequestAdapter requestAdapter=new RequestAdapter(response.body().getDealers(),LoginActivity.this);
                // dealer_request_containers.setAdapter(requestAdapter);

            }

            @Override
            public void onFailure(Call<DonarResponse> call, Throwable t) {

            }
        });

    }

    private void initAll()
    {
        menu=findViewById(R.id.admin_menu_icon);
        btn_back_image=findViewById(R.id.btn_back);
        btn_search_donar=findViewById(R.id.btn_search_money);
      donar_send_money_item_container=findViewById(R.id.donar_send_money_container);
      donar_send_money_item_container.setHasFixedSize(true);
      linearLayoutManager=new LinearLayoutManager(this);
      donar_send_money_item_container.setLayoutManager(linearLayoutManager);
      search_text_donar=findViewById(R.id.dsearch_text);
      progressBar=findViewById(R.id.progress_donar_item);
    }
    public void goSettings(){
        Bundle bundle=new Bundle();
        bundle.putString("main","");
        Intent intent=new Intent(AllDonarActivity.this,SettingActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }


    private void logout() {

        SharedPreferences adminSharedPreference=getSharedPreferences("admininfo",0);
        String user= adminSharedPreference.getString("adminuser","");
        SharedPreferences   donarinfo=getSharedPreferences("donarinfo",0);

        SharedPreferences dealerlogininfo=getSharedPreferences("dealerinfo",0);
        if(!dealerlogininfo.getString("contact","").equals("")){
            SharedPreferences.Editor   dealer_editor=dealerlogininfo.edit();
            dealer_editor.remove("contact");
            dealer_editor.commit();
            StyleableToast.makeText(AllDonarActivity.this,"You Logged out Successfully",R.style.greentoast).show();
            Intent intent=new Intent(AllDonarActivity.this,MainActivity.class);
            startActivity(intent);
        }
        else if(!TextUtils.isEmpty(user)){
            SharedPreferences.Editor editor = adminSharedPreference.edit();
            editor.remove("adminuser");
            editor.commit();
            StyleableToast.makeText(AllDonarActivity.this,"You Logged out Successfully",R.style.greentoast).show();
            Intent intent=new Intent(AllDonarActivity.this,MainActivity.class);
            startActivity(intent);
        }
        else if(!donarinfo.getString("contact","").equals("")){
            SharedPreferences.Editor donar_editor = donarinfo.edit();
            donar_editor.remove("contact");
            donar_editor.commit();
            StyleableToast.makeText(AllDonarActivity.this,"You Logged out Successfully",R.style.greentoast).show();
            Intent intent=new Intent(AllDonarActivity.this,MainActivity.class);
            startActivity(intent);
        }else {
            StyleableToast.makeText(AllDonarActivity.this,"You Did,nt Login",R.style.mytoast).show();
        }

    }
}