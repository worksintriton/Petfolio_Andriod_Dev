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
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.petfolio.infinitus.R;
import com.petfolio.infinitus.petlover.DoctorClinicDetailsActivity;
import com.petfolio.infinitus.petlover.ListOfProductsSeeMoreActivity;
import com.petfolio.infinitus.responsepojo.ShopDashboardResponse;

import java.util.List;


public class PetShopProductDetailsAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private  String TAG = "PetShopProductDetailsAdapter";
    private Context context;

    List<ShopDashboardResponse.DataBean.ProductDetailsBean> product_details;
    List<ShopDashboardResponse.DataBean.ProductDetailsBean.ProductListBean> productList;

    ShopDashboardResponse.DataBean.ProductDetailsBean currentItem;




    public PetShopProductDetailsAdapter(Context context,  List<ShopDashboardResponse.DataBean.ProductDetailsBean> product_details,List<ShopDashboardResponse.DataBean.ProductDetailsBean.ProductListBean> productList) {
        this.context = context;
        this.product_details = product_details;
        this.productList = productList;




    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_shop_productdetails, parent, false);
        return new ViewHolderOne(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        initLayoutOne((ViewHolderOne) holder, position);
    }

    @SuppressLint({"SetTextI18n", "LogNotTimber"})
    private void initLayoutOne(ViewHolderOne holder, final int position) {

        currentItem = product_details.get(position);
        if(product_details.get(position).getProduct_list() != null && product_details.get(position).getProduct_list().size()>0){
            holder.txt_category_title.setVisibility(View.VISIBLE);
            holder.txt_seemore_products.setVisibility(View.VISIBLE);
            holder.rv_productdetails.setVisibility(View.VISIBLE);
            holder.txt_category_title.setText(currentItem.getCat_name());
        }else{
            holder.txt_category_title.setVisibility(View.GONE);
            holder.txt_seemore_products.setVisibility(View.GONE);
            holder.rv_productdetails.setVisibility(View.GONE);
        }
        holder.rv_productdetails.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        holder.rv_productdetails.setItemAnimator(new DefaultItemAnimator());
        PetShopProductDetailsImageAdapter petShopProductDetailsAdapter = new PetShopProductDetailsImageAdapter(context,product_details.get(position).getProduct_list());
        holder.rv_productdetails.setAdapter(petShopProductDetailsAdapter);


        holder.txt_seemore_products.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(product_details.get(position).getProduct_list() != null && product_details.get(position).getProduct_list().size()>0) {
                    Intent intent = new Intent(context, ListOfProductsSeeMoreActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("cat_id",currentItem.getCat_id());
                    context.startActivity(intent);

                }

                }
        });





    }

    @Override
    public int getItemCount() {
        return product_details.size();
    }


    @Override
    public int getItemViewType(int position) {
        return position;
    }
    static class ViewHolderOne extends RecyclerView.ViewHolder {
        public TextView txt_category_title,txt_seemore_products;
        RecyclerView rv_productdetails;


        public ViewHolderOne(View itemView) {
            super(itemView);

            txt_category_title = itemView.findViewById(R.id.txt_category_title);
            rv_productdetails = itemView.findViewById(R.id.rv_productdetails);
            txt_seemore_products = itemView.findViewById(R.id.txt_seemore_products);




        }




    }


}
