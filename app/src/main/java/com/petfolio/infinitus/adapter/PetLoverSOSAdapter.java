package com.petfolio.infinitus.adapter;

import android.annotation.SuppressLint;
import android.content.Context;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.petfolio.infinitus.R;
import com.petfolio.infinitus.responsepojo.DoctorSearchResponse;
import com.petfolio.infinitus.responsepojo.PetLoverDashboardResponse;

import java.util.List;


public class PetLoverSOSAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private  String TAG = "PetLoverSOSAdapter";

    private Context context;





    private List<PetLoverDashboardResponse.DataBean.SOSBean> sosList;
    PetLoverDashboardResponse.DataBean.SOSBean currentItem;
    private int row_index;


    public PetLoverSOSAdapter(Context context, List<PetLoverDashboardResponse.DataBean.SOSBean> sosList) {
        this.sosList = sosList;
        this.context = context;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_sos_no_list, parent, false);
        return new ViewHolderOne(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        initLayoutOne((ViewHolderOne) holder, position);

    }

    @SuppressLint("SetTextI18n")
    private void initLayoutOne(ViewHolderOne holder, final int position) {

          currentItem = sosList.get(position);

          holder.txt_phn_num.setText(sosList.get(position).getNumber()+"");


        holder.ll_root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                row_index=position;
                notifyDataSetChanged();
                }




        });




        if(row_index==position){
            //holder.ll_root.setBackgroundResource(R.drawable.rounded_sos_bgm);
            holder.txt_phn_num.setTextColor(Color.parseColor("#ffffff"));//for both textviews
        }
        else
        {
            holder.ll_root.setBackgroundResource(R.drawable.user_bgm_trans);
            holder.txt_phn_num.setTextColor(Color.parseColor("#555555"));//for both textviews
        }





    }












    @Override
    public int getItemCount() {
        return sosList.size();



    }


    @Override
    public int getItemViewType(int position) {
        return position;
    }

    class ViewHolderOne extends RecyclerView.ViewHolder {
        public TextView txt_phn_num;
        public LinearLayout ll_root;




        public ViewHolderOne(View itemView) {
            super(itemView);

            txt_phn_num = itemView.findViewById(R.id.txt_phn_num);
            ll_root = itemView.findViewById(R.id.ll_root);




        }




    }










}
