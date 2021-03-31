package com.example.help2helpless;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.example.help2helpless.model.Dealer;

public class DonateDealerActivity extends AppCompatActivity {
    Dealer dealer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate_dealer);
        getData();

    }

    private void getData() {
        Bundle bundle=getIntent().getExtras();
        dealer=bundle.getParcelable("dealers");
    }
}