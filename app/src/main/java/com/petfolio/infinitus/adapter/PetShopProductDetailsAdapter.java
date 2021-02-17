package com.petfolio.infinitus.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
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
        holder.txt_category_title.setText(currentItem.getCat_name());
        holder.rv_productdetails.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        holder.rv_productdetails.setItemAnimator(new DefaultItemAnimator());
        PetShopProductDetailsImageAdapter petShopProductDetailsAdapter = new PetShopProductDetailsImageAdapter(context,product_details.get(position).getProduct_list());
        holder.rv_productdetails.setAdapter(petShopProductDetailsAdapter);





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
        public TextView txt_category_title;
        RecyclerView rv_productdetails;


        public ViewHolderOne(View itemView) {
            super(itemView);

            txt_category_title = itemView.findViewById(R.id.txt_category_title);
            rv_productdetails = itemView.findViewById(R.id.rv_productdetails);



        }




    }


}
