package com.example.comicpro.api;


import com.example.comicpro.model.thongke.ThongKeTheoNXB;
import com.example.comicpro.model.thongke.ThongKeTheoThang;
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


public interface ApiBieuDo {
    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();
    ApiBieuDo apiBieuDo = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiBieuDo.class);


    @POST("load_thongke_nxb_bieudo")
    Call < List < ThongKeTheoNXB > > GetBieuDoNXB();

    @FormUrlEncoded
    @POST("load_thongke_theothang")
    Call < List < ThongKeTheoThang > > GetBieuDoTheoThang(@Field("option") Integer option);


}
