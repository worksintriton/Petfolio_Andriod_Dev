package com.petfolio.infinitus.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.petfolio.infinitus.R;
import com.petfolio.infinitus.requestpojo.ServiceProviderRegisterFormCreateRequest;
import com.petfolio.infinitus.requestpojo.VendorRegisterFormCreateRequest;

import java.util.List;

public class VendorAddCertPdfAdapter extends RecyclerView.Adapter<VendorAddCertPdfAdapter.AddImageListHolder> {
    Context context;
    List<VendorRegisterFormCreateRequest.CertifiBean> bus_certif_list;
    View view;

    public VendorAddCertPdfAdapter(Context context, List<VendorRegisterFormCreateRequest.CertifiBean> bus_certif_list) {
        this.context = context;
        this.bus_certif_list = bus_certif_list;

    }

    @NonNull
    @Override
    public AddImageListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_pdf_upload, parent, false);
        return new AddImageListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddImageListHolder holder, final int position) {
        final VendorRegisterFormCreateRequest.CertifiBean certificatePicBean = bus_certif_list.get(position);
        if (certificatePicBean.getCertifi()!= null) {


        }

        holder.removeImg.setOnClickListener(view -> {
            bus_certif_list.remove(position);
            notifyDataSetChanged();
        });

    }

    @Override
    public int getItemCount() {
        return bus_certif_list.size();
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