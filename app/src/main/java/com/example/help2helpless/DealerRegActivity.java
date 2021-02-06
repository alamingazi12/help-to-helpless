package com.example.help2helpless;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class DealerRegActivity extends AppCompatActivity {
  public static int image_request=1, image_request2=2;
  ImageView reg_pic,nid_pic;
  Button signup,reg_up,id_up;
  TextInputLayout dname,fname,dladdress,phone,bks,mail,shpname,thana,Zilla,regno,nid,uname,password;
  Bitmap nid_bitmap,regno_bitmap;

    String url="https://apps.help2helpless.com/dealerinsert.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dealer_reg);

        initAll();
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerProcess();
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
       // imageview init
        reg_pic=findViewById(R.id.shpimage);
        nid_pic=findViewById(R.id.idimage);
      // BUtton init
        signup=findViewById(R.id.dealer_signup);
        reg_up=findViewById(R.id.pic_image1);
        id_up=findViewById(R.id.pic_image2);







    }


    private void registerProcess() {
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    String result=jsonObject.getString("response");
                    if(result.equals("success")){

                        Toast.makeText(DealerRegActivity.this,"You registered"+result,Toast.LENGTH_LONG).show();
                    }
                    else{

                        Toast.makeText(DealerRegActivity.this,"Error Occured"+result,Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }



                ,new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DealerRegActivity.this,"not Uploaded",Toast.LENGTH_LONG).show();
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
                    params.put("nidpic",nid_bitmap);
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