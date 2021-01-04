package com.petfolio.infinitus.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.petfolio.infinitus.R;
import com.petfolio.infinitus.requestpojo.DocBusInfoUploadRequest;
import com.petfolio.infinitus.responsepojo.DoctorDetailsByUserIdResponse;

import java.util.List;

public class DoctorAddEducAdapter extends RecyclerView.Adapter<DoctorAddEducAdapter.AddEduViewHolder> {
    Context context;
    List<DoctorDetailsByUserIdResponse.DataBean.EducationDetailsBean> education_detailsList;
    View view;

    public DoctorAddEducAdapter(Context context, List<DoctorDetailsByUserIdResponse.DataBean.EducationDetailsBean> education_detailsList) {
        this.context = context;
        this.education_detailsList = education_detailsList;

    }

    @NonNull
    @Override
    public AddEduViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_education_details_model, parent, false);
        return new AddEduViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddEduViewHolder holder, final int position) {
        final DoctorDetailsByUserIdResponse.DataBean.EducationDetailsBean educationDetailsBean = education_detailsList.get(position);
        if (educationDetailsBean.getEducation()!= null) {
            holder.eduName.setText(educationDetailsBean.getEducation());
        }

        if (educationDetailsBean.getYear()!= null) {
            holder.eduyr.setText(educationDetailsBean.getYear());
        }

        holder.removeImg.setOnClickListener(view -> {
            education_detailsList.remove(position);
            notifyDataSetChanged();
        });

    }

    @Override
    public int getItemCount() {
        return education_detailsList.size();
    }

    public static class AddEduViewHolder extends RecyclerView.ViewHolder {
        TextView eduName,eduyr;
        ImageView removeImg;
        public AddEduViewHolder(View itemView) {
            super(itemView);
            eduName = itemView.findViewById(R.id.educ_name);
            removeImg = itemView.findViewById(R.id.close);
            eduyr = itemView.findViewById(R.id.edu_yr);
        }
    }
}