package com.example.help2helpless;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.help2helpless.model.AvgDealerReceived;
import com.example.help2helpless.model.Client;
import com.example.help2helpless.model.Dealer;
import com.example.help2helpless.model.DealerAvgPaid;
import com.example.help2helpless.model.DealerBalance;
import com.example.help2helpless.model.DealerTDonation;
import com.example.help2helpless.model.TotalClients;
import com.example.help2helpless.model.TotalDonars;
import com.example.help2helpless.network.ApiClient;
import com.example.help2helpless.network.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DealerActivity extends AppCompatActivity {
    Button client_sign,btn_add_dealer;
    SharedPreferences dealerlogininfo;
    String dealer_contact;
    TextView dealer_balances,avg_dlr_discount,total_discount_paid,avg_dealer_received,ndonar,nclients,dlr_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dealer);
        //setFontToActionBar();

        initAll();
        client_sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(DealerActivity.this, ClientActivity.class);
                startActivity(intent);
            }
        });
        btn_add_dealer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(DealerActivity.this,AddDealerActivity.class);
                startActivity(intent);
            }
        });
      // Button discount_btn=findViewById(R.id.button_discount);

      //  getDealerBalance();
      //  getDealerAvgPaid();
      //  getDealerTotalPaid();
        //getAvgDealerReceived();
        //getTotalDonar();
        //getTotalClients();
        getDealerBalance();
        getDealerAvgPaid();
        //getDealerTotalPaid();
        getAvgDealerReceived();
        getTotalDonar();
        getTotalClients();
    }

    private void getTotalClients() {
        ApiInterface apiInterface= ApiClient.getApiClient(DealerActivity.this).create(ApiInterface.class);
        Call<TotalClients> totalClientsCall= apiInterface.getTotalClients(dealer_contact);
        totalClientsCall.enqueue(new Callback<TotalClients>() {
           @Override
           public void onResponse(Call<TotalClients> call, Response<TotalClients> response) {
                String total_clients=     response.body().getTotal_client();
               if(total_clients==null){
                   total_clients="0";
                   nclients.setText(""+total_clients);
               }else {
                   nclients.setText(""+total_clients);
                   //} Toast.makeText(DealerActivity.this,"avg:"+dealer_balance,Toast.LENGTH_LONG).show();
               }
           }

           @Override
           public void onFailure(Call<TotalClients> call, Throwable t) {

           }
       });
    }

    private void getTotalDonar() {
        ApiInterface apiInterface= ApiClient.getApiClient(DealerActivity.this).create(ApiInterface.class);
        Call<TotalDonars> totalDonarsCall=  apiInterface.getTotalDonars(dealer_contact);
        totalDonarsCall.enqueue(new Callback<TotalDonars>() {
          @Override
          public void onResponse(Call<TotalDonars> call, Response<TotalDonars> response) {
              String total_donars=response.body().getTotal_donar();
              if(total_donars==null){
                  total_donars="0";
                  ndonar.setText(""+total_donars);
              }else {
                  ndonar.setText(""+total_donars);
                  //} Toast.makeText(DealerActivity.this,"avg:"+dealer_balance,Toast.LENGTH_LONG).show();
              }
          }

          @Override
          public void onFailure(Call<TotalDonars> call, Throwable t) {

          }
      });

    }


    private void getAvgDealerReceived() {
        ApiInterface apiInterface= ApiClient.getApiClient(DealerActivity.this).create(ApiInterface.class);
        Call<AvgDealerReceived> avgDealerReceivedCall=  apiInterface.avgDealerReceived(dealer_contact);
        avgDealerReceivedCall.enqueue(new Callback<AvgDealerReceived>() {
          @Override
          public void onResponse(Call<AvgDealerReceived> call, Response<AvgDealerReceived> response) {
             String donaravgRecived= response.body().getAvg_donation();
              if(donaravgRecived==null){
                  donaravgRecived="0";
                  avg_dealer_received.setText(""+donaravgRecived);
              }else {
                  avg_dealer_received.setText(""+donaravgRecived);
                  //} Toast.makeText(DealerActivity.this,"avg:"+dealer_balance,Toast.LENGTH_LONG).show();
              }
          }

          @Override
          public void onFailure(Call<AvgDealerReceived> call, Throwable t) {

          }
      });
    }

    private void setFontToActionBar() {
        TextView tv = new TextView(DealerActivity.this);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        tv.setLayoutParams(lp);
        tv.setText("Dashboard");
        tv.setTextSize(24);
        tv.setGravity(Gravity.CENTER);
        tv.setTextColor(Color.parseColor("#ffffff"));
        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/regular.otf");
        tv.setTypeface(tf);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(tv);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#8ecae6")));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    private void getDealerBalance() {
        ApiInterface apiInterface= ApiClient.getApiClient(DealerActivity.this).create(ApiInterface.class);
        Call<DealerBalance> dealerBalanceCall=  apiInterface.getDealerBalance(dealer_contact);
        dealerBalanceCall.enqueue(new Callback<DealerBalance>() {
          @Override
          public void onResponse(Call<DealerBalance> call, Response<DealerBalance> response) {
            String dealer_balance=  response.body().getDealer_balance();
              //Log.d("balance",dealer_balance);

              if(dealer_balance==null){
                  dealer_balance="0";
                  dealer_balances.setText(""+dealer_balance);
              }else {
                  dealer_balances.setText(""+dealer_balance);
                  //} Toast.makeText(DealerActivity.this,"avg:"+dealer_balance,Toast.LENGTH_LONG).show();
              }
          }

          @Override
          public void onFailure(Call<DealerBalance> call, Throwable t) {
              Toast.makeText(DealerActivity.this,"Network Error",Toast.LENGTH_LONG).show();
          }
      });
    }
    private void getDealerAvgPaid() {
        ApiInterface apiInterface= ApiClient.getApiClient(DealerActivity.this).create(ApiInterface.class);
       final Call <DealerAvgPaid> dealerAvgPaidCall=apiInterface.getDealerAvgDonation(dealer_contact);
       dealerAvgPaidCall.enqueue(new Callback<DealerAvgPaid>() {
           @Override
           public void onResponse(Call<DealerAvgPaid> call, Response<DealerAvgPaid> response) {
             String avgdealerpaid=  response.body().getAvg_dealer_paid();
               if(avgdealerpaid==null){
                   avgdealerpaid="0";
                   avg_dlr_discount.setText(""+avgdealerpaid);
               }else
                   {
                       avg_dlr_discount.setText("" + avgdealerpaid);
               }

              // Toast.makeText(DealerActivity.this,"avg:"+avgdealerpaid,Toast.LENGTH_LONG).show();
           }

           @Override
           public void onFailure(Call<DealerAvgPaid> call, Throwable t) {
               Toast.makeText(DealerActivity.this,"Network Error",Toast.LENGTH_LONG).show();
           }
       });

    }
    private void getDealerTotalPaid() {
        ApiInterface apiInterface= ApiClient.getApiClient(DealerActivity.this).create(ApiInterface.class);
        Call<DealerTDonation> totalDealerDonation =apiInterface.getTotalDealerDonation(dealer_contact);
        totalDealerDonation.enqueue(new Callback<DealerTDonation>() {
           @Override
           public void onResponse(Call<DealerTDonation> call, Response<DealerTDonation> response) {
             String total_donation=  response.body().getTotal_donation();
               //Log.d("avg",total_donation);
               if(total_donation==null){
                   total_donation="0";
                   total_discount_paid.setText("Total Discount:"+total_donation);
               }else
               {
                   total_discount_paid.setText("Total Discount:" + total_donation);
                   //} Toast.makeText(DealerActivity.this,"avg:"+dealer_balance,Toast.LENGTH_LONG).show();
               }


               //Toast.makeText(DealerActivity.this,"avg:"+total_donation,Toast.LENGTH_LONG).show();
           }

           @Override
           public void onFailure(Call<DealerTDonation> call, Throwable t) {
               Toast.makeText(DealerActivity.this,"Error",Toast.LENGTH_LONG).show();
           }
       });


    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void initAll() {
        dealer_balances=findViewById(R.id.dlr_balance);
        avg_dlr_discount=findViewById(R.id.avg_dlr_donation);
        avg_dealer_received=findViewById(R.id.dealer_recived_avg);
        ndonar=findViewById(R.id.num_of_donars);
        nclients=findViewById(R.id.num_of_clients);
        dlr_name=findViewById(R.id.dlr_name);
       // total_discount_paid=findViewById(R.id.total_discount);
        btn_add_dealer=findViewById(R.id.dealer_add);
        client_sign=findViewById(R.id.btn_csign);
        dealerlogininfo=getSharedPreferences("dealerinfo",0);
        dealer_contact=dealerlogininfo.getString("contact","");
        dlr_name.setText(dealerlogininfo.getString("dname",""));
    }
}