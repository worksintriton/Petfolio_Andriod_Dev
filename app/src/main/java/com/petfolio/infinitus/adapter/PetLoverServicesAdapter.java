package com.petfolio.infinitus.adapter;

import android.annotation.SuppressLint;
import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.petfolio.infinitus.R;
import com.petfolio.infinitus.responsepojo.PetLoverDashboardResponse;


import java.util.List;



public class PetLoverServicesAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private  String TAG = "PetLoverServicesAdapter";

    private Context context;





    List<PetLoverDashboardResponse.DataBean.DashboarddataBean.ServiceDetailsBean> serviceDetailsResponseList;
    PetLoverDashboardResponse.DataBean.DashboarddataBean.ServiceDetailsBean currentItem;


    int size;

    public PetLoverServicesAdapter(Context context,  List<PetLoverDashboardResponse.DataBean.DashboarddataBean.ServiceDetailsBean> serviceDetailsResponseList, RecyclerView inbox_list, int size) {
        this.serviceDetailsResponseList = serviceDetailsResponseList;
        this.context = context;
        this.size = size;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_petlover_services, parent, false);
        return new ViewHolderOne(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        initLayoutOne((ViewHolderOne) holder, position);

    }

    @SuppressLint("SetTextI18n")
    private void initLayoutOne(ViewHolderOne holder, final int position) {
          currentItem = serviceDetailsResponseList.get(position);
          holder.txt_petlover_servicesname.setText(currentItem.getService_title());
          if (currentItem.getService_icon() != null && !currentItem.getService_icon().isEmpty()) {

            Glide.with(context)
                    .load(currentItem.getService_icon())
                    //.load(R.drawable.logo)
                    .into(holder.cv_serviceimage);

           }
          else{
            Glide.with(context)
                    .load(R.drawable.services)
                    .into(holder.cv_serviceimage);

        }

      //  int color = Integer.parseInt(currentItem.getBackground_color());
       /* int radius = 100; //radius will be 5px
        int strokeWidth = 1;
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setColor(Color.parseColor(currentItem.getBackground_color()));
        gradientDrawable.setCornerRadius(radius);
        gradientDrawable.setStroke(strokeWidth, Color.parseColor(currentItem.getBackground_color()));
        holder.rl_root.setBackground(gradientDrawable);*/
        holder.rl_root.setOnClickListener(new View.OnClickListener() {
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
        return Math.min(serviceDetailsResponseList.size(), size);



    }


    @Override
    public int getItemViewType(int position) {
        return position;
    }

    class ViewHolderOne extends RecyclerView.ViewHolder {
        public TextView txt_petlover_servicesname;
        public RelativeLayout rl_root;
        public ImageView cv_serviceimage;




        public ViewHolderOne(View itemView) {
            super(itemView);
            txt_petlover_servicesname = itemView.findViewById(R.id.txt_petlover_servicesname);
            cv_serviceimage = itemView.findViewById(R.id.cv_serviceimage);
            rl_root = itemView.findViewById(R.id.rl_root);



        }




    }










}
