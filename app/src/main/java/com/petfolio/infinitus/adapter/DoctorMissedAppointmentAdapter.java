package com.petfolio.infinitus.adapter;

import android.annotation.SuppressLint;
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
import com.petfolio.infinitus.responsepojo.DoctorMissedAppointmentResponse;

import java.util.List;


public class DoctorMissedAppointmentAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private  String TAG = "DoctorNewAppointmentAdapter";
    private List<DoctorMissedAppointmentResponse.DataBean> missedAppointmentResponseList;
    private Context context;
    private int size;

    DoctorMissedAppointmentResponse.DataBean currentItem;


    public DoctorMissedAppointmentAdapter(Context context, List<DoctorMissedAppointmentResponse.DataBean> missedAppointmentResponseList, RecyclerView inbox_list,int size) {
        this.missedAppointmentResponseList = missedAppointmentResponseList;
        this.context = context;
        this.size = size;


    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_doctor_missed_appointment, parent, false);
        return new ViewHolderOne(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        initLayoutOne((ViewHolderOne) holder, position);


    }

    @SuppressLint("SetTextI18n")
    private void initLayoutOne(ViewHolderOne holder, final int position) {

        Log.w(TAG,"Pet name-->"+missedAppointmentResponseList.get(position).getPet_id().getPet_name());

        currentItem = missedAppointmentResponseList.get(position);
        holder.txt_petname.setText(missedAppointmentResponseList.get(position).getPet_id().getPet_name());
        holder.txt_pettype.setText(missedAppointmentResponseList.get(position).getPet_id().getPet_type());
        holder.txt_missed_date.setText("Missed on:"+" "+missedAppointmentResponseList.get(position).getMissed_at());

        if(missedAppointmentResponseList.get(position).getAppointment_types() != null){
            holder.txt_type.setText(missedAppointmentResponseList.get(position).getAppointment_types());
        }
        if(missedAppointmentResponseList.get(position).getService_amount() != null){
            holder.txt_service_cost.setText(missedAppointmentResponseList.get(position).getService_amount());
        }
        if (missedAppointmentResponseList.get(position).getPet_id().getPet_img() != null && !missedAppointmentResponseList.get(position).getPet_id().getPet_img().isEmpty()) {

            Glide.with(context)
                    .load(missedAppointmentResponseList.get(position).getPet_id().getPet_img())
                    .into(holder.img_pet_imge);

        }
        else{
            Glide.with(context)
                    .load(R.drawable.image_thumbnail)
                    .into(holder.img_pet_imge);

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
        public TextView txt_petname,txt_pettype,txt_type,txt_service_cost,txt_missed_date;
        public ImageView img_pet_imge;
        public Button btn_cancel,btn_complete;



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



        }




    }








}
