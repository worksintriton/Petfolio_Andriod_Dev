package com.petfolio.infinitus.adapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.petfolio.infinitus.R;
import com.petfolio.infinitus.responsepojo.DoctorNewAppointmentResponse;

import java.util.List;


public class DoctorNewAppointmentAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private  String TAG = "DoctorNewAppointmentAdapter";
    private final List<DoctorNewAppointmentResponse.DataBean> newAppointmentResponseList;
    private Context context;

    DoctorNewAppointmentResponse.DataBean currentItem;


    public DoctorNewAppointmentAdapter(Context context, List<DoctorNewAppointmentResponse.DataBean> newAppointmentResponseList, RecyclerView inbox_list) {
        this.newAppointmentResponseList = newAppointmentResponseList;
        this.context = context;


    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_new_appointment, parent, false);
        return new ViewHolderOne(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        initLayoutOne((ViewHolderOne) holder, position);


    }

    @SuppressLint("SetTextI18n")
    private void initLayoutOne(ViewHolderOne holder, final int position) {

        Log.w(TAG,"Pet name-->"+newAppointmentResponseList.get(0).getPet_id().getPet_name());

        currentItem = newAppointmentResponseList.get(position);
        holder.txt_petname.setText("Pet name : "+newAppointmentResponseList.get(0).getPet_id().getPet_name());
        holder.txt_pettype.setText("Pet type : "+ newAppointmentResponseList.get(0).getPet_id().getPet_type());
        holder.txt_service_info.setText("Service name :"+" "+newAppointmentResponseList.get(0).getService_name()+" Service Cost : "+newAppointmentResponseList.get(0).getService_amount());

           if (newAppointmentResponseList.get(0).getPet_id().getPet_img() != null && !newAppointmentResponseList.get(0).getPet_id().getPet_img().isEmpty()) {

                Glide.with(context)
                        .load(newAppointmentResponseList.get(0).getPet_id().getPet_img())
                        .into(holder.img_pet_imge);

            }
           else{
                Glide.with(context)
                        .load(R.drawable.image_thumbnail)
                        .into(holder.img_pet_imge);

            }

        }
       /* holder.btn_pastappointment_details_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, DoctorPastAppointmentDetailsActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                i.putExtra("Patientname",pastAppointmentResponseList.get(position).getPatient_Name());
                i.putExtra("Bookingdate",pastAppointmentResponseList.get(position).getBooking_Date());
                i.putExtra("Bookingtime",pastAppointmentResponseList.get(position).getBooking_Time());
                i.putExtra("Doctorname",pastAppointmentResponseList.get(position).getDoctor_Name());
                i.putExtra("Ailmentdetails",pastAppointmentResponseList.get(position).getProblem_info());
                i.putExtra("Id",pastAppointmentResponseList.get(position).get_id());
                i.putExtra("Doctorid",pastAppointmentResponseList.get(position).getDoctor_ID());
                i.putExtra("Doctorimage",pastAppointmentResponseList.get(position).getDoctor_Image());
                i.putExtra("Doctoremailid",pastAppointmentResponseList.get(position).getDoctor_EmailId());
                i.putExtra("Patientemailid",pastAppointmentResponseList.get(position).getPatient_EmailId());
                i.putExtra("Patientid",pastAppointmentResponseList.get(position).getPatient_ID());
                i.putExtra("Bookingfor",pastAppointmentResponseList.get(position).getBooking_for());
                i.putExtra("Familyid",pastAppointmentResponseList.get(position).getFamily_ID());
                i.putExtra("Familyname",pastAppointmentResponseList.get(position).getFamily_Name());
                i.putExtra("video",pastAppointmentResponseList.get(position).getCommunication_Video());
                i.putExtra("chat",pastAppointmentResponseList.get(position).getCommunication_Chat());
                i.putExtra("patientnoshow",pastAppointmentResponseList.get(position).getAppointment_patient_St());
                i.putExtra("Documentsattached",pastAppointmentResponseList.get(position).getDoc_attached().toString());

                *//* pastmedications,patientage,patientheight,patientweight,patientgender*//*
                i.putExtra("pastmedications",pastAppointmentResponseList.get(position).getPassed_Medications());
                i.putExtra("patientage",String.valueOf(pastAppointmentResponseList.get(position).getPatiend_details().getAge()));
                i.putExtra("patientheight",pastAppointmentResponseList.get(position).getPatiend_details().getHeight());
                i.putExtra("patientweight",pastAppointmentResponseList.get(position).getPatiend_details().getWeight());
                i.putExtra("patientgender",pastAppointmentResponseList.get(position).getPatiend_details().getGender());



                Log.w(TAG,"ImageList--->"+pastAppointmentResponseList.get(position).getDoc_attached().toString());



                //  i.putExtra("Documentsattached",pastAppointmentResponseList.get(position).getDoc_attached().get(position));
                context.startActivity(i);

            }
        });*/

        /*if(pastAppointmentResponseList.get(position).getCommunication_Chat().equalsIgnoreCase("True")){
            holder.ivmessgaechat.setVisibility(View.VISIBLE);
        }else{
            holder.ivmessgaechat.setVisibility(View.GONE);
        }
*/














    @Override
    public int getItemCount() {
        return newAppointmentResponseList.size();
    }


    @Override
    public int getItemViewType(int position) {
        return position;
    }

    static class ViewHolderOne extends RecyclerView.ViewHolder {
        public TextView txt_petname,txt_pettype,txt_service_info;
        public ImageView img_pet_imge;
        public Button btn_cancel,btn_complete;



        public ViewHolderOne(View itemView) {
            super(itemView);
            img_pet_imge = itemView.findViewById(R.id.img_pet_imge);
            txt_petname = itemView.findViewById(R.id.txt_petname);
            txt_pettype = itemView.findViewById(R.id.txt_pettype);
            txt_service_info = itemView.findViewById(R.id.txt_service_info);
            btn_cancel = itemView.findViewById(R.id.btn_cancel);
            btn_complete = itemView.findViewById(R.id.btn_complete);



        }




    }








}
