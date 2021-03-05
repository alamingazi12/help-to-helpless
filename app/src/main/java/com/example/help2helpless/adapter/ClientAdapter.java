package com.example.help2helpless.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.help2helpless.ClientProfileActivity;
import com.example.help2helpless.R;
import com.example.help2helpless.model.Client;
import com.example.help2helpless.model.Dealer;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class ClientAdapter extends RecyclerView.Adapter<ClientAdapter.ClientViewHolder> {
    ArrayList<Client> clientList;
    EditText amount;
    View DialogueView;
    AlertDialog dialog;
    String url="https://apps.help2helpless.com/add_discount.php";
    public ClientAdapter(ArrayList<Client> clientList, Context context) {
        this.clientList = clientList;
        this.context = context;
    }

    Context context;
    @NonNull
    @Override
    public ClientAdapter.ClientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.client_item_discount,parent,false);
        return new ClientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClientAdapter.ClientViewHolder holder, final int position) {
            Client client=clientList.get(position);
            holder.cname.setText(client.getcName());
            holder.address.setText(client.getCaddres());
            holder.cl_phone.setText(client.getCnumber());
        String imageUrl="https://apps.help2helpless.com/uploads/"+client.getCphoto();
        Picasso.get().load(imageUrl).resize(80,80).centerCrop().into(holder.client_image);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Client client1=clientList.get(position);
                createDialoge(client1.getCnumber());
            }
        });
        holder.discount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Client client2=clientList.get(position);
                createDialoge(client2.getCnumber());
            }
        });
        holder.more_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Client client4=clientList.get(position);
                Intent intent=new Intent(context, ClientProfileActivity.class);
                Bundle bundle=new Bundle();
                bundle.putParcelable("client",client4);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });

    }

    public void   createDialoge(final String cnumber){
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        DialogueView=LayoutInflater.from(context).inflate(R.layout.add_discount,null);
        Button button_ok= DialogueView.findViewById(R.id.selectok);
        amount=DialogueView.findViewById(R.id.addamount);
        Button button_cancel=DialogueView.findViewById(R.id.selectcancel);
        button_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addDiscount(cnumber) ;
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

    @Override
    public int getItemCount() {
        return clientList.size();
    }
    private void addDiscount(final String cnumber) {

        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    String result=jsonObject.getString("response");
                    if(result.equals("success")){

                        Toast.makeText(context,"Discount added Successfully",Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                    }
                    else{
                        Toast.makeText(context," "+result,Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }



                ,new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,"not Uploaded",Toast.LENGTH_LONG).show();
            }
        })
        {
            @Override
            public byte[] getBody() throws AuthFailureError {
                JSONObject params = new JSONObject();
                try {
                 SharedPreferences   dealerlogininfo=context.getSharedPreferences("dealerinfo",0);
                    Date date=new Date();
                    SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
                    String today=sdf.format(date);

                    String pdatarr[]=today.split("-");
                    params.put("cl_contact",cnumber);
                    params.put("dlr_contact",dealerlogininfo.getString("contact",null));
                    params.put("month",getMonthString(pdatarr[1]));
                    params.put("year",pdatarr[2]);
                    params.put("amount",amount.getText().toString());


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

        RequestQueue requestQueue= Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);


    }


    private String getMonthString(String s) {

        String monthName=null;

        switch (s){
            case "01":
                monthName="January";
                break;
            case "02":
                monthName="February";
                break;
            case "03":
                monthName="March";
                break;
            case "04":
                monthName="April";
                break;
            case "05":
                monthName="May";
                break;
            case "06":
                monthName="June";
                break;
            case "07":
                monthName="July";
                break;
            case "08":
                monthName="August";
                break;
            case "09":
                monthName="September";
                break;
            case "10":
                monthName="October";
                break;
            case "11":
                monthName="November";
                break;
            case "12":
                monthName="December";
                break;

        }
        return monthName;
    }
    public class ClientViewHolder extends RecyclerView.ViewHolder {
        TextView cname,address,cl_phone;
        CircleImageView client_image;

        Button more_info,discount;


        public ClientViewHolder(@NonNull View itemView) {
            super(itemView);
            cname=itemView.findViewById(R.id.cl_name);
            address=itemView.findViewById(R.id.cl_address);
            client_image=itemView.findViewById(R.id.cl_image);
            cl_phone=itemView.findViewById(R.id.cl_phone);
            more_info=itemView.findViewById(R.id.moreinfo);
            discount=itemView.findViewById(R.id.cl_discount);
        }
    }
}
