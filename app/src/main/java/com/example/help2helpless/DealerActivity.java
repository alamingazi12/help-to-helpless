package com.example.help2helpless;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.help2helpless.model.Client;
import com.example.help2helpless.model.Dealer;
import com.example.help2helpless.model.DealerAvgPaid;
import com.example.help2helpless.model.DealerBalance;
import com.example.help2helpless.model.DealerTDonation;
import com.example.help2helpless.network.ApiClient;
import com.example.help2helpless.network.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DealerActivity extends AppCompatActivity {
   Button client_sign;
    SharedPreferences dealerlogininfo;
    String dealer_contact;
    TextView dealer_balances,avg_dlr_discount,total_discount_paid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dealer);
        initAll();
        client_sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(DealerActivity.this, ClientActivity.class);
                startActivity(intent);
            }
        });
       Button discount_btn=findViewById(R.id.button_discount);
        discount_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(DealerActivity.this,DiscountActivity.class);
                startActivity(intent);
            }
        });
        getDealerBalance();
        getDealerAvgPaid();
        getDealerTotalPaid();

    }

    private void getDealerBalance() {
        ApiInterface apiInterface= ApiClient.getApiClient(DealerActivity.this).create(ApiInterface.class);
        Call<DealerBalance> dealerBalanceCall=  apiInterface.getDealerBalance(dealer_contact);
        dealerBalanceCall.enqueue(new Callback<DealerBalance>() {
          @Override
          public void onResponse(Call<DealerBalance> call, Response<DealerBalance> response) {
            String dealer_balance=  response.body().getDealer_balance();
              Log.d("avg",dealer_balance);
              dealer_balances.setText("Dealer Balance:"+dealer_balance);
              Toast.makeText(DealerActivity.this,"avg:"+dealer_balance,Toast.LENGTH_LONG).show();
          }

          @Override
          public void onFailure(Call<DealerBalance> call, Throwable t) {
              Toast.makeText(DealerActivity.this,"Error",Toast.LENGTH_LONG).show();
          }
      });
    }
    private void getDealerAvgPaid() {
        ApiInterface apiInterface= ApiClient.getApiClient(DealerActivity.this).create(ApiInterface.class);
       Call <DealerAvgPaid> dealerAvgPaidCall=apiInterface.getDealerAvgDonation(dealer_contact);
       dealerAvgPaidCall.enqueue(new Callback<DealerAvgPaid>() {
           @Override
           public void onResponse(Call<DealerAvgPaid> call, Response<DealerAvgPaid> response) {
             String avgdealerpaid=  response.body().getAvg_dealer_paid();
               Log.d("avg",avgdealerpaid);
               avg_dlr_discount.setText("Avg Discount:"+avgdealerpaid);
               Toast.makeText(DealerActivity.this,"avg:"+avgdealerpaid,Toast.LENGTH_LONG).show();
           }

           @Override
           public void onFailure(Call<DealerAvgPaid> call, Throwable t) {
               Toast.makeText(DealerActivity.this,"Error",Toast.LENGTH_LONG).show();
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
               Log.d("avg",total_donation);
               total_discount_paid.setText("Total Discount:"+total_donation);
               Toast.makeText(DealerActivity.this,"avg:"+total_donation,Toast.LENGTH_LONG).show();
           }

           @Override
           public void onFailure(Call<DealerTDonation> call, Throwable t) {
               Toast.makeText(DealerActivity.this,"Error",Toast.LENGTH_LONG).show();
           }
       });


    }

    private void initAll() {
        dealer_balances=findViewById(R.id.dlr_balance);
        avg_dlr_discount=findViewById(R.id.avg_dlr_donation);

        total_discount_paid=findViewById(R.id.total_discount);

        client_sign=findViewById(R.id.btn_csign);
        dealerlogininfo=getSharedPreferences("dealerinfo",0);
        dealer_contact=dealerlogininfo.getString("contact","");
    }
}