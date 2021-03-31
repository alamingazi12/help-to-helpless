package com.example.help2helpless.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.help2helpless.DonateDealerActivity;
import com.example.help2helpless.R;
import com.example.help2helpless.model.Dealer;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AddedDealerAdapter extends RecyclerView.Adapter<AddedDealerAdapter.AddedDealerViewHolder> {
    ArrayList<Dealer> dealerslist;
    Context context;
    String imageUrl="https://apps.help2helpless.com/uploads/";

    public AddedDealerAdapter(ArrayList<Dealer> dealerslist, Context context) {
        this.dealerslist = dealerslist;
        this.context = context;
    }

    @NonNull
    @Override
    public AddedDealerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.added_dealer_item,parent,false);
        return new AddedDealerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddedDealerViewHolder holder, final int position) {
        Dealer dealer=dealerslist.get(position);
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

        holder.donate_dealer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dealer dealer2=dealerslist.get(position);
                // dealer_phone=dealer2.getPhone();
                Bundle bundle=new Bundle();
                bundle.putParcelable("dealers",dealer2);
                Intent intent=new Intent(context, DonateDealerActivity.class);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dealerslist.size();
    }

    public class AddedDealerViewHolder extends RecyclerView.ViewHolder {
        TextView name,shpname,address,dlr_phone;
        Button donate_dealer;
        ImageView imageView_dealer;
        public AddedDealerViewHolder(@NonNull View itemView) {
            super(itemView);
            name= itemView.findViewById(R.id.rdname);
            shpname= itemView.findViewById(R.id.rdshpname);
            address= itemView.findViewById(R.id.shopaddress);
            dlr_phone=itemView.findViewById(R.id.dlr_contact);
            donate_dealer=itemView.findViewById(R.id.donate_dlr);
            imageView_dealer=itemView.findViewById(R.id.dlr_shop_image);
        }
    }
}
