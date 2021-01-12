package com.petfolio.infinitus.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
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
import com.petfolio.infinitus.responsepojo.SPAppointmentResponse;

import java.util.List;


public class SPCompletedAppointmentAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private  String TAG = "SPCompletedAppointmentAdapter";
    private List<SPAppointmentResponse.DataBean> completedAppointmentResponseList;
    private Context context;
    private int size;



    public SPCompletedAppointmentAdapter(Context context, List<SPAppointmentResponse.DataBean> completedAppointmentResponseList, RecyclerView inbox_list, int size) {
        this.completedAppointmentResponseList = completedAppointmentResponseList;
        this.context = context;
        this.size = size;


    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_doctor_completed_appointment, parent, false);
        return new ViewHolderOne(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        initLayoutOne((ViewHolderOne) holder, position);


    }

    @SuppressLint("SetTextI18n")
    private void initLayoutOne(ViewHolderOne holder, final int position) {


        holder.txt_petname.setText(completedAppointmentResponseList.get(position).getPet_id().getPet_name());
        holder.txt_pettype.setText(completedAppointmentResponseList.get(position).getPet_id().getPet_type());
        holder.txt_completed_date.setText("Completed on:"+" "+completedAppointmentResponseList.get(position).getCompleted_at());

        holder.txt_lbl_type.setText("Service Name");
        if(completedAppointmentResponseList.get(position).getService_name() != null){
            holder.txt_type.setText(completedAppointmentResponseList.get(position).getService_name());
        }
        if(completedAppointmentResponseList.get(position).getService_amount() != null){
            holder.txt_service_cost.setText("\u20B9 "+completedAppointmentResponseList.get(position).getService_amount());
        }

        if (completedAppointmentResponseList.get(position).getPet_id().getPet_img() != null && !completedAppointmentResponseList.get(0).getPet_id().getPet_img().isEmpty()) {

            Glide.with(context)
                    .load(completedAppointmentResponseList.get(position).getPet_id().getPet_img())
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
        return Math.min(completedAppointmentResponseList.size(), size);
    }


    @Override
    public int getItemViewType(int position) {
        return position;
    }

    static class ViewHolderOne extends RecyclerView.ViewHolder {
        public TextView txt_petname,txt_pettype,txt_type,txt_service_cost,txt_completed_date,txt_lbl_type;
        public ImageView img_pet_imge;
        public Button btn_cancel,btn_complete;

        public ViewHolderOne(View itemView) {
            super(itemView);
            img_pet_imge = itemView.findViewById(R.id.img_pet_imge);
            txt_petname = itemView.findViewById(R.id.txt_petname);
            txt_pettype = itemView.findViewById(R.id.txt_pettype);
            txt_lbl_type = itemView.findViewById(R.id.txt_lbl_type);
            txt_type = itemView.findViewById(R.id.txt_type);
            txt_service_cost = itemView.findViewById(R.id.txt_service_cost);
            txt_completed_date = itemView.findViewById(R.id.txt_completed_date);
            btn_cancel = itemView.findViewById(R.id.btn_cancel);
            btn_complete = itemView.findViewById(R.id.btn_complete);



        }




    }








}
