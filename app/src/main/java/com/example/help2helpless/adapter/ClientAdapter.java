package com.example.help2helpless.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.help2helpless.R;
import com.example.help2helpless.model.Client;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ClientAdapter extends RecyclerView.Adapter<ClientAdapter.ClientViewHolder> {
    ArrayList<Client> clientList;

    public ClientAdapter(ArrayList<Client> clientList, Context context) {
        this.clientList = clientList;
        this.context = context;
    }

    Context context;
    @NonNull
    @Override
    public ClientAdapter.ClientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.client_item,parent,false);
        return new ClientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClientAdapter.ClientViewHolder holder, int position) {
            Client client=clientList.get(position);
            holder.cname.setText(client.getcName());
            holder.address.setText(client.getCaddres());
        String imageUrl="https://apps.help2helpless.com/uploads/"+client.getCphoto();
        Picasso.get().load(imageUrl).resize(80,80).centerCrop().into(holder.client_image);

    }

    @Override
    public int getItemCount() {
        return clientList.size();
    }

    public class ClientViewHolder extends RecyclerView.ViewHolder {
        TextView cname,address;
        ImageView client_image;
        public ClientViewHolder(@NonNull View itemView) {
            super(itemView);
            cname=itemView.findViewById(R.id.cl_name);
            address=itemView.findViewById(R.id.cl_address);
            client_image=itemView.findViewById(R.id.cl_image);
        }
    }
}
