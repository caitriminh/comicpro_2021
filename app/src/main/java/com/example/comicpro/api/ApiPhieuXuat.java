package com.example.comicpro.api;

import com.example.comicpro.model.phieuxuat.PhieuXuat;
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


public interface ApiPhieuXuat {
    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();
    ApiPhieuXuat apiPhieuXuat = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiPhieuXuat.class);

    @FormUrlEncoded
    @POST("load_phieuxuat")
    Call < List < PhieuXuat > > GetPhieuXuat(@Field("option") Integer option,
                                             @Field("tungay") String tungay,
                                             @Field("denngay") String denngay);

    @FormUrlEncoded
    @POST("delete_phieunhapxuat")
    Call < List < PhieuXuat > > Delete(@Field("maphieu") String maphieu);

}
