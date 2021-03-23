package com.petfolio.infinitus.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.petfolio.infinitus.R;
import com.petfolio.infinitus.api.APIClient;
import com.petfolio.infinitus.interfaces.OnAcceptsReturnOrder;
import com.petfolio.infinitus.interfaces.OnDeleteShipAddrListener;
import com.petfolio.infinitus.interfaces.OnEditShipAddrListener;
import com.petfolio.infinitus.interfaces.OnRejectsReturnOrder;
import com.petfolio.infinitus.interfaces.OnSelectingShipIdListener;
import com.petfolio.infinitus.responsepojo.ShippingAddressListingByUserIDResponse;
import com.petfolio.infinitus.responsepojo.VendorNewOrderResponse;
import com.petfolio.infinitus.vendor.VendorOrderDetailsActivity;
import com.petfolio.infinitus.vendor.VendorUpdateOrderStatusActivity;

import java.util.List;


public class ShippingAddressListAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private  String TAG = "ShippingAddressListAdapter";
    private List<ShippingAddressListingByUserIDResponse.DataBean> newOrderResponseList;

    private Context context;

    ShippingAddressListingByUserIDResponse.DataBean currentItem;

    private int size;

    String first_name, last_name, state, landmark, pincode;

    private int lastSelectedPosition = -1;

    OnSelectingShipIdListener onSelectingShipIdListener;

    OnEditShipAddrListener onEditShipAddrListener;

    OnDeleteShipAddrListener onDeleteShipAddrListener;

    public ShippingAddressListAdapter(Context context, List<ShippingAddressListingByUserIDResponse.DataBean> newOrderResponseList, int size,OnSelectingShipIdListener onSelectingShipIdListener,OnEditShipAddrListener onEditShipAddrListener,OnDeleteShipAddrListener onDeleteShipAddrListener) {
        this.newOrderResponseList = newOrderResponseList;
        this.context = context;
        this.size = size;
        this.onSelectingShipIdListener = onSelectingShipIdListener;
        this.onEditShipAddrListener = onEditShipAddrListener;
        this.onDeleteShipAddrListener = onDeleteShipAddrListener;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_shipping_address, parent, false);
        return new ViewHolderOne(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        initLayoutOne((ViewHolderOne) holder, position);


    }

    @SuppressLint("SetTextI18n")
    private void initLayoutOne(ViewHolderOne holder, final int position) {


        currentItem = newOrderResponseList.get(position);

        if(newOrderResponseList.get(position).getUser_first_name()!=null&&!newOrderResponseList.get(position).getUser_first_name().isEmpty()){

            first_name = newOrderResponseList.get(position).getUser_first_name();

        }

        if(newOrderResponseList.get(position).getUser_last_name()!=null&&!newOrderResponseList.get(position).getUser_last_name().isEmpty()){

            last_name = newOrderResponseList.get(position).getUser_last_name();

        }

        if(first_name!=null&&last_name!=null){

            holder.txt_username.setText(first_name + " " + last_name);
        }


        if(newOrderResponseList.get(position).getUser_mobile()!=null&&!newOrderResponseList.get(position).getUser_mobile().isEmpty()){

            holder.txt_phnum.setText(newOrderResponseList.get(position).getUser_mobile());

        }

        if(newOrderResponseList.get(position).getUser_address_type()!=null&&!newOrderResponseList.get(position).getUser_address_type().isEmpty()){

            holder.txt_addrs_type.setText(newOrderResponseList.get(position).getUser_address_type());

        }

        if(newOrderResponseList.get(position).getUser_display_date()!=null&&!newOrderResponseList.get(position).getUser_display_date().isEmpty()){

            holder.txt_date.setText(newOrderResponseList.get(position).getUser_display_date());

        }

        if(newOrderResponseList.get(position).getUser_city()!=null&&!newOrderResponseList.get(position).getUser_city().isEmpty()){

            holder.txt_user_city.setText(newOrderResponseList.get(position).getUser_city());

        }
        if(newOrderResponseList.get(position).getUser_state()!=null&&!newOrderResponseList.get(position).getUser_state().isEmpty()){

            state = newOrderResponseList.get(position).getUser_state();

        }

        if(newOrderResponseList.get(position).getUser_stree()!=null&&!newOrderResponseList.get(position).getUser_stree().isEmpty()){

            holder.txt_street.setText(newOrderResponseList.get(position).getUser_stree());

        }

        if(newOrderResponseList.get(position).getUser_landmark()!=null&&!newOrderResponseList.get(position).getUser_landmark().isEmpty()){

           landmark = newOrderResponseList.get(position).getUser_landmark();

        }

        if(newOrderResponseList.get(position).getUser_landmark()!=null&&!newOrderResponseList.get(position).getUser_landmark().isEmpty()){

           pincode = newOrderResponseList.get(position).getUser_picocode();

        }


        if(landmark!=null&&pincode!=null&&state!=null){

            holder.txt_dist_pincode_state.setText(landmark + " " + " "+ state +" "+ pincode);
        }

        //since only one radio button is allowed to be selected,
        // this condition un-checks previous selections
        holder.rb_choose_addr_list.setChecked(lastSelectedPosition == position);

        holder.rb_choose_addr_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lastSelectedPosition = holder.getAdapterPosition();
                notifyDataSetChanged();

                Toast.makeText(context,
                        "selected shipping Id is " + newOrderResponseList.get(position).get_id(),
                        Toast.LENGTH_LONG).show();

                onSelectingShipIdListener.onSelectShipID(newOrderResponseList.get(position).get_id(),newOrderResponseList.get(position).getUser_first_name(),newOrderResponseList.get(position).getUser_last_name(),newOrderResponseList.get(position).getUser_mobile(),newOrderResponseList.get(position).getUser_alter_mobile(),

                        newOrderResponseList.get(position).getUser_flat_no(),newOrderResponseList.get(position).getUser_state(),newOrderResponseList.get(position).getUser_stree(),newOrderResponseList.get(position).getUser_landmark(),newOrderResponseList.get(position).getUser_picocode(),newOrderResponseList.get(position).getUser_address_type(),newOrderResponseList.get(position).getUser_display_date(),newOrderResponseList.get(position).getUser_address_stauts());
            }
        });

        holder.ll_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onEditShipAddrListener.OnEditShipAddr(newOrderResponseList.get(position).get_id(),newOrderResponseList.get(position).getUser_first_name(),newOrderResponseList.get(position).getUser_last_name(),newOrderResponseList.get(position).getUser_mobile(),newOrderResponseList.get(position).getUser_alter_mobile(),

                        newOrderResponseList.get(position).getUser_flat_no(),newOrderResponseList.get(position).getUser_state(),newOrderResponseList.get(position).getUser_stree(),newOrderResponseList.get(position).getUser_landmark(),newOrderResponseList.get(position).getUser_picocode(),newOrderResponseList.get(position).getUser_address_type(),newOrderResponseList.get(position).getUser_display_date(),newOrderResponseList.get(position).getUser_address_stauts(),newOrderResponseList.get(position).getUser_city());
            }
        });


        holder.ll_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onDeleteShipAddrListener.OnDeleteShipAddr(newOrderResponseList.get(position).get_id(),newOrderResponseList.get(position).getUser_first_name(),newOrderResponseList.get(position).getUser_last_name(),newOrderResponseList.get(position).getUser_mobile(),newOrderResponseList.get(position).getUser_alter_mobile(),

                        newOrderResponseList.get(position).getUser_flat_no(),newOrderResponseList.get(position).getUser_state(),newOrderResponseList.get(position).getUser_stree(),newOrderResponseList.get(position).getUser_landmark(),newOrderResponseList.get(position).getUser_picocode(),newOrderResponseList.get(position).getUser_address_type(),newOrderResponseList.get(position).getUser_display_date(),newOrderResponseList.get(position).getUser_address_stauts());

            }
        });

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

        TextView txt_username, txt_phnum, txt_user_city, txt_street, txt_dist_pincode_state, txt_date, txt_addrs_type;

        LinearLayout ll_edit,ll_delete;

        RadioButton rb_choose_addr_list;


        public ViewHolderOne(View itemView) {
            super(itemView);

            txt_username = itemView.findViewById(R.id.txt_username);


            txt_phnum = itemView.findViewById(R.id.txt_phnum);


            txt_user_city = itemView.findViewById(R.id.txt_user_city);


            txt_street = itemView.findViewById(R.id.txt_street);


            txt_dist_pincode_state = itemView.findViewById(R.id.txt_dist_pincode_state);


            txt_date = itemView.findViewById(R.id.txt_date);


            txt_addrs_type = itemView.findViewById(R.id.txt_addrs_type);


            ll_edit = itemView.findViewById(R.id.ll_edit);


            ll_delete = itemView.findViewById(R.id.ll_delete);


            rb_choose_addr_list = itemView.findViewById(R.id.rb_choose_addr_list);

        }




    }

}
