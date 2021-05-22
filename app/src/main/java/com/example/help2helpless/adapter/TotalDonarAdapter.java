package com.example.help2helpless.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.help2helpless.R;
import com.example.help2helpless.model.Dealer;
import com.example.help2helpless.model.Donar;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TotalDonarAdapter  extends RecyclerView.Adapter<TotalDonarAdapter.DonarViewHolder> {
    public TotalDonarAdapter(ArrayList<Donar> donarList, Context context) {
        this.donarList = donarList;
        this.context = context;
    }

      ArrayList<Donar> donarList;
      Context context;
    @NonNull
    @Override
    public DonarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.send_money_donar_items,parent,false);
        return new DonarViewHolder(view);
    }
    public  void addLists(ArrayList<Donar> donars){
        this.donarList.addAll(donars);
        notifyDataSetChanged();

    }
    @Override
    public void onBindViewHolder(@NonNull DonarViewHolder holder, int position) {
        Donar donar=donarList.get(position);
        holder.add_dnr_btn.setVisibility(View.GONE);
        holder.dnr_name.setText(donar.getDname());
        holder.dnr_address.setText(donar.getPresentaddr());
        holder.donar_phone.setText(donar.getDcontact());
        String imageUrl="https://apps.help2helpless.com/donar_profile/"+donar.getDonar_photo();
        Picasso.get().load(imageUrl).resize(80,80).centerCrop().into(holder.dnr_profile_image);
    }

    @Override
    public int getItemCount() {
        return donarList.size();
    }

    public class DonarViewHolder extends RecyclerView.ViewHolder {
        TextView dnr_name,dnr_address,donar_phone;
        Button add_dnr_btn;
        RoundedImageView dnr_profile_image;
        public DonarViewHolder(@NonNull View itemView) {
            super(itemView);
            dnr_profile_image=itemView.findViewById(R.id.dnr_profile_image);
            dnr_name=itemView.findViewById(R.id.donar_name);
            dnr_address=itemView.findViewById(R.id.dnr_address);
            add_dnr_btn=itemView.findViewById(R.id.add_donar_request);
            donar_phone=itemView.findViewById(R.id.donar_contact);

        }
    }
}
