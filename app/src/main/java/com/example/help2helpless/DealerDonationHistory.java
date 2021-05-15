package com.example.help2helpless;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.help2helpless.model.DealerBalance;
import com.example.help2helpless.network.ApiClient;
import com.example.help2helpless.network.ApiInterface;
import com.google.android.material.tabs.TabLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DealerDonationHistory extends AppCompatActivity {

    ViewPager view_donation_history;
    TabLayout tabLayout_donation;
    Toolbar toolbar;
    ImageButton back_button_history,menu;
    TextView balance_dealer;
    String dealer_contact;

    TextView dealer_balances;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dealer_donation_history);
        initAll();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getDealerBalance();
    }

    @SuppressLint("WrongViewCast")
    private void initAll() {
        dealer_balances=findViewById(R.id.donar_balance_text);
        menu=findViewById(R.id.admin_menu_icon);
        balance_dealer=findViewById(R.id.donar_balance_text);
        //toolbar=findViewById(R.id.my_toolbar);
        //  setSupportActionBar(toolbar);
        back_button_history=findViewById(R.id.back_btn_historys);
        back_button_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(DealerDonationHistory.this,DonarDashBoardActivity.class);
                startActivity(intent);
            }
        });

        view_donation_history=findViewById(R.id.d_viewpager);
        DealerDonationHistory.SectionsPagerAdapter sectionsPagerAdapter=new DealerDonationHistory.SectionsPagerAdapter(getSupportFragmentManager());
        view_donation_history.setAdapter(sectionsPagerAdapter);
        tabLayout_donation=findViewById(R.id.donation_tab);
        tabLayout_donation.setupWithViewPager(view_donation_history);
    }

    private void getDealerBalance() {
        SharedPreferences sharedPreferences=getSharedPreferences("dealerinfo",0);
        ApiInterface apiInterface= ApiClient.getApiClient(DealerDonationHistory.this).create(ApiInterface.class);
        Call<DealerBalance> dealerBalanceCall=  apiInterface.getDealerBalance(sharedPreferences.getString("contact",""));
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
                Toast.makeText(DealerDonationHistory.this,"Network Error",Toast.LENGTH_LONG).show();
            }
        });
    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }



        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            switch (position) {
                case 0:
                    fragment = new DealerReceivedfragment();
                    break;
                case 1:
                    fragment = new DealerSendFragment();
                    break;
            }
            return fragment;
        }
        @Override
        public int getCount() {
            // Show 3 total pages.
            return 2;
        }
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Received";

                case 1:
                    return "Sent";
            }
            return null;
        }
    }
}