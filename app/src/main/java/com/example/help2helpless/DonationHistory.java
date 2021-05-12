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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.example.help2helpless.model.DonarBalance;
import com.example.help2helpless.model.Essentials;
import com.example.help2helpless.network.ApiClient;
import com.example.help2helpless.network.ApiInterface;
import com.google.android.material.tabs.TabLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DonationHistory extends AppCompatActivity {
     ViewPager view_donation_history;
     TabLayout tabLayout_donation;
     Toolbar toolbar;
     ImageButton back_button_history,menu;
     TextView balance_donar;
     String donar_contact;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation_history);
        initAll();

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(DonationHistory.this, menu);
                //inflating menu from xml resource
                popup.inflate(R.menu.admin_menu_item);
                //adding click listener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(android.view.MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.logout:
                                Essentials.logout(DonationHistory.this);
                                break;
                            case R.id.settings:
                                Essentials.goSettings(DonationHistory.this);
                                break;
                        }
                        return false;
                    }
                });
                popup.show();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        getDonarBalance();
    }

    private void getDonarBalance() {

        SharedPreferences donarinfo=this.getSharedPreferences("donarinfo",0);
        donar_contact=donarinfo.getString("contact",null);
        ApiInterface apiInterface= ApiClient.getApiClient(DonationHistory.this).create(ApiInterface.class);
        Call<DonarBalance> donarBalanceCall= apiInterface.getDonarBalance(donar_contact);
        donarBalanceCall.enqueue(new Callback<DonarBalance>() {
            @Override
            public void onResponse(Call<DonarBalance> call, Response<DonarBalance> response) {
                String balance=response.body().getDonar_balance();
                // Toast.makeText(DonarDashBoardActivity.this,""+balance,Toast.LENGTH_LONG).show();
                balance_donar.setText(balance);
                if(balance==null){
                    balance="0";
                    balance_donar.setText(balance);
                }else{
                    balance_donar.setText(balance);
                }
//
            }

            @Override
            public void onFailure(Call<DonarBalance> call, Throwable t) {

            }
        });

    }

    @SuppressLint("WrongViewCast")
    private void initAll() {
        menu=findViewById(R.id.admin_menu_icon);
         balance_donar=findViewById(R.id.donar_balance_text);
        //toolbar=findViewById(R.id.my_toolbar);
      //  setSupportActionBar(toolbar);
        back_button_history=findViewById(R.id.back_btn_historys);
        back_button_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(DonationHistory.this,DonarDashBoardActivity.class);
                startActivity(intent);
            }
        });
        view_donation_history=findViewById(R.id.d_viewpager);
        SectionsPagerAdapter sectionsPagerAdapter=new SectionsPagerAdapter(getSupportFragmentManager());
        view_donation_history.setAdapter(sectionsPagerAdapter);
        tabLayout_donation=findViewById(R.id.donation_tab);
        tabLayout_donation.setupWithViewPager(view_donation_history);
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
                        fragment = new FragmentReceived();
                        break;
                    case 1:
                        fragment = new FragmentSent();
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
