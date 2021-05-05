package com.example.help2helpless;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.help2helpless.adapter.CurrencyAdapter;
import com.example.help2helpless.model.Responses;
import com.example.help2helpless.model.Sections;
import com.example.help2helpless.network.ApiClient;
import com.example.help2helpless.network.ApiInterface;
import com.google.android.material.textfield.TextInputLayout;
import com.muddzdev.styleabletoast.StyleableToast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DonarRegisterActivity extends AppCompatActivity {

    RecyclerView currencycontainer;
    ArrayList<Sections> currencies;
    CurrencyAdapter currencyAdapter;
    public  static SharedPreferences sharedPreferences;
    public static SharedPreferences.Editor editor;
    public static String activity_value="";
    public static   AlertDialog alertDialog;
    public static int image_request=2;
    ProgressDialog dialogue;
    Bitmap bitmap;
    String url="https://apps.help2helpless.com/donarinsert.php";
    TextInputLayout dname,dcontacts,pass;
    Button donar_sign,browse_image;


  String type;
   public static Button dZilla;
    ImageView d_image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donar_register);

        intAll();
        SharedPreferences    usertype=getSharedPreferences("typedata",0);
        if(!usertype.getString("type","").equals("donar")){
            donar_sign.setText("Next");
        }




        donar_sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name=dname.getEditText().getText().toString();
                String contact=dcontacts.getEditText().getText().toString();
                String password=pass.getEditText().getText().toString().trim();
                if(TextUtils.isEmpty(name) ||TextUtils.isEmpty(contact) || TextUtils.isEmpty(password) ){
                    StyleableToast.makeText(DonarRegisterActivity.this,"One or More Fields Empty", R.style.mytoast).show();
                }else {
                    SharedPreferences    usertype=getSharedPreferences("typedata",0);
                   if(usertype.getString("type","").equals("donar")) {
                      donarRegister(name,contact,password);
                   }  else{
                       dealerRegister(name,contact,password);
                   }

                }

            }
        });

    }

    public  void donarRegister(String name,String contact,String password){
        showProgress();
        //registerProcess();
        ApiInterface apiInterface= ApiClient.getApiClient(DonarRegisterActivity.this).create(ApiInterface.class);
        Call<Responses> responseCall= apiInterface.donarSignResponses(name,contact,password);
        responseCall.enqueue(new Callback<Responses>() {
            @Override
            public void onResponse(Call<Responses> call, Response<Responses> response) {
                String result=response.body().getMessage();
                if(result.equals("success")){
                    dialogue.cancel();
                    StyleableToast.makeText(DonarRegisterActivity.this,"Registration Success",R.style.greentoast).show();
                    Intent intent=new Intent(DonarRegisterActivity.this,DonarLogin.class);
                    startActivity(intent);
                }else{
                    dialogue.cancel();
                    StyleableToast.makeText(DonarRegisterActivity.this,""+result,R.style.mytoast).show();
                }
            }

            @Override
            public void onFailure(Call<Responses> call, Throwable t) {
                dialogue.cancel();
                StyleableToast.makeText(DonarRegisterActivity.this,"Network Error",R.style.mytoast).show();
            }
        });

    }
    public void dealerRegister(String name,String phone,String password){

        Bundle dealer_reg_info=new Bundle();
        dealer_reg_info.putString("name",name);
        dealer_reg_info.putString("phone",phone);
        dealer_reg_info.putString("password",password);

        Intent intent=new Intent(DonarRegisterActivity.this,DealerRegActivity.class);
        intent.putExtras(dealer_reg_info);
        startActivity(intent);

        /*
        showProgress();
        ApiInterface apiInterface=ApiClient.getApiClient(DonarRegisterActivity.this).create(ApiInterface.class);
        Call<Responses> responsesCall=apiInterface.dealerSignResponse(name,phone,password);
        responsesCall.enqueue(new Callback<Responses>() {
            @Override
            public void onResponse(Call<Responses> call, Response<Responses> response) {
                String result=response.body().getMessage();
                if(result.equals("success")){
                    dialogue.cancel();
                    StyleableToast.makeText(DonarRegisterActivity.this,"Registration Success",R.style.mytoast).show();
                }else {
                    dialogue.cancel();
                    StyleableToast.makeText(DonarRegisterActivity.this,""+result,R.style.mytoast).show();
                }
            }

            @Override
            public void onFailure(Call<Responses> call, Throwable t) {
                dialogue.cancel();
                StyleableToast.makeText(DonarRegisterActivity.this,"Network Error",R.style.mytoast).show();
            }
        });
*/
    }
    private void filter(String text) {
        ArrayList<Sections> filteredList = new ArrayList<>();

        for (Sections item : currencies) {
            if (item.getDivision().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        currencyAdapter.filterList(filteredList);
    }



    private void initializeAll(View dialogView) {

        currencies=new ArrayList<>();
        currencycontainer=dialogView.findViewById(R.id.currency_container);
        currencycontainer.setHasFixedSize(true);
        currencycontainer.setLayoutManager(new LinearLayoutManager(DonarRegisterActivity.this));
    }



    private void intAll() {
        dname=findViewById(R.id.donar_name);

        dcontacts=findViewById(R.id.donar_contacts);




        pass=findViewById(R.id.donar_pass);

      donar_sign=findViewById(R.id.donar_sign_btn);
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


    public String imageToString(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte [] imageByte=byteArrayOutputStream.toByteArray();
        String imageImage= Base64.encodeToString(imageByte,Base64.DEFAULT);
        return imageImage;

    }
}