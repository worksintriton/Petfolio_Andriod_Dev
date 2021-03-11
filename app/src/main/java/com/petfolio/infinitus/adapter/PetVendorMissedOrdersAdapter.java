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

import com.bumptech.glide.Glide;
import com.petfolio.infinitus.R;
import com.petfolio.infinitus.api.APIClient;
import com.petfolio.infinitus.responsepojo.PetVendorOrderResponse;

import java.util.List;


public class PetVendorMissedOrdersAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private  String TAG = "PetVendorMissedOrdersAdapter";
    private List<PetVendorOrderResponse.DataBean> newOrderResponseList;


    private Context context;

    PetVendorOrderResponse.DataBean currentItem;


    private int size;


    public PetVendorMissedOrdersAdapter(Context context, List<PetVendorOrderResponse.DataBean> newOrderResponseList, int size) {
        this.newOrderResponseList = newOrderResponseList;
        this.context = context;
        this.size = size;




    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_pet_missed_orders, parent, false);
        return new ViewHolderOne(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        initLayoutOne((ViewHolderOne) holder, position);


    }

    @SuppressLint("SetTextI18n")
    private void initLayoutOne(ViewHolderOne holder, final int position) {
        currentItem = newOrderResponseList.get(position);
        if(newOrderResponseList.get(position).getOrder_id() != null){
            holder.txt_orderid.setText(newOrderResponseList.get(position).getOrder_id());
        }

        if(newOrderResponseList.get(position).getProduct_name() != null) {
            holder.txt_producttitle.setText(newOrderResponseList.get(position).getProduct_name());
        }
        if(newOrderResponseList.get(position).getProduct_price() != 0 && newOrderResponseList.get(position).getProduct_quantity() != 0) {
            holder.txt_products_price.setText("\u20B9 " + newOrderResponseList.get(position).getProduct_price() + " (" + newOrderResponseList.get(position).getProduct_quantity() + " items )");
        }



        if(newOrderResponseList.get(position).getDate_of_booking() != null){
            holder.txt_bookedon.setText("Missed on:"+" "+newOrderResponseList.get(position).getDate_of_booking());

        }

        if (newOrderResponseList.get(position).getProdcut_image() != null && !newOrderResponseList.get(position).getProdcut_image().isEmpty()) {
            Glide.with(context)
                    .load(newOrderResponseList.get(position).getProdcut_image())
                    .into(holder.img_products_image);

        }
        else{
            Glide.with(context)
                    .load(APIClient.PROFILE_IMAGE_URL)
                    .into(holder.img_products_image);

        }








    }


    @Override
    public int getItemCount() {
        return Math.min(newOrderResponseList.size(), size);

    }


    @Override
    public int getItemViewType(int position) {
        return position;
    }

    static class ViewHolderOne extends RecyclerView.ViewHolder {
        public TextView txt_orderid,txt_producttitle,txt_products_price,txt_bookedon;
        public ImageView img_products_image;



        public ViewHolderOne(View itemView) {
            super(itemView);
            img_products_image = itemView.findViewById(R.id.img_products_image);
            txt_orderid = itemView.findViewById(R.id.txt_orderid);
            txt_producttitle = itemView.findViewById(R.id.txt_producttitle);
            txt_products_price = itemView.findViewById(R.id.txt_products_price);
            txt_bookedon = itemView.findViewById(R.id.txt_bookedon);





        }




    }






}
