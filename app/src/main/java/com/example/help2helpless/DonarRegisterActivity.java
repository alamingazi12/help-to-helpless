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

public class DonarRegisterActivity extends AppCompatActivity {
    public static int image_request=2;
    ProgressDialog dialogue;
    Bitmap bitmap;
    String url="https://apps.help2helpless.com/donarinsert.php";
    TextInputLayout dname,profession,dcontacts,mail,presentaddres,thana,dZilla,amounts,uname,pass;
    Button donar_sign,browse_image;
    ImageView d_image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donar_register);
        setFontToActionBar();
        intAll();

        donar_sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                if(TextUtils.isEmpty(name) || TextUtils.isEmpty(professions) || TextUtils.isEmpty(contact) || TextUtils.isEmpty(email) || TextUtils.isEmpty(addres) || TextUtils.isEmpty(dthana) || TextUtils.isEmpty(dzilla) || TextUtils.isEmpty(amount) || TextUtils.isEmpty(username) || TextUtils.isEmpty(password) || bitmap==null ){
                    StyleableToast.makeText(DonarRegisterActivity.this,"One or More Fields Empty", R.style.mytoast).show();
                }else {
                    registerProcess();
                }

            }
        });
        browse_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                browseImage();
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
        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/regular.otf");
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
        d_image=findViewById(R.id.dimage);
        browse_image=findViewById(R.id.dpic_image);
        donar_sign=findViewById(R.id.dsignbtn);

        dialogue=new ProgressDialog(this);
        dialogue.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialogue.setTitle("Loading");
        dialogue.setMessage("Please Wait...");
        dialogue.setCanceledOnTouchOutside(false);




    }
    public  void showProgress(){
        dialogue=new ProgressDialog(this);
        dialogue.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialogue.setTitle("Loading");
        dialogue.setMessage("Please Wait...");
        dialogue.setCanceledOnTouchOutside(false);
        dialogue.show();

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
                d_image.setImageURI(uri);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
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
                        StyleableToast.makeText(DonarRegisterActivity.this,"Registration Success",R.style.mytoast).show();
                        Intent intent=new Intent(DonarRegisterActivity.this,DonarLogin.class);
                        startActivity(intent);
                    }
                    else{
                        dialogue.cancel();
                        StyleableToast.makeText(DonarRegisterActivity.this,""+result,R.style.mytoast).show();
                        //Toast.makeText(DonarRegisterActivity.this," "+result,Toast.LENGTH_LONG).show();
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
                StyleableToast.makeText(DonarRegisterActivity.this,"Network Error",R.style.mytoast).show();
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
                   if(TextUtils.isEmpty(name) || TextUtils.isEmpty(professions) || TextUtils.isEmpty(contact) || TextUtils.isEmpty(email) || TextUtils.isEmpty(addres) || TextUtils.isEmpty(dthana) || TextUtils.isEmpty(amount) || TextUtils.isEmpty(name) || TextUtils.isEmpty(username) || TextUtils.isEmpty(password) || bitmap==null ){
                       StyleableToast.makeText(DonarRegisterActivity.this,"One or More Fields Empty", R.style.mytoast).show();
                   }else {

                   }

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
                       params.put("donar_pic",imageToString(bitmap));





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

    public String imageToString(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte [] imageByte=byteArrayOutputStream.toByteArray();
        String imageImage= Base64.encodeToString(imageByte,Base64.DEFAULT);
        return imageImage;

    }
}