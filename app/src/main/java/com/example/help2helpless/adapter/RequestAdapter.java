package com.example.help2helpless.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.help2helpless.ApprovedActivity;
import com.example.help2helpless.R;
import com.example.help2helpless.model.Dealer;

import java.util.ArrayList;

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.RequestViewHolder> {
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

    @Override
    public void onBindViewHolder(@NonNull RequestViewHolder holder, int position) {
                final Dealer dealer=dealersList.get(position);
        holder.name.setText(dealer.getName());
        holder.shpname.setText(dealer.getShopnme());
        holder.address.setText(dealer.getShpnmthana());
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
    }

    @Override
    public int getItemCount() {
        return dealersList.size();
    }

    public class RequestViewHolder extends RecyclerView.ViewHolder {
        TextView name,shpname,address;
        public RequestViewHolder(@NonNull View itemView) {
            super(itemView);
            name= itemView.findViewById(R.id.rdname);
            shpname= itemView.findViewById(R.id.rdshpname);
            address= itemView.findViewById(R.id.shopaddress);
        }
    }
}
