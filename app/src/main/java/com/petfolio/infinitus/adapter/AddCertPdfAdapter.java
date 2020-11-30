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

public class AddCertPdfAdapter extends RecyclerView.Adapter<AddCertPdfAdapter.AddImageListHolder> {
    Context context;
    List<DocBusInfoUploadRequest.CertificatePicBean > certificatePicBeans;
    View view;

    public AddCertPdfAdapter(Context context,List<DocBusInfoUploadRequest.CertificatePicBean> certificatePicBean) {
        this.context = context;
        this.certificatePicBeans = certificatePicBean;

    }

    @NonNull
    @Override
    public AddImageListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_pdf_upload, parent, false);
        return new AddImageListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddImageListHolder holder, final int position) {
        final DocBusInfoUploadRequest.CertificatePicBean certificatePicBean = certificatePicBeans.get(position);
        if (certificatePicBean.getCertificate_pic()!= null) {


        }

        holder.removeImg.setOnClickListener(view -> {
            certificatePicBeans.remove(position);
            notifyDataSetChanged();
        });

    }

    @Override
    public int getItemCount() {
        return certificatePicBeans.size();
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