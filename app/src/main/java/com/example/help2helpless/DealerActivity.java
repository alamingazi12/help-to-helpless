package com.example.help2helpless;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.help2helpless.model.Client;

public class DealerActivity extends AppCompatActivity {
   Button client_sign;
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
    }

    private void initAll() {
        client_sign=findViewById(R.id.btn_csign);
    }
}