package com.example.help2helpless;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.help2helpless.model.Client;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class ClientProfileActivity extends AppCompatActivity {

       CircleImageView image_profile;
       TextView cname,cdeases,phone,month_income,month_cost,caddres;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_profile);
        setFontToActionBar();
        initAll();

        Bundle bundle= getIntent().getExtras();
        Client client= bundle.getParcelable("client");
        setValue(client);


    }

    private void setValue(Client client) {
        cname.setText(client.getcName());
        cdeases.setText(client.getCdisase());
        phone.setText(client.getCnumber());
        month_income.setText(client.getMincome());
        month_cost.setText(client.getCmedicost());
        caddres.setText(client.getCaddres());
        String imageUrl="https://apps.help2helpless.com/uploads/"+client.getCphoto();
        Picasso.get().load(imageUrl).resize(90,90).centerCrop().into(image_profile);

    }

    private void initAll() {
        cname=findViewById(R.id.cname);
        cdeases=findViewById(R.id.nm_deasese);
        phone=findViewById(R.id.ccontact);
        month_income=findViewById(R.id.cincome);
        month_cost=findViewById(R.id.mcost);
        caddres=findViewById(R.id.caddress);
        image_profile=findViewById(R.id.cprofile_image);

    }

    private void setFontToActionBar() {
        TextView tv = new TextView(ClientProfileActivity.this);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        tv.setLayoutParams(lp);
        tv.setText("Client Profile");
        tv.setTextSize(24);
        tv.setGravity(Gravity.CENTER);
        tv.setTextColor(Color.parseColor("#ffffff"));
        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/regular.otf");
        tv.setTypeface(tf);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(tv);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#8ecae6")));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
}