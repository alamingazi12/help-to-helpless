package com.example.help2helpless;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
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
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class ApprovedActivity extends AppCompatActivity {
     ImageView shpimage;
     String url="https://apps.help2helpless.com/dealerinsert.php";
     Dealer dealer;
     String imageUrl="https://apps.help2helpless.com/uploads/";
     TextView dlrname,zilla,thana,shopname,drugregno,phone;

     Button confirm;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approve);
        Bundle bundle=getIntent().getExtras();
         dealer= bundle.getParcelable("deler");

        initAll();
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    registerProcess();
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
               //sendSMS();
            }
        });

    }

    private void sendSMS() {

        Intent intent=new Intent(getApplicationContext(),MainActivity.class);
        PendingIntent pi=PendingIntent.getActivity(getApplicationContext(), 0, intent,0);

        //Get the SmsManager instance and call the sendTextMessage method to send message
        SmsManager sms=SmsManager.getDefault();
        sms.sendTextMessage(dealer.getPhone(), null, "Registration Approved", pi,null);

        Toast.makeText(getApplicationContext(), "Message Sent successfully!",
                Toast.LENGTH_LONG).show();
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
                    params.put("fname",dealer.getFathername());
                    params.put("homaddr",dealer.getHmaddres());
                    params.put("phone",dealer.getPhone());
                    params.put("bknumber",dealer.getBksnum());
                    params.put("email",dealer.getEmail());
                    params.put("shopname",dealer.getShopnme());
                    params.put("shopthana",dealer.getShpnmthana());
                    params.put("shopzilla",dealer.getShpnmzilla());
                    params.put("shoppic","123");
                    params.put("regno",dealer.getDrugsell_regnum());
                    params.put("nid",dealer.getNid());
                    params.put("nidpic","456");
                    params.put("username",dealer.getUsernm());
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
        dlrname=findViewById(R.id.rdname);
        zilla=findViewById(R.id.dlrzilla);
        thana=findViewById(R.id.dlrthana);
        shopname=findViewById(R.id.shopname);
        drugregno=findViewById(R.id.regno);
        shpimage=findViewById(R.id.shop_image);
        phone=findViewById(R.id.dlrphone);
        confirm=findViewById(R.id.confirm_btn);

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
