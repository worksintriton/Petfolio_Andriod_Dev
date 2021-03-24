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
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.petfolio.infinitus.R;
import com.petfolio.infinitus.activity.location.EditMyAddressActivity;
import com.petfolio.infinitus.api.APIClient;
import com.petfolio.infinitus.interfaces.LocationDefaultListener;
import com.petfolio.infinitus.interfaces.LocationDeleteListener;
import com.petfolio.infinitus.responsepojo.LocationListAddressResponse;
import com.petfolio.infinitus.responsepojo.ManageProductsListResponse;

import java.util.List;


public class ManageProductsListAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private  String TAG = "ManageProductsListAdapter";
    private Context context;
    ManageProductsListResponse.DataBean currentItem;
    private List<ManageProductsListResponse.DataBean> manageProductsListResponseList;
    public static String id = "";
    private int currentSelectedPosition = RecyclerView.NO_POSITION;
    boolean showCheckbox;
    public ManageProductsListAdapter(Context context, List<ManageProductsListResponse.DataBean> manageProductsListResponseList, boolean showCheckbox) {
        this.context = context;
        this.manageProductsListResponseList = manageProductsListResponseList;
        this.showCheckbox = showCheckbox;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_vendor_productlist_parent_view, parent, false);
        return new ViewHolderOne(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        initLayoutOne((ViewHolderOne) holder, position);


    }

    @SuppressLint("SetTextI18n")
    private void initLayoutOne(ViewHolderOne holder, final int position) {
        currentItem = manageProductsListResponseList.get(position);
        if(manageProductsListResponseList.get(position).getProduct_name() != null) {
            holder.txt_prod_name.setText(manageProductsListResponseList.get(position).getProduct_name());
        }
        if(manageProductsListResponseList.get(position).getProduct_price() != 0) {
            holder.txt_prod_price.setText("\u20B9 " + manageProductsListResponseList.get(position).getProduct_price());
        }
        if (manageProductsListResponseList.get(position).getProducts_image().get(0) != null && !manageProductsListResponseList.get(position).getProducts_image().get(0).isEmpty()) {
            Glide.with(context)
                    .load(manageProductsListResponseList.get(position).getProducts_image().get(0))
                    .into(holder.img_products_image);
        }
        else{
            Glide.with(context)
                    .load(APIClient.BANNER_IMAGE_URL)
                    .into(holder.img_products_image);

        }
        holder.img_expand_arrow.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("LogNotTimber")
            @Override
            public void onClick(View v) {
                currentSelectedPosition = position;
                notifyDataSetChanged();



            }
        });
        if (currentSelectedPosition == position) {
            holder.include_vendor_productlist_childview.setVisibility(View.VISIBLE);
            holder.img_expand_arrow.setImageResource(R.drawable.ic_up);
        }
        else {
            holder.include_vendor_productlist_childview.setVisibility(View.GONE);
            holder.img_expand_arrow.setImageResource(R.drawable.ic_down);
        }

        if(manageProductsListResponseList.get(position).getPet_type().get(position).getPet_type_title() != null){
            holder.txt_pet_type.setText(" : "+manageProductsListResponseList.get(position).getPet_type().get(position).getPet_type_title());

        }if(manageProductsListResponseList.get(position).getPet_breed().get(position).getPet_breed() != null){
            holder.txt_pet_breed.setText(" : "+manageProductsListResponseList.get(position).getPet_breed().get(position).getPet_breed());

        }if(manageProductsListResponseList.get(position).getPet_age().get(position) != null){
            holder.txt_age.setText(" : "+manageProductsListResponseList.get(position).getPet_age().get(position)+"");

        }if(manageProductsListResponseList.get(position).getPet_threshold()!= null){
            holder.txt_threshold.setText(" : "+manageProductsListResponseList.get(position).getPet_threshold());

        }



    }









    @Override
    public int getItemCount() {
        return manageProductsListResponseList.size();
    }


    @Override
    public int getItemViewType(int position) {
        return position;
    }

    class ViewHolderOne extends RecyclerView.ViewHolder {
        public TextView txt_prod_name,txt_prod_price,txt_pet_type,txt_age,txt_pet_breed,txt_threshold,txt_deal_status;
        public ImageView img_products_image,img_prodsettings,img_expand_arrow;
        public View include_vendor_productlist_childview;
        public CheckBox checkBox;
        public ViewHolderOne(View itemView) {
            super(itemView);
            img_products_image = itemView.findViewById(R.id.img_products_image);
            img_prodsettings = itemView.findViewById(R.id.img_prodsettings);
            txt_prod_name = itemView.findViewById(R.id.txt_prod_name);
            txt_prod_price = itemView.findViewById(R.id.txt_prod_price);
            img_expand_arrow = itemView.findViewById(R.id.img_expand_arrow);
            include_vendor_productlist_childview = itemView.findViewById(R.id.include_vendor_productlist_childview);
            include_vendor_productlist_childview.setVisibility(View.GONE);

            txt_pet_type = include_vendor_productlist_childview.findViewById(R.id.txt_pet_type);
            txt_age = include_vendor_productlist_childview.findViewById(R.id.txt_age);
            txt_pet_breed = include_vendor_productlist_childview.findViewById(R.id.txt_pet_breed);
            txt_threshold = include_vendor_productlist_childview.findViewById(R.id.txt_threshold);
            txt_deal_status = include_vendor_productlist_childview.findViewById(R.id.txt_deal_status);
            checkBox = itemView.findViewById(R.id.checkBox);

            if(showCheckbox){

                checkBox.setVisibility(View.VISIBLE);
            }

            else {

                checkBox.setVisibility(View.GONE);

            }
        }

    }







}
