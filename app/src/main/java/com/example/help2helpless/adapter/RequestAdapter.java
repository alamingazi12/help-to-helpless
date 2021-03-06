package com.example.help2helpless.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.help2helpless.ApprovedActivity;
import com.example.help2helpless.R;
import com.example.help2helpless.model.Dealer;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.RequestViewHolder> {
    String imageUrl="https://apps.help2helpless.com/uploads/";
    String url="https://apps.help2helpless.com/dealerinsert.php";
    public RequestAdapter(ArrayList<Dealer> dealersList, Context context) {
        this.dealersList = dealersList;

        this.context = context;
    }

    ArrayList<Dealer> dealersList;
    Context context;
    @NonNull
    @Override
    public RequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.request_items,parent,false);
        return new RequestViewHolder(view);
    }
    private void registerProcess(final Dealer dealer) {
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    String result=jsonObject.getString("response");
                    if(result.equals("success")){

                        Toast.makeText(context,"You registered"+result,Toast.LENGTH_LONG).show();
                    }
                    else{

                        Toast.makeText(context,"Error Occured"+result,Toast.LENGTH_LONG).show();
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



                    params.put("name",dealer.getName());
                    params.put("fname",dealer.getFathername());
                    params.put("homaddr",dealer.getHmaddres());
                    params.put("phone",dealer.getPhone());
                    params.put("bknumber",dealer.getBksnum());
                    params.put("email",dealer.getEmail());
                    params.put("shopname",dealer.getShopnme());
                    params.put("shopthana",dealer.getShpnmthana());
                    params.put("shopzilla",dealer.getShpnmzilla());
                    params.put("shoppic",dealer.getShoppic());
                    params.put("regno",dealer.getDrugsell_regnum());
                    params.put("nid",dealer.getNid());
                    params.put("nidpic",dealer.getNid_pic());
                    params.put("username",dealer.getUsernm());
                    params.put("password",dealer.getPasswrd());


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

    @Override
    public void onBindViewHolder(@NonNull RequestViewHolder holder, final int position) {
                final Dealer dealer=dealersList.get(position);
        holder.name.setText(dealer.getName());
        holder.shpname.setText(dealer.getShopnme());
        holder.address.setText(dealer.getShpnmthana());
        String url=imageUrl+dealer.getShoppic();
        //Picasso.get().load(imageUrl).into(shpimage);
        Picasso.get()
                .load(url)
                .resize(128, 128)
                .centerCrop()
                .into(holder.dlr_shop_pic);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, ApprovedActivity.class);
                Bundle bundle=new Bundle();
                bundle.putParcelable("deler",dealer);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
        holder.reg_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                  Dealer dealer1=dealersList.get(position);
                  registerProcess(dealer1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dealersList.size();
    }

    public class RequestViewHolder extends RecyclerView.ViewHolder {
        TextView name,shpname,address,dlr_contact,reg_confirm;
        ImageView dlr_shop_pic;
        public RequestViewHolder(@NonNull View itemView) {
            super(itemView);
            name= itemView.findViewById(R.id.rdname);
            shpname= itemView.findViewById(R.id.rdshpname);
            address= itemView.findViewById(R.id.shopaddress);
            dlr_contact=itemView.findViewById(R.id.dlr_contact);
            reg_confirm=itemView.findViewById(R.id.confirm_dlr);
            dlr_shop_pic=itemView.findViewById(R.id.dlr_shop_image);
        }
    }
}
