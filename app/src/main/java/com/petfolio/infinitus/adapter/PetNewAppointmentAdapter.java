package com.petfolio.infinitus.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.petfolio.infinitus.R;
import com.petfolio.infinitus.interfaces.OnAppointmentCancel;
import com.petfolio.infinitus.petlover.PetNewAppointmentDetailsActivity;
import com.petfolio.infinitus.petlover.VideoCallPetLoverActivity;
import com.petfolio.infinitus.responsepojo.PetAppointmentResponse;

import java.util.List;

import es.dmoral.toasty.Toasty;


public class PetNewAppointmentAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private  String TAG = "PetNewAppointmentAdapter";
    private List<PetAppointmentResponse.DataBean> newAppointmentResponseList;
    private Context context;

    PetAppointmentResponse.DataBean currentItem;

    private OnAppointmentCancel onAppointmentCancel;

    private int size;
    private String communicationtype;

    public PetNewAppointmentAdapter(Context context, List<PetAppointmentResponse.DataBean> newAppointmentResponseList, RecyclerView inbox_list,int size,OnAppointmentCancel onAppointmentCancel) {
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

        Log.w(TAG,"Pet name-->"+newAppointmentResponseList.get(position).getPet_name());

        currentItem = newAppointmentResponseList.get(position);
        communicationtype = newAppointmentResponseList.get(position).getCommunication_type();
       Log.w(TAG,"Communicationtype : "+ newAppointmentResponseList.get(position).getCommunication_type());
        holder.txt_petname.setText(newAppointmentResponseList.get(position).getPet_name());

        if(newAppointmentResponseList.get(position).getAppointment_for() != null && newAppointmentResponseList.get(position).getAppointment_for().equalsIgnoreCase("Doctor") ){
            holder.txt_type.setText(newAppointmentResponseList.get(position).getAppointment_for());
            holder.txt_clinicname.setText(newAppointmentResponseList.get(position).getClinic_name());


        }
        else if(newAppointmentResponseList.get(position).getAppointment_for() != null && newAppointmentResponseList.get(position).getAppointment_for().equalsIgnoreCase("SP") ){
            holder.txt_type.setText(newAppointmentResponseList.get(position).getAppointment_for());
            holder.txt_clinicname.setText(newAppointmentResponseList.get(position).getService_provider_name());

        }

            if(newAppointmentResponseList.get(position).getCost() != null){
            holder.txt_service_cost.setText("\u20B9 "+newAppointmentResponseList.get(position).getCost());
            }

        if(newAppointmentResponseList.get(position).getBooked_at() != null){
            holder.txt_bookedon.setText("Booked for :"+" "+newAppointmentResponseList.get(position).getBooked_at());

        }

        if(communicationtype != null){
             if(communicationtype.equalsIgnoreCase("Online")){
                 holder.img_videocall.setVisibility(View.VISIBLE);
             }else{
                 holder.img_videocall.setVisibility(View.GONE);
             }
        }


        if(newAppointmentResponseList.get(position).getAppointment_type() != null && newAppointmentResponseList.get(position).getAppointment_type().equalsIgnoreCase("Emergency")){
        holder.img_emergency_appointment.setVisibility(View.VISIBLE);
        }else{
            holder.img_emergency_appointment.setVisibility(View.GONE);

        }

        if (newAppointmentResponseList.get(position).getPhoto() != null && !newAppointmentResponseList.get(position).getPhoto().isEmpty()) {

                Glide.with(context)
                        .load(newAppointmentResponseList.get(position).getPhoto())
                        .into(holder.img_clinic_imge);

            }
           else{
                Glide.with(context)
                        .load(R.drawable.image_thumbnail)
                        .into(holder.img_clinic_imge);

            }

        holder.btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAppointmentCancel.onAppointmentCancel(newAppointmentResponseList.get(position).get_id(),newAppointmentResponseList.get(position).getAppointment_for());

            }
        });

        holder.img_videocall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.w(TAG,"Start_appointment_status : "+newAppointmentResponseList.get(position).getStart_appointment_status());
                if(newAppointmentResponseList.get(position).getStart_appointment_status() != null && newAppointmentResponseList.get(position).getStart_appointment_status().equalsIgnoreCase("Not Started")){
                    Toasty.warning(context,"Doctor is yet to start the Appointment. Please wait for the doctor to initiate the Appointment", Toast.LENGTH_SHORT, true).show();
                }else {
                    Intent i = new Intent(context, VideoCallPetLoverActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.putExtra("id", newAppointmentResponseList.get(position).get_id());
                    Log.w(TAG, "ID-->" + newAppointmentResponseList.get(position).get_id());
                    context.startActivity(i);
                }


            }
        });

        if(!newAppointmentResponseList.get(position).getClinic_name().equals("")){

            holder.ll_new.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context, PetNewAppointmentDetailsActivity.class);

                    //Create the bundle
                    Bundle bundle = new Bundle();

                    Log.w("appointment_id",newAppointmentResponseList.get(position).get_id());

                    //Add your data from getFactualResults method to bundle
                    bundle.putString("appointment_id",newAppointmentResponseList.get(position).get_id());

                    //Add the bundle to the intent
                    intent.putExtras(bundle);

                    context.startActivity(intent);
                }
            });


        }


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
        public ImageView img_clinic_imge,img_emergency_appointment,img_videocall;
        public Button btn_cancel;
        LinearLayout ll_new;


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
            img_videocall = itemView.findViewById(R.id.img_videocall);
            ll_new = itemView.findViewById(R.id.ll_new);


        }




    }








}
