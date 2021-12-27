package com.example.comicpro.api;


import com.example.comicpro.model.danhmuc.LoaiTruyen;
import com.example.comicpro.model.tuatruyen.TuaTruyen;
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


public interface ApiLoaiTruyen {
    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();
    ApiLoaiTruyen apiLoaiTruyen = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiLoaiTruyen.class);

    @POST("load_loaitruyen")
    Call < List < LoaiTruyen > > GetLoaiTruyen();

    @FormUrlEncoded
    @POST("delete_loaitruyen")
    Call < List < LoaiTruyen > > Delete(@Field("maloai") String maloai);

    @FormUrlEncoded
    @POST("load_tuatruyen_loaitruyen")
    Call < List < TuaTruyen > > GetTuaTruyen(@Field("maloai") String maloai);
}
