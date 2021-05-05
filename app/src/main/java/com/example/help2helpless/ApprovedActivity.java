package com.example.help2helpless;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.help2helpless.model.Dealer;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import de.hdodenhof.circleimageview.CircleImageView;

public class ApprovedActivity extends AppCompatActivity {
     CircleImageView shpimage;
     String url="https://apps.help2helpless.com/dealerinsert.php";
     Dealer dealer;
     String imageUrl="https://apps.help2helpless.com/uploads/";
     TextView dlrname,zilla,thana,shopname,drugregno,phone;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approve);
        Bundle bundle=getIntent().getExtras();
        // dealer= bundle.getParcelable("deler");

        initAll();
        ImageButton back_image_btn=findViewById(R.id.btn_back);
        back_image_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ApprovedActivity.this,DealerActivity.class);
                startActivity(intent);
            }
        });


    }



    private void registerProcess() {
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    String result=jsonObject.getString("response");
                    if(result.equals("success")){

                        Toast.makeText(ApprovedActivity.this,"You registered"+result,Toast.LENGTH_LONG).show();
                    }
                    else{

                        Toast.makeText(ApprovedActivity.this,"Error Occured"+result,Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }



                ,new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ApprovedActivity.this,"not Uploaded",Toast.LENGTH_LONG).show();
            }
        })
        {
            @Override
            public byte[] getBody() throws AuthFailureError {
                JSONObject params = new JSONObject();
                try {



                    params.put("name",dealer.getName());

                    params.put("homaddr",dealer.getHmaddres());
                    params.put("phone",dealer.getPhone());

                    params.put("email",dealer.getEmail());

                    params.put("shopthana",dealer.getShpnmthana());
                    params.put("shopzilla",dealer.getShpnmzilla());
                    params.put("shoppic","123");
                    params.put("nidpic","456");
                    params.put("password",dealer.getPasswrd());


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return params.toString().getBytes();
            }

            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

        };

        RequestQueue requestQueue= Volley.newRequestQueue(ApprovedActivity.this);
        requestQueue.add(stringRequest);

    }







    private void initAll() {
        dlrname=findViewById(R.id.dname);
        zilla=findViewById(R.id.dlrzilla);
        thana=findViewById(R.id.dlrthana);
        shopname=findViewById(R.id.shopname);
        drugregno=findViewById(R.id.regno);
        shpimage=findViewById(R.id.shop_image);
        phone=findViewById(R.id.dlrphone);

        SharedPreferences dealerlogininfo=getSharedPreferences("dealerinfo",0);
        Gson gson = new Gson();
        String json = dealerlogininfo.getString("MyObject", "");
        Dealer dealers = gson.fromJson(json, Dealer.class);

        dlrname.setText(dealers.getName());
        zilla.setText(dealers.getShpnmzilla());
        thana.setText(dealers.getShpnmthana());
        phone.setText(dealers.getPhone());

        //Picasso.get().load(imageUrl).into(shpimage);
        Picasso.get()
                .load(imageUrl+dealers.getProfile_pic())
                .resize(90, 90)
                .centerCrop()
                .into(shpimage);


    }
}
