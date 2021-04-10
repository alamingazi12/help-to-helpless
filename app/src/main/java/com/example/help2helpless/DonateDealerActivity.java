package com.example.help2helpless;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.help2helpless.model.Dealer;
import com.example.help2helpless.model.DonarBalance;
import com.example.help2helpless.network.ApiClient;
import com.example.help2helpless.network.ApiInterface;
import com.google.android.material.textfield.TextInputLayout;
import com.muddzdev.styleabletoast.StyleableToast;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DonateDealerActivity extends AppCompatActivity {
    Dealer dealer;
    TextView donar_balace;
    TextInputLayout damount;
    Button btn_donate;
    String imageUrl="https://apps.help2helpless.com/donar_profile/";
    String url="https://apps.help2helpless.com/donar_donation.php";
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate_dealer);
        getData();
        initAll();
        btn_donate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveAmount(dealer);
            }
        });

    }

    private void initAll() {
        donar_balace=findViewById(R.id.d_balance);
        damount=findViewById(R.id.donate_amount);
        btn_donate=findViewById(R.id.btn_donate);
        CircleImageView d_image=findViewById(R.id.donar_profile_pic);
        SharedPreferences   donarinfo=this.getSharedPreferences("donarinfo",0);
        Picasso.get().load(imageUrl+donarinfo.getString("donar_pic",null)).resize(60,60).centerCrop().into(d_image);
    }

    private void getData() {
        Bundle bundle=getIntent().getExtras();
        dealer=bundle.getParcelable("dealers");
    }

    @Override
    protected void onResume() {
        super.onResume();
        getDonarBalance();
    }

    private void getDonarBalance() {
        SharedPreferences donarinfo=getSharedPreferences("donarinfo",0);
        String dcontact=donarinfo.getString("contact",null);
        ApiInterface apiInterface= ApiClient.getApiClient(DonateDealerActivity.this).create(ApiInterface.class);
        Call<DonarBalance> donarBalanceCall= apiInterface.getDonarBalance(dcontact);
        donarBalanceCall.enqueue(new Callback<DonarBalance>() {
            @Override
            public void onResponse(Call<DonarBalance> call, Response<DonarBalance> response) {
                String balance=response.body().getDonar_balance();
                // Toast.makeText(DonarDashBoardActivity.this,""+balance,Toast.LENGTH_LONG).show();
                donar_balace.setText(balance);
                if(balance==null){
                    balance="0";
                    donar_balace.setText(balance);
                }else{
                    donar_balace.setText(balance);
                }
//
            }

            @Override
            public void onFailure(Call<DonarBalance> call, Throwable t) {

            }
        });

    }
    private void saveAmount(final Dealer dealer) {

        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    String result=jsonObject.getString("response");
                    if(result.equals("success")){
                   TextInputLayout description=     findViewById(R.id.amount_description);
                   description.getEditText().setText("");
                        damount.getEditText().setText("");
                        onResume();
                        // Toast.makeText(context,"Dealer Added Successfully",Toast.LENGTH_LONG).show();
                        StyleableToast.makeText(DonateDealerActivity.this, "Donation Added Successfully", Toast.LENGTH_LONG, R.style.mytoast).show();
                        AddDealerActivity addDealerActivity=new AddDealerActivity();
                        //addDealerActivity.initRecycler();
                        //addDealerActivity.Back();


                    }
                    else{
                        StyleableToast.makeText(DonateDealerActivity.this,""+result,R.style.mytoast).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }



                ,new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                StyleableToast.makeText(DonateDealerActivity.this,"Network Error",R.style.mytoast).show();
                // Toast.makeText(context,"not Uploaded",Toast.LENGTH_LONG).show();
            }
        })
        {
            @Override
            public byte[] getBody() throws AuthFailureError {
                JSONObject params = new JSONObject();
                try {
                    SharedPreferences donarinfo=getSharedPreferences("donarinfo",0);
                    String dcontact=donarinfo.getString("contact",null);
                    Date date=new Date();
                    SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
                    String today=sdf.format(date);

                    String pdatarr[]=today.split("-");
                    Log.d("phone",dcontact);
                    params.put("dnr_cont",dcontact);
                    params.put("dealer_cont",dealer.getPhone());
                    params.put("month",getMonthString(pdatarr[1]));
                    params.put("year",pdatarr[2]);
                    params.put("amount",damount.getEditText().getText().toString());


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