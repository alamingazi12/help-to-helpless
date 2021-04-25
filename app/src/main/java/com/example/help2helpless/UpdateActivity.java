package com.example.help2helpless;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.help2helpless.adapter.CurrencyAdapter;
import com.example.help2helpless.model.Responses;
import com.example.help2helpless.model.Sections;
import com.example.help2helpless.network.ApiClient;
import com.example.help2helpless.network.ApiInterface;
import com.google.android.material.textfield.TextInputLayout;
import com.muddzdev.styleabletoast.StyleableToast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateActivity extends AppCompatActivity {
    RecyclerView currencycontainer;
    ArrayList<Sections> currencies;
    CurrencyAdapter currencyAdapter;
    public static SharedPreferences sharedPreferences;
    public static SharedPreferences.Editor editor;

    public static AlertDialog alertDialog;

    ProgressDialog dialogue;
    public static int image_request = 1, image_request2 = 2;
    ImageView reg_pic;
    CircleImageView profile_pic_view;
    Button signup, reg_upload;
    TextInputLayout  address, mail,month_donation;
    Bitmap profile_bitmap, regno_bitmap;
    public static Button update_zilla;
    SharedPreferences sharedPreferences_zilla;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        initAll();
        update_zilla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Rect displayRectangle = new Rect();
                Window window = UpdateActivity.this.getWindow();
                window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);
                final AlertDialog.Builder builder = new AlertDialog.Builder(UpdateActivity.this, R.style.CustomAlertDialog);
                ViewGroup viewGroup = findViewById(android.R.id.content);
                View dialogView = LayoutInflater.from(UpdateActivity.this).inflate(R.layout.activity_currency, viewGroup, false);
                dialogView.setMinimumWidth((int) (displayRectangle.width() * 1f));
                dialogView.setMinimumHeight((int) (displayRectangle.height() * 1f));
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
            }


        });

        profile_pic_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                browseProfilePic();
            }
        });
        reg_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                browseImage();
            }
        });

         signup.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                String addres= address.getEditText().getText().toString().trim();
                String email=mail.getEditText().getText().toString().trim();

                 sharedPreferences_zilla=getSharedPreferences("zilla_info",0);
                 String zilla =sharedPreferences_zilla.getString("zilla","");
                 String thana =sharedPreferences_zilla.getString("thana","");


                 if(profile_bitmap==null || regno_bitmap==null || TextUtils.isEmpty(addres) || TextUtils.isEmpty(email) || TextUtils.isEmpty(zilla) || TextUtils.isEmpty(thana)){

                     donar_Update(addres,email,zilla,thana);
                     dealer_update(addres,email,zilla,thana);


                 }else {

                     StyleableToast.makeText(UpdateActivity.this,"One Or More Fields Empty",R.style.mytoast).show();
                 }
             }
         });


    }

    private void donar_Update(String addres, String email, String zilla, String thana) {
        String month_amount=month_donation.getEditText().getText().toString().trim();
        SharedPreferences   donarsharedpreference=  this.getSharedPreferences("donarinfo",0);
        String phone=  donarsharedpreference.getString("contact","");
         if(TextUtils.isEmpty(phone) || TextUtils.isEmpty(month_amount)){
             StyleableToast.makeText(UpdateActivity.this,"Fields",R.style.mytoast).show();
         }else{
             ApiInterface apiInterface=ApiClient.getApiClient(UpdateActivity.this).create(ApiInterface.class);
             Call<Responses> responsesCall= apiInterface.donarUpdateResponse(imageToString(profile_bitmap),addres,zilla,thana,phone,email,imageToString(regno_bitmap),month_amount);
              responsesCall.enqueue(new Callback<Responses>() {
                  @Override
                  public void onResponse(Call<Responses> call, Response<Responses> response) {
                      String message=   response.body().getMessage();
                      if(message.equals("success")){
                          dialogue.cancel();
                          StyleableToast.makeText(UpdateActivity.this,"Information Updated",R.style.mytoast).show();
                      }else{
                          dialogue.cancel();
                          StyleableToast.makeText(UpdateActivity.this,"Something wrong",R.style.mytoast).show();
                      }
                  }

                  @Override
                  public void onFailure(Call<Responses> call, Throwable t) {
                      dialogue.cancel();
                      StyleableToast.makeText(UpdateActivity.this,"Netword Error",R.style.mytoast).show();
                  }
              });
         }
    }

    private void dealer_update(String addres, String email, String zilla, String thana) {

        SharedPreferences    dealerlogininfo=getSharedPreferences("dealerinfo",0);
        String phone=dealerlogininfo.getString("contact","");
        ApiInterface apiInterface=ApiClient.getApiClient(UpdateActivity.this).create(ApiInterface.class);
        Call<Responses> responsesCall= apiInterface.dealerUpdateResponse(imageToString(profile_bitmap),addres,zilla,thana,phone,email,imageToString(regno_bitmap));
        responsesCall.enqueue(new Callback<Responses>() {
            @Override
            public void onResponse(Call<Responses> call, Response<Responses> response) {
                String message=   response.body().getMessage();
                if(message.equals("success")){
                    dialogue.cancel();
                    StyleableToast.makeText(UpdateActivity.this,"Information Updated",R.style.mytoast).show();
                }else{
                    dialogue.cancel();
                    StyleableToast.makeText(UpdateActivity.this,"Something wrong",R.style.mytoast).show();
                }

            }

            @Override
            public void onFailure(Call<Responses> call, Throwable t) {
                dialogue.cancel();
                StyleableToast.makeText(UpdateActivity.this,"Netword Error",R.style.mytoast).show();
            }
        });
    }




    private void initializeAll(View dialogView) {
        currencies=new ArrayList<>();
        currencycontainer=dialogView.findViewById(R.id.currency_container);
        currencycontainer.setHasFixedSize(true);
        currencycontainer.setLayoutManager(new LinearLayoutManager(UpdateActivity.this));
    }


    private void filter(String text) {
        ArrayList<Sections> filteredList = new ArrayList<>();

        for (Sections item : currencies) {
            if ((item.getZilla().toLowerCase().contains(text.toLowerCase())) || (item.getThana().toLowerCase().contains(text.toLowerCase()))) {
                filteredList.add(item);
            }
        }
        currencyAdapter.filterList(filteredList);
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


    private void browseProfilePic(){
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
                profile_bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                profile_pic_view.setImageBitmap(profile_bitmap);

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
    private void initAll() {
        profile_pic_view = findViewById(R.id.edit_profile_image);
        address = findViewById(R.id.edit_address);
        mail = findViewById(R.id.edit_mail);
        update_zilla = findViewById(R.id.update_shpzila);
        month_donation=findViewById(R.id.mdonation);
        sharedPreferences_zilla = getSharedPreferences("zilla_info", 0);
        if (sharedPreferences_zilla.getString("zilla", "").equals("")) {
            update_zilla.setText("Select Zilla");
        } else {
            update_zilla.setText(sharedPreferences_zilla.getString("zilla", ""));
        }

        reg_pic=findViewById(R.id.editshpimage);

        // BUtton init
        signup=findViewById(R.id.edit_dealer);
        reg_upload=findViewById(R.id.editpic_shop);

    }


    public  void  initializeArraylist() {



        currencies.add(new Sections("Dhaka","Dhaka","ADABOR"));
        currencies.add(new Sections("Dhaka","Dhaka","BADDA"));
        currencies.add(new Sections("Dhaka","Dhaka","BANGSHAL"));
        currencies.add(new Sections("Dhaka","Dhaka","BIMAN BANDAR"));
        currencies.add(new Sections("Dhaka","Dhaka","CANTONMENT"));
        currencies.add(new Sections("Dhaka","Dhaka","CHAK BAZAR"));
        currencies.add(new Sections("Dhaka","Dhaka","DAKSHINKHAN"));

        currencies.add(new Sections("Dhaka","Dhaka","DARUS SALAM"));
        currencies.add(new Sections("Dhaka","Dhaka","DEMRA"));
        currencies.add(new Sections("Dhaka","Dhaka","DHAMRAI"));
        currencies.add(new Sections("Dhaka","Dhaka","DHANMONDI"));
        currencies.add(new Sections("Dhaka","Dhaka","DOHAR"));
        currencies.add(new Sections("Dhaka","Dhaka","BHASAN TEK"));
        currencies.add(new Sections("Dhaka","Dhaka","BHATARA"));
        currencies.add(new Sections("Dhaka","Dhaka","GENDARIA"));
        currencies.add(new Sections("Dhaka","Dhaka","GULSHAN"));
        currencies.add(new Sections("Dhaka","Dhaka","HAZARIBAGH"));
        currencies.add(new Sections("Dhaka","Dhaka","JATRABARI"));
        currencies.add(new Sections("Dhaka","Dhaka","KAFRUL"));
        currencies.add(new Sections("Dhaka","Dhaka","KADAMTALI"));
        currencies.add(new Sections("Dhaka","Dhaka","KALABAGAN"));

        currencies.add(new Sections("Dhaka","Dhaka","KAMRANGIR CHAR"));
        currencies.add(new Sections("Dhaka","Dhaka","KHILGAON"));
        currencies.add(new Sections("Dhaka","Dhaka","KHILKHET"));
        currencies.add(new Sections("Dhaka","Dhaka","KERANIGANJ"));
        currencies.add(new Sections("Dhaka","Dhaka","KOTWALI"));
        currencies.add(new Sections("Dhaka","Dhaka","LALBAGH"));
        currencies.add(new Sections("Dhaka","Dhaka","MIRPUR"));
        currencies.add(new Sections("Dhaka","Dhaka","MOHAMMADPUR"));
        currencies.add(new Sections("Dhaka","Dhaka","MOTIJHEEL"));
        currencies.add(new Sections("Dhaka","Dhaka","MUGDA PARA"));
        currencies.add(new Sections("Dhaka","Dhaka","NAWABGANJ"));
        currencies.add(new Sections("Dhaka","Dhaka","NEW MARKET"));
        currencies.add(new Sections("Dhaka","Dhaka","PALLABI"));
        currencies.add(new Sections("Dhaka","Dhaka","PALTAN"));
        currencies.add(new Sections("Dhaka","Dhaka","RAMNA"));


        currencies.add(new Sections("Dhaka","Dhaka","RAMPURA"));
        currencies.add(new Sections("Dhaka","Dhaka","SABUJBAGH"));
        currencies.add(new Sections("Dhaka","Dhaka","RUPNAGAR"));
        currencies.add(new Sections("Dhaka","Dhaka","SAVAR"));
        currencies.add(new Sections("Dhaka","Dhaka","SHAHJAHANPUR"));
        currencies.add(new Sections("Dhaka","Dhaka","SHAH ALI"));
        currencies.add(new Sections("Dhaka","Dhaka","SHAHBAGH"));
        currencies.add(new Sections("Dhaka","Dhaka","SHYAMPUR"));
        currencies.add(new Sections("Dhaka","Dhaka","SHER-E-BANGLA NAGAR"));
        currencies.add(new Sections("Dhaka","Dhaka","SUTRAPUR"));
        currencies.add(new Sections("Dhaka","Dhaka","TEJGAON"));
        currencies.add(new Sections("Dhaka","Dhaka","TEJGAON IND. AREA"));
        currencies.add(new Sections("Dhaka","Dhaka","TURAG"));
        currencies.add(new Sections("Dhaka","Dhaka","UTTARA PASCHIM"));
        currencies.add(new Sections("Dhaka","Dhaka","UTTARA PURBA"));
        currencies.add(new Sections("Dhaka","Dhaka","UTTAR KHAN"));
        currencies.add(new Sections("Dhaka","Dhaka","WARI"));


        currencies.add(new Sections("Dhaka","Faridpur","ALFADANGA"));
        currencies.add(new Sections("Dhaka","Faridpur","BHANGA"));
        currencies.add(new Sections("Dhaka","Faridpur","BOALMARI"));
        currencies.add(new Sections("Dhaka","Faridpur","CHAR BHADRASAN"));
        currencies.add(new Sections("Dhaka","Faridpur","FARIDPUR SADAR"));
        currencies.add(new Sections("Dhaka","Faridpur","MADHUKHALI"));
        currencies.add(new Sections("Dhaka","Faridpur","NAGARKANDA"));
        currencies.add(new Sections("Dhaka","Faridpur","SADARPUR"));
        currencies.add(new Sections("Dhaka","Faridpur","SALTHA"));





        currencies.add(new Sections("Dhaka","Gazipur","GAZIPUR SADAR"));
        currencies.add(new Sections("Dhaka","Gazipur","KALIAKAIR"));
        currencies.add(new Sections("Dhaka","Gazipur","KALIGANJ"));
        currencies.add(new Sections("Dhaka","Gazipur","KAPASIA"));
        currencies.add(new Sections("Dhaka","Gazipur","SREEPUR"));



        currencies.add(new Sections("Dhaka","Gopalganj","GOPALGANJ SADAR"));
        currencies.add(new Sections("Dhaka","Gopalganj","KASHIANI"));
        currencies.add(new Sections("Dhaka","Gopalganj","KOTALIPARA"));
        currencies.add(new Sections("Dhaka","Gopalganj","MUKSUDPUR"));
        currencies.add(new Sections("Dhaka","Gopalganj","TUNGIPARA"));


        currencies.add(new Sections("Dhaka","JAMALPUR","BAKSHIGANJ"));
        currencies.add(new Sections("Dhaka","JAMALPUR","DEWANGANJ"));
        currencies.add(new Sections("Dhaka","JAMALPUR","ISLAMPUR"));
        currencies.add(new Sections("Dhaka","JAMALPUR","JAMALPUR SADAR"));
        currencies.add(new Sections("Dhaka","JAMALPUR","MADARGANJ"));
        currencies.add(new Sections("Dhaka","JAMALPUR","MELANDAHA"));
        currencies.add(new Sections("Dhaka","JAMALPUR","SARISHABARI UPAZILA"));




        currencies.add(new Sections("Dhaka","Kishoreganj","AUSTAGRAM"));
        currencies.add(new Sections("Dhaka","Kishoreganj","BAJITPUR"));
        currencies.add(new Sections("Dhaka","Kishoreganj","BHAIRAB"));
        currencies.add(new Sections("Dhaka","Kishoreganj","HOSSAINPUR"));
        currencies.add(new Sections("Dhaka","Kishoreganj","ITNA"));
        currencies.add(new Sections("Dhaka","Kishoreganj","KARIMGANJ"));
        currencies.add(new Sections("Dhaka","Kishoreganj","KATIADI"));
        currencies.add(new Sections("Dhaka","Kishoreganj","KISHOREGANJ SADAR"));
        currencies.add(new Sections("Dhaka","Kishoreganj","KULIAR CHAR"));
        currencies.add(new Sections("Dhaka","Kishoreganj","MITHAMAIN"));
        currencies.add(new Sections("Dhaka","Kishoreganj","NIKLI"));
        currencies.add(new Sections("Dhaka","Kishoreganj","PAKUNDIA"));
        currencies.add(new Sections("Dhaka","Kishoreganj","TARAIL"));



        currencies.add(new Sections("Dhaka","Madaripur","KALKINI"));
        currencies.add(new Sections("Dhaka","Madaripur","MADARIPUR SADAR"));
        currencies.add(new Sections("Dhaka","Madaripur","RAJOIR"));
        currencies.add(new Sections("Dhaka","Madaripur","SHIBCHAR"));



        currencies.add(new Sections("Dhaka","Manikganj","DAULATPUR"));
        currencies.add(new Sections("Dhaka","Manikganj","GHIOR"));
        currencies.add(new Sections("Dhaka","Manikganj","HARIRAMPUR"));
        currencies.add(new Sections("Dhaka","Manikganj","MANIKGANJ SADAR"));
        currencies.add(new Sections("Dhaka","Manikganj","SATURIA"));
        currencies.add(new Sections("Dhaka","Manikganj","SHIBALAYA"));
        currencies.add(new Sections("Dhaka","Manikganj","SINGAIR"));



        currencies.add(new Sections("Dhaka","Munshiganj","GAZARIA"));
        currencies.add(new Sections("Dhaka","Munshiganj","LOHAJANG"));
        currencies.add(new Sections("Dhaka","Munshiganj","MUNSHIGANJ SADAR"));
        currencies.add(new Sections("Dhaka","Munshiganj","SERAJDIKHAN"));
        currencies.add(new Sections("Dhaka","Munshiganj","SREENAGAR"));
        currencies.add(new Sections("Dhaka","Munshiganj","TONGIBARI"));



        currencies.add(new Sections("Dhaka","MYMENSINGH","BHALUKA"));
        currencies.add(new Sections("Dhaka","MYMENSINGH","DHOBAURA"));
        currencies.add(new Sections("Dhaka","MYMENSINGH","FULBARIA"));
        currencies.add(new Sections("Dhaka","MYMENSINGH","GAFFARGAON"));
        currencies.add(new Sections("Dhaka","MYMENSINGH","GAURIPUR"));
        currencies.add(new Sections("Dhaka","MYMENSINGH","HALUAGHAT"));
        currencies.add(new Sections("Dhaka","MYMENSINGH","ISHWARGANJ"));
        currencies.add(new Sections("Dhaka","MYMENSINGH","MYMENSINGH SADAR"));
        currencies.add(new Sections("Dhaka","MYMENSINGH","MUKTAGACHHA"));
        currencies.add(new Sections("Dhaka","MYMENSINGH","NANDAIL"));
        currencies.add(new Sections("Dhaka","MYMENSINGH","PHULPUR"));
        currencies.add(new Sections("Dhaka","MYMENSINGH","TRISHAL"));




        currencies.add(new Sections("Dhaka","Narayanganj","ARAIHAZAR"));
        currencies.add(new Sections("Dhaka","Narayanganj","SONARGAON"));
        currencies.add(new Sections("Dhaka","Narayanganj","BANDAR"));
        currencies.add(new Sections("Dhaka","Narayanganj","NARAYANGANJ SADAR"));
        currencies.add(new Sections("Dhaka","Narayanganj","RUPGANJ"));



        currencies.add(new Sections("Dhaka","Narsingdi","BELABO"));
        currencies.add(new Sections("Dhaka","Narsingdi","MANOHARDI"));
        currencies.add(new Sections("Dhaka","Narsingdi","NARSINGDI SADAR"));
        currencies.add(new Sections("Dhaka","Narsingdi","PALASH"));
        currencies.add(new Sections("Dhaka","Narsingdi","ROYPURA"));
        currencies.add(new Sections("Dhaka","Narsingdi","SHIBPUR"));




        currencies.add(new Sections("Dhaka","NETRAKONA","ATPARA"));
        currencies.add(new Sections("Dhaka","NETRAKONA","BARHATTA"));
        currencies.add(new Sections("Dhaka","NETRAKONA","DURGAPUR"));
        currencies.add(new Sections("Dhaka","NETRAKONA","KHALIAJURI"));
        currencies.add(new Sections("Dhaka","NETRAKONA","KALMAKANDA"));
        currencies.add(new Sections("Dhaka","NETRAKONA","KENDUA"));
        currencies.add(new Sections("Dhaka","NETRAKONA","MADAN"));
        currencies.add(new Sections("Dhaka","NETRAKONA","MOHANGANJ"));
        currencies.add(new Sections("Dhaka","NETRAKONA","PURBADHALA"));
        currencies.add(new Sections("Dhaka","NETRAKONA","NETROKONA SADAR"));


        currencies.add(new Sections("Dhaka","Rajbari","ATPARA"));
        currencies.add(new Sections("Dhaka","Rajbari","BARHATTA"));
        currencies.add(new Sections("Dhaka","Rajbari","DURGAPUR"));
        currencies.add(new Sections("Dhaka","Rajbari","KHALIAJURI"));
        currencies.add(new Sections("Dhaka","Rajbari","KALMAKANDA"));
        currencies.add(new Sections("Dhaka","Rajbari","MADAN"));
        currencies.add(new Sections("Dhaka","Rajbari","MOHANGANJ"));
        currencies.add(new Sections("Dhaka","Rajbari","PURBADHALA"));
        currencies.add(new Sections("Dhaka","Rajbari","NETROKONA SADAR"));




        currencies.add(new Sections("Dhaka","Shariatpur","BHEDARGANJ"));
        currencies.add(new Sections("Dhaka","Shariatpur","DAMUDYA"));
        currencies.add(new Sections("Dhaka","Shariatpur","GOSAIRHAT"));
        currencies.add(new Sections("Dhaka","Shariatpur","NARIA"));
        currencies.add(new Sections("Dhaka","Shariatpur","SHARIATPUR SADAR"));
        currencies.add(new Sections("Dhaka","Shariatpur","ZANJIRA"));


        currencies.add(new Sections("Dhaka","SHERPUR","JHENAIGATI"));
        currencies.add(new Sections("Dhaka","SHERPUR","NAKLA"));
        currencies.add(new Sections("Dhaka","SHERPUR","SHERPUR SADAR"));
        currencies.add(new Sections("Dhaka","SHERPUR","SREEBARDI"));
        currencies.add(new Sections("Dhaka","SHERPUR","NALITABARI"));



        currencies.add(new Sections("Dhaka","Tangail","BASAIL"));
        currencies.add(new Sections("Dhaka","Tangail","BHUAPUR"));
        currencies.add(new Sections("Dhaka","Tangail","DELDUAR"));
        currencies.add(new Sections("Dhaka","Tangail","DHANBARI"));
        currencies.add(new Sections("Dhaka","Tangail","GHATAIL"));
        currencies.add(new Sections("Dhaka","Tangail","GOPALPUR"));
        currencies.add(new Sections("Dhaka","Tangail","KALIHATI"));
        currencies.add(new Sections("Dhaka","Tangail","MADHUPUR"));
        currencies.add(new Sections("Dhaka","Tangail","MIRZAPUR"));
        currencies.add(new Sections("Dhaka","Tangail","NAGARPUR"));
        currencies.add(new Sections("Dhaka","Tangail","SAKHIPUR"));
        currencies.add(new Sections("Dhaka","Tangail","TANGAIL SADAR"));





        currencies.add(new Sections("Khulna","Bagerhat","BAGERHAT SADAR"));
        currencies.add(new Sections("Khulna","Bagerhat","CHITALMARI"));
        currencies.add(new Sections("Khulna","Bagerhat","FAKIRHAT"));
        currencies.add(new Sections("Khulna","Bagerhat","KACHUA"));
        currencies.add(new Sections("Khulna","Bagerhat","MOLLAHAT"));
        currencies.add(new Sections("Khulna","Bagerhat","MONGLA"));
        currencies.add(new Sections("Khulna","Bagerhat","MORRELGANJ"));
        currencies.add(new Sections("Khulna","Bagerhat","RAMPAL"));
        currencies.add(new Sections("Khulna","Bagerhat","SARANKHOLA"));



        currencies.add(new Sections("Khulna","Chuadanga","ALAMDANGA"));
        currencies.add(new Sections("Khulna","Chuadanga","CHUADANGA SADAR"));
        currencies.add(new Sections("Khulna","Chuadanga","DAMURHUDA"));
        currencies.add(new Sections("Khulna","Chuadanga"," JIBAN NAGAR"));


        currencies.add(new Sections("Khulna","Jessore","ABHAYNAGAR"));
        currencies.add(new Sections("Khulna","Jessore","BAGHER PARA"));
        currencies.add(new Sections("Khulna","Jessore","CHAUGACHHA"));
        currencies.add(new Sections("Khulna","Jessore","JHIKARGACHHA"));
        currencies.add(new Sections("Khulna","Jessore","KESHABPUR"));
        currencies.add(new Sections("Khulna","Jessore","JESSORE SADAR"));
        currencies.add(new Sections("Khulna","Jessore","MANIRAMPUR"));
        currencies.add(new Sections("Khulna","Jessore","SHARSHA"));


        currencies.add(new Sections("Khulna","Jhenaidah","HARINAKUNDA"));
        currencies.add(new Sections("Khulna","Jhenaidah","JHENAIDAH SADAR"));
        currencies.add(new Sections("Khulna","Jhenaidah","KALIGANJ"));
        currencies.add(new Sections("Khulna","Jhenaidah","KOTCHANDPUR"));
        currencies.add(new Sections("Khulna","Jhenaidah","MAHESHPUR"));
        currencies.add(new Sections("Khulna","Jhenaidah","SHAILKUPA"));




        currencies.add(new Sections("Khulna","Khulna","BATIAGHATA"));
        currencies.add(new Sections("Khulna","Khulna","DACOPE"));
        currencies.add(new Sections("Khulna","Khulna","DAULATPUR"));
        currencies.add(new Sections("Khulna","Khulna","DUMURIA"));
        currencies.add(new Sections("Khulna","Khulna","DIGHALIA"));
        currencies.add(new Sections("Khulna","Khulna","KHALISHPUR"));
        currencies.add(new Sections("Khulna","Khulna","KHAN JAHAN ALI"));
        currencies.add(new Sections("Khulna","Khulna","KHULNA SADAR"));
        currencies.add(new Sections("Khulna","Khulna","KOYRA"));
        currencies.add(new Sections("Khulna","Khulna","PHULTALA"));
        currencies.add(new Sections("Khulna","Khulna","RUPSA"));
        currencies.add(new Sections("Khulna","Khulna","SONADANGA"));
        currencies.add(new Sections("Khulna","Khulna","TEROKHADA"));





        currencies.add(new Sections("Khulna","Kushtia","BHERAMARA"));
        currencies.add(new Sections("Khulna","Kushtia","DAULATPUR"));
        currencies.add(new Sections("Khulna","Kushtia","KHOKSA"));
        currencies.add(new Sections("Khulna","Kushtia","KUSHTIA SADAR"));
        currencies.add(new Sections("Khulna","Kushtia","MIRPUR"));



        currencies.add(new Sections("Khulna","Magura","MAGURA SADAR"));
        currencies.add(new Sections("Khulna","Magura","MOHAMMADPUR"));
        currencies.add(new Sections("Khulna","Magura","SHALIKHA"));
        currencies.add(new Sections("Khulna","Magura","SREEPUR"));


        currencies.add(new Sections("Khulna","Meherpur","GANGNI"));
        currencies.add(new Sections("Khulna","Meherpur","MUJIB NAGAR"));
        currencies.add(new Sections("Khulna","Meherpur","MEHERPUR SADAR"));

        currencies.add(new Sections("Khulna","Narail","KALIA"));
        currencies.add(new Sections("Khulna","Narail","LOHAGARA"));
        currencies.add(new Sections("Khulna","Narail","NARAIL SADAR"));

        currencies.add(new Sections("Khulna","SATKHIRA","ASSASUNI"));
        currencies.add(new Sections("Khulna","SATKHIRA","DEBHATA"));
        currencies.add(new Sections("Khulna","SATKHIRA","KALAROA"));
        currencies.add(new Sections("Khulna","SATKHIRA","KALIGANJ"));
        currencies.add(new Sections("Khulna","SATKHIRA","SATKHIRA SADAR"));
        currencies.add(new Sections("Khulna","SATKHIRA","SHYAMNAGAR"));
        currencies.add(new Sections("Khulna","SATKHIRA","TALA"));



        currencies.add(new Sections("Sylhet","Habiganj","AJMIRIGANJ"));
        currencies.add(new Sections("Sylhet","Habiganj","BAHUBAL"));
        currencies.add(new Sections("Sylhet","Habiganj","BANIACHONG"));
        currencies.add(new Sections("Sylhet","Habiganj","CHUNARUGHAT"));
        currencies.add(new Sections("Sylhet","Habiganj","HABIGANJ SADAR"));
        currencies.add(new Sections("Sylhet","Habiganj","LAKHAI"));
        currencies.add(new Sections("Sylhet","Habiganj","MADHABPUR"));
        currencies.add(new Sections("Sylhet","Habiganj","NABIGANJ"));
        ;



        currencies.add(new Sections("Sylhet","Moulvibazar","BARLEKHA"));
        currencies.add(new Sections("Sylhet","Moulvibazar","JURI"));
        currencies.add(new Sections("Sylhet","Moulvibazar","KAMALGANJ"));
        currencies.add(new Sections("Sylhet","Moulvibazar","KULAURA"));
        currencies.add(new Sections("Sylhet","Moulvibazar","MAULVIBAZAR SADAR"));
        currencies.add(new Sections("Sylhet","Moulvibazar","RAJNAGAR"));
        currencies.add(new Sections("Sylhet","Moulvibazar","SREEMANGAL"));


        currencies.add(new Sections("Sylhet","Sunamganj","BISHWAMBARPUR"));
        currencies.add(new Sections("Sylhet","Sunamganj","CHHATAK"));
        currencies.add(new Sections("Sylhet","Sunamganj","DAKSHIN SUNAMGANJ"));
        currencies.add(new Sections("Sylhet","Sunamganj","DERAI"));
        currencies.add(new Sections("Sylhet","Sunamganj","DHARAMPASHA"));
        currencies.add(new Sections("Sylhet","Sunamganj","DOWARABAZAR"));
        currencies.add(new Sections("Sylhet","Sunamganj","JAGANNATHPUR"));
        currencies.add(new Sections("Sylhet","Sunamganj","JAMALGANJ"));
        currencies.add(new Sections("Sylhet","Sunamganj","SULLA"));
        currencies.add(new Sections("Sylhet","Sunamganj","SUNAMGANJ SADAR"));




        currencies.add(new Sections("Sylhet","Sylhet","BALAGANJ"));
        currencies.add(new Sections("Sylhet","Sylhet","BEANI BAZAR"));
        currencies.add(new Sections("Sylhet","Sylhet","BISHWANATH"));
        currencies.add(new Sections("Sylhet","Sylhet","COMPANIGANJ"));
        currencies.add(new Sections("Sylhet","Sylhet","DAKSHIN SURMA"));
        currencies.add(new Sections("Sylhet","Sylhet","FENCHUGANJ"));
        currencies.add(new Sections("Sylhet","Sylhet","GOLAPGANJ"));
        currencies.add(new Sections("Sylhet","Sylhet","GOWAINGHAT"));
        currencies.add(new Sections("Sylhet","Sylhet","JAINTIAPUR"));
        currencies.add(new Sections("Sylhet","Sylhet","KANAIGHAT"));
        currencies.add(new Sections("Sylhet","Sylhet","SYLHET SADAR"));
        currencies.add(new Sections("Sylhet","Sylhet","ZAKIGANJ"));




        currencies.add(new Sections("Rangpur","Dinajpur","BIRAMPUR"));
        currencies.add(new Sections("Rangpur","Dinajpur","BIRGANJ"));
        currencies.add(new Sections("Rangpur","Dinajpur","BIRAL"));
        currencies.add(new Sections("Rangpur","Dinajpur","BOCHAGANJ"));
        currencies.add(new Sections("Rangpur","Dinajpur","CHIRIRBANDAR"));
        currencies.add(new Sections("Rangpur","Dinajpur","FULBARI"));
        currencies.add(new Sections("Rangpur","Dinajpur","GHORAGHAT"));
        currencies.add(new Sections("Rangpur","Dinajpur","HAKIMPUR"));
        currencies.add(new Sections("Rangpur","Dinajpur","KAHAROLE"));
        currencies.add(new Sections("Rangpur","Dinajpur","KHANSAMA"));
        currencies.add(new Sections("Rangpur","Dinajpur","DINAJPUR SADAR"));
        currencies.add(new Sections("Rangpur","Dinajpur","NAWABGANJ"));
        currencies.add(new Sections("Rangpur","Dinajpur","PARBATIPUR"));



        currencies.add(new Sections("Rangpur","Gaibandha","FULCHHARI"));
        currencies.add(new Sections("Rangpur","Gaibandha","GAIBANDHA SADAR"));
        currencies.add(new Sections("Rangpur","Gaibandha","GOBINDAGANJ"));
        currencies.add(new Sections("Rangpur","Gaibandha","PALASHBARI"));
        currencies.add(new Sections("Rangpur","Gaibandha","SADULLAPUR"));
        currencies.add(new Sections("Rangpur","Gaibandha","SAGHATA"));
        currencies.add(new Sections("Rangpur","Gaibandha","SUNDARGANJ"));



        currencies.add(new Sections("Rangpur","Kurigram","BHURUNGAMARI"));
        currencies.add(new Sections("Rangpur","Kurigram","CHAR RAJIBPUR"));
        currencies.add(new Sections("Rangpur","Kurigram","CHILMARI"));
        currencies.add(new Sections("Rangpur","Kurigram","PHULBARI"));
        currencies.add(new Sections("Rangpur","Kurigram","KURIGRAM SADAR"));
        currencies.add(new Sections("Rangpur","Kurigram","NAGESHWARI"));
        currencies.add(new Sections("Rangpur","Kurigram","RAJARHAT"));
        currencies.add(new Sections("Rangpur","Kurigram","RAUMARI"));
        currencies.add(new Sections("Rangpur","Kurigram","ULIPUR"));



        currencies.add(new Sections("Rangpur","Rangpur","BADARGANJ"));
        currencies.add(new Sections("Rangpur","Rangpur","GANGACHARA"));
        currencies.add(new Sections("Rangpur","Rangpur","KAUNIA"));
        currencies.add(new Sections("Rangpur","Rangpur","RANGPUR SADAR"));
        currencies.add(new Sections("Rangpur","Rangpur","MITHA PUKUR"));
        currencies.add(new Sections("Rangpur","Rangpur","PIRGACHHA"));
        currencies.add(new Sections("Rangpur","Rangpur","PIRGANJ"));
        currencies.add(new Sections("Rangpur","Rangpur","TARAGANJ"));



        currencies.add(new Sections("Rangpur","Lalmonirhat","ADITMARI"));
        currencies.add(new Sections("Rangpur","Lalmonirhat","HATIBANDHA"));
        currencies.add(new Sections("Rangpur","Lalmonirhat","KALIGANJ"));
        currencies.add(new Sections("Rangpur","Lalmonirhat","LALMONIRHAT SADAR"));
        currencies.add(new Sections("Rangpur","Lalmonirhat","PATGRAM"));


        currencies.add(new Sections("Rangpur","Nilphamari","DIMLA"));
        currencies.add(new Sections("Rangpur","Nilphamari","DOMAR UPAZILA"));
        currencies.add(new Sections("Rangpur","Nilphamari","JALDHAKA UPAZILA"));
        currencies.add(new Sections("Rangpur","Nilphamari","KISHOREGANJ UPAZILA"));
        currencies.add(new Sections("Rangpur","Nilphamari","NILPHAMARI SADAR UPAZ"));
        currencies.add(new Sections("Rangpur","Nilphamari","SAIDPUR UPAZILA"));



        currencies.add(new Sections("Rangpur","Panchagarh","ATWARI"));
        currencies.add(new Sections("Rangpur","Panchagarh","BODA"));
        currencies.add(new Sections("Rangpur","Panchagarh","DEBIGANJ"));
        currencies.add(new Sections("Rangpur","Panchagarh","PANCHAGARH SADAR"));
        currencies.add(new Sections("Rangpur","Panchagarh","TENTULIA"));



        currencies.add(new Sections("Rangpur","Thakurgaon","BALIADANGI"));
        currencies.add(new Sections("Rangpur","Thakurgaon","HARIPUR"));
        currencies.add(new Sections("Rangpur","Thakurgaon","PIRGANJ"));
        currencies.add(new Sections("Rangpur","Thakurgaon","RANISANKAIL"));
        currencies.add(new Sections("Rangpur","Thakurgaon","THAKURGAON SADAR"));



        currencies.add(new Sections("Rajshahi","Bogra","ADAMDIGHI"));
        currencies.add(new Sections("Rajshahi","Bogra","BOGRA SADAR"));
        currencies.add(new Sections("Rajshahi","Bogra","DHUNAT"));
        currencies.add(new Sections("Rajshahi","Bogra","DHUPCHANCHIA"));
        currencies.add(new Sections("Rajshahi","Bogra","GABTALI"));
        currencies.add(new Sections("Rajshahi","Bogra","KAHALOO"));
        currencies.add(new Sections("Rajshahi","Bogra","NANDIGRAM"));
        currencies.add(new Sections("Rajshahi","Bogra","SARIAKANDI"));
        currencies.add(new Sections("Rajshahi","Bogra","SHAJAHANPUR"));
        currencies.add(new Sections("Rajshahi","Bogra","SHERPUR"));
        currencies.add(new Sections("Rajshahi","Bogra","SHIBGANJ"));
        currencies.add(new Sections("Rajshahi","Bogra","SONATOLA"));


        currencies.add(new Sections("Rajshahi","Joypurhat","AKKELPUR"));
        currencies.add(new Sections("Rajshahi","Joypurhat","JOYPURHAT SADAR"));
        currencies.add(new Sections("Rajshahi","Joypurhat","KALAI"));
        currencies.add(new Sections("Rajshahi","Joypurhat","KHETLAL"));
        currencies.add(new Sections("Rajshahi","Joypurhat","PANCHBIBI"));


        currencies.add(new Sections("Rajshahi","Naogaon","ATRAI"));
        currencies.add(new Sections("Rajshahi","Naogaon","BADALGACHHI"));
        currencies.add(new Sections("Rajshahi","Naogaon","DHAMOIRHAT"));
        currencies.add(new Sections("Rajshahi","Naogaon","MAHADEBPUR"));
        currencies.add(new Sections("Rajshahi","Naogaon","NAOGAON SADAR"));
        currencies.add(new Sections("Rajshahi","Naogaon","NIAMATPUR"));
        currencies.add(new Sections("Rajshahi","Naogaon","MANDA"));

        currencies.add(new Sections("Rajshahi","Naogaon","PATNITALA"));
        currencies.add(new Sections("Rajshahi","Naogaon","PORSHA"));
        currencies.add(new Sections("Rajshahi","Naogaon","RANINAGAR"));
        currencies.add(new Sections("Rajshahi","Naogaon","SAPAHAR"));


        currencies.add(new Sections("Rajshahi","Chapainawabganj","BHOLAHAT"));
        currencies.add(new Sections("Rajshahi","Chapainawabganj","GOMASTAPUR"));
        currencies.add(new Sections("Rajshahi","Chapainawabganj","NACHOLE"));
        currencies.add(new Sections("Rajshahi","Chapainawabganj","CHAPAI NABABGANJ SADAR"));
        currencies.add(new Sections("Rajshahi","Chapainawabganj","SHIBGANJ"));


        currencies.add(new Sections("Rajshahi","Natore","BAGATIPARA"));
        currencies.add(new Sections("Rajshahi","Natore","BARAIGRAM"));
        currencies.add(new Sections("Rajshahi","Natore","GURUDASPUR"));
        currencies.add(new Sections("Rajshahi","Natore","LALPUR"));
        currencies.add(new Sections("Rajshahi","Natore","NATORE SADAR"));
        currencies.add(new Sections("Rajshahi","Natore","SINGRA"));


        currencies.add(new Sections("Rajshahi","Pabna","ATGHARIA"));
        currencies.add(new Sections("Rajshahi","Pabna","BERA"));
        currencies.add(new Sections("Rajshahi","Pabna","CHATMOHAR"));
        currencies.add(new Sections("Rajshahi","Pabna","FARIDPUR"));
        currencies.add(new Sections("Rajshahi","Pabna","ISHWARDI"));
        currencies.add(new Sections("Rajshahi","Pabna","PABNA SADAR"));
        currencies.add(new Sections("Rajshahi","Pabna","SANTHIA"));
        currencies.add(new Sections("Rajshahi","Pabna","SUJANAGAR"));



        currencies.add(new Sections("Rajshahi","Rajshahi","BAGHA"));
        currencies.add(new Sections("Rajshahi","Rajshahi","BAGHMARA"));
        currencies.add(new Sections("Rajshahi","Rajshahi","BOALIA"));
        currencies.add(new Sections("Rajshahi","Rajshahi","CHARGHAT"));
        currencies.add(new Sections("Rajshahi","Rajshahi","DURGAPUR"));
        currencies.add(new Sections("Rajshahi","Rajshahi","GODAGARI"));
        currencies.add(new Sections("Rajshahi","Rajshahi","MATIHAR"));
        currencies.add(new Sections("Rajshahi","Rajshahi","MOHANPUR"));
        currencies.add(new Sections("Rajshahi","Rajshahi","PUTHIA"));
        currencies.add(new Sections("Rajshahi","Rajshahi","RAJPARA"));
        currencies.add(new Sections("Rajshahi","Rajshahi","SHAH MAKHDUM"));
        currencies.add(new Sections("Rajshahi","Rajshahi","TANORE"));




        currencies.add(new Sections("Rajshahi","Sirajganj","BELKUCHI"));
        currencies.add(new Sections("Rajshahi","Sirajganj","CHAUHALI"));
        currencies.add(new Sections("Rajshahi","Sirajganj","KAMARKHANDA"));
        currencies.add(new Sections("Rajshahi","Sirajganj","ROYGANJ"));
        currencies.add(new Sections("Rajshahi","Sirajganj","SHAHJADPUR"));

        currencies.add(new Sections("Rajshahi","Sirajganj","SIRAJGANJ SADAR"));
        currencies.add(new Sections("Rajshahi","Sirajganj","TARASH"));
        currencies.add(new Sections("Rajshahi","Sirajganj","ULLAH PARA"));




        //start Barishal

        currencies.add(new Sections("Barisal","Barisal","AGAILJHARA"));
        currencies.add(new Sections("Barisal","Barisal","BABUGANJ"));
        currencies.add(new Sections("Barisal","Barisal","BAKERGANJ"));
        currencies.add(new Sections("Barisal","Barisal","BANARI PARA"));
        currencies.add(new Sections("Barisal","Barisal","GAURNADI"));
        currencies.add(new Sections("Barisal","Barisal","HIZLA"));
        currencies.add(new Sections("Barisal","Barisal","BARISAL SADAR"));
        currencies.add(new Sections("Barisal","Barisal","MULADI"));
        currencies.add(new Sections("Barisal","Barisal","WAZIRPUR"));
        currencies.add(new Sections("Barisal","Barisal","MHENDIGANJ"));

        //$€£¥₽₩₪฿₫₴₹
        currencies.add(new Sections("Barisal","Barguna","AMTALI"));
        currencies.add(new Sections("Barisal","Barguna","BAMNA"));
        currencies.add(new Sections("Barisal","Barguna","BARGUNA"));
        currencies.add(new Sections("Barisal","Barguna","BETAGI"));
        currencies.add(new Sections("Barisal","Barguna","PATHARGHATA"));
        currencies.add(new Sections("Barisal","Barguna","TALTALI"));


        currencies.add(new Sections("Barisal","Bhola","BHOLA SADAR"));
        currencies.add(new Sections("Barisal","Bhola","BURHANUDDIN"));
        currencies.add(new Sections("Barisal","Bhola","CHAR FASSON"));
        currencies.add(new Sections("Barisal","Bhola","DAULAT KHAN"));
        currencies.add(new Sections("Barisal","Bhola","LALMOHAN"));
        currencies.add(new Sections("Barisal","Bhola","MANPURA"));
        currencies.add(new Sections("Barisal","Bhola","TAZUMUDDIN"));



        currencies.add(new Sections("Barisal","JHALOKATI","JHALOKATI SADAR"));
        currencies.add(new Sections("Barisal","JHALOKATI","KANTHALIA"));
        currencies.add(new Sections("Barisal","JHALOKATI","NALCHITY"));
        currencies.add(new Sections("Barisal","JHALOKATI","RAJAPUR"));



        currencies.add(new Sections("Barisal","PATUAKHALI","BAUPHAL"));
        currencies.add(new Sections("Barisal","PATUAKHALI","DASHMINA"));
        currencies.add(new Sections("Barisal","PATUAKHALI","DUMKI"));
        currencies.add(new Sections("Barisal","PATUAKHALI","GALACHIPA"));
        currencies.add(new Sections("Barisal","PATUAKHALI","KALAPARA"));
        currencies.add(new Sections("Barisal","PATUAKHALI","MIRZAGANJ"));
        currencies.add(new Sections("Barisal","PATUAKHALI","PATUAKHALI SADAR"));
        currencies.add(new Sections("Barisal","PATUAKHALI","RANGABALI"));


        currencies.add(new Sections("Barisal","PIROJPUR","BHANDARIA"));
        currencies.add(new Sections("Barisal","PIROJPUR","KAWKHALI"));
        currencies.add(new Sections("Barisal","PIROJPUR","MATHBARIA"));
        currencies.add(new Sections("Barisal","PIROJPUR","NAZIRPUR"));
        currencies.add(new Sections("Barisal","PIROJPUR","PIROJPUR SADAR"));
        currencies.add(new Sections("Barisal","PIROJPUR","NESARABAD"));
        currencies.add(new Sections("Barisal","PIROJPUR","ZIANAGAR"));



        // "Chittagong":["Bandarban","Brahmanbaria",   "Chandpur", "Chittagong", "Comilla",    "Cox's Bazar","Feni",     "Khagrachhari","Lakshmipur", "Noakhali", "Rangamati"],
        //$€£¥₽₩₪฿₫₴₹
        currencies.add(new Sections("Chittagong","Chittagong","ANOWARA"));
        currencies.add(new Sections("Chittagong","Chittagong","BANSHKHALI"));
        currencies.add(new Sections("Chittagong","Chittagong","BAYEJID"));
        currencies.add(new Sections("Chittagong","Chittagong","BAKALIA"));





        currencies.add(new Sections("Chittagong","Chittagong","CHANDANAISH"));
        currencies.add(new Sections("Chittagong","Chittagong","CHANDGAON"));
        currencies.add(new Sections("Chittagong","Chittagong","CHITTAGONG PORT"));
        currencies.add(new Sections("Chittagong","Chittagong","DOUBLE MOORING"));
        currencies.add(new Sections("Chittagong","Chittagong","FATIKCHHARI"));
        currencies.add(new Sections("Chittagong","Chittagong","HALISHAHAR"));
        currencies.add(new Sections("Chittagong","Chittagong","HATHAZARI"));
        currencies.add(new Sections("Chittagong","Chittagong","KOTWALI"));
        currencies.add(new Sections("Chittagong","Chittagong","KHULSHI"));
        currencies.add(new Sections("Chittagong","Chittagong","LOHAGARA"));
        currencies.add(new Sections("Chittagong","Chittagong","MIRSHARAI"));
        currencies.add(new Sections("Chittagong","Chittagong","PAHARTALI"));
        currencies.add(new Sections("Chittagong","Chittagong","PANCHLAISH"));
        currencies.add(new Sections("Chittagong","Chittagong","PATIYA"));
        currencies.add(new Sections("Chittagong","Chittagong","RANGUNIA"));
        currencies.add(new Sections("Chittagong","Chittagong","RAOZAN"));
        currencies.add(new Sections("Chittagong","Chittagong","SANDWIP"));
        currencies.add(new Sections("Chittagong","Chittagong","SATKANIA"));
        currencies.add(new Sections("Chittagong","Chittagong","SITAKUNDA"));



        currencies.add(new Sections("Chittagong","Comilla","BARURA"));
        currencies.add(new Sections("Chittagong","Comilla","BRAHMAN PARA"));
        currencies.add(new Sections("Chittagong","Comilla","BURICHANG"));
        currencies.add(new Sections("Chittagong","Comilla","CHANDINA"));
        currencies.add(new Sections("Chittagong","Comilla","CHAUDDAGRAM"));
        currencies.add(new Sections("Chittagong","Comilla","COMILLA SADAR DAKSHIN"));
        currencies.add(new Sections("Chittagong","Comilla","DAUDKANDI"));
        currencies.add(new Sections("Chittagong","Comilla","DEBIDWAR"));
        currencies.add(new Sections("Chittagong","Comilla","HOMNA"));
        currencies.add(new Sections("Chittagong","Comilla","COMILLA ADARSHA SADAR"));
        currencies.add(new Sections("Chittagong","Comilla","LAKSAM"));
        currencies.add(new Sections("Chittagong","Comilla","MANOHARGANJ"));
        currencies.add(new Sections("Chittagong","Comilla","MEGHNA"));
        currencies.add(new Sections("Chittagong","Comilla","MURADNAGAR"));
        currencies.add(new Sections("Chittagong","Comilla","NANGALKOT"));
        currencies.add(new Sections("Chittagong","Comilla","TITAS"));



        //$€£¥₽₩₪฿₫₴₹


        currencies.add(new Sections("Chittagong","Cox's Bazar","CHAKARIA"));
        currencies.add(new Sections("Chittagong","Cox's Bazar","COX'S BAZAR SADAR"));
        currencies.add(new Sections("Chittagong","Cox's Bazar","KUTUBDIA"));
        currencies.add(new Sections("Chittagong","Cox's Bazar","MAHESHKHALI"));
        currencies.add(new Sections("Chittagong","Cox's Bazar","PEKUA"));

        currencies.add(new Sections("Chittagong","Cox's Bazar","RAMU"));
        currencies.add(new Sections("Chittagong","Cox's Bazar","TEKNAF"));
        currencies.add(new Sections("Chittagong","Cox's Bazar","UKHIA"));




        currencies.add(new Sections("Chittagong","Feni","CHHAGALNAIYA"));
        currencies.add(new Sections("Chittagong","Feni","DAGANBHUIYAN"));
        currencies.add(new Sections("Chittagong","Feni","FENI SADAR"));
        currencies.add(new Sections("Chittagong","Feni","FULGAZI"));
        currencies.add(new Sections("Chittagong","Feni","PARSHURAM"));
        currencies.add(new Sections("Chittagong","Feni","SONAGAZI"));



        currencies.add(new Sections("Chittagong","Khagrachhari","DIGHINALA"));
        currencies.add(new Sections("Chittagong","Khagrachhari","KHAGRACHHARI SADAR"));
        currencies.add(new Sections("Chittagong","Khagrachhari","LAKSHMICHHARI"));
        currencies.add(new Sections("Chittagong","Khagrachhari","MAHALCHHARI"));
        currencies.add(new Sections("Chittagong","Khagrachhari","MANIKCHHARI"));

        currencies.add(new Sections("Chittagong","Khagrachhari","MATIRANGA"));
        currencies.add(new Sections("Chittagong","Khagrachhari","PANCHHARI"));
        currencies.add(new Sections("Chittagong","Khagrachhari","RAMGARH"));




        currencies.add(new Sections("Chittagong","Lakshmipur","KAMALNAGAR"));
        currencies.add(new Sections("Chittagong","Lakshmipur","LAKSHMIPUR SADAR"));
        currencies.add(new Sections("Chittagong","Lakshmipur","ROYPUR"));
        currencies.add(new Sections("Chittagong","Lakshmipur","RAMGANJ"));
        currencies.add(new Sections("Chittagong","Lakshmipur","RAMGATI"));



        currencies.add(new Sections("Chittagong","Noakhali","BEGUMGANJ"));
        currencies.add(new Sections("Chittagong","Noakhali","CHATKHIL"));
        currencies.add(new Sections("Chittagong","Noakhali","COMPANIGANJ"));
        currencies.add(new Sections("Chittagong","Noakhali","HATIYA"));
        currencies.add(new Sections("Chittagong","Noakhali","KABIRHAT"));
        currencies.add(new Sections("Chittagong","Noakhali","SENBAGH"));
        currencies.add(new Sections("Chittagong","Noakhali","SONAIMURI"));
        currencies.add(new Sections("Chittagong","Noakhali","SUBARNACHAR"));
        currencies.add(new Sections("Chittagong","Noakhali","NOAKHALI SADAR"));



        //$€£¥₽₩₪฿₫₴₹
        currencies.add(new Sections("Chittagong","Rangamati","BAGHAICHHARI"));
        currencies.add(new Sections("Chittagong","Rangamati","BARKAL UPAZILA"));
        currencies.add(new Sections("Chittagong","Rangamati","KAWKHALI (BETBUNIA)"));
        currencies.add(new Sections("Chittagong","Rangamati","BELAI CHHARI UPAZI"));
        currencies.add(new Sections("Chittagong","Rangamati","KAPTAI UPAZILA"));
        currencies.add(new Sections("Chittagong","Rangamati","JURAI CHHARI UPAZIL"));


        currencies.add(new Sections("Chittagong","Rangamati","LANGADU UPAZILA"));
        currencies.add(new Sections("Chittagong","Rangamati"," NANIARCHAR UPAZILA"));
        currencies.add(new Sections("Chittagong","Rangamati","RAJASTHALI UPAZILA"));
        currencies.add(new Sections("Chittagong","Rangamati","RANGAMATI SADAR UP"));


        currencyAdapter=new CurrencyAdapter(currencies, UpdateActivity.this,3);
        currencycontainer.setAdapter(currencyAdapter);






    }
}