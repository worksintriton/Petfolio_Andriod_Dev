package com.petfolio.infinitus.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.petfolio.infinitus.R;
import com.petfolio.infinitus.requestpojo.DocBusInfoUploadRequest;

import java.util.List;

public class AddGovtIdPdfAdapter extends RecyclerView.Adapter<AddGovtIdPdfAdapter.AddImageListHolder> {
    Context context;
    List<  DocBusInfoUploadRequest.GovtIdPicBean> govtIdPicBeans;
    View view;

    public AddGovtIdPdfAdapter(Context context, List<  DocBusInfoUploadRequest.GovtIdPicBean> govtIdPicBeans) {
        this.context = context;
        this.govtIdPicBeans = govtIdPicBeans;

    }

    @NonNull
    @Override
    public AddImageListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pdf_upload_model, parent, false);
        return new AddImageListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddImageListHolder holder, final int position) {
        final   DocBusInfoUploadRequest.GovtIdPicBean govtIdPicBeanse = govtIdPicBeans.get(position);
        if (govtIdPicBeanse.getGovt_id_pic()!= null) {


        }

        holder.removeImg.setOnClickListener(view -> {
            govtIdPicBeans.remove(position);
            notifyDataSetChanged();
        });

    }

    @Override
    public int getItemCount() {
        return govtIdPicBeans.size();
    }

    public static class AddImageListHolder extends RecyclerView.ViewHolder {
        ImageView removeImg,certificate_pics_1;
        public AddImageListHolder(View itemView) {
            super(itemView);
            certificate_pics_1 = itemView.findViewById(R.id.certificate_pics_1);
            removeImg = itemView.findViewById(R.id.close);
        }
    }


}