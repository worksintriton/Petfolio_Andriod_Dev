package com.petfolio.infinitus.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.petfolio.infinitus.R;
import com.petfolio.infinitus.responsepojo.LocationListAddressResponse;
import com.petfolio.infinitus.responsepojo.PetListResponse;

import java.util.List;


public class ManagePetListAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private  String TAG = "ManagePetListAdapter";
    private List<PetListResponse.DataBean> petListResponseList  = null;
    private Context context;

    PetListResponse.DataBean currentItem;




    public static String id = "";


    public ManagePetListAdapter(Context context, List<PetListResponse.DataBean> petListResponseList, RecyclerView inbox_list) {
        this.petListResponseList = petListResponseList;
        this.context = context;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_petlist, parent, false);
        return new ViewHolderOne(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        initLayoutOne((ViewHolderOne) holder, position);


    }

    @SuppressLint("SetTextI18n")
    private void initLayoutOne(ViewHolderOne holder, final int position) {
        currentItem = petListResponseList.get(position);
        holder.txt_pet_name.setText(petListResponseList.get(position).getPet_name());

        if (petListResponseList.get(position).getPet_img() != null && !petListResponseList.get(0).getPet_img().isEmpty()) {

            Glide.with(context)
                    .load(petListResponseList.get(0).getPet_img())
                    .into(holder.img_pet_imge);

        }
        else{
            Glide.with(context)
                    .load(R.drawable.image_thumbnail)
                    .into(holder.img_pet_imge);

        }



        holder.img_settings.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Creating the instance of PopupMenu
                PopupMenu popup = new PopupMenu(context, v);
                //Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        return true;
                    }
                });

                popup.show();//showing popup menu
            }
        });
        //closing the setOnClickListener method

        if(position == petListResponseList.size()){
            holder.ll_add.setVisibility(View.VISIBLE);
        }else{
            holder.ll_add.setVisibility(View.GONE);

        }

    }









    @Override
    public int getItemCount() {
        return petListResponseList.size();
    }


    @Override
    public int getItemViewType(int position) {
        return position;
    }

    class ViewHolderOne extends RecyclerView.ViewHolder {
        public TextView txt_pet_name;
        public ImageView img_pet_imge,img_settings;
        public LinearLayout ll_add;





        public ViewHolderOne(View itemView) {
            super(itemView);
            img_pet_imge = itemView.findViewById(R.id.img_pet_imge);
            img_settings = itemView.findViewById(R.id.img_settings);
            txt_pet_name = itemView.findViewById(R.id.txt_pet_name);
            ll_add = itemView.findViewById(R.id.ll_add);
            ll_add.setVisibility(View.GONE);



        }




    }







}
