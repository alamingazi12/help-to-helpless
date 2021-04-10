package com.example.help2helpless;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.help2helpless.model.DonarAllDonation;
import com.example.help2helpless.model.DonarBalance;
import com.example.help2helpless.model.DonarsAvgDonation;
import com.example.help2helpless.model.TotalDealer;
import com.example.help2helpless.network.ApiClient;
import com.example.help2helpless.network.ApiInterface;
import com.muddzdev.styleabletoast.StyleableToast;
import com.squareup.picasso.Picasso;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DonarDashBoardActivity extends AppCompatActivity {
    Button show_dealers,show_dealer_req,donate_dealers,btn_history;
    String donar_contact;
    SharedPreferences donarinfo;
    TextView donar_balnce,avg_donations,t_donation,ndealer,dname;
    CircleImageView donar_profile_image;
    String imageUrl="https://apps.help2helpless.com/donar_profile/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donar_dash_board);


       // final Bundle bundle= getIntent().getExtras();
        //donar=bundle.getParcelable("obj");
        initAll();
        show_dealers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(DonarDashBoardActivity.this,AddDealerActivity.class);
                startActivity(intent);
            }
        });
        show_dealer_req.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(DonarDashBoardActivity.this,RequestActivity.class);
                startActivity(intent);
            }
        });
        donate_dealers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(DonarDashBoardActivity.this,AddedDealerActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
       switch (item.getItemId()){
           case R.id.logout:
               logOut();
               StyleableToast.makeText(DonarDashBoardActivity.this,"Loged Out",R.style.mytoast).show();
               break;
           default:
               break;
       }
       return true;
    }

    private void logOut() {
    }

    @Override
    protected void onResume() {
        super.onResume();
        getDonarBalance();
        getAvgDonation();
        getTotalDealers();

        //getTotalDonation();
    }

    private void getTotalDealers() {
        ApiInterface apiInterface= ApiClient.getApiClient(DonarDashBoardActivity.this).create(ApiInterface.class);
       Call<TotalDealer> totalDealerCall=  apiInterface.getTotalDealer(donar_contact);
       totalDealerCall.enqueue(new Callback<TotalDealer>() {
          @Override
          public void onResponse(Call<TotalDealer> call, Response<TotalDealer> response) {
            String ndealers= response.body().getTotal_dealer();
              if(ndealers==null){
                  ndealers="0";
                  ndealer.setText(ndealers);
              }else{

                  ndealer.setText(ndealers);
              }

          }

          @Override
          public void onFailure(Call<TotalDealer> call, Throwable t) {

          }
      });
    }

    private void getTotalDonation() {
        ApiInterface apiInterface= ApiClient.getApiClient(DonarDashBoardActivity.this).create(ApiInterface.class);
        Call<DonarAllDonation> donarAllDonationCall = apiInterface.getDonarsAllDonations(donar_contact);
        donarAllDonationCall.enqueue(new Callback<DonarAllDonation>() {
            @Override
            public void onResponse(Call<DonarAllDonation> call, Response<DonarAllDonation> response) {
             String total_donation=response.body().getTotal_donation();
                //Toast.makeText(DonarDashBoardActivity.this,""+total_donation,Toast.LENGTH_LONG).show();
                //Log.d("total_donation:",total_donation);
                if(total_donation==null){
                   total_donation="0";
                    //t_donation.setText(total_donation);
                }else{

                   // t_donation.setText(total_donation);
                }

            }

            @Override
            public void onFailure(Call<DonarAllDonation> call, Throwable t) {

            }
        });
    }

    private void getAvgDonation() {
        ApiInterface apiInterface= ApiClient.getApiClient(DonarDashBoardActivity.this).create(ApiInterface.class);
       Call<DonarsAvgDonation> donarsAvgDonationCall =  apiInterface.getDonaravgDonations(donar_contact);
       donarsAvgDonationCall.enqueue(new Callback<DonarsAvgDonation>() {
           @Override
           public void onResponse(Call<DonarsAvgDonation> call, Response<DonarsAvgDonation> response) {
               String avg_donation= response.body().getAvg_donation();
               avg_donations.setText(avg_donation);
               if(avg_donation==null){

                   avg_donation="0";
                   avg_donations.setText(avg_donation);

               }else {
                   double donation=Double.parseDouble(avg_donation);
                   int IntValue = (int) Math.round(donation);
                   avg_donations.setText(""+IntValue);
               }
              // Toast.makeText(DonarDashBoardActivity.this,""+avg_donation,Toast.LENGTH_LONG).show();
              // Log.d("avg:",avg_donation);
           }

           @Override
           public void onFailure(Call<DonarsAvgDonation> call, Throwable t) {

           }
       });
    }

    private void getDonarBalance() {
        ApiInterface apiInterface= ApiClient.getApiClient(DonarDashBoardActivity.this).create(ApiInterface.class);
        Call<DonarBalance> donarBalanceCall= apiInterface.getDonarBalance(donar_contact);
        donarBalanceCall.enqueue(new Callback<DonarBalance>() {
           @Override
           public void onResponse(Call<DonarBalance> call, Response<DonarBalance> response) {
               String balance=response.body().getDonar_balance();
              // Toast.makeText(DonarDashBoardActivity.this,""+balance,Toast.LENGTH_LONG).show();
               donar_balnce.setText(balance);
               if(balance==null){
                   balance="0";
                   donar_balnce.setText(balance);
               }else{
                   donar_balnce.setText(balance);
               }
//
           }

           @Override
           public void onFailure(Call<DonarBalance> call, Throwable t) {

           }
       });

    }

    private void initAll() {
        btn_history=findViewById(R.id.btn_history);
        btn_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(DonarDashBoardActivity.this,DonationHistory.class);
                startActivity(intent);
            }
        });
        dname=findViewById(R.id.dlr_name);
        show_dealers=findViewById(R.id.add_dealers);
        donar_balnce=findViewById(R.id.dnr_balance);
        avg_donations=findViewById(R.id.dnr_avg_donation);
        ndealer=findViewById(R.id.num_of_donars);
        donar_profile_image=findViewById(R.id.profile_image_donar);
        //t_donation=findViewById(R.id.t_doantion);
        donate_dealers=findViewById(R.id.donate_to_dealer);
        donarinfo=this.getSharedPreferences("donarinfo",0);
        donar_contact=donarinfo.getString("contact",null);
        show_dealer_req=findViewById(R.id.show_request);
        dname.setText(donarinfo.getString("name",null));
        Picasso.get().load(imageUrl+donarinfo.getString("donar_pic",null)).resize(60,60).centerCrop().into(donar_profile_image);
    }
}