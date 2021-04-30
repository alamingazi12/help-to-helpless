package com.example.help2helpless;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.help2helpless.adapter.DSendMoneyAdapter;
import com.example.help2helpless.model.DonarResponse;
import com.example.help2helpless.network.ApiClient;
import com.example.help2helpless.network.ApiInterface;
import com.muddzdev.styleabletoast.StyleableToast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllDonarActivity extends AppCompatActivity {
    RecyclerView donar_send_money_item_container;
    LinearLayoutManager linearLayoutManager;
    EditText search_text_donar;
    Button btn_search_donar;
    int page=1,row_per_page=5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_donar);
        initAll();
        showDonar();
        btn_search_donar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showDonar();
            }
        });

        if(TextUtils.isEmpty(search_text_donar.getText().toString().trim())){
            showDonar();
        }

    }

    private void showDonar() {
       String text= search_text_donar.getText().toString().trim();
        ApiInterface apiInterface=ApiClient.getApiClient(this).create(ApiInterface.class);
        Call<DonarResponse> donarResponseCall=apiInterface.getAllDonars(page,row_per_page,text);
        donarResponseCall.enqueue(new Callback<DonarResponse>() {
           @Override
           public void onResponse(Call<DonarResponse> call, Response<DonarResponse> response) {

               if (response.body().getUsers().size()>0){
                   DSendMoneyAdapter dSendMoneyAdapter=new DSendMoneyAdapter(response.body().getUsers(),AllDonarActivity.this);
                   donar_send_money_item_container.setAdapter(dSendMoneyAdapter);
               }else{

                   StyleableToast.makeText(AllDonarActivity.this,"Empty",R.style.mytoast).show();
               }



           }

           @Override
           public void onFailure(Call<DonarResponse> call, Throwable t) {

           }
       });
    }



    private void initAll() {
        btn_search_donar=findViewById(R.id.btn_search_money);
      donar_send_money_item_container=findViewById(R.id.donar_send_money_container);
      donar_send_money_item_container.setHasFixedSize(true);
      linearLayoutManager=new LinearLayoutManager(this);
      donar_send_money_item_container.setLayoutManager(linearLayoutManager);
      search_text_donar=findViewById(R.id.dsearch_text);
    }
}