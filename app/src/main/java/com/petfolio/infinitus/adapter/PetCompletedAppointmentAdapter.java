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

import com.petfolio.infinitus.R;

import com.petfolio.infinitus.responsepojo.PetNewAppointmentResponse;

import java.util.List;


public class PetCompletedAppointmentAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private  String TAG = "PetCompletedAppointmentAdapter";
    private List<PetNewAppointmentResponse.DataBean> completedAppointmentResponseList;
    private Context context;

    PetNewAppointmentResponse.DataBean currentItem;
    int size;


    public PetCompletedAppointmentAdapter(Context context, List<PetNewAppointmentResponse.DataBean> completedAppointmentResponseList, RecyclerView inbox_list, int size) {
        this.completedAppointmentResponseList = completedAppointmentResponseList;
        this.context = context;
        this.size = size;


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

        Log.w(TAG,"Pet name-->"+completedAppointmentResponseList.get(0).getPet_id().getPet_name());

        holder.txt_petname.setText("Pet name : "+completedAppointmentResponseList.get(0).getPet_id().getPet_name());
        holder.txt_pettype.setText("Pet type : "+ completedAppointmentResponseList.get(0).getPet_id().getPet_type());
        holder.txt_service_info.setText("Service name :"+" "+completedAppointmentResponseList.get(0).getService_name()+" Service Cost : "+completedAppointmentResponseList.get(0).getService_amount());
        holder.txt_completed_date.setText("Completed on:"+" "+completedAppointmentResponseList.get(0).getCompleted_at());







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
        public TextView txt_petname,txt_pettype,txt_service_info,txt_completed_date;
        public ImageView img_pet_imge;
        public Button btn_cancel,btn_complete;



        public ViewHolderOne(View itemView) {
            super(itemView);
            img_pet_imge = itemView.findViewById(R.id.img_pet_imge);
            txt_petname = itemView.findViewById(R.id.txt_petname);
            txt_pettype = itemView.findViewById(R.id.txt_pettype);
            txt_service_info = itemView.findViewById(R.id.txt_service_info);
            txt_completed_date = itemView.findViewById(R.id.txt_completed_date);
            btn_cancel = itemView.findViewById(R.id.btn_cancel);
            btn_complete = itemView.findViewById(R.id.btn_complete);



        }




    }








}
