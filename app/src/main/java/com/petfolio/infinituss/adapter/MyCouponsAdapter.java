package com.petfolio.infinituss.adapter;


import android.content.Context;
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
import com.petfolio.infinituss.R;
import com.petfolio.infinituss.api.APIClient;
import com.petfolio.infinituss.responsepojo.CouponCodeListResponse;
import com.petfolio.infinituss.responsepojo.NotificationGetlistResponse;

import java.util.List;


public class MyCouponsAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private  String TAG = "MyCouponsAdapter";
    private Context context;

    CouponCodeListResponse.DataBean currentItem;
    private List<CouponCodeListResponse.DataBean> couponcoderesponseList;





    public MyCouponsAdapter(Context context, List<CouponCodeListResponse.DataBean> couponcoderesponseList) {
        this.context = context;
        this.couponcoderesponseList = couponcoderesponseList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_mycoupons_cardview, parent, false);
        return new ViewHolderOne(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        initLayoutOne((ViewHolderOne) holder, position);


    }

  private void initLayoutOne(ViewHolderOne holder, final int position) {
        currentItem = couponcoderesponseList.get(position);


        if(currentItem.getTitle() != null && !currentItem.getTitle().isEmpty() ) {
            holder.txt_title.setVisibility(View.VISIBLE);
            holder.txt_title.setText(currentItem.getTitle());
        }else{
            holder.txt_title.setVisibility(View.INVISIBLE);
        }
        if(currentItem.getDescri() != null){
            holder.txt_desc.setText(currentItem.getDescri());

        }
      Log.w(TAG,"getExpired_date : "+currentItem.getCoupon_code());

      if(currentItem.getCoupon_code() != null) {
            holder.txt_coupon_code.setText(currentItem.getCoupon_code());
        }
        Log.w(TAG,"getExpired_date : "+currentItem.getExpired_date());
        if(currentItem.getExpired_date() != null && !currentItem.getExpired_date().isEmpty()) {
            holder.txt_expired.setVisibility(View.VISIBLE);
            holder.txt_expired.setText(currentItem.getExpired_date());
        }else{
            holder.txt_expired.setVisibility(View.INVISIBLE);
        }

      /*  if (currentItem.getNotify_img() != null && !currentItem.getNotify_img().isEmpty()) {

            Glide.with(context)
                    .load(currentItem.getNotify_img())
                    //.load(R.drawable.logo)
                    .into(holder.img_notify_imge);

        }
        else{
            Glide.with(context)
                    .load(APIClient.PROFILE_IMAGE_URL)
                    .into(holder.img_notify_imge);

        }*/

      Glide.with(context)
              .load(APIClient.PROFILE_IMAGE_URL)
              .into(holder.img_notify_imge);

        holder.ll_root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {





            }




        });


   }

   @Override
    public int getItemCount() {
        
        return couponcoderesponseList.size();
    }
   

    @Override
    public int getItemViewType(int position) {
        return position;
    }
    class ViewHolderOne extends RecyclerView.ViewHolder {
        public TextView txt_title,txt_desc,txt_coupon_code,txt_expired;
        public LinearLayout ll_root;
        public ImageView img_notify_imge;



        public ViewHolderOne(View itemView) {
            super(itemView);

            txt_title = itemView.findViewById(R.id.txt_title);
            txt_desc = itemView.findViewById(R.id.txt_desc);
            txt_coupon_code = itemView.findViewById(R.id.txt_coupon_code);
            txt_expired = itemView.findViewById(R.id.txt_expired);
            img_notify_imge = itemView.findViewById(R.id.img_notify_imge);
            ll_root = itemView.findViewById(R.id.ll_root);

        }

    }

}
