package com.petfolio.infinitus.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.petfolio.infinitus.R;
import com.petfolio.infinitus.api.APIClient;
import com.petfolio.infinitus.petlover.DoctorClinicDetailsActivity;
import com.petfolio.infinitus.responsepojo.PetLoverDashboardResponse;

import java.util.List;


public class PetLoverShopNewAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private  String TAG = "PetLoverShopNewAdapter";
    private Context context;
    List<PetLoverDashboardResponse.DataBean.DashboarddataBean.ProductsDetailsBean> productDetailsResponseList;
    PetLoverDashboardResponse.DataBean.DashboarddataBean.ProductsDetailsBean currentItem;
    int size;



    public PetLoverShopNewAdapter(Context context,   List<PetLoverDashboardResponse.DataBean.DashboarddataBean.ProductsDetailsBean> productDetailsResponseList, RecyclerView inbox_list, int size) {
       this.context = context;
       this.productDetailsResponseList = productDetailsResponseList;
        this.size = size;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_shop_card, parent, false);
        return new ViewHolderOne(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        initLayoutOne((ViewHolderOne) holder, position);

    }

    @SuppressLint("SetTextI18n")
    private void initLayoutOne(ViewHolderOne holder, final int position) {
          currentItem = productDetailsResponseList.get(position);
          if(currentItem.getProduct_title() != null){
              holder.txt_products_title.setText(currentItem.getProduct_title());
          } if(currentItem.getCat_name() != null){
              holder.txt_category_title.setText(currentItem.getCat_name());
          }
          if(currentItem.getProduct_price() != 0){
              holder.txt_products_price.setText("INR "+currentItem.getProduct_price());
          }else{
              holder.txt_products_price.setText("INR 0");
          }

          if(currentItem.isProduct_fav()){
              Glide.with(context)
                      .load(R.drawable.ic_fav)
                      .into(holder.img_fav);
          }else{
              Glide.with(context)
                      .load(R.drawable.heart_gray)
                      .into(holder.img_fav);
          }


        if (currentItem.getProduct_img() != null && !currentItem.getProduct_img().isEmpty()) {
            Glide.with(context)
                    .load(currentItem.getProduct_img())
                    .into(holder.img_products_image);

        }
        else{
            Glide.with(context)
                    .load(APIClient.PROFILE_IMAGE_URL)
                    .into(holder.img_products_image);

        }
        holder.ll_root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent(context, DoctorClinicDetailsActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("doctorid",doctorDetailsResponseList.get(position).get_id());
                intent.putExtra("doctorname",doctorDetailsResponseList.get(position).getDoctor_name());
                intent.putExtra("reviewcount",doctorDetailsResponseList.get(position).getReview_count());
                intent.putExtra("starcount",doctorDetailsResponseList.get(position).getStar_count());
                intent.putExtra("distance",doctorDetailsResponseList.get(position).getDistance());
                Log.w(TAG,"doctorid :"+doctorDetailsResponseList.get(position).get_id());
                context.startActivity(intent);*/
                }

        });
    }

    @Override
    public int getItemCount() {
        return Math.min(productDetailsResponseList.size(), size);



    }


    @Override
    public int getItemViewType(int position) {
        return position;
    }

    class ViewHolderOne extends RecyclerView.ViewHolder {
        public TextView txt_products_title,txt_category_title,txt_products_price;
        public ImageView img_products_image,img_fav;
        public LinearLayout ll_root;
        public RelativeLayout rl_shop;




        public ViewHolderOne(View itemView) {
            super(itemView);
            txt_products_title = itemView.findViewById(R.id.txt_products_title);
            txt_category_title = itemView.findViewById(R.id.txt_category_title);
            rl_shop = itemView.findViewById(R.id.rl_shop);
            ll_root = itemView.findViewById(R.id.ll_root);
            img_products_image = itemView.findViewById(R.id.img_products_image);
            img_fav = itemView.findViewById(R.id.img_fav);
            txt_products_price = itemView.findViewById(R.id.txt_products_price);



        }




    }










}