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
import com.petfolio.infinitus.interfaces.PetHandledTypeCheckedListener;
import com.petfolio.infinitus.responsepojo.DoctorDetailsResponse;
import com.petfolio.infinitus.responsepojo.DropDownListResponse;

import java.util.List;


public class DoctorClinicPetsHandledListAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final String TAG = "DoctorClinicPetsHandledListAdapter";
    private Context context;
    private List<DoctorDetailsResponse.DataBean.PetHandledBean> petHandledBeanList;
    DoctorDetailsResponse.DataBean.PetHandledBean currentItem;


    public DoctorClinicPetsHandledListAdapter(Context context, List<DoctorDetailsResponse.DataBean.PetHandledBean> pettypedataBeanList) {

        this.petHandledBeanList = pettypedataBeanList;
        this.context = context;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_sp_petlist, parent, false);
        return new ViewHolderOne(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        initLayoutOne((ViewHolderOne) holder, position);


    }

    private void initLayoutOne(ViewHolderOne holder, final int position) {

        currentItem = petHandledBeanList.get(position);

        if(currentItem.getPet_handled()!=null&&!currentItem.getPet_handled().isEmpty()){

            holder.txt_petname.setText(currentItem.getPet_handled());
        }





    }
    @Override
    public int getItemCount() {
        return petHandledBeanList.size();
    }


    @Override
    public int getItemViewType(int position) {
        return position;
    }

    static class ViewHolderOne extends RecyclerView.ViewHolder {

        public TextView txt_petname;



        public ViewHolderOne(View itemView) {
            super(itemView);

            txt_petname = itemView.findViewById(R.id.txt_petname);



        }

    }

}
