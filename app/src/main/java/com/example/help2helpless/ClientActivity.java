package com.example.help2helpless;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
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
import com.muddzdev.styleabletoast.StyleableToast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ClientActivity extends AppCompatActivity {
     String baseurl="https://apps.help2helpless.com/insertclient.php";
     public static int image_request=1;
     ProgressDialog progressDialog;
     Bitmap bitmap;
     ImageView clientimg;
     TextInputLayout clname,fname,cprofession,cage,cincome,incomesrc,nsons,ndaughter,gurdian,gurdian_contact,clientbk_num,deases
             ,medicost,caddress,ccontact,dcontact;

     Button client_signup,browse_image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);
        setFontToActionBar();
        initAll();

        browse_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                browseImage();
            }
        });
        client_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name= clname.getEditText().getText().toString();
                String fathername= fname.getEditText().getText().toString();
                String profession=  cprofession.getEditText().getText().toString();
                String age= cage.getEditText().getText().toString();
                String income= cincome.getEditText().getText().toString();

                String  isrc= incomesrc.getEditText().getText().toString();
                String nof_son=  nsons.getEditText().getText().toString();
                String daughtr= ndaughter.getEditText().getText().toString();
                String guardian=  gurdian.getEditText().getText().toString();
                String gcontact= gurdian_contact.getEditText().getText().toString();
                String clbknum=  clientbk_num.getEditText().getText().toString();
                String deasis=  deases.getEditText().getText().toString();
                String mcost=  medicost.getEditText().getText().toString();
                String addres=  caddress.getEditText().getText().toString();
                String ccon= ccontact.getEditText().getText().toString();
                String dcon=  dcontact.getEditText().getText().toString();
                if(TextUtils.isEmpty(name) || TextUtils.isEmpty(fathername) || TextUtils.isEmpty(profession) || TextUtils.isEmpty(age) || TextUtils.isEmpty(income) || TextUtils.isEmpty(isrc) || TextUtils.isEmpty(nof_son) || TextUtils.isEmpty(daughtr) || TextUtils.isEmpty(guardian) || TextUtils.isEmpty(gcontact) || TextUtils.isEmpty(clbknum) || TextUtils.isEmpty(deasis) || TextUtils.isEmpty(mcost) || TextUtils.isEmpty(addres) || TextUtils.isEmpty(ccon) || bitmap==null){
                    StyleableToast.makeText(ClientActivity.this,"One or More Fields Empty", R.style.mytoast).show();
                }else{
                    registerProcess();
                }

            }
        });
    }

   public void showProgress(){
       progressDialog=new ProgressDialog(this);
       progressDialog.setTitle("Processing");
       progressDialog.setMessage("Please Wait...");
       progressDialog.setCanceledOnTouchOutside(false);
       progressDialog.show();
   }

    private void setFontToActionBar() {
        TextView tv = new TextView(ClientActivity.this);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        tv.setLayoutParams(lp);
        tv.setText("Client Registration");
        tv.setTextSize(24);
        tv.setGravity(Gravity.CENTER);
        tv.setTextColor(Color.parseColor("#ffffff"));
        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/regular.otf");
        tv.setTypeface(tf);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(tv);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#8ecae6")));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initAll() {
        clname=findViewById(R.id.cname);
        fname=findViewById(R.id.fname);
        cprofession=findViewById(R.id.cprofesion);
        cage=findViewById(R.id.cage);
        cincome=findViewById(R.id.mincome);
        incomesrc=findViewById(R.id.incomesrc);
        nsons=findViewById(R.id.nsons);
        ndaughter=findViewById(R.id.ndaughter);
        gurdian=findViewById(R.id.gurdian);
        gurdian_contact=findViewById(R.id.gurdian_num);
        clientbk_num=findViewById(R.id.cbk_num);
        deases=findViewById(R.id.deasese);
        medicost=findViewById(R.id.mcost);
        caddress=findViewById(R.id.caddress);
        ccontact=findViewById(R.id.ccontact);
        dcontact=findViewById(R.id.dcontact);
        clientimg=findViewById(R.id.cimage);
        client_signup=findViewById(R.id.client_signup);
        browse_image=findViewById(R.id.pic_image);

    }


    private void registerProcess() {
        showProgress();
        StringRequest stringRequest=new StringRequest(Request.Method.POST, baseurl, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonObject=new JSONObject(response);
                    String result=jsonObject.getString("response");
                    if(result.equals("success")){
                        progressDialog.cancel();
                        StyleableToast.makeText(ClientActivity.this,"Registration Success",R.style.mytoast).show();
                        //Toast.makeText(ClientActivity.this,"You registered Successfully",Toast.LENGTH_LONG).show();
                    }
                    else{
                        progressDialog.cancel();
                        StyleableToast.makeText(ClientActivity.this,""+result,R.style.mytoast).show();
                    }
                } catch (JSONException e) {
                    progressDialog.cancel();
                    e.printStackTrace();
                }
            }
        }



                ,new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.cancel();
                StyleableToast.makeText(ClientActivity.this,"Network Errror",R.style.mytoast).show();
            }
        })
        {
            @Override
            public byte[] getBody() throws AuthFailureError {
                JSONObject params = new JSONObject();
                //String passwordInput = textInputPassword.getEditText().getText().toString().trim()
                try {
                   String name= clname.getEditText().getText().toString();
                   String fathername= fname.getEditText().getText().toString();
                   String profession=  cprofession.getEditText().getText().toString();
                   String age= cage.getEditText().getText().toString();
                   String income= cincome.getEditText().getText().toString();

                   String  isrc= incomesrc.getEditText().getText().toString();
                   String nof_son=  nsons.getEditText().getText().toString();
                   String daughtr= ndaughter.getEditText().getText().toString();
                   String guardian=  gurdian.getEditText().getText().toString();
                   String gcontact= gurdian_contact.getEditText().getText().toString();
                   String clbknum=  clientbk_num.getEditText().getText().toString();
                   String deasis=  deases.getEditText().getText().toString();
                   String mcost=  medicost.getEditText().getText().toString();
                   String addres=  caddress.getEditText().getText().toString();
                   String ccon= ccontact.getEditText().getText().toString();
                   String dcon=  dcontact.getEditText().getText().toString();
                    if(bitmap!=null){
                       // params.put("name",imagename.getText().toString().trim());
                        params.put("name",name);
                        params.put("fname",fathername);
                        params.put("profession",profession);
                        params.put("age",age);
                        params.put("mincome",income);
                        params.put("incomesrc",isrc);
                        params.put("nson",nof_son);
                        params.put("ndaughter",daughtr);
                        params.put("guardian",guardian);
                        params.put("guardianno",gcontact);
                        params.put("clientbksnum",clbknum);
                        params.put("cdisease",deasis);
                        params.put("cmedicost",mcost);
                        params.put("caddress",addres);

                        params.put("cphoto",imageToString(bitmap));
                        params.put("ccontact",ccon);
                        params.put("dcontact",dcon);

                      //  Log.d("tag",imageToString(bitmap));
                    }
                    else{
                        //StyleableToast.makeText(ClientActivity.this,"Bitmap Null",R.style.mytoast).show();
                       // Log.d("tag","bitmap  null");
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

        RequestQueue requestQueue= Volley.newRequestQueue(ClientActivity.this);
        requestQueue.add(stringRequest);

    }


    private void browseImage(){
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,image_request);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==image_request &&resultCode==RESULT_OK && data!=null){
            Uri uri=data.getData();

            try {
                bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                clientimg.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public String imageToString(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte [] imageByte=byteArrayOutputStream.toByteArray();
        String imageImage= Base64.encodeToString(imageByte,Base64.DEFAULT);

        return imageImage;

    }
}