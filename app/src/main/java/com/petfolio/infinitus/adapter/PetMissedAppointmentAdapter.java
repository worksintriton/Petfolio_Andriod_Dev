package com.petfolio.infinitus.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import com.petfolio.infinitus.petlover.PetMissedAppointmentDetailsActivity;
import com.petfolio.infinitus.responsepojo.PetAppointmentResponse;
import com.petfolio.infinitus.responsepojo.PetNewAppointmentResponse;

import java.util.List;


public class PetMissedAppointmentAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private  String TAG = "PetMissedAppointmentAdapter";
    private List<PetAppointmentResponse.DataBean> missedAppointmentResponseList;
    private Context context;

    PetAppointmentResponse.DataBean currentItem;
    int size;


    public PetMissedAppointmentAdapter(Context context, List<PetAppointmentResponse.DataBean> missedAppointmentResponseList, RecyclerView inbox_list,int size) {
        this.missedAppointmentResponseList = missedAppointmentResponseList;
        this.context = context;
        this.size = size;


    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_pet_missed_appointment, parent, false);
        return new ViewHolderOne(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        initLayoutOne((ViewHolderOne) holder, position);


    }

    @SuppressLint("SetTextI18n")
    private void initLayoutOne(ViewHolderOne holder, final int position) {

        Log.w(TAG,"Pet name-->"+missedAppointmentResponseList.get(position).getPet_name());

        currentItem = missedAppointmentResponseList.get(position);


        holder.txt_pettype.setText(missedAppointmentResponseList.get(position).getPet_name());
        holder.txt_missed_date.setText("Missed on:"+" "+missedAppointmentResponseList.get(position).getMissed_at());



        if(missedAppointmentResponseList.get(position).getAppointment_for() != null && missedAppointmentResponseList.get(position).getAppointment_for().equalsIgnoreCase("Doctor") ){
            holder.txt_type.setText(missedAppointmentResponseList.get(position).getAppointment_for());
            holder.txt_petname.setText(missedAppointmentResponseList.get(position).getClinic_name());


        }else if(missedAppointmentResponseList.get(position).getAppointment_for() != null && missedAppointmentResponseList.get(position).getAppointment_for().equalsIgnoreCase("SP") ){
            holder.txt_type.setText(missedAppointmentResponseList.get(position).getAppointment_for());
            holder.txt_petname.setText(missedAppointmentResponseList.get(position).getService_provider_name());

        }
        if(missedAppointmentResponseList.get(position).getCost() != null){
            holder.txt_service_cost.setText("\u20B9 "+missedAppointmentResponseList.get(position).getCost());
        }


        if (missedAppointmentResponseList.get(position).getPhoto() != null && !missedAppointmentResponseList.get(position).getPhoto().isEmpty()) {

            Glide.with(context)
                    .load(missedAppointmentResponseList.get(position).getPhoto())
                    .into(holder.img_pet_imge);

        }
        else{
            Glide.with(context)
                    .load(R.drawable.image_thumbnail)
                    .into(holder.img_pet_imge);

        }

        if(missedAppointmentResponseList.get(position).getAppoint_patient_st() != null && missedAppointmentResponseList.get(position).getAppoint_patient_st().equalsIgnoreCase("Petowner Cancelled appointment")){}
        else if(missedAppointmentResponseList.get(position).getAppoint_patient_st() != null && missedAppointmentResponseList.get(position).getAppoint_patient_st().equalsIgnoreCase("Doctor Cancelled appointment")){
            holder.ll_appointmentstatus.setVisibility(View.VISIBLE);
            holder.txt_appointment_status.setText("Not available");
        } else if(missedAppointmentResponseList.get(position).getAppoint_patient_st() != null && missedAppointmentResponseList.get(position).getAppoint_patient_st().equalsIgnoreCase("Doctor missed appointment")){
            holder.ll_appointmentstatus.setVisibility(View.VISIBLE);
            holder.txt_appointment_status.setText("No show");
        }else if(missedAppointmentResponseList.get(position).getAppoint_patient_st() != null && missedAppointmentResponseList.get(position).getAppoint_patient_st().equalsIgnoreCase("Petowner Not Available")){
            holder.ll_appointmentstatus.setVisibility(View.VISIBLE);
            holder.txt_appointment_status.setText("Not available");
        }

        if(missedAppointmentResponseList.get(position).getStatus() != null && missedAppointmentResponseList.get(position).getStatus().equalsIgnoreCase("no show")){
            holder.ll_appointmentstatus.setVisibility(View.VISIBLE);
            holder.txt_appointment_status.setText("No show");
        }

        if(!missedAppointmentResponseList.get(position).getClinic_name().equals("")){

            holder.ll_new.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context, PetMissedAppointmentDetailsActivity.class);

                    //Create the bundle
                    Bundle bundle = new Bundle();

                    Log.w("appointment_id",missedAppointmentResponseList.get(position).get_id());

                    //Add your data from getFactualResults method to bundle
                    bundle.putString("appointment_id",missedAppointmentResponseList.get(position).get_id());

                    //Add the bundle to the intent
                    intent.putExtras(bundle);

                    context.startActivity(intent);
                }
            });

        }



    }

   @Override
    public int getItemCount() {
       return Math.min(missedAppointmentResponseList.size(), size);

    }


    @Override
    public int getItemViewType(int position) {
        return position;
    }

    static class ViewHolderOne extends RecyclerView.ViewHolder {
        public TextView txt_petname,txt_pettype,txt_type,txt_service_cost,txt_missed_date,txt_appointment_status;
        public ImageView img_pet_imge;
        public Button btn_cancel,btn_complete;
        public LinearLayout ll_appointmentstatus;
        LinearLayout ll_new;



        public ViewHolderOne(View itemView) {
            super(itemView);
            img_pet_imge = itemView.findViewById(R.id.img_pet_imge);
            txt_petname = itemView.findViewById(R.id.txt_petname);
            txt_pettype = itemView.findViewById(R.id.txt_pettype);
            txt_type = itemView.findViewById(R.id.txt_type);
            txt_service_cost = itemView.findViewById(R.id.txt_service_cost);
            txt_missed_date = itemView.findViewById(R.id.txt_missed_date);
            btn_cancel = itemView.findViewById(R.id.btn_cancel);
            btn_complete = itemView.findViewById(R.id.btn_complete);
            txt_appointment_status = itemView.findViewById(R.id.txt_appointment_status);
            ll_appointmentstatus = itemView.findViewById(R.id.ll_appointmentstatus);
            ll_appointmentstatus.setVisibility(View.GONE);
            ll_new = itemView.findViewById(R.id.ll_new);


        }




    }








}
