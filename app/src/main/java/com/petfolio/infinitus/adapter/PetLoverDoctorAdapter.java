package com.petfolio.infinitus.adapter;

import android.annotation.SuppressLint;
import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.petfolio.infinitus.R;
import com.petfolio.infinitus.responsepojo.PetLoverDashboardResponse;


import java.util.List;


public class PetLoverDoctorAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private  String TAG = "PetLoverDoctorAdapter";

    private Context context;





    List<PetLoverDashboardResponse.DataBean.DashboarddataBean.DoctorDetailsBean> doctorDetailsResponseList;
    PetLoverDashboardResponse.DataBean.DashboarddataBean.DoctorDetailsBean currentItem;




    int size;



    public PetLoverDoctorAdapter(Context context,  List<PetLoverDashboardResponse.DataBean.DashboarddataBean.DoctorDetailsBean> doctorDetailsResponseList, RecyclerView inbox_list,int size) {
        this.doctorDetailsResponseList = doctorDetailsResponseList;
        this.context = context;
        this.size = size;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_petloverdoctor, parent, false);
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
          holder.txt_doctors_specialization.setText(currentItem.getSpecialization().get(0).getSpecialization());
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

                /*Intent intent = new Intent(context, SubServicesActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("vehicletypeid",popularserviceBeanList.get(position).getVehicle_Type_id());
                intent.putExtra("serviceid",popularserviceBeanList.get(position).get_id());
                intent.putExtra("city",city);
                intent.putExtra("street",street);
                intent.putExtra("vehicleImage", vehicleImage);
                intent.putExtra("vehicleName", vehicleName);
                intent.putExtra("vehicleModelName", vehicleModelName);
                intent.putExtra("fuelType", fuelType);
                intent.putExtra("servicename", servicename);
                intent.putExtra("masterservicename", masterservicename);
                intent.putExtra("vehicletypename", vehicletypename);
                intent.putExtra("customervehicledatabeanlist", customerVehicleDataBeanList);
                intent.putExtra("twowheelervehicleid",twowheelervehicleid);
                intent.putExtra("fourwheelervehicleid",fourwheelervehicleid);
                intent.putExtra("masterserviceid",masterserviceid);
                intent.putExtra("selectedVehicleId",selectedVehicleId);
                intent.putExtra("selectedVehicleType",selectedVehicleType);
                Log.w(TAG,"vehicletypeid :"+popularserviceBeanList.get(position).getVehicle_Type_id()+" "+"serviceid : "+popularserviceBeanList.get(position).get_id());
                context.startActivity(intent);*/
                }




        });










    }












    @Override
    public int getItemCount() {
        return Math.min(doctorDetailsResponseList.size(), size);



    }


    @Override
    public int getItemViewType(int position) {
        return position;
    }

    class ViewHolderOne extends RecyclerView.ViewHolder {
        public TextView txt_doctors_name,txt_doctors_specialization;
        public LinearLayout ll_root;
        public ImageView img_doctors_image;




        public ViewHolderOne(View itemView) {
            super(itemView);
            txt_doctors_name = itemView.findViewById(R.id.txt_doctors_name);
            txt_doctors_specialization = itemView.findViewById(R.id.txt_doctors_specialization);
            img_doctors_image = itemView.findViewById(R.id.img_doctors_image);
            ll_root = itemView.findViewById(R.id.ll_root);



        }




    }










}
