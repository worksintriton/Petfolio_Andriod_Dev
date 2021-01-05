package com.petfolio.infinitus.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.petfolio.infinitus.R;
import com.petfolio.infinitus.api.APIClient;
import com.petfolio.infinitus.api.RestApiInterface;
import com.petfolio.infinitus.doctor.DoctorBusinessInfoActivity;
import com.petfolio.infinitus.interfaces.SPServiceChckedListener;
import com.petfolio.infinitus.interfaces.SPServiceCheckedListener;
import com.petfolio.infinitus.interfaces.SpecTypeChckedListener;
import com.petfolio.infinitus.responsepojo.DropDownListResponse;
import com.petfolio.infinitus.responsepojo.SPServiceListResponse;
import com.petfolio.infinitus.utils.RestUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


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


    public SPServiceListAdapter(Context context,List<SPServiceListResponse.DataBean.ServiceListBean> spServiceList,  SPServiceCheckedListener spServiceCheckedListener,List<SPServiceListResponse.DataBean.TimeBean> spTimeList) {
        this.spServiceList = spServiceList;
        this.mcontext = context;
        this.spServiceCheckedListener = spServiceCheckedListener;
        this.spTimeList = spTimeList;
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

    private void initLayoutOne(ViewHolderOne holder, final int position) {

        currentItem = spServiceList.get(position);
        holder.txt_servicename.setText(currentItem.getService_list());
        setTimeListtype(holder,position);
        holder.checkbox_service_type.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                 chservice = spServiceList.get(position).getService_list();

                isChbxChecked =  isChecked;

                if(isChecked){
                    if (holder.checkbox_service_type.isChecked()) {
                        spServiceCheckedListener.onItemSPServiceCheck(position,chservice,strTimeslot);
                    }

                }else{
                    spServiceCheckedListener.onItemSPServiceUnCheck(position,chservice,strTimeslot);

                }

            }
        });

        holder.spr_timeslottype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int arg2, long arg3) {
               String  strTimeslot = holder.spr_timeslottype.getSelectedItem().toString();
                Log.w(TAG,"strTimeslot : "+strTimeslot);

                if(isChbxChecked){
                    if (holder.checkbox_service_type.isChecked()) {
                        spServiceCheckedListener.onItemSPServiceCheck(position,chservice,strTimeslot);
                    }

                }else{
                    spServiceCheckedListener.onItemSPServiceUnCheck(position,chservice,strTimeslot);

                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

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
        public Spinner spr_timeslottype;


        public ViewHolderOne(View itemView) {
            super(itemView);
            txt_servicename = itemView.findViewById(R.id.txt_servicename);
            checkbox_service_type = itemView.findViewById(R.id.checkbox_service_type);
            spr_timeslottype = itemView.findViewById(R.id.spr_timeslottype);


        }

    }

    private void setTimeListtype(ViewHolderOne holder, int position) {
        ArrayList<String> timetypeArrayList = new ArrayList<>();
        //timetypeArrayList.add("Select Time Type");
        if(spTimeList != null && spTimeList.size()>0){
            for (int i = 0; i < spTimeList.size(); i++) {
                strTimeslot = spTimeList.get(0).getTime();
                String timeType = spTimeList.get(i).getTime();
                timetypeArrayList.add(timeType);
                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(mcontext, R.layout.spinner_item, timetypeArrayList);
                spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item); // The drop down view
                holder.spr_timeslottype.setAdapter(spinnerArrayAdapter);


            }

        }

    }



}
