package com.example.help2helpless;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.help2helpless.model.Donar;
import com.google.android.material.textfield.TextInputLayout;
import com.makeramen.roundedimageview.RoundedImageView;
import com.muddzdev.styleabletoast.StyleableToast;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AdminSendMoneyActivity extends AppCompatActivity {
    String url="https://apps.help2helpless.com/donar_amount.php";

    //initialize button
    Button btn_send_money_to_donar;
    ImageButton btn_back_image;
    //image initialize
    RoundedImageView donar_profile_pic;
    //textview initialize
    TextView donar_name,address,phone;
    TextInputLayout donar_amount;



    Donar donar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_send_money);
        Bundle bundle = getIntent().getExtras();
        donar = bundle.getParcelable("donar_obj");
        initAll();
        btn_back_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        btn_send_money_to_donar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addBalance();
            }
        });
    }

    private void initAll() {
        btn_back_image=findViewById(R.id.btn_back);
        //button
        btn_send_money_to_donar=findViewById(R.id.btn_donate_client);

        //imageview
        donar_profile_pic=findViewById(R.id.client_profile_pic);

        String imageUrl="https://apps.help2helpless.com/donar_profile/"+donar.getDonar_photo();
        Picasso.get().load(imageUrl).resize(80,80).centerCrop().into(donar_profile_pic);
        //EditText
         donar_amount=findViewById(R.id.donate_amount);
        //textview
        donar_name=findViewById(R.id.client_name);
        address=findViewById(R.id.client_address);
        phone=findViewById(R.id.client_phone);

        donar_name.setText(donar.getDname());
        address.setText(donar.getPresentaddr());
        phone.setText(donar.getDcontact());



    }

    private void addBalance() {

        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    String result=jsonObject.getString("response");
                    if(result.equals("success")){
                       // Toast.makeText(AdminDashBoardActivity.this,"Amount added Successfully",Toast.LENGTH_LONG).show();
                        StyleableToast.makeText(AdminSendMoneyActivity.this,"Money Added Succesfully",R.style.greentoast).show();
                        Intent intent=new Intent(AdminSendMoneyActivity.this,AllDonarActivity.class);
                        startActivity(intent);
                    }else{
                        StyleableToast.makeText(AdminSendMoneyActivity.this,""+result,R.style.mytoast).show();
                        //Log.d("result","No Donar with this Contact");
                       // Toast.makeText(AdminDashBoardActivity.this," "+result,Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }



                ,new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AdminSendMoneyActivity.this,"not Uploaded",Toast.LENGTH_LONG).show();
            }
        })
        {
            @Override
            public byte[] getBody() throws AuthFailureError {
                JSONObject params = new JSONObject();
                try {
                   SharedPreferences adminSharedPreference=getSharedPreferences("admininfo",0);
                   String id=  adminSharedPreference.getString("id","");
                   
                   String amount= donar_amount.getEditText().getText().toString();
                   if(!TextUtils.isEmpty(amount)){

                       Date date=new Date();
                       SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
                       String today=sdf.format(date);

                       String pdatarr[]=today.split("-");
                       params.put("dnr_cont",donar.getDcontact());
                       params.put("amount",amount);
                       params.put("admin_id",id);
                       params.put("date",today);

                   }else{
                     StyleableToast.makeText(AdminSendMoneyActivity.this,"Amount is Empty",R.style.mytoast).show();

                   }



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

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);


    }
}