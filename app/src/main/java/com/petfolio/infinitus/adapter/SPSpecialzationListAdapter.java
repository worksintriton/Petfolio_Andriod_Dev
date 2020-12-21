package com.petfolio.infinitus.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.petfolio.infinitus.R;
import com.petfolio.infinitus.interfaces.SPSpecialzationChckedListener;
import com.petfolio.infinitus.responsepojo.SPServiceListResponse;

import java.util.List;


public class SPSpecialzationListAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private  String TAG = "SPSpecialzationListAdapter";
    private Context mcontext;
    private List<SPServiceListResponse.DataBean.SpecializationBean> spSpecialzationList;
    SPServiceListResponse.DataBean.SpecializationBean currentItem;
    private SPSpecialzationChckedListener spSpecialzationChckedListener;


    public SPSpecialzationListAdapter(Context context, List<SPServiceListResponse.DataBean.SpecializationBean> spSpecialzationList, SPSpecialzationChckedListener spSpecialzationChckedListener) {
        this.spSpecialzationList = spSpecialzationList;
        this.mcontext = context;
        this.spSpecialzationChckedListener = spSpecialzationChckedListener;
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

        currentItem = spSpecialzationList.get(position);



        holder.txt_spectypes.setText(currentItem.getSpecialization());

        holder.chx_spectypes.setChecked(currentItem.isSelected());

        holder.chx_spectypes.setTag(position);

        holder.chx_spectypes.setOnClickListener(v -> {

            Integer pos = (Integer) holder.chx_spectypes.getTag();

            if (spSpecialzationList.get(pos).isSelected())
            {
                spSpecialzationList.get(pos).setSelected(false);

                spSpecialzationChckedListener.onItemSPSpecialzationUnCheck(pos,spSpecialzationList.get(pos).getSpecialization());

            }

            else
            {
                spSpecialzationChckedListener.onItemSPSpecialzationCheck(pos,spSpecialzationList.get(pos).getSpecialization(),spSpecialzationList);

            }

        });

    }
    @Override
    public int getItemCount() {
        return spSpecialzationList.size();
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
