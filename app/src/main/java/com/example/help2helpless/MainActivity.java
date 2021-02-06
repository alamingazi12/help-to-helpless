package com.example.help2helpless;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;

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
        initAll();
        initDrawerListener();
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

        items.add(new MenuItem(R.drawable.person,"Home"));
        items.add(new MenuItem(R.drawable.person,"Admin Panel"));
        items.add(new MenuItem(R.drawable.person,"Dealer Login"));
        items.add(new MenuItem(R.drawable.person,"About Us"));

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