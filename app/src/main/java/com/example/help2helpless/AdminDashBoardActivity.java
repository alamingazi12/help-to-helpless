package com.example.help2helpless;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.help2helpless.model.Admin;

public class AdminDashBoardActivity extends AppCompatActivity {

    Button show_request;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);
      Bundle bundle= getIntent().getExtras();
      Admin admin=bundle.getParcelable("obj");
      initAll();
      Toast.makeText(AdminDashBoardActivity.this,"admin:"+admin.getAname(),Toast.LENGTH_LONG).show();
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
