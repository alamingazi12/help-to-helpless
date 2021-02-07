package com.example.help2helpless;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.help2helpless.model.Dealer;
import com.squareup.picasso.Picasso;

public class ApprovedActivity extends AppCompatActivity {
     ImageView shpimage;
     Dealer dealer;
    String imageUrl="https://apps.help2helpless.com/uploads/";
    TextView dlrname,zilla,thana,shopname,drugregno,phone;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approve);
        Bundle bundle=getIntent().getExtras();
         dealer= bundle.getParcelable("deler");

        initAll();
    }

    private void initAll() {
        dlrname=findViewById(R.id.dlrname);
        zilla=findViewById(R.id.dlrzilla);
        thana=findViewById(R.id.dlrthana);
        shopname=findViewById(R.id.shopname);
        drugregno=findViewById(R.id.regno);
        shpimage=findViewById(R.id.shop_image);
        phone=findViewById(R.id.dlrphone);

        dlrname.setText(dealer.getName());
        zilla.setText(dealer.getShpnmzilla());
        thana.setText(dealer.getShpnmthana());
        shopname.setText(dealer.getShopnme());
        drugregno.setText(dealer.getDrugsell_regnum());
        phone.setText(dealer.getPhone());
         String url=imageUrl+dealer.getShoppic();
        //Picasso.get().load(imageUrl).into(shpimage);
        Picasso.get()
                .load(url)
                .resize(200, 200)
                .centerCrop()
                .into(shpimage);


    }
}
