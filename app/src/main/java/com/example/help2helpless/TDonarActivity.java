package com.example.help2helpless;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.help2helpless.adapter.TotalDonarAdapter;
import com.example.help2helpless.model.Dealer;
import com.example.help2helpless.model.Essentials;
import com.example.help2helpless.model.TotalDonarResponse;
import com.muddzdev.styleabletoast.StyleableToast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TDonarActivity extends AppCompatActivity {
    ImageButton back_Image_btn;
    ProgressBar progressBar;
    RecyclerView donar_item_container;
    LinearLayoutManager linearLayoutManager;
    int page=1,row_per_page=6;
    private boolean isloading=true;
    int pastVisibleItems,totalItemcount,previous_total,visible_item_count=0;
    private  int view_thresold=5;
    public  boolean has_more;

    TotalDonarAdapter donarAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_t_donar);
        initAll();

        back_Image_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        showConnectedDonar();
    }

    private void showConnectedDonar() {
        Dealer dealer= Essentials.getDealerData(this);
        Essentials.getApiInterface(this).getTotalDonarList(page,row_per_page,dealer.getPhone()).enqueue(new Callback<TotalDonarResponse>() {
            @Override
            public void onResponse(Call<TotalDonarResponse> call, Response<TotalDonarResponse> response) {
                if(response.body().getResult().size()>0){
                    progressBar.setVisibility(View.INVISIBLE);
                     donarAdapter=new TotalDonarAdapter(response.body().getResult(),TDonarActivity.this);
                    donar_item_container.setAdapter(donarAdapter);
                }
                else{
                    StyleableToast.makeText(TDonarActivity.this,"You Have No Donar",R.style.mytoast).show();
                }
            }

            @Override
            public void onFailure(Call<TotalDonarResponse> call, Throwable t) {

            }
        });
        donar_item_container.addOnScrollListener(new RecyclerView.OnScrollListener() {
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

    private void performPagination() {
        progressBar.setVisibility(View.VISIBLE);
        page++;
        Dealer dealer= Essentials.getDealerData(this);
        Essentials.getApiInterface(this).getTotalDonarList(page,row_per_page,dealer.getPhone()).enqueue(new Callback<TotalDonarResponse>() {
            @Override
            public void onResponse(Call<TotalDonarResponse> call, Response<TotalDonarResponse> response) {
                has_more= response.body().isHas_more();
                if(response.body().getResult().size()>0){
                    donarAdapter.addLists(response.body().getResult());
                    // addedDealerAdapter.addLists(response.body().getDealerlist());
                    progressBar.setVisibility(View.GONE);
                    // StyleableToast.makeText(AddedDealerActivity.this,"data size"+response.body().getDealerlist().size(),R.style.mytoast).show();
                }
                else{
                    //StyleableToast.makeText(AddedDealerActivity.this,"data null",R.style.mytoast).show();
                    progressBar.setVisibility(View.GONE);
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<TotalDonarResponse> call, Throwable t) {

            }
        });
    }

    private void initAll() {
        back_Image_btn=findViewById(R.id.btn_back);
        progressBar=findViewById(R.id._progress);
        linearLayoutManager=new LinearLayoutManager(this);
        donar_item_container=findViewById(R.id.donar_container_view);
        donar_item_container.setHasFixedSize(true);
        donar_item_container.setLayoutManager(linearLayoutManager);
    }
}