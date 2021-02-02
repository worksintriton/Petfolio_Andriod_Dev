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

import com.petfolio.infinitus.R;

import com.petfolio.infinitus.doctor.DoctorPrescriptionDetailsActivity;
import com.petfolio.infinitus.interfaces.AddReviewListener;
import com.petfolio.infinitus.petlover.PetCompletedAppointmentDetailsActivity;
import com.petfolio.infinitus.responsepojo.PetAppointmentResponse;
import com.petfolio.infinitus.responsepojo.PetNewAppointmentResponse;

import java.util.List;


public class PetCompletedAppointmentAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private  String TAG = "PetCompletedAppointmentAdapter";
    private List<PetAppointmentResponse.DataBean> completedAppointmentResponseList;
    private Context context;

    int size;
    private AddReviewListener addReviewListener;


    public PetCompletedAppointmentAdapter(Context context, List<PetAppointmentResponse.DataBean> completedAppointmentResponseList, RecyclerView inbox_list, int size,AddReviewListener addReviewListener) {
        this.completedAppointmentResponseList = completedAppointmentResponseList;
        this.context = context;
        this.size = size;
        this.addReviewListener = addReviewListener;


    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_petcompleted_appointment, parent, false);
        return new ViewHolderOne(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        initLayoutOne((ViewHolderOne) holder, position);


    }

    @SuppressLint("SetTextI18n")
    private void initLayoutOne(ViewHolderOne holder, final int position) {
        Log.w(TAG,"Pet name-->"+completedAppointmentResponseList.get(position).getPet_name());
        holder.txt_pettype.setText(completedAppointmentResponseList.get(position).getPet_name());
        holder.txt_completed_date.setText("Completed on:"+" "+completedAppointmentResponseList.get(position).getCompleted_at());


        if(completedAppointmentResponseList.get(position).getAppointment_for() != null && completedAppointmentResponseList.get(position).getAppointment_for().equalsIgnoreCase("Doctor") ){
            holder.txt_type.setText(completedAppointmentResponseList.get(position).getAppointment_for());
            holder.txt_petname.setText(completedAppointmentResponseList.get(position).getClinic_name());


        }else if(completedAppointmentResponseList.get(position).getAppointment_for() != null && completedAppointmentResponseList.get(position).getAppointment_for().equalsIgnoreCase("SP") ){
            holder.txt_type.setText(completedAppointmentResponseList.get(position).getAppointment_for());
            holder.txt_petname.setText(completedAppointmentResponseList.get(position).getService_provider_name());

        }
        if(completedAppointmentResponseList.get(position).getCost() != null){
            holder.txt_service_cost.setText("\u20B9 "+completedAppointmentResponseList.get(position).getCost());
        }


        Log.w(TAG,"userrate: "+completedAppointmentResponseList.get(position).getUser_rate());

        if(completedAppointmentResponseList.get(position).getUser_rate() != null && completedAppointmentResponseList.get(position).getUser_rate().equalsIgnoreCase("0")){
            holder.btn_add_review.setVisibility(View.VISIBLE);
        }else{
            holder.btn_add_review.setVisibility(View.GONE);

        }

        holder.btn_add_review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addReviewListener.addReviewListener(completedAppointmentResponseList.get(position).get_id(),completedAppointmentResponseList.get(position).getUser_rate(),completedAppointmentResponseList.get(position).getUser_feedback());

            }
        });

        holder.btn__prescriptiondetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(completedAppointmentResponseList.get(position).get_id() != null) {
                    Intent i = new Intent(context, DoctorPrescriptionDetailsActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.putExtra("id", completedAppointmentResponseList.get(position).get_id());
                    context.startActivity(i);
                }

            }
        });

        if(!completedAppointmentResponseList.get(position).getClinic_name().equals("")){

            holder.ll_new.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context, PetCompletedAppointmentDetailsActivity.class);

                    //Create the bundle
                    Bundle bundle = new Bundle();

                    Log.w("appointment_id",completedAppointmentResponseList.get(position).get_id());

                    //Add your data from getFactualResults method to bundle
                    bundle.putString("appointment_id",completedAppointmentResponseList.get(position).get_id());

                    //Add the bundle to the intent
                    intent.putExtras(bundle);

                    context.startActivity(intent);
                }
            });


        }





        /*if (currentItem.getc() != null && !currentItem.getPic().isEmpty()) {

                Glide.with(context)
                        .load(currentItem.getPic())
                        .into(holder.cv_doctor_pic);

            }
           else{
                Glide.with(context)
                        .load(R.drawable.ic_drawer_delivery)
                        .into(holder.cv_doctor_pic);

            }*/















        }

    @Override
    public int getItemCount() {
        return Math.min(completedAppointmentResponseList.size(), size);

    }


    @Override
    public int getItemViewType(int position) {
        return position;
    }

    static class ViewHolderOne extends RecyclerView.ViewHolder {
        public TextView txt_petname,txt_pettype,txt_type,txt_completed_date,txt_service_cost;
        public ImageView img_pet_imge,img_prescriptiondetails;
        public Button btn_cancel,btn_complete,btn_add_review,btn__prescriptiondetails;
        LinearLayout ll_new;


        public ViewHolderOne(View itemView) {
            super(itemView);
            img_pet_imge = itemView.findViewById(R.id.img_pet_imge);
            txt_petname = itemView.findViewById(R.id.txt_petname);
            txt_pettype = itemView.findViewById(R.id.txt_pettype);
            txt_type = itemView.findViewById(R.id.txt_type);
            txt_completed_date = itemView.findViewById(R.id.txt_completed_date);
            btn_cancel = itemView.findViewById(R.id.btn_cancel);
            btn_complete = itemView.findViewById(R.id.btn_complete);
            txt_service_cost = itemView.findViewById(R.id.txt_service_cost);
            btn_add_review = itemView.findViewById(R.id.btn_add_review);
            //img_prescriptiondetails = itemView.findViewById(R.id.img_prescriptiondetails);
            ll_new = itemView.findViewById(R.id.ll_new);
            btn__prescriptiondetails = itemView.findViewById(R.id.btn_prescriptiondetails);



        }




    }








}
