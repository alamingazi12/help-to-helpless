package com.example.help2helpless.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
import com.example.help2helpless.DonarRegisterActivity;
import com.example.help2helpless.R;
import com.example.help2helpless.model.Dealer;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DealerAdapter extends RecyclerView.Adapter<DealerAdapter.DealerViewHolder> {
    ArrayList<Dealer> dealers;
    String donar_contact;
    String url="https://apps.help2helpless.com/add_dealer.php";
    Context context;
    AlertDialog dialog;
    View DialogueView;
    Dealer   dealer;
    String dealer_phone;
    EditText amount;

    public DealerAdapter(ArrayList<Dealer> dealers, Context context,String donar_contact) {
        this.dealers = dealers;
        this.context = context;
        this.donar_contact=donar_contact;
    }

    @NonNull
    @Override
    public DealerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.dealer_item_add,parent,false);
        return new DealerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DealerViewHolder holder, final int position) {
        dealer=dealers.get(position);

        holder.name.setText(dealer.getName());
        holder.shpname.setText(dealer.getShopnme());
        holder.address.setText(dealer.getShpnmthana());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dealer dealer1=dealers.get(position);
                dealer_phone=dealer1.getPhone();
                //Log.d("phone",dealer_phone);
                /*
                Intent intent=new Intent(context, ApprovedActivity.class);
                Bundle bundle=new Bundle();
                bundle.putParcelable("deler",dealer);
                intent.putExtras(bundle);
                context.startActivity(intent);

                 */
                createDialoge(dealer);
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
               saveAmount(dealer) ;
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

                            Toast.makeText(context,"Dealer Added Successfully",Toast.LENGTH_LONG).show();
                            dialog.dismiss();
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
                   SharedPreferences donarinfo=context.getSharedPreferences("donarinfo",0);
                        String dcontact=donarinfo.getString("contact",null);

                        Log.d("phone",dcontact);
                        params.put("dnr_cont",dcontact);
                        params.put("dealer_cont",dealer_phone);
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

    @Override
    public int getItemCount() {
        return dealers.size();
    }

    public class DealerViewHolder extends RecyclerView.ViewHolder {
        TextView name,shpname,address;
        public DealerViewHolder(@NonNull View itemView) {
            super(itemView);
            name= itemView.findViewById(R.id.dlrname);
            shpname= itemView.findViewById(R.id.dlrshpname);
            address= itemView.findViewById(R.id.dlrshopaddress);
        }
    }
}
