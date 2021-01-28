package com.petfolio.infinitus.adapter;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
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
import com.google.gson.Gson;
import com.petfolio.infinitus.R;
import com.petfolio.infinitus.api.APIClient;
import com.petfolio.infinitus.api.RestApiInterface;
import com.petfolio.infinitus.doctor.DoctorDashboardActivity;
import com.petfolio.infinitus.doctor.PrescriptionActivity;
import com.petfolio.infinitus.doctor.VideoCallDoctorActivity;
import com.petfolio.infinitus.interfaces.OnAppointmentCancel;
import com.petfolio.infinitus.interfaces.StartAppointmentListener;
import com.petfolio.infinitus.petlover.VideoCallPetLoverActivity;
import com.petfolio.infinitus.requestpojo.DoctorStartAppointmentRequest;
import com.petfolio.infinitus.requestpojo.PetNoShowRequest;
import com.petfolio.infinitus.responsepojo.AppointmentsUpdateResponse;
import com.petfolio.infinitus.responsepojo.DoctorNewAppointmentResponse;
import com.petfolio.infinitus.utils.RestUtils;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DoctorNewAppointmentAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private  String TAG = "DoctorNewAppointmentAdapter";
    private final List<DoctorNewAppointmentResponse.DataBean> newAppointmentResponseList;
    private Context context;

    DoctorNewAppointmentResponse.DataBean currentItem;

    private OnAppointmentCancel onAppointmentCancel;
    private StartAppointmentListener startAppointmentListener;
    private int size;
    private String communicationtype;
    AVLoadingIndicatorView avi_indicator;


    public DoctorNewAppointmentAdapter(Context context, List<DoctorNewAppointmentResponse.DataBean> newAppointmentResponseList, RecyclerView inbox_list,int size,OnAppointmentCancel onAppointmentCancel, AVLoadingIndicatorView avi_indicator) {
        this.newAppointmentResponseList = newAppointmentResponseList;
        this.context = context;
        this.size = size;
        this.onAppointmentCancel = onAppointmentCancel;
        this.avi_indicator = avi_indicator;



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
        communicationtype = newAppointmentResponseList.get(position).getCommunication_type();

        holder.txt_petname.setText(newAppointmentResponseList.get(position).getPet_id().getPet_name());
        holder.txt_pettype.setText(newAppointmentResponseList.get(position).getPet_id().getPet_type());
        if(newAppointmentResponseList.get(position).getAppointment_types() != null){
            holder.txt_type.setText(newAppointmentResponseList.get(position).getAppointment_types());
        }
        if(newAppointmentResponseList.get(position).getAmount() != null){
            holder.txt_service_cost.setText("\u20B9 "+newAppointmentResponseList.get(position).getAmount());
        }

        if(newAppointmentResponseList.get(position).getBooking_date_time() != null){
            holder.txt_bookedon.setText("Booked for :"+" "+newAppointmentResponseList.get(position).getBooking_date_time());

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

        if(communicationtype != null){
            if(communicationtype.equalsIgnoreCase("Online")){
                holder.img_videocall.setVisibility(View.VISIBLE);
            }else{
                holder.img_videocall.setVisibility(View.GONE);
            }
        }




        holder.btn_complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, PrescriptionActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("petname",newAppointmentResponseList.get(position).getPet_id().getPet_name());
                i.putExtra("pettype",newAppointmentResponseList.get(position).getPet_id().getPet_type());
                i.putExtra("id",newAppointmentResponseList.get(position).get_id());
                i.putExtra("userid",newAppointmentResponseList.get(position).getUser_id().get_id());
                i.putExtra("allergies",newAppointmentResponseList.get(position).getAllergies());
                i.putExtra("probleminfo",newAppointmentResponseList.get(position).getProblem_info());
                i.putExtra("doctorid",newAppointmentResponseList.get(position).getDoctor_id().get_id());

                context.startActivity(i);

            }
        });

        holder.btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAppointmentCancel.onAppointmentCancel(newAppointmentResponseList.get(position).get_id(),newAppointmentResponseList.get(position).getAppointment_types());

            }
        });

        holder.img_videocall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.w(TAG,"Start_appointment_status : "+newAppointmentResponseList.get(position).getStart_appointment_status());
                if(newAppointmentResponseList.get(position).getStart_appointment_status() != null && newAppointmentResponseList.get(position).getStart_appointment_status().equalsIgnoreCase("Not Started")){
                    doctorStartAppointmentResponseCall(newAppointmentResponseList.get(position).get_id(),position);
                }else {
                    Intent i = new Intent(context, VideoCallDoctorActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.putExtra("id", newAppointmentResponseList.get(position).get_id());
                    i.putExtra("petname", newAppointmentResponseList.get(position).getPet_id().getPet_name());
                    i.putExtra("pettype", newAppointmentResponseList.get(position).getPet_id().getPet_type());
                    i.putExtra("userid", newAppointmentResponseList.get(position).getUser_id().get_id());
                    i.putExtra("allergies", newAppointmentResponseList.get(position).getAllergies());
                    i.putExtra("probleminfo", newAppointmentResponseList.get(position).getProblem_info());
                    Log.w(TAG, "ID-->" + newAppointmentResponseList.get(position).get_id());
                    context.startActivity(i);
                }


            }
        });




    }

        /*if(pastAppointmentResponseList.get(position).getCommunication_Chat().equalsIgnoreCase("True")){
            holder.ivmessgaechat.setVisibility(View.VISIBLE);
        }else{
            holder.ivmessgaechat.setVisibility(View.GONE);
        }
*/
    @Override
    public int getItemCount() {
        return Math.min(newAppointmentResponseList.size(), size);

    }
    @Override
    public int getItemViewType(int position) {
        return position;
    }
    static class ViewHolderOne extends RecyclerView.ViewHolder {
        public TextView txt_petname,txt_pettype,txt_type,txt_service_cost,txt_bookedon;
        public ImageView img_pet_imge,img_emergency_appointment,img_videocall;
        public Button btn_cancel,btn_complete;
        public LinearLayout ll_new;



        public ViewHolderOne(View itemView) {
            super(itemView);
            img_pet_imge = itemView.findViewById(R.id.img_pet_imge);
            txt_petname = itemView.findViewById(R.id.txt_petname);
            txt_pettype = itemView.findViewById(R.id.txt_pettype);
            txt_type = itemView.findViewById(R.id.txt_type);
            txt_service_cost = itemView.findViewById(R.id.txt_service_cost);
            txt_bookedon = itemView.findViewById(R.id.txt_bookedon);
            btn_cancel = itemView.findViewById(R.id.btn_cancel);
            btn_complete = itemView.findViewById(R.id.btn_complete);
            ll_new = itemView.findViewById(R.id.ll_new);
            img_emergency_appointment = itemView.findViewById(R.id.img_emergency_appointment);
            img_emergency_appointment.setVisibility(View.GONE);
            img_videocall = itemView.findViewById(R.id.img_videocall);




        }




    }

    private void doctorStartAppointmentResponseCall(String id, int position) {
        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        RestApiInterface apiInterface = APIClient.getClient().create(RestApiInterface.class);
        Call<AppointmentsUpdateResponse> call = apiInterface.doctorStartAppointmentResponseCall(RestUtils.getContentType(), doctorStartAppointmentRequest(id));
        Log.w(TAG,"startAppointmentResponseCall url  :%s"+" "+ call.request().url().toString());

        call.enqueue(new Callback<AppointmentsUpdateResponse>() {
            @Override
            public void onResponse(@NonNull Call<AppointmentsUpdateResponse> call, @NonNull Response<AppointmentsUpdateResponse> response) {

                Log.w(TAG,"startAppointmentResponseCall"+ "--->" + new Gson().toJson(response.body()));

                avi_indicator.smoothToHide();

                if (response.body() != null) {
                    if(response.body().getCode() == 200){
                        Intent i = new Intent(context, VideoCallDoctorActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        i.putExtra("id", newAppointmentResponseList.get(position).get_id());
                        i.putExtra("petname", newAppointmentResponseList.get(position).getPet_id().getPet_name());
                        i.putExtra("pettype", newAppointmentResponseList.get(position).getPet_id().getPet_type());
                        i.putExtra("userid", newAppointmentResponseList.get(position).getUser_id().get_id());
                        i.putExtra("allergies", newAppointmentResponseList.get(position).getAllergies());
                        i.putExtra("probleminfo", newAppointmentResponseList.get(position).getProblem_info());
                        Log.w(TAG, "ID-->" + newAppointmentResponseList.get(position).get_id());
                        context.startActivity(i);

                    }

                }


            }

            @Override
            public void onFailure(@NonNull Call<AppointmentsUpdateResponse> call, @NonNull Throwable t) {

                avi_indicator.smoothToHide();
                Log.w(TAG,"startAppointmentResponseCall flr"+"--->" + t.getMessage());
            }
        });

    }
    private DoctorStartAppointmentRequest doctorStartAppointmentRequest(String id) {
        /*
         * _id : 5fc639ea72fc42044bfa1683
         * appoinment_status : In-Progress
         */
        DoctorStartAppointmentRequest doctorStartAppointmentRequest = new DoctorStartAppointmentRequest();
        doctorStartAppointmentRequest.set_id(id);
        doctorStartAppointmentRequest.setStart_appointment_status("In-Progress");
        Log.w(TAG,"doctorStartAppointmentRequest"+ "--->" + new Gson().toJson(doctorStartAppointmentRequest));
        return doctorStartAppointmentRequest;
    }
}
