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
import com.example.help2helpless.model.Donar;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class DonarAdapter extends RecyclerView.Adapter<DonarAdapter.DonarViewHolder> {

    ArrayList<Donar> donarlist;
    Context context;
    public DonarAdapter(ArrayList<Donar> donarlist, Context context) {
        this.donarlist = donarlist;
        this.context = context;
    }


    @NonNull
    @Override
    public DonarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.add_donar_item_list,parent,false);
       return new DonarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DonarViewHolder holder, int position) {

        Donar donar=donarlist.get(position);
        holder.dnr_name.setText(donar.getDname());
        holder.dnr_address.setText(donar.getPresentaddr());
        String imageUrl="https://apps.help2helpless.com/donar_profile/"+donar.getDonar_photo();
        Picasso.get().load(imageUrl).resize(80,80).centerCrop().into(holder.dnr_profile_image);
        holder.add_dnr_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return donarlist.size();
    }

    public class DonarViewHolder extends RecyclerView.ViewHolder {
        TextView dnr_name,dnr_address;
        Button add_dnr_btn;
        CircleImageView dnr_profile_image;
        public DonarViewHolder(@NonNull View itemView) {
            super(itemView);
            dnr_profile_image=itemView.findViewById(R.id.dnr_image);
            dnr_name=itemView.findViewById(R.id.donar_name);
            dnr_address=itemView.findViewById(R.id.dnr_address);
            add_dnr_btn=itemView.findViewById(R.id.add_donar_request);
        }
    }
}
