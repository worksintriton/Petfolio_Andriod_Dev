package com.petfolio.infinitus.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.petfolio.infinitus.R;
import com.petfolio.infinitus.requestpojo.VendorRegisterFormCreateRequest;

import java.util.List;

public class AddMoreServiceAdapter extends RecyclerView.Adapter<AddMoreServiceAdapter.AddExpViewHolder> {
    Context context;
    private List<VendorRegisterFormCreateRequest.BusServiceListBean> bus_service_list;
    View view;

    public AddMoreServiceAdapter(Context context, List<VendorRegisterFormCreateRequest.BusServiceListBean> bus_service_list) {
        this.context = context;
        this.bus_service_list = bus_service_list;

    }

    @NonNull
    @Override
    public AddExpViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_sp_add_more_service, parent, false);
        return new AddExpViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddExpViewHolder holder, final int position) {
        final  VendorRegisterFormCreateRequest.BusServiceListBean busServiceListBean = bus_service_list.get(position);
        if (bus_service_list.get(position).getBus_service_list()!= null) {
            holder.txt_servicename.setText(bus_service_list.get(position).getBus_service_list());
        }



        holder.img_close.setOnClickListener(view -> {
            bus_service_list.remove(position);
            notifyDataSetChanged();
        });

    }

    @Override
    public int getItemCount() {
        return bus_service_list.size();
    }

    public static class AddExpViewHolder extends RecyclerView.ViewHolder {
        TextView txt_servicename;
        ImageView img_close;
        public AddExpViewHolder(View itemView) {
            super(itemView);
            txt_servicename = itemView.findViewById(R.id.txt_servicename);
            img_close = itemView.findViewById(R.id.img_close);


        }
    }
}