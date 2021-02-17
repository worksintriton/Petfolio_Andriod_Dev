package com.petfolio.infinitus.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.petfolio.infinitus.R;
import com.petfolio.infinitus.interfaces.OnAppointmentCancel;
import com.petfolio.infinitus.interfaces.OnAppointmentComplete;
import com.petfolio.infinitus.responsepojo.SPAppointmentResponse;
import com.petfolio.infinitus.responsepojo.VendorOrderResponse;
import com.petfolio.infinitus.serviceprovider.SPAppointmentDetailsActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class VendorNewOrdersAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private  String TAG = "VendorNewOrdersAdapter";
    private List<VendorOrderResponse.DataBean> newOrderResponseList;

    private Context context;

    VendorOrderResponse.DataBean currentItem;


    private int size;


    public VendorNewOrdersAdapter(Context context, List<VendorOrderResponse.DataBean> newOrderResponseList,int size) {
        this.newOrderResponseList = newOrderResponseList;
        this.context = context;
        this.size = size;




    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_vendor_new_appointment, parent, false);
        return new ViewHolderOne(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        initLayoutOne((ViewHolderOne) holder, position);


    }

    @SuppressLint("SetTextI18n")
    private void initLayoutOne(ViewHolderOne holder, final int position) {


        currentItem = newOrderResponseList.get(position);
        holder.txt_orderid.setText(newOrderResponseList.get(position).getOrder_id());
        holder.txt_producttitle.setText(newOrderResponseList.get(position).getOrder_title());

        if(newOrderResponseList.get(position).getOrder_final_amount() != null){
            holder.txt_service_cost.setText("\u20B9 "+newOrderResponseList.get(position).getOrder_final_amount());
            if(newOrderResponseList.get(position).getOrder_item_count() != null){
                holder.txt_service_cost.setText("\u20B9 "+newOrderResponseList.get(position).getOrder_final_amount()+" ("+newOrderResponseList.get(position).getOrder_item_count()+" items )");

            }
        }

        if(newOrderResponseList.get(position).getOrder_booked_at() != null){
            holder.txt_bookedon.setText("Booked on:"+" "+newOrderResponseList.get(position).getOrder_booked_at());

        }




        holder.ll_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                   /* Intent i = new Intent(context, SPAppointmentDetailsActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.putExtra("appointment_id",newAppointmentResponseList.get(position).get_id());
                    i.putExtra("bookedat",newAppointmentResponseList.get(position).getBooking_date_time());
                    i.putExtra("fromactivity",TAG);
                    context.startActivity(i);*/

            }
        });




    }


    @Override
    public int getItemCount() {
        return Math.min(newOrderResponseList.size(), size);

    }


    @Override
    public int getItemViewType(int position) {
        return position;
    }

    static class ViewHolderOne extends RecyclerView.ViewHolder {
        public TextView txt_orderid,txt_producttitle,txt_service_cost,txt_bookedon;
        public ImageView img_pet_imge;
        public LinearLayout ll_new;



        public ViewHolderOne(View itemView) {
            super(itemView);
            img_pet_imge = itemView.findViewById(R.id.img_pet_imge);
            txt_orderid = itemView.findViewById(R.id.txt_orderid);
            txt_producttitle = itemView.findViewById(R.id.txt_producttitle);
            txt_service_cost = itemView.findViewById(R.id.txt_service_cost);
            txt_bookedon = itemView.findViewById(R.id.txt_bookedon);
            ll_new = itemView.findViewById(R.id.ll_new);




        }




    }






}
