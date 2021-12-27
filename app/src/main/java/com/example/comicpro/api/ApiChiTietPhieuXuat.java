package com.example.comicpro.api;


import com.example.comicpro.model.phieuxuat.ChiTietPhieuXuat;
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


public interface ApiChiTietPhieuXuat {
    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();
    ApiChiTietPhieuXuat apiChiTietPhieuXuat = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiChiTietPhieuXuat.class);

    @FormUrlEncoded
    @POST("load_chitiet_tentruyen")
    Call < List < ChiTietPhieuXuat > > GetChiTietPhieuXuat(@Field("option") Integer option,
                                                           @Field("madonvi") String madonvi,
                                                           @Field("maphieu") String maphieu);

    @FormUrlEncoded
    @POST("delete_ct_phieunhap")
    Call < List < ChiTietPhieuXuat > > Delete(@Field("id") Integer id);
}
