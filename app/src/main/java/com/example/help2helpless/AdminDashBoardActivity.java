package com.example.help2helpless;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.help2helpless.model.Admin;
import com.example.help2helpless.model.Amount;
import com.example.help2helpless.network.ApiClient;
import com.example.help2helpless.network.ApiInterface;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminDashBoardActivity extends AppCompatActivity {

    String url="https://apps.help2helpless.com/donar_amount.php";
    AlertDialog dialog;
    View DialogueView;
    Button show_request,add_amount_donar;
  EditText amount,donar_contact;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);
        setFontToActionBar();
      Bundle bundle= getIntent().getExtras();
      Admin admin=bundle.getParcelable("obj");
       initAll();
       getAmount();
      add_amount_donar.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              createDialoge();
          }
      });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getAmount();
    }

    private void getAmount() {
        Log.d("value","running");
        ApiInterface apiInterface= ApiClient.getApiClient(AdminDashBoardActivity.this).create(ApiInterface.class);
        Call<Amount> amountCall =apiInterface.getAmount();
       amountCall.enqueue(new Callback<Amount>() {
          @Override
          public void onResponse(Call<Amount> call, Response<Amount> response) {
              Amount amounts=response.body();
              Log.d("amount:",amounts.getValue());

          }

          @Override
          public void onFailure(Call<Amount> call, Throwable t) {
              Log.d("amount:","something wrong");
              Toast.makeText(AdminDashBoardActivity.this," something wrong"+amount,Toast.LENGTH_SHORT).show();
          }
      });
    }

    public void   createDialoge(){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        DialogueView= LayoutInflater.from(this).inflate(R.layout.add_donar_amount,null);
        Button button_ok= DialogueView.findViewById(R.id.selectok);
        amount=DialogueView.findViewById(R.id.addamount);
        donar_contact=DialogueView.findViewById(R.id.dnr_conts);
        Button button_cancel=DialogueView.findViewById(R.id.selectcancel);
        button_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addBalance();
                dialog.dismiss();
            }
        });
        button_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        builder.setView(DialogueView);
        dialog= builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();


    }

    private void addBalance() {

        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    String result=jsonObject.getString("response");
                    if(result.equals("success")){
                        Toast.makeText(AdminDashBoardActivity.this,"Amount added Successfully",Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                    }
                    else{
                        Log.d("result","No Donar with this Contact");
                        Toast.makeText(AdminDashBoardActivity.this,"No Donar with this Contact",Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }



                ,new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AdminDashBoardActivity.this,"not Uploaded",Toast.LENGTH_LONG).show();
            }
        })
        {
            @Override
            public byte[] getBody() throws AuthFailureError {
                JSONObject params = new JSONObject();
                try {
                    Date date=new Date();
                    SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
                    String today=sdf.format(date);

                    String pdatarr[]=today.split("-");
                    params.put("dnr_cont",donar_contact.getText().toString());
                    params.put("amount",amount.getText().toString().trim());

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
        add_amount_donar=findViewById(R.id.btn_amount);
        show_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AdminDashBoardActivity.this,DealerRequestActivity.class);
                startActivity(intent);
            }
        });
    }
}
