package com.petfolio.infinitus.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.petfolio.infinitus.R;
import com.petfolio.infinitus.activity.location.EditMyAddressActivity;
import com.petfolio.infinitus.interfaces.LocationDefaultListener;
import com.petfolio.infinitus.interfaces.LocationDeleteListener;
import com.petfolio.infinitus.responsepojo.LocationListAddressResponse;

import java.util.List;


public class ManageServiceListAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private  String TAG = "ManageServiceListAdapter";
    private List<LocationListAddressResponse.DataBean> locationListResponseList  = null;
    private Context context;

    LocationListAddressResponse.DataBean currentItem;




    public static String id = "";

    private LocationDeleteListener locationDeleteListener;
    private LocationDefaultListener locationDefaultListener;



    public ManageServiceListAdapter(Context context, List<LocationListAddressResponse.DataBean> locationListResponseList, RecyclerView inbox_list, LocationDeleteListener locationDeleteListener, LocationDefaultListener locationDefaultListener) {
        this.locationListResponseList = locationListResponseList;
        this.context = context;
        this.locationDeleteListener = locationDeleteListener;
        this.locationDefaultListener = locationDefaultListener;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_manageservice_list, parent, false);
        return new ViewHolderOne(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        initLayoutOne((ViewHolderOne) holder, position);


    }

    @SuppressLint("SetTextI18n")
    private void initLayoutOne(ViewHolderOne holder, final int position) {
        currentItem = locationListResponseList.get(position);
        holder.txt_servicename.setText(locationListResponseList.get(position).getLocation_title());
        holder.txt_service_cost.setText(locationListResponseList.get(position).getLocation_nickname());

        holder.img_settings.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Creating the instance of PopupMenu
                PopupMenu popup = new PopupMenu(context, v);
                //Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        String titleName = String.valueOf(item.getTitle());
                        if(titleName != null && titleName.equalsIgnoreCase("Edit")){

                            Intent i = new Intent(context, EditMyAddressActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            i.putExtra("id",locationListResponseList.get(position).get_id());
                            i.putExtra("userid",locationListResponseList.get(position).getUser_id());
                            i.putExtra("cityname",locationListResponseList.get(position).getLocation_city());
                            i.putExtra("state",locationListResponseList.get(position).getLocation_state());
                            i.putExtra("country",locationListResponseList.get(position).getLocation_country());
                            i.putExtra("address",locationListResponseList.get(position).getLocation_address());
                            i.putExtra("pincode",locationListResponseList.get(position).getLocation_pin());
                            i.putExtra("nickname",locationListResponseList.get(position).getLocation_nickname());
                            i.putExtra("locationtype",locationListResponseList.get(position).getLocation_title());
                            i.putExtra("defaultstatus",locationListResponseList.get(position).isDefault_status());
                            Bundle b = new Bundle();
                            b.putDouble("lat", locationListResponseList.get(position).getLocation_lat());
                            b.putDouble("lon", locationListResponseList.get(position).getLocation_long());
                            i.putExtras(b);
                            Log.w(TAG,"cityname-->"+locationListResponseList.get(position).getLocation_city());
                            context.startActivity(i);

                        } else if(titleName != null && titleName.equalsIgnoreCase("Delete")){
                            locationDeleteListener.locationDeleteListener(locationListResponseList.get(position).isDefault_status(),locationListResponseList.get(position).get_id());

                        }
                        return true;
                    }
                });

                popup.show();//showing popup menu
            }
        });

        holder.rl_root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                locationDefaultListener.locationDefaultListener(locationListResponseList.get(position).isDefault_status(),locationListResponseList.get(position).get_id(),locationListResponseList.get(position).getUser_id());

            }
        });









    }









    @Override
    public int getItemCount() {
        return locationListResponseList.size();
    }


    @Override
    public int getItemViewType(int position) {
        return position;
    }

    class ViewHolderOne extends RecyclerView.ViewHolder {
        public TextView txt_servicename,txt_service_cost;
        public ImageView img_settings,img_service;
        public RelativeLayout rl_root;




        public ViewHolderOne(View itemView) {
            super(itemView);
            txt_servicename = itemView.findViewById(R.id.txt_servicename);
            txt_service_cost = itemView.findViewById(R.id.txt_service_cost);
            img_settings = itemView.findViewById(R.id.img_settings);
            img_service = itemView.findViewById(R.id.img_service);
            rl_root = itemView.findViewById(R.id.rl_root);


        }




    }







}
