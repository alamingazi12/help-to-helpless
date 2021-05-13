package com.example.help2helpless;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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
import com.example.help2helpless.model.Client;
import com.example.help2helpless.model.DealerBalance;
import com.example.help2helpless.network.ApiClient;
import com.example.help2helpless.network.ApiInterface;
import com.google.android.material.textfield.TextInputLayout;
import com.makeramen.roundedimageview.RoundedImageView;
import com.muddzdev.styleabletoast.StyleableToast;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.PicassoProvider;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClientDonationActivity extends AppCompatActivity {
    String url="https://apps.help2helpless.com/add_discount.php";
    Client client_data;
    RoundedImageView profile_view_image;
    CircleImageView dealer_profile_image;
    ImageButton btn_image_back;
    TextView text_client_name,address,phone,dealer_balance;
    Button donat_to_client;
    TextInputLayout amount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_donation);
        Bundle bundle= getIntent().getExtras();
         client_data= bundle.getParcelable("client");
         iniiAll();

         btn_image_back.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 onBackPressed();
             }
         });

        donat_to_client.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              String amounts=  amount.getEditText().getText().toString().trim();
                if(TextUtils.isEmpty(amounts)){
                    StyleableToast.makeText(ClientDonationActivity.this,"Amount is Empty",R.style.mytoast).show();
                }else{
                    addDiscount(client_data.getPhone());

                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getDealerBalance();
    }

    private void iniiAll() {
        btn_image_back=findViewById(R.id.btn_back);
        dealer_profile_image=findViewById(R.id.dealer_profile_pic);
        profile_view_image=findViewById(R.id.client_profile_pic);
        text_client_name=findViewById(R.id.client_name);
        address=findViewById(R.id.client_address);
        phone=findViewById(R.id.client_phone);
        donat_to_client=findViewById(R.id.btn_donate_client);
        amount=findViewById(R.id.donate_amount);
        dealer_balance=findViewById(R.id.d_balance);


        text_client_name.setText(client_data.getcName());
        address.setText(client_data.getAddress());
        phone.setText(client_data.getPhone());


        String imageUrl="https://apps.help2helpless.com/client_profile/"+client_data.getProfile_pic();
        Picasso.get().invalidate(imageUrl);
        Picasso.get().load(imageUrl).resize(80,80).centerCrop().into(profile_view_image);
        SharedPreferences dealerlogininfo=getSharedPreferences("dealerinfo",0);
        String  dealer_image_url="https://apps.help2helpless.com/uploads/"+dealerlogininfo.getString("dealer_pic","");
        Log.d("images",dealer_image_url);
        Picasso.get().invalidate(dealer_image_url);
        Picasso.get().load(dealer_image_url).resize(80,80).centerCrop().into(dealer_profile_image);

    }


    private void getDealerBalance() {
        SharedPreferences dealerlogininfo=getSharedPreferences("dealerinfo",0);
        ApiInterface apiInterface=ApiClient.getApiClient(this).create(ApiInterface.class);
        Call<DealerBalance> dealerBalanceCall=  apiInterface.getDealerBalance(dealerlogininfo.getString("contact",""));
        dealerBalanceCall.enqueue(new Callback<DealerBalance>() {
            @Override
            public void onResponse(Call<DealerBalance> call, Response<DealerBalance> response) {
                String dealer_balances=  response.body().getDealer_balance();
                //Log.d("balance",dealer_balance);

                if(dealer_balances==null){
                    dealer_balances="0";
                    dealer_balance.setText(""+dealer_balances);

                }else {
                    dealer_balance.setText(""+dealer_balances);
                    //} Toast.makeText(DealerActivity.this,"avg:"+dealer_balance,Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<DealerBalance> call, Throwable t) {
                Toast.makeText(ClientDonationActivity.this,"Network Error",Toast.LENGTH_LONG).show();
            }
        });
    }
    private void addDiscount(final String cnumber) {

        final StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    String result=jsonObject.getString("response");
                    if(result.equals("success")){
                        onResume();
                        StyleableToast.makeText(ClientDonationActivity.this,"Donation Sent Successfully",R.style.mytoast).show();
                        Intent intent=new Intent(ClientDonationActivity.this,DiscountActivity.class);
                        startActivity(intent);
                       // Toast.makeText(ClientDonationActivity.this,"Discount added Successfully",Toast.LENGTH_LONG).show();
                    }
                    else{
                        Toast.makeText(ClientDonationActivity.this," "+result,Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }



                ,new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ClientDonationActivity.this,"not Uploaded",Toast.LENGTH_LONG).show();
            }
        })
        {
            @Override
            public byte[] getBody() throws AuthFailureError {
                JSONObject params = new JSONObject();
                try {
                    SharedPreferences dealerlogininfo=getSharedPreferences("dealerinfo",0);
                    Date date=new Date();
                    SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
                    String today=sdf.format(date);
                    String pdatarr[]=today.split("-");
                    params.put("cl_contact",cnumber);
                    params.put("dlr_contact",dealerlogininfo.getString("contact",null));
                    params.put("month",getMonthString(pdatarr[1]));
                    params.put("year",pdatarr[2]);
                    params.put("amount",amount.getEditText().getText().toString().trim());


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

    private String getMonthString(String s) {

        String monthName=null;

        switch (s){
            case "01":
                monthName="January";
                break;
            case "02":
                monthName="February";
                break;
            case "03":
                monthName="March";
                break;
            case "04":
                monthName="April";
                break;
            case "05":
                monthName="May";
                break;
            case "06":
                monthName="June";
                break;
            case "07":
                monthName="July";
                break;
            case "08":
                monthName="August";
                break;
            case "09":
                monthName="September";
                break;
            case "10":
                monthName="October";
                break;
            case "11":
                monthName="November";
                break;
            case "12":
                monthName="December";
                break;

        }
        return monthName;
    }
}