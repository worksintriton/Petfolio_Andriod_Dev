package com.petfolio.infinitus.adapter;

import android.annotation.SuppressLint;
import android.content.Context;

import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.petfolio.infinitus.R;
import com.petfolio.infinitus.api.APIClient;

import com.petfolio.infinitus.interfaces.AddandRemoveProductListener;
import com.petfolio.infinitus.responsepojo.CartDetailsResponse;

import java.util.List;



public class Cart_Adapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private  String TAG = "Cart_Adapter";
    List<CartDetailsResponse.DataBean> data;
    private Context context;
    private String petImagePath;
    private List<String> petImgBeanList;
    private AddandRemoveProductListener addandRemoveProductListener;


    public Cart_Adapter(Context context, List<CartDetailsResponse.DataBean> data,AddandRemoveProductListener addandRemoveProductListener) {
        this.context = context;
        this.data = data;
        this.addandRemoveProductListener = addandRemoveProductListener;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_cart, parent, false);
        return new ViewHolderOne(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        initLayoutOne((ViewHolderOne) holder, position);


    }

    @SuppressLint("SetTextI18n")
    private void initLayoutOne(ViewHolderOne holder, final int position) {
        petImgBeanList = data.get(position).getProduct_id().getProduct_img();

        if (data.get(position).getProduct_id().getProduct_name() != null) {
            holder.txt_products_title.setText(data.get(position).getProduct_id().getProduct_name());
        }
        if (data.get(position).getProduct_id().getDiscount_amount() != 0) {
            holder.txt_original_amount.setVisibility(View.VISIBLE);
            holder.txt_original_amount.setPaintFlags(holder.txt_original_amount.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.txt_original_amount.setText("\u20B9 " + data.get(position).getProduct_id().getDiscount_amount());

        }else{
            holder.txt_original_amount.setVisibility(View.GONE);
        }
        if (data.get(position).getProduct_id().getCost() != 0) {
            holder.txt_discount_amount.setText("\u20B9 " + data.get(position).getProduct_id().getCost());
        }
        if (data.get(position).getProduct_id().getDiscount() != 0) {
            holder.txt_discount.setVisibility(View.VISIBLE);
            holder.txt_discount.setText(data.get(position).getProduct_id().getDiscount() + " % off");
        }else {
            holder.txt_discount.setVisibility(View.GONE);
        }

        if (data.get(position).getProduct_count() != 0) {
            holder.txt_cart_count.setText(data.get(position).getProduct_count()+"");
        }


        if (petImgBeanList != null && petImgBeanList.size() > 0) {
            for (int j = 0; j < petImgBeanList.size(); j++) {
                petImagePath = petImgBeanList.get(0);

            }
        }

        if (petImagePath != null && !petImagePath.isEmpty()) {
            Glide.with(context)
                    .load(petImagePath)
                    .into(holder.img_products_image);
        } else {
            Glide.with(context)
                    .load(APIClient.PROFILE_IMAGE_URL)
                    .into(holder.img_products_image);

        }

        holder.img_add_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addandRemoveProductListener.addandRemoveProductListener(data.get(position).getProduct_id().get_id(),"add");


            }
        });

        holder.img_remove_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (data.get(position).getProduct_count() != 0) {
                    addandRemoveProductListener.addandRemoveProductListener(data.get(position).getProduct_id().get_id(),"remove");
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
        public TextView txt_products_title,txt_discount_amount,txt_original_amount,txt_discount,txt_cart_count;
        public ImageView img_products_image,img_remove_product,img_add_product;

        public ViewHolderOne(View itemView) {
            super(itemView);
            img_products_image = itemView.findViewById(R.id.img_products_image);
            txt_products_title = itemView.findViewById(R.id.txt_products_title);
            txt_discount_amount = itemView.findViewById(R.id.txt_discount_amount);
            txt_original_amount = itemView.findViewById(R.id.txt_original_amount);
            txt_discount = itemView.findViewById(R.id.txt_discount);
            txt_cart_count = itemView.findViewById(R.id.txt_cart_count);
            img_remove_product = itemView.findViewById(R.id.img_remove_product);
            img_add_product = itemView.findViewById(R.id.img_add_product);




           }




    }


}
