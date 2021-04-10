package com.example.help2helpless;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.help2helpless.adapter.MenuAdapter;
import com.example.help2helpless.model.MenuItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    ArrayList<MenuItem> items;
    public static   DrawerLayout drawer;
    RecyclerView menucontentitems;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      //  setFontToActionBar();
        initAll();
        initDrawerListener();

    }

    private void setFontToActionBar() {
        TextView tv = new TextView(MainActivity.this);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        tv.setLayoutParams(lp);
        tv.setText("Help To Helpless");
        tv.setTextSize(24);
        tv.setGravity(Gravity.CENTER);
        tv.setTextColor(Color.parseColor("#ffffff"));
        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/regular.otf");
        tv.setTypeface(tf);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(tv);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initAll() {
        menucontentitems=findViewById(R.id.myrecyclermenucontents);
        menucontentitems.setHasFixedSize(true);
        menucontentitems.setLayoutManager(new LinearLayoutManager(MainActivity.this));
    }

    private void initDrawerListener() {
        toolbar=findViewById(R.id.toolbar);
        drawer=findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();
        toggle.getDrawerArrowDrawable().setColor(Color.parseColor("#ffffff"));
        showMenuData();
    }

    private void showMenuData() {

        items=new ArrayList<>();
        items.add(new MenuItem(R.drawable.ic_baseline_home_24,"Home"));
        items.add(new MenuItem(R.drawable.profile,"Admin Panel"));
        items.add(new MenuItem(R.drawable.person,"Dealer"));
        items.add(new MenuItem(R.drawable.person,"Dealer Registration"));
        items.add(new MenuItem(R.drawable.person,"Donar"));
        items.add(new MenuItem(R.drawable.person,"Donar Registration"));
        items.add(new MenuItem(R.drawable.info,"About Us"));

        MenuAdapter menuAdapter=new MenuAdapter(items,MainActivity.this);
        menucontentitems.setAdapter(menuAdapter);

    }

    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)){

            drawer.closeDrawer(GravityCompat.START);
        }
        else {

            super.onBackPressed();
        }

    }
}