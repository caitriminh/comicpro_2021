package com.triminh.comicpro.api;



import com.triminh.comicpro.model.danhmuc.DonViTinh;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

import static com.triminh.comicpro.system.ComicPro.BASE_URL;


public interface ApiDonViTinh {
    Gson gson=new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();
    ApiDonViTinh apiDonViTinh=new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiDonViTinh.class);

    @FormUrlEncoded
    @POST("Unit")
    Call < List < DonViTinh > > Unit(@Field("action") String action,
                                     @Field("id") Integer id,
                                     @Field("donvitinh") String donvitinh,
                                     @Field("nguoitd") String nguoitd, @Field("nguoitd2") String nguoitd2);
}
