package com.example.help2helpless;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.help2helpless.model.Dealer;
import com.example.help2helpless.model.Donar;
import com.google.gson.Gson;

public class DonarProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donar_profile);
        initAll();

    }

    private void initAll() {
        SharedPreferences donarlogininfo=getSharedPreferences("donarinfo",0);
        Gson gson = new Gson();
        String json = donarlogininfo.getString("MyObject", "");
        Donar dealers = gson.fromJson(json, Donar.class);
    }
}