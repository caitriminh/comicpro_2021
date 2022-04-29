package com.triminh.comicpro.api;


import com.triminh.comicpro.model.nguoidung.User;
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


public interface ApiUser {
    Gson gson=new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();
    ApiUser apiUser=new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiUser.class);

    @FormUrlEncoded
    @POST("GetUser")
    Call < List < User > > GetUser(@Field("action") String action,
                                   @Field("tendangnhap") String tendangnhap,
                                   @Field("hoten") String hoten,
                                   @Field("matkhau") String matkhau,
                                   @Field("truycap") boolean truycap,
                                   @Field("nguoitd") String nguoitd);


}
