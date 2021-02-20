package com.example.help2helpless;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.help2helpless.model.Admin;

public class AdminDashBoardActivity extends AppCompatActivity {

    Button show_request;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);
        setFontToActionBar();
      Bundle bundle= getIntent().getExtras();
      Admin admin=bundle.getParcelable("obj");
      initAll();
      Toast.makeText(AdminDashBoardActivity.this,"admin:"+admin.getAname(),Toast.LENGTH_LONG).show();
    }
    private void setFontToActionBar() {
        TextView tv = new TextView(AdminDashBoardActivity.this);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        tv.setLayoutParams(lp);
        tv.setText("Admin Dashboard");
        tv.setTextSize(24);
        tv.setGravity(Gravity.CENTER);
        tv.setTextColor(Color.parseColor("#ffffff"));
        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/Titillium-Regular.otf");
        tv.setTypeface(tf);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(tv);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    private void initAll() {
        show_request=findViewById(R.id.rq_btn);
        show_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AdminDashBoardActivity.this,DealerRequestActivity.class);
                startActivity(intent);
            }
        });
    }
}
