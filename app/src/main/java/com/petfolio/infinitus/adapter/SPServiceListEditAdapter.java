package com.petfolio.infinitus.adapter;

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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_specialization_list, parent, false);
        return new ViewHolderOne(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        initLayoutOne((ViewHolderOne) holder, position);


    }

    private void initLayoutOne(ViewHolderOne holder, final int position) {

        currentItem = spServiceList.get(position);



        holder.txt_spectypes.setText(currentItem.getService_list());

       /* holder.chx_spectypes.setChecked(currentItem.isSelected());

        holder.chx_spectypes.setTag(position);

       holder.chx_spectypes.setOnClickListener(v -> {

            Integer pos = (Integer) holder.chx_spectypes.getTag();

            if (spServiceList.get(pos).isSelected())
            {
                spServiceList.get(pos).setSelected(false);
                spServiceChckedListener.onItemSPServiceUnCheck(pos,spServiceList.get(pos).getService_list());

            }

            else
            {
                spServiceChckedListener.onItemSPServiceCheck(pos,spServiceList.get(pos).getService_list(),spServiceList);

            }

        });*/


        for(int i=0;i<spServiceListEdit.size();i++){
            if(null!=spServiceListEdit && null!=currentItem.getService_list() && spServiceListEdit.get(i).getBus_service_list().equalsIgnoreCase(currentItem.getService_list().trim())){
                holder.chx_spectypes.setChecked(true);
                Log.w(TAG,"ServiceEdit");


            }

        }



        holder.chx_spectypes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String chservice = spServiceList.get(position).getService_list();

                if(isChecked){
                    if (holder.chx_spectypes.isChecked()) {
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

        public TextView txt_spectypes;
        public CheckBox chx_spectypes;


        public ViewHolderOne(View itemView) {
            super(itemView);

            txt_spectypes = itemView.findViewById(R.id.spec_name);

            chx_spectypes = itemView.findViewById(R.id.checkbox_specialization_type);


        }

    }

}
