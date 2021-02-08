package com.petfolio.infinitus.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.petfolio.infinitus.R;
import com.petfolio.infinitus.petlover.DoctorClinicDetailsActivity;
import com.petfolio.infinitus.responsepojo.NotificationGetlistResponse;
import com.petfolio.infinitus.sessionmanager.SessionManager;

import java.util.HashMap;
import java.util.List;


import de.hdodenhof.circleimageview.CircleImageView;


public class NotificationDashboardAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder> {
   
    private  String TAG = "NotificationDashboardAdapter";
    private Context context;
 
    NotificationGetlistResponse.DataBean currentItem;
  private List<NotificationGetlistResponse.DataBean> notificationGetlistResponseList;
   

    SessionManager session;



   public NotificationDashboardAdapter(Context context, List<NotificationGetlistResponse.DataBean> notificationGetlistResponseList) {
        this.notificationGetlistResponseList = notificationGetlistResponseList;
        this.context = context;

       

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_notifications_cardview, parent, false);
        return new ViewHolderOne(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        initLayoutOne((ViewHolderOne) holder, position);


    }

  private void initLayoutOne(ViewHolderOne holder, final int position) {
        currentItem = notificationGetlistResponseList.get(position);
        holder.txt_message.setText(currentItem.getNotify_title());
        holder.txt_date.setText(currentItem.getDate_and_time());
        if (currentItem.getNotify_img() != null && !currentItem.getNotify_img().isEmpty()) {

            Glide.with(context)
                    .load(currentItem.getNotify_img())
                    //.load(R.drawable.logo)
                    .into(holder.img_notify_imge);

        }
        else{
            Glide.with(context)
                    .load(R.drawable.app_logo)
                    .into(holder.img_notify_imge);

        }
        holder.ll_root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*Intent intent = new Intent(context, DoctorClinicDetailsActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("doctorid",doctorDetailsResponseList.get(position).getUser_id());
                intent.putExtra("doctorname",doctorDetailsResponseList.get(position).getDoctor_name());
                intent.putExtra("reviewcount",doctorDetailsResponseList.get(position).getReview_count());
                intent.putExtra("starcount",doctorDetailsResponseList.get(position).getStar_count());
                intent.putExtra("distance",doctorDetailsResponseList.get(position).getDistance());
                intent.putExtra("fromactivity", "PetCareFragment");
                Log.w(TAG,"doctorid :"+doctorDetailsResponseList.get(position).getUser_id());
                context.startActivity(intent);*/
            }




        });

   }

   @Override
    public int getItemCount() {
        
        return notificationGetlistResponseList.size();
    }
   

    @Override
    public int getItemViewType(int position) {
        return position;
    }
    class ViewHolderOne extends RecyclerView.ViewHolder {
        public TextView txt_message,txt_date;
        public LinearLayout ll_root;
        public ImageView img_notify_imge;



        public ViewHolderOne(View itemView) {
            super(itemView);

            txt_message = itemView.findViewById(R.id.txt_message);
            txt_date = itemView.findViewById(R.id.txt_date);
            img_notify_imge = itemView.findViewById(R.id.img_notify_imge);
            ll_root = itemView.findViewById(R.id.ll_root);

        }

    }

}
