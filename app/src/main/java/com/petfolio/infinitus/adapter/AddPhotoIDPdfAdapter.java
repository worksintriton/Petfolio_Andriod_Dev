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

public class AddPhotoIDPdfAdapter extends RecyclerView.Adapter<AddPhotoIDPdfAdapter.AddImageListHolder> {
    Context context;
    List< DocBusInfoUploadRequest.PhotoIdPicBean> photoIdPicBeans;
    View view;

    public AddPhotoIDPdfAdapter(Context context, List<DocBusInfoUploadRequest.PhotoIdPicBean> photoIdPicBeans) {
        this.context = context;
        this.photoIdPicBeans = photoIdPicBeans;

    }

    @NonNull
    @Override
    public AddImageListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_pdf_upload, parent, false);
        return new AddImageListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddImageListHolder holder, final int position) {
        final DocBusInfoUploadRequest.PhotoIdPicBean photoIdPicBean = photoIdPicBeans.get(position);
        if (photoIdPicBean.getPhoto_id_pic()!= null) {


        }

        holder.removeImg.setOnClickListener(view -> {
            photoIdPicBeans.remove(position);
            notifyDataSetChanged();
        });

    }

    @Override
    public int getItemCount() {
        return photoIdPicBeans.size();
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