package com.carpeinfinitus.petfolio.adapter;

import android.content.Context;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.carpeinfinitus.petfolio.R;
import com.carpeinfinitus.petfolio.api.APIClient;
import com.carpeinfinitus.petfolio.responsepojo.FilterDoctorResponse;

import java.util.List;


public class ViewPagerPetCareFilterAdapter extends PagerAdapter {
    private FilterDoctorResponse.BannerBean currentItem;
    List<FilterDoctorResponse.BannerBean> doctorFilterBannerResponseList;

    private Context context;
    private LayoutInflater inflater;
    private View itemView;

    private String TAG = "ViewPagerClinicDetailsAdapter";

    public ViewPagerPetCareFilterAdapter(Context context1,  List<FilterDoctorResponse.BannerBean> doctorFilterBannerResponseList){

        this.context = context1;
        this.doctorFilterBannerResponseList = doctorFilterBannerResponseList;

    }



    @Override
    public int getCount() {
        return doctorFilterBannerResponseList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view.equals(o);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);

    }


    @Override
    public Object instantiateItem(ViewGroup view, int position) {

        View itemView = LayoutInflater.from(view.getContext()).inflate(R.layout.sliding_image, view, false);

        ImageView imageView = itemView.findViewById(R.id.itemImage);


        try {
            String imageURL = doctorFilterBannerResponseList.get(position).getImage_path();
            if(imageURL != null && !imageURL.isEmpty()){
                Glide.with(context)
                        .load(imageURL)
                        .into(imageView);
            }else{
                Glide.with(context)
                        .load(APIClient.BANNER_IMAGE_URL)
                        .into(imageView);

            }


        } catch (Exception e) {
            // Handle the condition when str is not a number.
        }






        view.addView(itemView);

        return itemView;

    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }
}
