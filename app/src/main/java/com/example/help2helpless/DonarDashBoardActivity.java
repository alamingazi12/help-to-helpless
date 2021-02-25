package com.example.help2helpless;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.example.help2helpless.model.Donar;
import com.example.help2helpless.model.DonarAllDonation;
import com.example.help2helpless.model.DonarBalance;
import com.example.help2helpless.model.DonarsAvgDonation;
import com.example.help2helpless.network.ApiClient;
import com.example.help2helpless.network.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DonarDashBoardActivity extends AppCompatActivity {
    Button show_dealers;
    Donar donar;
    SharedPreferences donarinfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donar_dash_board);
        final Bundle bundle= getIntent().getExtras();
        donar=bundle.getParcelable("obj");
        initAll();
        show_dealers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(DonarDashBoardActivity.this,AddDealerActivity.class);
                Bundle bundle1=new Bundle();
                bundle1.putParcelable("dobj",donar);
                intent.putExtras(bundle1);
                startActivity(intent);

            }
        });
     getDonarBalance();
     getAvgDonation();
     getTotalDonation();
    }

    private void getTotalDonation() {
        ApiInterface apiInterface= ApiClient.getApiClient(DonarDashBoardActivity.this).create(ApiInterface.class);
        Call<DonarAllDonation> donarAllDonationCall = apiInterface.getDonarsAllDonations("01711506657");
        donarAllDonationCall.enqueue(new Callback<DonarAllDonation>() {
            @Override
            public void onResponse(Call<DonarAllDonation> call, Response<DonarAllDonation> response) {
             String total_donation=response.body().getTotal_donation();
                Toast.makeText(DonarDashBoardActivity.this,""+total_donation,Toast.LENGTH_LONG).show();
                Log.d("total_donation:",total_donation);
            }

            @Override
            public void onFailure(Call<DonarAllDonation> call, Throwable t) {

            }
        });
    }

    private void getAvgDonation() {
        ApiInterface apiInterface= ApiClient.getApiClient(DonarDashBoardActivity.this).create(ApiInterface.class);
      Call<DonarsAvgDonation> donarsAvgDonationCall =  apiInterface.getDonaravgDonations("01711506657");
       donarsAvgDonationCall.enqueue(new Callback<DonarsAvgDonation>() {
           @Override
           public void onResponse(Call<DonarsAvgDonation> call, Response<DonarsAvgDonation> response) {
               String avg_donation=    response.body().getAvg_donation();
               Toast.makeText(DonarDashBoardActivity.this,""+avg_donation,Toast.LENGTH_LONG).show();
               Log.d("avg:",avg_donation);
           }

           @Override
           public void onFailure(Call<DonarsAvgDonation> call, Throwable t) {

           }
       });
    }

    private void getDonarBalance() {

        ApiInterface apiInterface= ApiClient.getApiClient(DonarDashBoardActivity.this).create(ApiInterface.class);
        Call<DonarBalance> donarBalanceCall= apiInterface.getDonarBalance("01711506657");
        donarBalanceCall.enqueue(new Callback<DonarBalance>() {
           @Override
           public void onResponse(Call<DonarBalance> call, Response<DonarBalance> response) {
               String balance=response.body().getDonar_balance();
               Toast.makeText(DonarDashBoardActivity.this,""+balance,Toast.LENGTH_LONG).show();
               Log.d("balance:",balance);
           }

           @Override
           public void onFailure(Call<DonarBalance> call, Throwable t) {

           }
       });

    }

    private void initAll() {
        show_dealers=findViewById(R.id.show_dealers);
        donarinfo=this.getSharedPreferences("donarinfo",0);
        String username=donarinfo.getString("uname",null);

    }
}