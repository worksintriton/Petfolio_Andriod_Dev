package com.petfolio.infinitus.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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
import com.petfolio.infinitus.doctor.PrescriptionActivity;
import com.petfolio.infinitus.interfaces.OnAppointmentCancel;
import com.petfolio.infinitus.responsepojo.PetNewAppointmentResponse;

import java.util.List;


public class PetNewAppointmentAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private  String TAG = "PetNewAppointmentAdapter";
    private List<PetNewAppointmentResponse.DataBean> newAppointmentResponseList;
    private Context context;

    PetNewAppointmentResponse.DataBean currentItem;

    private OnAppointmentCancel onAppointmentCancel;

    private int size;

    public PetNewAppointmentAdapter(Context context, List<PetNewAppointmentResponse.DataBean> newAppointmentResponseList, RecyclerView inbox_list,int size,OnAppointmentCancel onAppointmentCancel) {
        this.newAppointmentResponseList = newAppointmentResponseList;
        this.context = context;
        this.size = size;
        this.onAppointmentCancel = onAppointmentCancel;


    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_pet_new_appointment, parent, false);
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
        holder.txt_clinicname.setText(newAppointmentResponseList.get(position).getDoc_business_info().get(0).getClinic_name());
        holder.txt_petname.setText(newAppointmentResponseList.get(position).getPet_id().getPet_name());

        if(newAppointmentResponseList.get(position).getAppointment_types() != null){
            holder.txt_type.setText(newAppointmentResponseList.get(position).getAppointment_types());
        }
        if(newAppointmentResponseList.get(position).getService_amount() != null){
            holder.txt_service_cost.setText(newAppointmentResponseList.get(position).getService_amount());
        }

        if(newAppointmentResponseList.get(position).getBooking_date_time() != null){
            holder.txt_bookedon.setText("Booked on:"+" "+newAppointmentResponseList.get(position).getBooking_date_time());

        }


        if(newAppointmentResponseList.get(position).getAppointment_types() != null && newAppointmentResponseList.get(position).getAppointment_types().equalsIgnoreCase("Emergency")){
        holder.img_emergency_appointment.setVisibility(View.VISIBLE);
        }else{
            holder.img_emergency_appointment.setVisibility(View.GONE);

        }









        if (newAppointmentResponseList.get(0).getDoc_business_info().get(0).getClinic_pic().get(0).getClinic_pic() != null && !newAppointmentResponseList.get(0).getDoc_business_info().get(0).getClinic_pic().get(0).getClinic_pic().isEmpty()) {

                Glide.with(context)
                        .load(newAppointmentResponseList.get(0).getDoc_business_info().get(0).getClinic_pic().get(0).getClinic_pic())
                        .into(holder.img_clinic_imge);

            }
           else{
                Glide.with(context)
                        .load(R.drawable.image_thumbnail)
                        .into(holder.img_clinic_imge);

            }




        holder.btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { Intent i = new Intent(context, PrescriptionActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                onAppointmentCancel.onAppointmentCancel(newAppointmentResponseList.get(position).get_id());

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
        public TextView txt_clinicname,txt_petname,txt_type,txt_service_cost,txt_bookedon;
        public ImageView img_clinic_imge,img_emergency_appointment;
        public Button btn_cancel;



        public ViewHolderOne(View itemView) {
            super(itemView);
            img_clinic_imge = itemView.findViewById(R.id.img_clinic_imge);
            txt_clinicname = itemView.findViewById(R.id.txt_clinicname);
            txt_petname = itemView.findViewById(R.id.txt_petname);
            txt_type = itemView.findViewById(R.id.txt_type);
            txt_bookedon = itemView.findViewById(R.id.txt_bookedon);
            txt_service_cost = itemView.findViewById(R.id.txt_service_cost);
            btn_cancel = itemView.findViewById(R.id.btn_cancel);
            img_emergency_appointment = itemView.findViewById(R.id.img_emergency_appointment);
            img_emergency_appointment.setVisibility(View.GONE);


        }




    }








}
