package com.example.help2helpless;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.help2helpless.model.Donar;

public class DonarDashBoardActivity extends AppCompatActivity {
   Button show_dealers;
    Donar donar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donar_dash_board);
        final Bundle bundle= getIntent().getExtras();
       donar=bundle.getParcelable("obj");
        initAll();
        show_dealers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(DonarDashBoardActivity.this,AddDealerActivity.class);
                Bundle bundle1=new Bundle();
                bundle1.putParcelable("dobj",donar);
                intent.putExtras(bundle1);
                startActivity(intent);

            }
        });

    }

    private void initAll() {
        show_dealers=findViewById(R.id.show_dealers);
    }
}