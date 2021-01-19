package com.petfolio.infinitus.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.petfolio.infinitus.R;
import com.petfolio.infinitus.interfaces.SPServiceChckedListener;
import com.petfolio.infinitus.responsepojo.SPServiceListResponse;
import com.petfolio.infinitus.responsepojo.ServiceProviderRegisterFormCreateResponse;

import java.util.List;


public class SPServiceListEditAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private  String TAG = "SPServiceListEditAdapter";
    private Context mcontext;
    private List<SPServiceListResponse.DataBean.ServiceListBean> spServiceList;
    private List<ServiceProviderRegisterFormCreateResponse.DataBean.BusServiceListBean> spServiceListEdit;
    SPServiceListResponse.DataBean.ServiceListBean currentItem;
    private SPServiceChckedListener spServiceChckedListener;


    public SPServiceListEditAdapter(Context context, List<SPServiceListResponse.DataBean.ServiceListBean> spServiceList,List<ServiceProviderRegisterFormCreateResponse.DataBean.BusServiceListBean> spServiceListEdit, SPServiceChckedListener spServiceChckedListener) {
        this.spServiceList = spServiceList;
        this.spServiceListEdit = spServiceListEdit;
        this.mcontext = context;
        this.spServiceChckedListener = spServiceChckedListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_edit_sp_service_list, parent, false);
        return new ViewHolderOne(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        initLayoutOne((ViewHolderOne) holder, position);


    }

    @SuppressLint("SetTextI18n")
    private void initLayoutOne(ViewHolderOne holder, final int position) {
            currentItem = spServiceList.get(position);
            holder.txt_servicename.setText(spServiceList.get(position).getService_list());


            Log.w(TAG,"spServiceListEdit : "+new Gson().toJson(spServiceListEdit));

        for(int i=0;i<spServiceListEdit.size();i++){
            if(null != currentItem.getService_list() && spServiceListEdit.get(i).getBus_service_list().equalsIgnoreCase(currentItem.getService_list().trim())){
                holder.checkbox_service_type.setChecked(true);
                holder.txt_timeslottype.setText(spServiceListEdit.get(i).getTime_slots());
                holder.txt_amount.setText(spServiceListEdit.get(i).getAmount()+"");
                Log.w(TAG,"ServiceEdit");


            }

        }



        holder.checkbox_service_type.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String chservice = spServiceList.get(position).getService_list();

                if(isChecked){
                    if (holder.checkbox_service_type.isChecked()) {
                        spServiceChckedListener.onItemSPServiceCheck(position,chservice);
                    }

                }else{

                    spServiceChckedListener.onItemSPServiceUnCheck(position,chservice);

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
