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
import com.petfolio.infinitus.requestpojo.ServiceProviderRegisterFormCreateRequest;
import com.petfolio.infinitus.responsepojo.ServiceProviderRegisterFormCreateResponse;

import java.util.ArrayList;
import java.util.List;

public class EditCertPdfAdapter extends RecyclerView.Adapter<EditCertPdfAdapter.AddImageListHolder> {
    Context context;
    private List<ServiceProviderRegisterFormCreateResponse.DataBean.BusCertifBean> bus_certif_list_edit;
    private List<ServiceProviderRegisterFormCreateRequest.BusCertifBean> bus_certif_list;

    View view;

    public EditCertPdfAdapter(Context context,  List<ServiceProviderRegisterFormCreateResponse.DataBean.BusCertifBean> bus_certif_list_edit,List<ServiceProviderRegisterFormCreateRequest.BusCertifBean> bus_certif_list) {
        this.context = context;
        this.bus_certif_list_edit = bus_certif_list_edit;
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
        final ServiceProviderRegisterFormCreateResponse.DataBean.BusCertifBean certificatePicBean = bus_certif_list_edit.get(position);
        if (certificatePicBean.getBus_certif()!= null) {


        }

        holder.removeImg.setOnClickListener(view -> {
            bus_certif_list_edit.remove(position);
            bus_certif_list.remove(position);
            notifyDataSetChanged();
        });

    }

    @Override
    public int getItemCount() {
        return bus_certif_list_edit.size();
    }

    public static class AddImageListHolder extends RecyclerView.ViewHolder {
        ImageView removeImg,pdf_file;
        public AddImageListHolder(View itemView) {
            super(itemView);
            pdf_file = itemView.findViewById(R.id.pdf_file);
            removeImg = itemView.findViewById(R.id.close);
        }
    }


}