package com.petfolio.infinitus.api;

import com.petfolio.infinitus.responsepojo.PetLoverDashboardResponse;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;


public class APIClient {



    private static Retrofit retrofit = null;
    private static OkHttpClient client;


   public static String BASE_LIVE_URL = "http://52.25.163.13:3000/api/";
    public static String BASE_DEV_URL = "http://54.212.108.156:3000/api/";
    public static String IMAGE_BASE_URL = "http://52.25.163.13:3000/";
    //public static String IMAGE_BASE_URL = "http://54.212.108.156:3000/";

    public static List<PetLoverDashboardResponse.DataBean.SOSBean> sosList;

    public static Retrofit getClient() {
        client = new OkHttpClient();
        client = new OkHttpClient.Builder()
                .readTimeout(3, TimeUnit.MINUTES)
                .connectTimeout(3, TimeUnit.MINUTES)
                .writeTimeout(3, TimeUnit.MINUTES )
                .cache(null)
                .build();
                retrofit = new Retrofit.Builder()
                .baseUrl(BASE_DEV_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        return retrofit;
    }
    public static Retrofit getImageClient() {
        client = new OkHttpClient();
        client = new OkHttpClient.Builder()
                .readTimeout(3, TimeUnit.MINUTES)
                .connectTimeout(3, TimeUnit.MINUTES)
                .writeTimeout(3, TimeUnit.MINUTES )
                .cache(null)
                .build();
                retrofit = new Retrofit.Builder()
                .baseUrl(IMAGE_BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        return retrofit;
    }
}
