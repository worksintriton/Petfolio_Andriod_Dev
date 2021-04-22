package com.petfolio.infinitus.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
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
import com.petfolio.infinitus.petlover.PetLoverVendorOrderDetailsActivity;
import com.petfolio.infinitus.responsepojo.PetLoverVendorOrderListResponse;

import java.util.List;


public class PetLoverVendorOrdersAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final String TAG = "PetLoverVendorOrdersAdapter";
    List<PetLoverVendorOrderListResponse.DataBean> orderResponseListAll;
    private final Context context;
    PetLoverVendorOrderListResponse.DataBean currentItem;
    String fromactivity;



    public PetLoverVendorOrdersAdapter(Context context, List<PetLoverVendorOrderListResponse.DataBean> orderResponseListAll, String fromactivity) {
        this.context = context;
        this.orderResponseListAll = orderResponseListAll;
        this.fromactivity = fromactivity;


    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_petlover_new_orders, parent, false);
        return new ViewHolderOne(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        initLayoutOne((ViewHolderOne) holder, position);


    }

    @SuppressLint("SetTextI18n")
    private void initLayoutOne(ViewHolderOne holder, final int position) {
        currentItem = orderResponseListAll.get(position);
        if (orderResponseListAll.get(position).getP_order_id() != null) {
            holder.txt_orderid.setText(orderResponseListAll.get(position).getP_order_id());
        }
        if (orderResponseListAll.get(position).getP_order_text() != null) {
            holder.txt_producttitle.setText(orderResponseListAll.get(position).getP_order_text());
        }
        if (orderResponseListAll.get(position).getP_order_price() != 0 && orderResponseListAll.get(position).getP_order_product_count() != 0) {
            if (orderResponseListAll.get(position).getP_order_product_count() == 1) {
                holder.txt_products_price.setText("\u20B9 " + orderResponseListAll.get(position).getP_order_price() + " (" + orderResponseListAll.get(position).getP_order_product_count() + " product )");
            } else {
                holder.txt_products_price.setText("\u20B9 " + orderResponseListAll.get(position).getP_order_price() + " (" + orderResponseListAll.get(position).getP_order_product_count() + " products )");

            }
        }
        else { if (orderResponseListAll.get(position).getP_order_product_count() == 1) {
                holder.txt_products_price.setText("\u20B9 " + 0 + " (" + orderResponseListAll.get(position).getP_order_product_count() + " item )");
            } else { holder.txt_products_price.setText("\u20B9 " + 0 + " (" + orderResponseListAll.get(position).getP_order_product_count() + " items )"); } }


        if(fromactivity != null && fromactivity.equalsIgnoreCase("FragmentPetLoverNewOrders")){
            if (orderResponseListAll.get(position).getP_order_booked_on() != null) {
                holder.txt_bookedon.setText("Booked on:" + " " + orderResponseListAll.get(position).getP_order_booked_on());

            }
        }
        else if(fromactivity != null && fromactivity.equalsIgnoreCase("FragmentPetLoverCompletedOrders")){
            if (orderResponseListAll.get(position).getP_order_booked_on() != null) {
                holder.txt_bookedon.setText("Delivered on:" + " " + orderResponseListAll.get(position).getP_order_booked_on());

            }
        }
        else if(fromactivity != null && fromactivity.equalsIgnoreCase("FragmentPetLoverCancelledOrders")){
            if (orderResponseListAll.get(position).getP_order_booked_on() != null) {
                holder.txt_bookedon.setText("Cancelled on:" + " " + orderResponseListAll.get(position).getP_order_booked_on());

            }
        }

        if (orderResponseListAll.get(position).getP_order_image() != null && !orderResponseListAll.get(position).getP_order_image().isEmpty()) {
            Glide.with(context)
                    .load(orderResponseListAll.get(position).getP_order_image())
                    .into(holder.img_products_image);

        }
        else {
            Glide.with(context)
                    .load(APIClient.PROFILE_IMAGE_URL)
                    .into(holder.img_products_image);

        }
        holder.txt_order_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, PetLoverVendorOrderDetailsActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("_id", orderResponseListAll.get(position).getP_order_id());
                i.putExtra("fromactivity", fromactivity);
                context.startActivity(i);

            }
        });
       /* holder.txt_track_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, PetVendorTrackOrderActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("_id", newOrderResponseList.get(position).get_id());
                i.putExtra("fromactivity", TAG);
                context.startActivity(i);


            }
        });
        holder.txt_cancell_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, PetVendorCancelOrderActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("_id", newOrderResponseList.get(position).get_id());
                i.putExtra("fromactivity", TAG);
                context.startActivity(i);

            }
        });
*/
    }

    @Override
    public int getItemCount() {
        return orderResponseListAll.size();

    }
    @Override
    public int getItemViewType(int position) {
        return position;
    }

    static class ViewHolderOne extends RecyclerView.ViewHolder {
        public TextView txt_orderid, txt_producttitle, txt_products_price, txt_bookedon, txt_order_details;
        public ImageView img_products_image;


        public ViewHolderOne(View itemView) {
            super(itemView);
            img_products_image = itemView.findViewById(R.id.img_products_image);
            txt_orderid = itemView.findViewById(R.id.txt_orderid);
            txt_producttitle = itemView.findViewById(R.id.txt_producttitle);
            txt_products_price = itemView.findViewById(R.id.txt_products_price);
            txt_bookedon = itemView.findViewById(R.id.txt_bookedon);
            txt_order_details = itemView.findViewById(R.id.txt_order_details);
        }


    }

}