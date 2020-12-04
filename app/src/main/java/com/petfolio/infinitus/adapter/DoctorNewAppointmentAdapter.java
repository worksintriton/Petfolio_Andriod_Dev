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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.petfolio.infinitus.R;
import com.petfolio.infinitus.doctor.PrescriptionActivity;
import com.petfolio.infinitus.interfaces.OnAppointmentCancel;
import com.petfolio.infinitus.interfaces.OnItemDeleteHoliday;
import com.petfolio.infinitus.responsepojo.DoctorNewAppointmentResponse;

import java.util.List;


public class DoctorNewAppointmentAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private  String TAG = "DoctorNewAppointmentAdapter";
    private final List<DoctorNewAppointmentResponse.DataBean> newAppointmentResponseList;
    private Context context;

    DoctorNewAppointmentResponse.DataBean currentItem;

    private OnAppointmentCancel onAppointmentCancel;



    public DoctorNewAppointmentAdapter(Context context, List<DoctorNewAppointmentResponse.DataBean> newAppointmentResponseList, RecyclerView inbox_list,OnAppointmentCancel onAppointmentCancel) {
        this.newAppointmentResponseList = newAppointmentResponseList;
        this.context = context;
        this.onAppointmentCancel = onAppointmentCancel;



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

        Log.w(TAG,"Pet name-->"+newAppointmentResponseList.get(0).getPet_id().getPet_name());

        currentItem = newAppointmentResponseList.get(position);
        holder.txt_petname.setText("Pet name : "+newAppointmentResponseList.get(0).getPet_id().getPet_name());
        holder.txt_pettype.setText("Pet type : "+ newAppointmentResponseList.get(0).getPet_id().getPet_type());
        if(newAppointmentResponseList.get(0).getService_name() != null){
            holder.txt_service_info.setText("Service name :"+" "+newAppointmentResponseList.get(0).getService_name());
        }
        if(newAppointmentResponseList.get(0).getService_amount() != null){
            holder.txt_service_cost.setText(" Service Cost : "+newAppointmentResponseList.get(0).getService_amount());
        }

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



        holder.btn_complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { Intent i = new Intent(context, PrescriptionActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("petname",newAppointmentResponseList.get(position).getPet_id().getPet_name());
                i.putExtra("pettype",newAppointmentResponseList.get(position).getPet_id().getPet_type());
                i.putExtra("id",newAppointmentResponseList.get(position).get_id());
                i.putExtra("userid",newAppointmentResponseList.get(position).getUser_id().get_id());
                i.putExtra("allergies",newAppointmentResponseList.get(position).getAllergies());
                i.putExtra("probleminfo",newAppointmentResponseList.get(position).getProblem_info());
                context.startActivity(i);

            }
        });

        holder.btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { Intent i = new Intent(context, PrescriptionActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                onAppointmentCancel.onAppointmentCancel(newAppointmentResponseList.get(position).get_id());

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
        return newAppointmentResponseList.size();
    }


    @Override
    public int getItemViewType(int position) {
        return position;
    }

    static class ViewHolderOne extends RecyclerView.ViewHolder {
        public TextView txt_petname,txt_pettype,txt_service_info,txt_service_cost;
        public ImageView img_pet_imge;
        public Button btn_cancel,btn_complete;



        public ViewHolderOne(View itemView) {
            super(itemView);
            img_pet_imge = itemView.findViewById(R.id.img_pet_imge);
            txt_petname = itemView.findViewById(R.id.txt_petname);
            txt_pettype = itemView.findViewById(R.id.txt_pettype);
            txt_service_info = itemView.findViewById(R.id.txt_service_info);
            txt_service_cost = itemView.findViewById(R.id.txt_service_cost);
            btn_cancel = itemView.findViewById(R.id.btn_cancel);
            btn_complete = itemView.findViewById(R.id.btn_complete);



        }




    }








}
