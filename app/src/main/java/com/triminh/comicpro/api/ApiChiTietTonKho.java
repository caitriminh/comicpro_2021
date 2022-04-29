package com.triminh.comicpro.api;



import com.triminh.comicpro.model.tonkho.ChiTietTonKho;
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


public interface ApiChiTietTonKho {
    Gson gson=new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();
    ApiChiTietTonKho apiChiTietTonKho=new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiChiTietTonKho.class);

    @FormUrlEncoded
    @POST("load_chitiet_tonkho")
    Call < List < ChiTietTonKho > > GetTonKho(@Field("matua") String matua);
}
