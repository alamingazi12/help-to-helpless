package com.example.help2helpless;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.help2helpless.model.Donar;

public class DonarDashBoardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donar_dash_board);
       Bundle bundle= getIntent().getExtras();
       Donar donar=bundle.getParcelable("obj");
    }
}