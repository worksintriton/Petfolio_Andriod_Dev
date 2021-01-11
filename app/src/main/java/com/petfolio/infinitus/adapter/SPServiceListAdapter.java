package com.petfolio.infinitus.adapter;

import android.annotation.SuppressLint;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.CheckBox;
import android.widget.CompoundButton;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.petfolio.infinitus.R;
import com.petfolio.infinitus.interfaces.SPServiceCheckedListener;
import com.petfolio.infinitus.requestpojo.ServiceProviderRegisterFormCreateRequest;
import com.petfolio.infinitus.responsepojo.SPServiceListResponse;

import java.util.ArrayList;
import java.util.List;



public class SPServiceListAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private  String TAG = "SPServiceListAdapter";
    private Context mcontext;
    private List<SPServiceListResponse.DataBean.ServiceListBean> spServiceList;
    SPServiceListResponse.DataBean.ServiceListBean currentItem;
    private SPServiceCheckedListener spServiceCheckedListener;
    private List<SPServiceListResponse.DataBean.TimeBean> spTimeList;
    private String strTimeslot;
    private boolean isChbxChecked;
    private String chservice;
    private Integer amount;
    List<ServiceProviderRegisterFormCreateRequest.BusServiceListBean> bus_service_list = new ArrayList<>();



    public SPServiceListAdapter(Context context,List<SPServiceListResponse.DataBean.ServiceListBean> spServiceList,  SPServiceCheckedListener spServiceCheckedListener,List<SPServiceListResponse.DataBean.TimeBean> spTimeList, List<ServiceProviderRegisterFormCreateRequest.BusServiceListBean> bus_service_list) {
        this.spServiceList = spServiceList;
        this.mcontext = context;
        this.spServiceCheckedListener = spServiceCheckedListener;
        this.spTimeList = spTimeList;
        this.bus_service_list = bus_service_list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_sp_service_list, parent, false);
        return new ViewHolderOne(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        initLayoutOne((ViewHolderOne) holder, position);


    }

    @SuppressLint("SetTextI18n")
    private void initLayoutOne(ViewHolderOne holder, final int position) {

        currentItem = spServiceList.get(position);
        holder.txt_servicename.setText(currentItem.getService_list());
        if(bus_service_list != null && bus_service_list.size() > 0) {
            for(int i=0; i<bus_service_list.size();i++){
                if(bus_service_list.get(i).getAmount() != null){
                    holder.txt_amount.setText(bus_service_list.get(i).getAmount() + "");
                }  if(bus_service_list.get(i).getTime_slots() != null){
                    holder.txt_timeslottype.setText(bus_service_list.get(i).getTime_slots());
                }
            }
           /* if(bus_service_list.get(position).getAmount() != null){
                holder.txt_amount.setText(bus_service_list.get(position).getAmount() + "");
            }
            holder.txt_timeslottype.setText(bus_service_list.get(position).getTime_slots());
            if(spServiceList.get(position).isChbxChecked()){
                holder.checkbox_service_type.setChecked(true);
            }*/

        }


        holder.checkbox_service_type.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                 chservice = spServiceList.get(position).getService_list();
                spServiceList.get(position).setBus_service_list(chservice);
                spServiceList.get(position).setTime_slots(strTimeslot);
                spServiceList.get(position).setAmount(amount);

                 isChbxChecked =  isChecked;

                if(isChecked){
                    if (holder.checkbox_service_type.isChecked()) {
                        spServiceCheckedListener.onItemSPServiceCheck(position,chservice,isChbxChecked);
                    }

                }else{
                    spServiceCheckedListener.onItemSPServiceUnCheck(position,chservice,isChbxChecked);

                }

            }
        });







    }
    @Override
    public int getItemCount() {
        return spServiceList.size();
    }


    @Override
    public int getItemViewType(int position) {
        return position;
    }

    static class ViewHolderOne extends RecyclerView.ViewHolder {

        public TextView txt_servicename;
        public CheckBox checkbox_service_type;
        public TextView txt_amount,txt_timeslottype;


        public ViewHolderOne(View itemView) {
            super(itemView);
            txt_servicename = itemView.findViewById(R.id.txt_servicename);
            checkbox_service_type = itemView.findViewById(R.id.checkbox_service_type);
            txt_timeslottype = itemView.findViewById(R.id.txt_timeslottype);
            txt_amount = itemView.findViewById(R.id.txt_amount);


        }

    }





}
