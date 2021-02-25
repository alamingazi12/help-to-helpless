package com.example.help2helpless;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

public class DonarRegisterActivity extends AppCompatActivity {

    String url="https://apps.help2helpless.com/donarinsert.php";
    TextInputLayout dname,profession,dcontacts,mail,presentaddres,thana,dZilla,amounts,uname,pass;
    Button donar_sign;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donar_register);
        setFontToActionBar();
        intAll();

        donar_sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerProcess();
            }
        });
    }

    private void setFontToActionBar() {
        TextView tv = new TextView(DonarRegisterActivity.this);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        tv.setLayoutParams(lp);
        tv.setText("Donar Registration");
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


    private void intAll() {
        dname=findViewById(R.id.dname);
        profession=findViewById(R.id.dprofession);
        dcontacts=findViewById(R.id.dcontacts);
        mail=findViewById(R.id.demail);
        presentaddres=findViewById(R.id.dpresntaddres);
        thana=findViewById(R.id.dthana);
        dZilla=findViewById(R.id.dzilla);
        amounts=findViewById(R.id.damount);
        uname=findViewById(R.id.duname);
        pass=findViewById(R.id.dpass);
        donar_sign=findViewById(R.id.dsignbtn);



    }

    private void registerProcess() {
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    String result=jsonObject.getString("response");
                    if(result.equals("success")){
                        Toast.makeText(DonarRegisterActivity.this,"You registered Successfully",Toast.LENGTH_LONG).show();
                        Intent intent=new Intent(DonarRegisterActivity.this,DonarLogin.class);
                        startActivity(intent);
                    }
                    else{

                        Toast.makeText(DonarRegisterActivity.this,"Error Occured"+result,Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }



                ,new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DonarRegisterActivity.this,"not Uploaded",Toast.LENGTH_LONG).show();
            }
        })
        {
            @Override
            public byte[] getBody() throws AuthFailureError {
                JSONObject params = new JSONObject();
                try {

                    String name=dname.getEditText().getText().toString();
                    String professions=profession.getEditText().getText().toString();
                    String contact=dcontacts.getEditText().getText().toString();
                    String email=mail.getEditText().getText().toString();
                    String addres=presentaddres.getEditText().getText().toString();
                    String dthana=thana.getEditText().getText().toString();
                    String dzilla=dZilla.getEditText().getText().toString();
                    String amount=amounts.getEditText().getText().toString();
                    String username=uname.getEditText().getText().toString().trim();
                    String password=pass.getEditText().getText().toString().trim();


                    params.put("name",name);
                    params.put("profession",professions);
                    params.put("contact",contact);
                    params.put("demail",email);
                    params.put("presentaddr",addres);
                    params.put("zilla",dzilla);
                    params.put("thana",dthana);
                    params.put("donation",amount);
                    params.put("uname",username);
                    params.put("passwrd",password);


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

        RequestQueue requestQueue= Volley.newRequestQueue(DonarRegisterActivity.this);
        requestQueue.add(stringRequest);

    }
}