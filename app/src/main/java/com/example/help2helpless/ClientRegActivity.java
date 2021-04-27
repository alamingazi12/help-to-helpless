package com.example.help2helpless;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.help2helpless.model.Sections;
import com.makeramen.roundedimageview.RoundedImageView;
import com.muddzdev.styleabletoast.StyleableToast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class ClientRegActivity extends AppCompatActivity {
    Bitmap bitmap;
    String profile_image_string;
    public static int image_request=1;
    Button btn_client_next,btn_browse_profile_pic;
    EditText client_full_name;
    RoundedImageView client_Profile_picView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_reg);
        initAll();

        btn_browse_profile_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                browseProfilePic();
            }
        });

        btn_client_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name=client_full_name.getText().toString();

                if(bitmap==null || TextUtils.isEmpty(client_full_name.getText())){
                    StyleableToast.makeText(ClientRegActivity.this,"Fields Empty",R.style.mytoast).show();
                }else{
                    Bundle bundle=new Bundle();
                    bundle.putString("fullName",name);
                    bundle.putString("imageString",imageToString(bitmap));
                    Intent intent=new Intent(ClientRegActivity.this,ClientActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }

            }
        });
    }




    private void browseProfilePic() {

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
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                client_Profile_picView.setImageBitmap(bitmap);
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
                //button initialize
                btn_client_next =findViewById(R.id.btn_client_next);
                btn_browse_profile_pic=findViewById(R.id.btn_browse_profile_image);

                client_full_name=findViewById(R.id.cl_ful_name);
                client_Profile_picView=findViewById(R.id.cl_profile_view);
    }
}