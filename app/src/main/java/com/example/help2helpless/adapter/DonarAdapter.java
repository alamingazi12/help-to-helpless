package com.example.help2helpless.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.example.help2helpless.model.Donar;
import com.makeramen.roundedimageview.RoundedImageView;
import com.muddzdev.styleabletoast.StyleableToast;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class DonarAdapter extends RecyclerView.Adapter<DonarAdapter.DonarViewHolder> {
    String url="https://apps.help2helpless.com/add_request.php";
  public static   ArrayList<Donar> donarlist;
    Context context;
    public DonarAdapter(ArrayList<Donar> donarlist, Context context) {
        this.donarlist = donarlist;
        this.context = context;
    }

    public  void addList(ArrayList<Donar> dealers){
        this.donarlist.addAll(dealers);
        notifyDataSetChanged();

    }

    public void filterList(ArrayList<Donar> filteredList) {
        donarlist = filteredList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DonarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.add_donar_item_list,parent,false);
       return new DonarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DonarViewHolder holder, final int position) {

        Donar donar=donarlist.get(position);
        holder.dnr_name.setText(donar.getDname());
        holder.dnr_address.setText(donar.getPresentaddr());
        String imageUrl="https://apps.help2helpless.com/donar_profile/"+donar.getDonar_photo();
        Picasso.get().load(imageUrl).resize(80,80).centerCrop().into(holder.dnr_profile_image);
        holder.add_dnr_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Donar donar1=   donarlist.get(position);
                addRequest(donar1);
            }
        });

    }

    private void addRequest(final Donar donars) {

        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    String result=jsonObject.getString("response");
                    if(result.equals("success")){
                        StyleableToast.makeText(context,"Request Sent",R.style.greentoast).show();
                        donarlist.remove(donars);
                        notifyDataSetChanged();
                        //Toast.makeText(context,"Discount added Successfully",Toast.LENGTH_LONG).show();

                    }
                    else{
                        StyleableToast.makeText(context,"Request Sent",R.style.mytoast).show();
                       // Toast.makeText(context," "+result,Toast.LENGTH_LONG).show();
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
                    params.put("dnr_cont",donars.getDcontact());
                    params.put("dlr_cont",dealerlogininfo.getString("contact",null));

                   // params.put("month",getMonthString(pdatarr[1]));



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
        return donarlist.size();
    }

    public class DonarViewHolder extends RecyclerView.ViewHolder {
        TextView dnr_name,dnr_address;
        Button add_dnr_btn;
        RoundedImageView dnr_profile_image;
        public DonarViewHolder(@NonNull View itemView) {
            super(itemView);
            dnr_profile_image=itemView.findViewById(R.id.dnr_profile_image);
            dnr_name=itemView.findViewById(R.id.donar_name);
            dnr_address=itemView.findViewById(R.id.dnr_address);
            add_dnr_btn=itemView.findViewById(R.id.add_donar_request);
        }
    }
}
