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
import android.graphics.Rect;
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
    TextInputLayout dname,profession,dcontacts,mail,presentaddres,thana,amounts,uname,pass;
    Button donar_sign,browse_image;



   public static Button dZilla;
    ImageView d_image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donar_register);
        setFontToActionBar();
        intAll();

       dZilla.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               Rect displayRectangle = new Rect();
               Window window = DonarRegisterActivity.this.getWindow();
               window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);
               final AlertDialog.Builder builder = new AlertDialog.Builder(DonarRegisterActivity.this,R.style.CustomAlertDialog);
               ViewGroup viewGroup = findViewById(android.R.id.content);
               View dialogView = LayoutInflater.from(DonarRegisterActivity.this).inflate(R.layout.activity_currency, viewGroup, false);
               dialogView.setMinimumWidth((int)(displayRectangle.width() * 1f));
               dialogView.setMinimumHeight((int)(displayRectangle.height() * 1f));
               builder.setView(dialogView);
                alertDialog = builder.create();
               initializeAll(dialogView);
               initializeArraylist();

               EditText editText = dialogView.findViewById(R.id.edittext_search);
               editText.addTextChangedListener(new TextWatcher() {
                   @Override
                   public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                   }

                   @Override
                   public void onTextChanged(CharSequence s, int start, int before, int count) {

                   }

                   @Override
                   public void afterTextChanged(Editable s) {
                       filter(s.toString());
                   }
               });





               alertDialog.show();
               /*
               Bundle bundle=new Bundle();
               bundle.putString("value","donar");
               Intent intent =new Intent(DonarRegisterActivity.this,CurrencyActivity.class);
               intent.putExtras(bundle);
               startActivity(intent);

                */
           }
       });



        donar_sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name=dname.getEditText().getText().toString();
                String professions=profession.getEditText().getText().toString();
                String contact=dcontacts.getEditText().getText().toString();
                String email=mail.getEditText().getText().toString();
                String addres=presentaddres.getEditText().getText().toString();
                String dthana=thana.getEditText().getText().toString();
                String dzilla=dZilla.getText().toString();
                String amount=amounts.getEditText().getText().toString();
                String username=uname.getEditText().getText().toString().trim();
                String password=pass.getEditText().getText().toString().trim();
                if(TextUtils.isEmpty(name) || TextUtils.isEmpty(professions) || TextUtils.isEmpty(contact) || TextUtils.isEmpty(email) || TextUtils.isEmpty(addres) || TextUtils.isEmpty(dthana) || TextUtils.isEmpty(dzilla) || TextUtils.isEmpty(amount) || TextUtils.isEmpty(username) || TextUtils.isEmpty(password) || bitmap==null ){
                    StyleableToast.makeText(DonarRegisterActivity.this,"One or More Fields Empty", R.style.mytoast).show();
                }else {
                    showProgress();
                    //registerProcess();
                    ApiInterface apiInterface= ApiClient.getApiClient(DonarRegisterActivity.this).create(ApiInterface.class);
                    Call<Responses> responseCall= apiInterface.donarSignResponses(name,professions,contact,email,addres,dthana,dzilla,amount,username,password,imageToString(bitmap));
                    responseCall.enqueue(new Callback<Responses>() {
                        @Override
                        public void onResponse(Call<Responses> call, Response<Responses> response) {
                            String result=response.body().getMessage();
                            if(result.equals("success")){
                                 dialogue.cancel();
                                StyleableToast.makeText(DonarRegisterActivity.this,"Registration Success",R.style.mytoast).show();
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

            }
        });
        browse_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                browseImage();
            }
        });
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

    private void initializeArraylist() {
        currencies.add(new Sections("Dhaka","Dhaka"));
        currencies.add(new Sections("Dhaka","Faridpur"));
        currencies.add(new Sections("Dhaka","Gazipur"));
        currencies.add(new Sections("Dhaka","Gopalganj"));
        currencies.add(new Sections("Dhaka","Kishoreganj"));
        currencies.add(new Sections("Dhaka","Madaripur"));
        currencies.add(new Sections("Dhaka","Manikganj"));
        currencies.add(new Sections("Dhaka","Munshiganj"));
        currencies.add(new Sections("Dhaka","Narayanganj"));
        currencies.add(new Sections("Dhaka","Narsingdi"));
        currencies.add(new Sections("Dhaka","Rajbari"));
        currencies.add(new Sections("Dhaka","Shariatpur"));
        currencies.add(new Sections("Dhaka","Tangail"));
        currencies.add(new Sections("Khulna","Jessore"));
        currencies.add(new Sections("Khulna","Chuadanga"));
        currencies.add(new Sections("Khulna","Bagerhat"));
        currencies.add(new Sections("Khulna","Jhenaidah"));
        currencies.add(new Sections("Khulna","Khulna"));
        currencies.add(new Sections("Khulna","Kushtia"));
        currencies.add(new Sections("Khulna","Magura"));

        currencies.add(new Sections("Khulna","Meherpur"));
        currencies.add(new Sections("Khulna","Narail"));
        currencies.add(new Sections("Mymensingh","Jamalpur"));
        currencies.add(new Sections("Mymensingh","Mymensingh"));
        currencies.add(new Sections("Mymensingh","Netrakona"));
        currencies.add(new Sections("Mymensingh","Sherpur"));

        currencies.add(new Sections("Sylhet","Habiganj"));
        currencies.add(new Sections("Sylhet","Moulvibazar"));
        currencies.add(new Sections("Sylhet","Sunamganj"));
        currencies.add(new Sections("Sylhet","Sylhet"));

        currencies.add(new Sections("Rajshahi","Bogra"));
        currencies.add(new Sections("Rajshahi","Chapainawabganj"));
        currencies.add(new Sections("Rajshahi","Joypurhat"));
        currencies.add(new Sections("Rajshahi","Naogaon"));
        currencies.add(new Sections("Rajshahi","Natore"));
        currencies.add(new Sections("Rajshahi","Pabna"));
        currencies.add(new Sections("Rajshahi","Rajshahi"));
        currencies.add(new Sections("Rajshahi","Sirajganj"));

        currencies.add(new Sections("Rangpur","Rangpur"));
        currencies.add(new Sections("Rangpur","Dinajpur"));
        currencies.add(new Sections("Rangpur","Gaibandha"));
        currencies.add(new Sections("Rangpur","Kurigram"));
        currencies.add(new Sections("Rangpur","Lalmonirhat"));
        currencies.add(new Sections("Rangpur","Nilphamari"));
        currencies.add(new Sections("Rangpur","Panchagarh"));
        currencies.add(new Sections("Rangpur","Thakurgaon"));

        currencies.add(new Sections("Barisal","Barisal")); //$€£¥₽₩₪฿₫₴₹
        currencies.add(new Sections("Barisal","Barguna"));
        currencies.add(new Sections("Barisal","Bhola"));
        currencies.add(new Sections("Barisal","Jhalokati"));
        currencies.add(new Sections("Barisal","Patuakhali"));
        currencies.add(new Sections("Barisal","Pirojpur"));
        // "Chittagong":["Bandarban","Brahmanbaria",   "Chandpur", "Chittagong", "Comilla",    "Cox's Bazar","Feni",     "Khagrachhari","Lakshmipur", "Noakhali", "Rangamati"],
        //$€£¥₽₩₪฿₫₴₹
        currencies.add(new Sections("Chittagong","Chittagong"));
        currencies.add(new Sections("Chittagong","Bandarban"));
        currencies.add(new Sections("Chittagong","Brahmanbaria"));
        currencies.add(new Sections("Chittagong","Chandpur"));
        currencies.add(new Sections("Chittagong","Comilla")); //$€£¥₽₩₪฿₫₴₹
        currencies.add(new Sections("Chittagong","Cox's Bazar"));
        currencies.add(new Sections("Chittagong","Feni"));
        currencies.add(new Sections("Chittagong","Khagrachhari"));


        currencies.add(new Sections("Chittagong","Lakshmipur"));
        currencies.add(new Sections("Chittagong","Noakhali")); //$€£¥₽₩₪฿₫₴₹
        currencies.add(new Sections("Chittagong","Rangamati"));


        currencyAdapter=new CurrencyAdapter(currencies, DonarRegisterActivity.this, 1);
        currencycontainer.setAdapter(currencyAdapter);






    }

    private void initializeAll(View dialogView) {


        currencies=new ArrayList<>();
        currencycontainer=dialogView.findViewById(R.id.currency_container);
        currencycontainer.setHasFixedSize(true);
        currencycontainer.setLayoutManager(new LinearLayoutManager(DonarRegisterActivity.this));
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

        SharedPreferences sharedPreferences_zilla=getSharedPreferences("zilla_info",0);
        if(sharedPreferences_zilla.getString("zilla","").equals("")){
            Log.d("shared","null_value");
            dZilla.setText("Select Zilla");
        }else{
            Log.d("shared","value contains");
            dZilla.setText(sharedPreferences_zilla.getString("zilla",""));
        }
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
                    String dzilla=dZilla.getText().toString();
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