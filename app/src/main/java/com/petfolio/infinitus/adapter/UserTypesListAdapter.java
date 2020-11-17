package com.petfolio.infinitus.adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.petfolio.infinitus.R;
import com.petfolio.infinitus.interfaces.UserTypeSelectListener;
import com.petfolio.infinitus.responsepojo.UserTypeListResponse;

import java.util.List;


public class UserTypesListAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private  String TAG = "UserTypesListAdapter";
    private Context context;
    private UserTypeSelectListener userTypeSelectListener;



   private List<UserTypeListResponse.DataBean.UsertypedataBean> usertypedataBeanList;
    UserTypeListResponse.DataBean.UsertypedataBean currentItem;

    int selectedPosition=-1;


    public UserTypesListAdapter(Context context, List<UserTypeListResponse.DataBean.UsertypedataBean> usertypedataBeanList,UserTypeSelectListener userTypeSelectListener) {
        this.usertypedataBeanList = usertypedataBeanList;
        this.context = context;
        this.userTypeSelectListener = userTypeSelectListener;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_usertypes_cardview, parent, false);
        return new ViewHolderOne(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        initLayoutOne((ViewHolderOne) holder, position);


    }

    private void initLayoutOne(ViewHolderOne holder, final int position) {
        currentItem = usertypedataBeanList.get(position);
        holder.txt_usertypes.setText(currentItem.getUser_type_title());
        if (currentItem.getUser_type_img() != null && !currentItem.getUser_type_img().isEmpty()) {

                Glide.with(context)
                        .load(currentItem.getUser_type_img())
                        .into(holder.img_userimages);

            }
        else{
                Glide.with(context)
                        .load(R.mipmap.ic_launcher)
                        .into(holder.img_userimages);

            }
        holder.ll_usertypes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedPosition=position;
                notifyDataSetChanged();
                userTypeSelectListener.userTypeSelectListener(usertypedataBeanList.get(position).getUser_type_title(),usertypedataBeanList.get(position).getUser_type_value());

            }
        });


        if(selectedPosition==position){
            holder.ll_usertypes.setBackgroundResource(R.drawable.rectangle_corner_bg_darkgray);
            holder.chx_usertypes.setChecked(true);}

        else{
            holder.ll_usertypes.setBackgroundResource(R.drawable.rectangle_corner_bg_gray);
            holder.chx_usertypes.setChecked(false);
        }


    }
    @Override
    public int getItemCount() {
        return usertypedataBeanList.size();
    }


    @Override
    public int getItemViewType(int position) {
        return position;
    }

    static class ViewHolderOne extends RecyclerView.ViewHolder {
        public TextView txt_usertypes;
        public ImageView img_userimages;
        public LinearLayout ll_usertypes;
        public CheckBox chx_usertypes;



        public ViewHolderOne(View itemView) {
            super(itemView);

            txt_usertypes = itemView.findViewById(R.id.txt_usertypes);
            img_userimages = itemView.findViewById(R.id.img_userimages);
            ll_usertypes = itemView.findViewById(R.id.ll_usertypes);
            chx_usertypes = itemView.findViewById(R.id.chx_usertypes);
            chx_usertypes.setClickable(false);










        }




    }














}