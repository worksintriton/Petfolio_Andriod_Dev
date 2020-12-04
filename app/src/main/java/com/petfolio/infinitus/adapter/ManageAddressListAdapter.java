package com.petfolio.infinitus.adapter;

import android.annotation.SuppressLint;

import android.content.Context;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.petfolio.infinitus.R;
import com.petfolio.infinitus.responsepojo.LocationListAddressResponse;



import java.util.List;




public class ManageAddressListAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private  String TAG = "ManageAddressListAdapter";
    private List<LocationListAddressResponse.DataBean> locationListResponseList  = null;
    private Context context;

    LocationListAddressResponse.DataBean currentItem;




    public static String id = "";


    public ManageAddressListAdapter(Context context, List<LocationListAddressResponse.DataBean> locationListResponseList, RecyclerView inbox_list) {
        this.locationListResponseList = locationListResponseList;
        this.context = context;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_address_list, parent, false);
        return new ViewHolderOne(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        initLayoutOne((ViewHolderOne) holder, position);


    }

    @SuppressLint("SetTextI18n")
    private void initLayoutOne(ViewHolderOne holder, final int position) {
        currentItem = locationListResponseList.get(position);
        holder.txt_location_title.setText(locationListResponseList.get(position).getLocation_title());
        holder.txt_location_nickname.setText(locationListResponseList.get(position).getLocation_nickname());
        holder.txt_address.setText(locationListResponseList.get(position).getLocation_address());



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
        public TextView txt_location_title,txt_location_nickname,txt_address;
        public ImageView img_settings;




        public ViewHolderOne(View itemView) {
            super(itemView);
            txt_location_title = itemView.findViewById(R.id.txt_location_title);
            txt_location_nickname = itemView.findViewById(R.id.txt_location_nickname);
            txt_address = itemView.findViewById(R.id.txt_address);
            img_settings = itemView.findViewById(R.id.img_settings);


        }




    }







}
