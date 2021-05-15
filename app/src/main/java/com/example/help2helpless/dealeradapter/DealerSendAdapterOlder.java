package com.example.help2helpless.dealeradapter;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.help2helpless.R;
import com.example.help2helpless.model.DealerSendRecord;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import de.hdodenhof.circleimageview.CircleImageView;

public class DealerSendAdapterOlder extends RecyclerView.Adapter<DealerSendAdapterOlder.RecordViewHolder> {

    public DealerSendAdapterOlder(ArrayList<DealerSendRecord> recordslist, Context context) {
        this.recordslist = recordslist;
        this.context = context;
    }
    public DealerSendAdapterOlder(ArrayList<DealerSendRecord> recordslist, Context context,int value) {
        this.recordslist = recordslist;
        this.context = context;
        this.value=value;
    }

    static   ArrayList<DealerSendRecord> recordslist;
    Context context;
    int value;
    @NonNull
    @Override
    public DealerSendAdapterOlder.RecordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.send_record_item,parent,false);
        return new DealerSendAdapterOlder.RecordViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DealerSendAdapterOlder.RecordViewHolder holder, int position) {


        DealerSendRecord record=  recordslist.get(position);

        holder.dealer_name.setText(record.getcName());
        holder.amount.setText(""+record.getDiscount());
        String date=record.getT_date();
        String datearr[] =date.split("-");
        String years=datearr[0];
        String months=datearr[1];
        String days=datearr[2];
        holder.month.setText(getMonthString(months));
        holder.day.setText(days);
        holder.year.setText(years);

        String imageUrl="https://apps.help2helpless.com/client_profile/"+record.getProfile_pic();
        Log.d("Image",imageUrl);
        Picasso.get().load(imageUrl).resize(80,80).centerCrop().into(holder.dealer_image);

        /*
        if(value==2){
            String imageUrl_admin="https://apps.help2helpless.com/admin_photo/"+record.getProfile_pic();
            Picasso.get().load(imageUrl_admin).resize(80,80).centerCrop().into(holder.dealer_image);
        }else{
            String imageUrl="https://apps.help2helpless.com/uploads/"+record.getProfile_pic();
            Log.d("Image",imageUrl);
            Picasso.get().load(imageUrl).resize(80,80).centerCrop().into(holder.dealer_image);

        }

         */
    }



    @Override
    public int getItemCount() {
        return recordslist.size();
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

    public class RecordViewHolder extends RecyclerView.ViewHolder {
        TextView dealer_name,amount,day,month,year;
        CircleImageView dealer_image;
        public RecordViewHolder(@NonNull View itemView) {
            super(itemView);
            dealer_image=itemView.findViewById(R.id.dealer_profile_img);
            dealer_name=itemView.findViewById(R.id.history_dealer_name);
            amount=itemView.findViewById(R.id.history_amount);
            day=itemView.findViewById(R.id.history_day_of_month);
            month=itemView.findViewById(R.id.history_month);
            year=itemView.findViewById(R.id.history_year);




        }
    }
}
