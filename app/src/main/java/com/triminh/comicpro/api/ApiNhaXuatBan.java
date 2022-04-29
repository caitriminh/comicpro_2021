package com.triminh.comicpro.api;


import com.triminh.comicpro.model.danhmuc.NhaXuatBan;
import com.triminh.comicpro.model.tuatruyen.TuaTruyen;
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


public interface ApiNhaXuatBan {
    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();
    ApiNhaXuatBan apiNhaXuatBan = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiNhaXuatBan.class);

    @FormUrlEncoded
    @POST("load_nhaxuatban")
    Call < List < NhaXuatBan > > GetNhaXuatBan(@Field("option") Integer option,
                                               @Field("manxb") String manxb);

    @FormUrlEncoded
    @POST("load_tuatruyen_nxb")
    Call < List < TuaTruyen > > GetTuaTruyen(@Field("manxb") String manxb);



}
