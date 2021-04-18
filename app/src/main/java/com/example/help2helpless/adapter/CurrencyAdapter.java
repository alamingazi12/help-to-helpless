package com.example.help2helpless.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.help2helpless.DealerRegActivity;
import com.example.help2helpless.DonarRegisterActivity;
import com.example.help2helpless.R;
import com.example.help2helpless.model.Sections;

import java.util.ArrayList;

public class CurrencyAdapter extends RecyclerView.Adapter<CurrencyAdapter.CurrencyViewHolder> {
    ArrayList<Sections> currencies;
    Context context;
    SharedPreferences sharedPreferences_zilla;
    SharedPreferences.Editor editor;
    int i;



    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public CurrencyAdapter(ArrayList<Sections> currencies, Context context, int i) {
        this.currencies = currencies;
        this.context = context;
        this.i=i;
        if(sharedPreferences_zilla==null){
            sharedPreferences_zilla=context.getSharedPreferences("zilla_info",0);
            editor=sharedPreferences_zilla.edit();
        }


    }


    @NonNull
    @Override
    public CurrencyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_currency,parent,false);
        return new CurrencyViewHolder(view,mListener);

    }

    @Override
    public void onBindViewHolder(@NonNull CurrencyViewHolder holder, final int position) {
       final Sections sections=currencies.get(position);

        holder.divisions.setText(sections.getDivision());
        holder.zilla.setText(sections.getZilla());
        holder.select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                final Sections sections2=currencies.get(position);
                Log.d("zilla_adapter",sections2.getZilla());
                editor.putString("zilla",sections2.getZilla());
                editor.apply();
                if(i==2){

                    DealerRegActivity.zilla.setText(sections2.getZilla());
                    DealerRegActivity.alertDialog.dismiss();


                }if(i==1){
                    DonarRegisterActivity.dZilla.setText(sections2.getZilla());
                    DonarRegisterActivity.alertDialog.dismiss();
                }

                /*
                if (CurrencyActivity.activity_value.equals("dealer")){
                    Intent intent=new Intent(context, DealerRegActivity.class);
                    context.startActivity(intent);
                }
                if (CurrencyActivity.activity_value.equals("donar")){
                    Intent intent=new Intent(context, DonarRegisterActivity.class);
                    context.startActivity(intent);
                }

                 */

            }
        });


    }

    @Override
    public int getItemCount() {
        return currencies.size();
    }

    public class CurrencyViewHolder extends RecyclerView.ViewHolder {

        private TextView divisions,zilla;
        Button select;
        Button ok_button,ok_cancel,currency;
        public CurrencyViewHolder(@NonNull View itemView,final OnItemClickListener listener) {
            super(itemView);
            divisions=itemView.findViewById(R.id._division);
            zilla=itemView.findViewById(R.id._zilla);
            select=itemView.findViewById(R.id._currency);



        }
    }



    public void filterList(ArrayList<Sections> filteredList) {
        currencies = filteredList;
        notifyDataSetChanged();
    }
}