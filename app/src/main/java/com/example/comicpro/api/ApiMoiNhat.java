package com.example.comicpro.api;



import com.example.comicpro.model.phieunhap.MoiNhat;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

import static com.example.comicpro.system.ComicPro.BASE_URL;


public interface ApiMoiNhat {
    Gson gson=new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();
    ApiMoiNhat apiMoiNhat=new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiMoiNhat.class);

    @FormUrlEncoded
    @POST("load_nhatky_muahang")
    Call < List < MoiNhat > > MoiNhat(@Field("option") Integer option,
                                   @Field("thang") Integer thang,
                                   @Field("nam") Integer nam);
}
