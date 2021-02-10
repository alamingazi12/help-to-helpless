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

public class DealerAdapter extends RecyclerView.Adapter<DealerAdapter.DealerViewHolder> {
    ArrayList<Dealer> dealers;
    Context context;

    public DealerAdapter(ArrayList<Dealer> dealers, Context context) {
        this.dealers = dealers;
        this.context = context;
    }

    @NonNull
    @Override
    public DealerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.dealer_item_add,parent,false);
        return new DealerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DealerViewHolder holder, int position) {
        final Dealer dealer=dealers.get(position);
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
