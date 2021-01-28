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
import com.petfolio.infinitus.petlover.BookAppointmentActivity;
import com.petfolio.infinitus.petlover.DoctorClinicDetailsActivity;
import com.petfolio.infinitus.petlover.PetAppointment_Doctor_Date_Time_Activity;
import com.petfolio.infinitus.responsepojo.DoctorSearchResponse;

import java.util.List;


public class PetLoverNearByDoctorAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private  String TAG = "PetLoverNearByDoctorAdapter";

    private Context context;







    private List<DoctorSearchResponse.DataBean> doctorDetailsResponseList;
    DoctorSearchResponse.DataBean currentItem;







    public PetLoverNearByDoctorAdapter(Context context, List<DoctorSearchResponse.DataBean> doctorDetailsResponseList) {
        this.doctorDetailsResponseList = doctorDetailsResponseList;
        this.context = context;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_nearby_doctors, parent, false);
        return new ViewHolderOne(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        initLayoutOne((ViewHolderOne) holder, position);

    }

    @SuppressLint("SetTextI18n")
    private void initLayoutOne(ViewHolderOne holder, final int position) {

          currentItem = doctorDetailsResponseList.get(position);
          holder.txt_doctors_name.setText(currentItem.getDoctor_name());
          holder.txt_place.setText(currentItem.getClinic_loc());
          holder.txt_km.setText(currentItem.getDistance()+"km away");
          List<DoctorSearchResponse.DataBean.SpecializationBean> specializationBeanList = currentItem.getSpecialization();

          for(int i=0;i<specializationBeanList.size();i++){
              holder.txt_doctors_specialization.setText(specializationBeanList.get(i).getSpecialization());

          }
          holder.txt_star_rating.setText( doctorDetailsResponseList.get(position).getStar_count()+"");
          holder.txt_review_count.setText( doctorDetailsResponseList.get(position).getReview_count()+"");
          if (currentItem.getDoctor_img() != null && !currentItem.getDoctor_img().isEmpty()) {

            Glide.with(context)
                    .load(currentItem.getDoctor_img())
                    //.load(R.drawable.logo)
                    .into(holder.img_doctors_image);

        }
          else{
            Glide.with(context)
                    .load(R.drawable.services)
                    .into(holder.img_doctors_image);

        }

        holder.ll_root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, DoctorClinicDetailsActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("doctorid",doctorDetailsResponseList.get(position).getUser_id());
                intent.putExtra("doctorname",doctorDetailsResponseList.get(position).getDoctor_name());
                intent.putExtra("reviewcount",doctorDetailsResponseList.get(position).getReview_count());
                intent.putExtra("starcount",doctorDetailsResponseList.get(position).getStar_count());
                intent.putExtra("distance",doctorDetailsResponseList.get(position).getDistance());
                intent.putExtra("fromactivity", "PetCareFragment");
                Log.w(TAG,"doctorid :"+doctorDetailsResponseList.get(position).getUser_id());
                context.startActivity(intent);
                }




        });
        holder.btn_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, PetAppointment_Doctor_Date_Time_Activity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("doctorid",doctorDetailsResponseList.get(position).getUser_id());
                intent.putExtra("doctorname",doctorDetailsResponseList.get(position).getDoctor_name());
                intent.putExtra("reviewcount",doctorDetailsResponseList.get(position).getReview_count());
                intent.putExtra("starcount",doctorDetailsResponseList.get(position).getStar_count());
                intent.putExtra("distance",doctorDetailsResponseList.get(position).getDistance());
                intent.putExtra("communicationtype",doctorDetailsResponseList.get(position).getCommunication_type());
                intent.putExtra("fromactivity", "PetCareFragment");
                intent.putExtra("fromto", "direct");
                Log.w(TAG,"doctorid :"+doctorDetailsResponseList.get(position).getUser_id());
                context.startActivity(intent);
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
        public TextView txt_doctors_name,txt_doctors_specialization,txt_star_rating,txt_review_count,txt_place,txt_km;
        public LinearLayout ll_root;
        public ImageView img_doctors_image;
        public Button btn_book;




        public ViewHolderOne(View itemView) {
            super(itemView);
            txt_doctors_name = itemView.findViewById(R.id.txt_doctors_name);
            txt_doctors_specialization = itemView.findViewById(R.id.txt_doctors_specialization);
            img_doctors_image = itemView.findViewById(R.id.img_doctors_image);
            ll_root = itemView.findViewById(R.id.ll_root);
            txt_star_rating = itemView.findViewById(R.id.txt_star_rating);
            txt_review_count = itemView.findViewById(R.id.txt_review_count);
            txt_place = itemView.findViewById(R.id.txt_place);
            txt_km = itemView.findViewById(R.id.txt_km);
            btn_book = itemView.findViewById(R.id.btn_book);



        }




    }










}
