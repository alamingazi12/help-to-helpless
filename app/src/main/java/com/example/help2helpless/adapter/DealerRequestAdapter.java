package com.example.help2helpless.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
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
import com.example.help2helpless.R;
import com.example.help2helpless.model.Dealer;
import com.muddzdev.styleabletoast.StyleableToast;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DealerRequestAdapter extends RecyclerView.Adapter<DealerRequestAdapter.DealerRequestViewHolder> {
    AlertDialog dialog;
    View DialogueView;
    EditText amount;
    String imageUrl="https://apps.help2helpless.com/uploads/";
    String url="https://apps.help2helpless.com/add_dealer.php";
    public DealerRequestAdapter(Context context, ArrayList<Dealer> dealerlist) {
        this.context = context;
        this.dealerlist = dealerlist;
    }


    Context context;
    ArrayList<Dealer> dealerlist;
    @NonNull
    @Override
    public DealerRequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.dealer_req_items,parent,false);
        return  new DealerRequestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DealerRequestViewHolder holder, final int position) {
        Dealer dealer=dealerlist.get(position);
        holder.name.setText(dealer.getName());
        holder.shpname.setText(dealer.getShopnme());
        holder.address.setText(dealer.getShpnmthana());
        holder.dlr_phone.setText(dealer.getPhone());

        String url=imageUrl+dealer.getShoppic();
        //Picasso.get().load(imageUrl).into(shpimage);
        Picasso.get()
                .load(url)
                .resize(128, 128)
                .centerCrop()
                .into(holder.imageView_dealer);

        holder.add_dealer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dealer dealer2=dealerlist.get(position);
               // dealer_phone=dealer2.getPhone();
                createDialoge(dealer2);
            }
        });
    }

    public void   createDialoge(final Dealer dealer){
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        DialogueView=LayoutInflater.from(context).inflate(R.layout.add_amount,null);
        amount=DialogueView.findViewById(R.id.addamount);
        Button button_ok= DialogueView.findViewById(R.id.selectok);
        Button button_cancel=DialogueView.findViewById(R.id.selectcancel);
        button_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveAmount(dealer);
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

    private void saveAmount(final Dealer dealer) {

        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    String result=jsonObject.getString("response");
                    if(result.equals("success")){

                        // Toast.makeText(context,"Dealer Added Successfully",Toast.LENGTH_LONG).show();
                        StyleableToast.makeText(context, "Dealer Added Successfully", Toast.LENGTH_LONG, R.style.mytoast).show();
                        //AddDealerActivity addDealerActivity=new AddDealerActivity();
                        //addDealerActivity.initRecycler();
                        //addDealerActivity.Back();
                        dealerlist.remove(dealer);
                        notifyDataSetChanged();



                    }
                    else{
                        StyleableToast.makeText(context, ""+result, Toast.LENGTH_LONG, R.style.mytoast).show();
                       // Toast.makeText(context,""+result,Toast.LENGTH_LONG).show();
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
                    SharedPreferences donarinfo=context.getSharedPreferences("donarinfo",0);
                    String dcontact=donarinfo.getString("contact",null);
                    Date date=new Date();
                    SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
                    String today=sdf.format(date);

                    String pdatarr[]=today.split("-");
                    //Log.d("phone",dcontact);
                     params.put("dnr_cont",dcontact);
                     params.put("dealer_cont",dealer.getPhone());
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

    @Override
    public int getItemCount() {
        return dealerlist.size();
    }

    public class DealerRequestViewHolder extends RecyclerView.ViewHolder {
        TextView name,shpname,address,dlr_phone;
        Button add_dealer;
        ImageView imageView_dealer;
        public DealerRequestViewHolder(@NonNull View itemView) {
            super(itemView);
            name= itemView.findViewById(R.id.dname);
            shpname= itemView.findViewById(R.id.rdshpname);
            address= itemView.findViewById(R.id.shopaddress);
            dlr_phone=itemView.findViewById(R.id.dlr_contact);
            add_dealer=itemView.findViewById(R.id.confirm_dlr);
            imageView_dealer=itemView.findViewById(R.id.dlr_shop_image);
        }
    }
}
