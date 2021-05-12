package com.example.help2helpless;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminDashBoardActivity extends AppCompatActivity {

    String url="https://apps.help2helpless.com/donar_amount.php";
    AlertDialog dialog;
    View DialogueView;
    Button show_request,add_amount_donar;
    ImageButton admin_menu,back_btn;
    EditText amount,donar_contact;
    FloatingActionButton floatingActionButton;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);
        initAll();

       back_btn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               goHome();
           }
       });
        add_amount_donar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // createDialoge();
                Intent intent = new Intent(AdminDashBoardActivity.this, AllDonarActivity.class);
                startActivity(intent);
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goHome();
            }
        });

        admin_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(AdminDashBoardActivity.this, admin_menu);
                //inflating menu from xml resource
                popup.inflate(R.menu.admin_menu_item);
                //adding click listener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.logout:
                                logout();
                                break;
                            case R.id.settings:
                                setting();
                                break;
                        }
                        return false;
                    }
                });
                popup.show();
            }
        });
    }

        @Override
        protected void onResume () {
            super.onResume();
            //  getAmount();

        }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        goHome();
    }

    private void goHome(){
        Intent intent=new Intent(AdminDashBoardActivity.this,MainActivity.class);
        startActivity(intent);
    }
        private void setting () {
            Bundle bundle=new Bundle();
            bundle.putString("main","");
            Intent intent = new Intent(AdminDashBoardActivity.this, SettingActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        }


        private void logout () {
            SharedPreferences adminSharedPreference = getSharedPreferences("admininfo", 0);
            SharedPreferences.Editor editor = adminSharedPreference.edit();
            editor.remove("adminuser");
            editor.commit();
            Intent intent = new Intent(AdminDashBoardActivity.this, MainActivity.class);
            startActivity(intent);
        }

        private void getAmount () {
            Log.d("value", "running");
            ApiInterface apiInterface = ApiClient.getApiClient(AdminDashBoardActivity.this).create(ApiInterface.class);
            Call<Amount> amountCall = apiInterface.getAmount();
            amountCall.enqueue(new Callback<Amount>() {
                @Override
                public void onResponse(Call<Amount> call, Response<Amount> response) {
                    Amount amounts = response.body();
                    Log.d("amount:", amounts.getValue());

                }

                @Override
                public void onFailure(Call<Amount> call, Throwable t) {
                    Log.d("amount:", "something wrong");
                    Toast.makeText(AdminDashBoardActivity.this, " something wrong" + amount, Toast.LENGTH_SHORT).show();
                }
            });
        }
   /*
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
              //  addBalance();
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
                    }else{
                        //Log.d("result","No Donar with this Contact");
                        Toast.makeText(AdminDashBoardActivity.this," "+result,Toast.LENGTH_SHORT).show();
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

*/
        private void initAll () {
            back_btn=findViewById(R.id.btn_back);
            floatingActionButton=findViewById(R.id.orderPlus);
            admin_menu = findViewById(R.id.admin_menu_icon);
            show_request = findViewById(R.id.btn_dealer_aprove);
            add_amount_donar = findViewById(R.id.btn_admin_add_money);
            show_request.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(AdminDashBoardActivity.this, DealerRequestActivity.class);
                    startActivity(intent);
                }
            });
        }




}


