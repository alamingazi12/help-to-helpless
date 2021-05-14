package com.example.help2helpless;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.help2helpless.model.Donar;
import com.example.help2helpless.model.Essentials;

import de.hdodenhof.circleimageview.CircleImageView;

public class DonarProfileActivity extends AppCompatActivity {
    TextView dlrname,zilla,thana,address,phone;
    CircleImageView shpimage;
    ImageButton btn_back;
    String imageUrl="https://apps.help2helpless.com/donar_profile/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donar_profile);
        initAll();

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }

    private void initAll() {
        btn_back=findViewById(R.id.btn_back);
        dlrname=findViewById(R.id.dname);
        zilla=findViewById(R.id.dlrzilla);
        thana=findViewById(R.id.dlrthana);
        address=findViewById(R.id.address);
        shpimage=findViewById(R.id.shop_image);
        phone=findViewById(R.id.dlrphone);
        Donar donar= Essentials.getDonarData(DonarProfileActivity.this);
        address.setText(donar.getPresentaddr());
        dlrname.setText(donar.getDname());
        zilla.setText(donar.getZilla());
        thana.setText(donar.getThana());
        phone.setText(donar.getDcontact());

        //Picasso.get().load(imageUrl).into(shpimage);
      /*  Picasso.get()
                .load(imageUrl+donar.getDonar_photo())
                .resize(90, 90)
                .centerCrop()
                .into(shpimage);

       */

        Glide.with(DonarProfileActivity.this)
                .load(imageUrl+donar.getDonar_photo()).diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true)
                .into(shpimage);


        /*
        SharedPreferences donarlogininfo=getSharedPreferences("donarinfo",0);
        Gson gson = new Gson();
        String json = donarlogininfo.getString("MyObject", "");
        Donar dealers = gson.fromJson(json, Donar.class);*/
    }
}