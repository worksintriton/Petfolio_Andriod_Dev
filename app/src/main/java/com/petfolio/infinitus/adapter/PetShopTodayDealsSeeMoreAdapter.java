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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.petfolio.infinitus.R;
import com.petfolio.infinitus.petlover.ProductDetailsActivity;
import com.petfolio.infinitus.petlover.SelectedServiceActivity;
import com.petfolio.infinitus.responsepojo.ShopDashboardResponse;

import java.util.List;


public class PetShopTodayDealsSeeMoreAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private  String TAG = "PetShopTodayDealsSeeMoreAdapter";
    private Context context;

    List<ShopDashboardResponse.DataBean.TodaySpecialBean> today_special;

    ShopDashboardResponse.DataBean.TodaySpecialBean currentItem;




    public PetShopTodayDealsSeeMoreAdapter(Context context, List<ShopDashboardResponse.DataBean.TodaySpecialBean> today_special) {
        this.today_special = today_special;
        this.context = context;



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

        currentItem = today_special.get(position);
        holder.txt_products_title.setText(today_special.get(position).getProduct_title());
        if(today_special.get(position).getProduct_price() != 0){
            holder.txt_products_price.setText("\u20B9 "+today_special.get(position).getProduct_price());
            }

        if(today_special.get(position).isProduct_fav()){
            holder.img_like.setVisibility(View.VISIBLE);
            holder.img_dislike.setVisibility(View.GONE);

        }
        else{
            holder.img_dislike.setVisibility(View.VISIBLE);
            holder.img_like.setVisibility(View.GONE);



        }

        Log.w(TAG,"discount : "+today_special.get(position).getProduct_discount());


        if(today_special.get(position).getProduct_discount() != 0){
            holder.txt_products_offer.setVisibility(View.VISIBLE);
            holder.txt_products_offer.setText(today_special.get(position).getProduct_discount()+" % off");
        }else{
            holder.txt_products_offer.setVisibility(View.GONE);

        }

        if (today_special.get(position).getProduct_img() != null && !today_special.get(position).getProduct_img().isEmpty()) {

                Glide.with(context)
                        .load(today_special.get(position).getProduct_img())
                        .into(holder.img_products_image);

            }
           else{
                Glide.with(context)
                        .load(R.drawable.app_logo)
                        .into(holder.img_products_image);

            }

           holder.ll_root.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   Intent intent = new Intent(context, ProductDetailsActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                   intent.putExtra("productid",today_special.get(position).get_id());
                   context.startActivity(intent);
               }
           });




    }

    @Override
    public int getItemCount() {
        return today_special.size();
    }


    @Override
    public int getItemViewType(int position) {
        return position;
    }
    static class ViewHolderOne extends RecyclerView.ViewHolder {
        public TextView txt_products_title,txt_products_price,txt_products_offer,txt_star_rating,txt_review_count;
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



        }




    }


}
