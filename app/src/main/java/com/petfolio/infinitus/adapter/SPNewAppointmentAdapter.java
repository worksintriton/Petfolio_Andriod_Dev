package com.petfolio.infinitus.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
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

import java.util.List;


public class SPNewAppointmentAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private  String TAG = "SPNewAppointmentAdapter";
    private final List<SPAppointmentResponse.DataBean> newAppointmentResponseList;
    private Context context;

    SPAppointmentResponse.DataBean currentItem;

    private OnAppointmentCancel onAppointmentCancel;
    private OnAppointmentComplete onAppointmentComplete;
    private int size;



    public SPNewAppointmentAdapter(Context context, List<SPAppointmentResponse.DataBean> newAppointmentResponseList, RecyclerView inbox_list, int size, OnAppointmentCancel onAppointmentCancel,OnAppointmentComplete onAppointmentComplete) {
        this.newAppointmentResponseList = newAppointmentResponseList;
        this.context = context;
        this.size = size;
        this.onAppointmentCancel = onAppointmentCancel;
        this.onAppointmentComplete = onAppointmentComplete;



    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_doctor_new_appointment, parent, false);
        return new ViewHolderOne(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        initLayoutOne((ViewHolderOne) holder, position);


    }

    @SuppressLint("SetTextI18n")
    private void initLayoutOne(ViewHolderOne holder, final int position) {

        Log.w(TAG,"Pet name-->"+newAppointmentResponseList.get(position).getPet_id().getPet_name());

        currentItem = newAppointmentResponseList.get(position);
        holder.txt_petname.setText(newAppointmentResponseList.get(position).getPet_id().getPet_name());
        holder.txt_pettype.setText(newAppointmentResponseList.get(position).getPet_id().getPet_type());
        holder.txt_lbl_type.setText("Service Name");
        if(newAppointmentResponseList.get(position).getService_name() != null){
            holder.txt_type.setText(newAppointmentResponseList.get(position).getService_name());
        }
        if(newAppointmentResponseList.get(position).getService_amount() != null){
            holder.txt_service_cost.setText("\u20B9 "+newAppointmentResponseList.get(position).getService_amount());
        }

        if(newAppointmentResponseList.get(position).getBooking_date_time() != null){
            holder.txt_bookedon.setText("Booked on:"+" "+newAppointmentResponseList.get(position).getBooking_date_time());

        }

           if (newAppointmentResponseList.get(position).getPet_id().getPet_img() != null && !newAppointmentResponseList.get(position).getPet_id().getPet_img().isEmpty()) {

                Glide.with(context)
                        .load(newAppointmentResponseList.get(position).getPet_id().getPet_img())
                        .into(holder.img_pet_imge);

            }
           else{
                Glide.with(context)
                        .load(R.drawable.image_thumbnail)
                        .into(holder.img_pet_imge);

            }

        if(newAppointmentResponseList.get(position).getAppointment_types() != null && newAppointmentResponseList.get(position).getAppointment_types().equalsIgnoreCase("Emergency")){
            holder.img_emergency_appointment.setVisibility(View.VISIBLE);
        }else{
            holder.img_emergency_appointment.setVisibility(View.GONE);

        }


        holder.btn_complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onAppointmentComplete.onAppointmentComplete(newAppointmentResponseList.get(position).get_id());

            }
        });

        holder.btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAppointmentCancel.onAppointmentCancel(newAppointmentResponseList.get(position).get_id(),newAppointmentResponseList.get(position).getAppointment_types());

            }
        });



    }


    @Override
    public int getItemCount() {
        return Math.min(newAppointmentResponseList.size(), size);

    }


    @Override
    public int getItemViewType(int position) {
        return position;
    }

    static class ViewHolderOne extends RecyclerView.ViewHolder {
        public TextView txt_petname,txt_pettype,txt_type,txt_service_cost,txt_bookedon,txt_lbl_type;
        public ImageView img_pet_imge,img_emergency_appointment;
        public Button btn_cancel,btn_complete;
        public LinearLayout ll_new;



        public ViewHolderOne(View itemView) {
            super(itemView);
            img_pet_imge = itemView.findViewById(R.id.img_pet_imge);
            txt_petname = itemView.findViewById(R.id.txt_petname);
            txt_pettype = itemView.findViewById(R.id.txt_pettype);
            txt_lbl_type = itemView.findViewById(R.id.txt_lbl_type);
            txt_type = itemView.findViewById(R.id.txt_type);
            txt_service_cost = itemView.findViewById(R.id.txt_service_cost);
            txt_bookedon = itemView.findViewById(R.id.txt_bookedon);
            btn_cancel = itemView.findViewById(R.id.btn_cancel);
            btn_complete = itemView.findViewById(R.id.btn_complete);
            ll_new = itemView.findViewById(R.id.ll_new);
            img_emergency_appointment = itemView.findViewById(R.id.img_emergency_appointment);
            img_emergency_appointment.setVisibility(View.GONE);



        }




    }








}
