package com.carpeinfinitus.petfolio.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.carpeinfinitus.petfolio.R;
import com.carpeinfinitus.petfolio.api.APIClient;
import com.carpeinfinitus.petfolio.doctor.shop.DoctorProductDetailsActivity;
import com.carpeinfinitus.petfolio.petlover.ProductDetailsActivity;
import com.carpeinfinitus.petfolio.responsepojo.FetctProductByCatResponse;
import com.carpeinfinitus.petfolio.serviceprovider.shop.SPProductDetailsActivity;

import java.util.List;


public class PetShopCategorySeeMoreAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private  String TAG = "PetShopCategorySeeMoreAdapter";
    private Context context;

    List<FetctProductByCatResponse.DataBean> data;
    FetctProductByCatResponse.DataBean currentItem;
    private String fromactivity, cat_id;
    public PetShopCategorySeeMoreAdapter(Context context, List<FetctProductByCatResponse.DataBean> data,String fromactivity,String cat_id) {
        this.data = data;
        this.context = context;
        this.fromactivity = fromactivity;
        this.cat_id = cat_id;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_shop_todaysdeal_seemore, parent, false);
        return new ViewHolderOne(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        initLayoutOne((ViewHolderOne) holder, position);


    }

    @SuppressLint({"SetTextI18n", "LogNotTimber"})
    private void initLayoutOne(ViewHolderOne holder, final int position) {

        currentItem = data.get(position);
        Log.w(TAG,"fromactivity : "+fromactivity);
        if(data.get(position).getProduct_title() != null) {
            holder.txt_products_title.setText(data.get(position).getProduct_title());
        }
        if(data.get(position).getProduct_price() != 0){
            holder.txt_products_price.setText("INR "+data.get(position).getProduct_price());
            }

        if(currentItem.getProduct_discount_price() != 0){
            holder.txt_product_discount_price.setVisibility(View.VISIBLE);
            holder.txt_product_discount_price.setText("INR "+currentItem.getProduct_discount_price());
            holder.txt_product_discount_price.setPaintFlags(holder.txt_product_discount_price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }else{
            holder.txt_product_discount_price.setText("INR "+0);
            holder.txt_product_discount_price.setVisibility(View.GONE);
        }


        if(data.get(position).isProduct_fav()){
            holder.img_like.setVisibility(View.VISIBLE);
            holder.img_dislike.setVisibility(View.GONE);

        }
        else{
            holder.img_dislike.setVisibility(View.VISIBLE);
            holder.img_like.setVisibility(View.GONE);



        }

        Log.w(TAG,"discount : "+data.get(position).getProduct_discount());


        if(data.get(position).getProduct_discount() != 0){
            holder.txt_products_offer.setVisibility(View.VISIBLE);
            holder.txt_products_offer.setText(data.get(position).getProduct_discount()+" % off");
        }else{
            holder.txt_products_offer.setVisibility(View.GONE);

        }

        if (data.get(position).getThumbnail_image() != null && !data.get(position).getThumbnail_image().isEmpty()) {

            Glide.with(context)
                        .load(data.get(position).getThumbnail_image())
                        .into(holder.img_products_image);

            }
           else{
                Glide.with(context)
                        .load(APIClient.PROFILE_IMAGE_URL)
                        .into(holder.img_products_image);

            }

        if(currentItem.getProduct_rating() != 0){
            holder.txt_star_rating.setText(currentItem.getProduct_rating()+"");
        }else{
            holder.txt_star_rating.setText("0");
        }
        if(currentItem.getProduct_review() != 0){
            holder.txt_review_count.setText(currentItem.getProduct_review()+"");
        }else{
            holder.txt_review_count.setText("0");
        }

           holder.ll_root.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   if(fromactivity != null && fromactivity.equalsIgnoreCase("DoctorListOfProductsSeeMoreActivity")){
                       Intent intent = new Intent(context, DoctorProductDetailsActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                       intent.putExtra("productid",data.get(position).get_id());
                       intent.putExtra("fromactivity",fromactivity);
                       intent.putExtra("cat_id",cat_id);
                       context.startActivity(intent);
                   }else if(fromactivity != null && fromactivity.equalsIgnoreCase("SPListOfProductsSeeMoreActivity")){
                       Intent intent = new Intent(context, SPProductDetailsActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                       intent.putExtra("productid",data.get(position).get_id());
                       intent.putExtra("fromactivity",fromactivity);
                       intent.putExtra("cat_id",cat_id);
                       context.startActivity(intent);
                   }else{
                       Intent intent = new Intent(context, ProductDetailsActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                       intent.putExtra("productid",data.get(position).get_id());
                       intent.putExtra("fromactivity",fromactivity);
                       intent.putExtra("cat_id",cat_id);
                       context.startActivity(intent);
                   }

               }
           });




    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    @Override
    public int getItemViewType(int position) {
        return position;
    }
    static class ViewHolderOne extends RecyclerView.ViewHolder {
        public TextView txt_products_title,txt_products_price,txt_products_offer,txt_star_rating,txt_review_count,txt_product_discount_price;
        public ImageView img_products_image,img_like,img_dislike;
        LinearLayout ll_root;


        public ViewHolderOne(View itemView) {
            super(itemView);
            txt_products_title = itemView.findViewById(R.id.txt_products_title);
            txt_products_price = itemView.findViewById(R.id.txt_products_price);
            txt_products_offer = itemView.findViewById(R.id.txt_products_offer);
            txt_star_rating = itemView.findViewById(R.id.txt_star_rating);
            txt_review_count = itemView.findViewById(R.id.txt_review_count);
            ll_root = itemView.findViewById(R.id.ll_root);
            img_products_image = itemView.findViewById(R.id.img_products_image);
            img_like = itemView.findViewById(R.id.img_like);
            img_dislike = itemView.findViewById(R.id.img_dislike);
            txt_product_discount_price = itemView.findViewById(R.id.txt_product_discount_price);
            txt_review_count.setVisibility(View.GONE);



        }




    }


}
