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
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.petfolio.infinitus.R;
import com.petfolio.infinitus.petlover.DoctorClinicDetailsActivity;
import com.petfolio.infinitus.petlover.PetAppointment_Doctor_Date_Time_Activity;
import com.petfolio.infinitus.responsepojo.DoctorSearchResponse;

import java.util.List;


public class PetServicesAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private  String TAG = "PetServicesAdapter";

    private Context context;







    private List<DoctorSearchResponse.DataBean> doctorDetailsResponseList;
    DoctorSearchResponse.DataBean currentItem;







    public PetServicesAdapter(Context context, List<DoctorSearchResponse.DataBean> doctorDetailsResponseList) {
        this.doctorDetailsResponseList = doctorDetailsResponseList;
        this.context = context;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_petservices, parent, false);
        return new ViewHolderOne(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        initLayoutOne((ViewHolderOne) holder, position);

    }

    @SuppressLint("SetTextI18n")
    private void initLayoutOne(ViewHolderOne holder, final int position) {

          currentItem = doctorDetailsResponseList.get(position);
          if (currentItem.getDoctor_img() != null && !currentItem.getDoctor_img().isEmpty()) {
              Glide.with(context)
                    .load(currentItem.getDoctor_img())
                    //.load(R.drawable.logo)
                    .into(holder.img_petservice);

        }
          else{
            Glide.with(context)
                    .load(R.drawable.services)
                    .into(holder.img_petservice);

        }

        holder.img_petservice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*Intent intent = new Intent(context, DoctorClinicDetailsActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("doctorid",doctorDetailsResponseList.get(position).getUser_id());
                context.startActivity(intent);*/
                }




        });



    }



    @Override
    public int getItemCount() {
        return doctorDetailsResponseList.size();



    }


    @Override
    public int getItemViewType(int position) {
        return position;
    }

    class ViewHolderOne extends RecyclerView.ViewHolder {

        public ImageView img_petservice;

        public ViewHolderOne(View itemView) {
            super(itemView);
            img_petservice = itemView.findViewById(R.id.img_petservice);




        }

    }
}
