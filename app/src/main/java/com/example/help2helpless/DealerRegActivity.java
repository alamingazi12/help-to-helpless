package com.example.help2helpless;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.help2helpless.model.Responses;
import com.example.help2helpless.network.ApiClient;
import com.example.help2helpless.network.ApiInterface;
import com.google.android.material.textfield.TextInputLayout;
import com.muddzdev.styleabletoast.StyleableToast;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DealerRegActivity extends AppCompatActivity { ProgressDialog dialogue;
  public static int image_request=1, image_request2=2;
  ImageView reg_pic,nid_pic;
  Button signup,reg_up,id_up;
  TextInputLayout dname,fname,dladdress,phone,bks,mail,shpname,thana,Zilla,regno,nid,uname,password;
  Bitmap nid_bitmap,regno_bitmap;

    String url="https://apps.help2helpless.com/temp_dealerinsert.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dealer_reg);
        setFontToActionBar();
        initAll();
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // showProgress();
                String name=dname.getEditText().getText().toString();
                String dfname=fname.getEditText().getText().toString();
                String daddres=dladdress.getEditText().getText().toString();
                String dlphone=phone.getEditText().getText().toString();
                String dlbks=bks.getEditText().getText().toString();
                String dlmail=mail.getEditText().getText().toString();
                String dlshpname=shpname.getEditText().getText().toString();
                String dlthana=thana.getEditText().getText().toString();
                String dlZilla=Zilla.getEditText().getText().toString();
                String dlregno=regno.getEditText().getText().toString();
                String dlnid=nid.getEditText().getText().toString();
                String dluname=uname.getEditText().getText().toString();
                String dlpass=password.getEditText().getText().toString();

                if(regno_bitmap==null || nid_bitmap==null || TextUtils.isEmpty(name) || TextUtils.isEmpty(dfname) || TextUtils.isEmpty(daddres) || TextUtils.isEmpty(dlphone) ||TextUtils.isEmpty(dlbks) || TextUtils.isEmpty(dlmail) || TextUtils.isEmpty(dlshpname) || TextUtils.isEmpty(dlthana) || TextUtils.isEmpty(dlZilla) || TextUtils.isEmpty(dlregno) || TextUtils.isEmpty(dlnid) || TextUtils.isEmpty(dluname) || TextUtils.isEmpty(dlpass)){
                    StyleableToast.makeText(DealerRegActivity.this,"One or More Fields Empty", R.style.mytoast).show();
                }else {

                    ApiInterface apiInterface=ApiClient.getApiClient(DealerRegActivity.this).create(ApiInterface.class);
                    Call<Responses> responsesCall=apiInterface.dealerSignResponse(name,dfname,daddres,dlphone,dlbks,dlmail,dlshpname,dlthana,dlZilla,imageToString(regno_bitmap),dlregno,dlnid,imageToString(nid_bitmap),dluname,dlpass);
                    responsesCall.enqueue(new Callback<Responses>() {
                        @Override
                        public void onResponse(Call<Responses> call, Response<Responses> response) {
                            String result=response.body().getMessage();
                            if(result.equals("success")){
                              dialogue.cancel();
                                StyleableToast.makeText(DealerRegActivity.this,"Registration Success",R.style.mytoast).show();
                            }else {
                                dialogue.cancel();
                                StyleableToast.makeText(DealerRegActivity.this,""+result,R.style.mytoast).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Responses> call, Throwable t) {
                            dialogue.cancel();
                            StyleableToast.makeText(DealerRegActivity.this,"Network Error",R.style.mytoast).show();
                        }
                    });
                }

            }
        });
        reg_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               browseImage();
            }
        });
     id_up.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             browseImage2();
         }
     });
    }

    private void dealerSign() {

    }

    public  void showProgress(){
        dialogue=new ProgressDialog(this);
        dialogue.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialogue.setTitle("Loading");
        dialogue.setMessage("Please Wait...");
        dialogue.setCanceledOnTouchOutside(false);
        dialogue.show();

    }

    private void setFontToActionBar() {
        TextView tv = new TextView(DealerRegActivity.this);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        tv.setLayoutParams(lp);
        tv.setText("Dealer Registration");
        tv.setTextSize(24);
        tv.setGravity(Gravity.CENTER);
        tv.setTextColor(Color.parseColor("#ffffff"));
        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/regular.otf");
        tv.setTypeface(tf);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(tv);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    private void initAll() {
        dname=findViewById(R.id.dlname);
        fname=findViewById(R.id.dlfname);
        dladdress=findViewById(R.id.dladdres);

        phone=findViewById(R.id.dlphon);
        bks=findViewById(R.id.dlbknum);
        mail=findViewById(R.id.dlmail);
        shpname=findViewById(R.id.shpname);
        thana=findViewById(R.id.shpthana);
        Zilla=findViewById(R.id.shpzila);
        regno=findViewById(R.id.dlregno);
        nid=findViewById(R.id.nidnum);
        uname=findViewById(R.id.dluname);
        password=findViewById(R.id.dlpass);
       // imageview_style.xml init
        reg_pic=findViewById(R.id.shpimage);
        nid_pic=findViewById(R.id.idimage);
      // BUtton init
        signup=findViewById(R.id.dealer_signup);
        reg_up=findViewById(R.id.pic_image1);
        id_up=findViewById(R.id.pic_image2);







    }


    private void registerProcess() {
        showProgress();
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    String result=jsonObject.getString("response");
                    if(result.equals("success")){
                        dialogue.cancel();
                        StyleableToast.makeText(DealerRegActivity.this,"Registration Success",R.style.mytoast).show();
                        //Toast.makeText(DealerRegActivity.this,"You registered Successfully",Toast.LENGTH_LONG).show();
                    }
                    else{
                        dialogue.cancel();
                        StyleableToast.makeText(DealerRegActivity.this,""+result,R.style.mytoast).show();
                       // Toast.makeText(DealerRegActivity.this," "+result,Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }



                ,new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialogue.cancel();
                StyleableToast.makeText(DealerRegActivity.this,"Network Errror",R.style.mytoast).show();
                //Toast.makeText(DealerRegActivity.this,"not Uploaded",Toast.LENGTH_LONG).show();
            }
        })
        {
            @Override
            public byte[] getBody() throws AuthFailureError {
                JSONObject params = new JSONObject();
                try {


                    String name=dname.getEditText().getText().toString();
                    String dfname=fname.getEditText().getText().toString();
                    String daddres=dladdress.getEditText().getText().toString();
                    String dlphone=phone.getEditText().getText().toString();
                    String dlbks=bks.getEditText().getText().toString();
                    String dlmail=mail.getEditText().getText().toString();
                    String dlshpname=shpname.getEditText().getText().toString();
                    String dlthana=thana.getEditText().getText().toString();
                    String dlZilla=Zilla.getEditText().getText().toString();
                    String dlregno=regno.getEditText().getText().toString();
                    String dlnid=nid.getEditText().getText().toString();
                    String dluname=uname.getEditText().getText().toString();
                    String dlpass=password.getEditText().getText().toString();

                    params.put("name",name);
                    params.put("fname",dfname);
                    params.put("homaddr",daddres);
                    params.put("phone",dlphone);
                    params.put("bknumber",dlbks);
                    params.put("email",dlmail);
                    params.put("shopname",dlshpname);
                    params.put("shopthana",dlthana);
                    params.put("shopzilla",dlZilla);
                    params.put("shoppic",imageToString(regno_bitmap));
                    params.put("regno",dlregno);
                    params.put("nid",dlnid);
                    params.put("nidpic",imageToString(nid_bitmap));
                    params.put("username",dluname);
                    params.put("password",dlpass);


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

        RequestQueue requestQueue= Volley.newRequestQueue(DealerRegActivity.this);
        requestQueue.add(stringRequest);

    }

    private void browseImage(){
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,image_request);
    }

    private void browseImage2(){
        Intent intent2=new Intent();
        intent2.setType("image/*");
        intent2.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent2,image_request2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==image_request &&resultCode==RESULT_OK && data!=null){
            Uri uri=data.getData();

            try {
                    regno_bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                    reg_pic.setImageBitmap(regno_bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }


        }

       else if(requestCode==image_request2 &&resultCode==RESULT_OK && data!=null){
            Uri uri=data.getData();
            try {
                nid_bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                nid_pic.setImageBitmap(nid_bitmap);
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