package com.example.help2helpless;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.android.material.tabs.TabLayout;

public class DonationHistory extends AppCompatActivity {
     ViewPager view_donation_history;
     TabLayout tabLayout_donation;
     Toolbar toolbar;
     ImageButton back_button_history;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation_history);
        initAll();
    }

    @SuppressLint("WrongViewCast")
    private void initAll() {

        toolbar=findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
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
