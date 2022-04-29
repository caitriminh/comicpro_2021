package com.triminh.comicpro.api;


import com.triminh.comicpro.model.tonkho.KetChuyenSoDu;
import com.triminh.comicpro.model.tonkho.TonKho;
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


public interface ApiTonKho {
    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();
    ApiTonKho apiTonKho = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiTonKho.class);

    @POST("load_tuatruyen_tonkho")
    Call < List < TonKho > > GetTonKho();


    @POST("load_ketchuyen_sodu")
    Call < List < KetChuyenSoDu > > GetKetChuyen();

    @FormUrlEncoded
    @POST("delete_ketchuyen")
    Call < List < KetChuyenSoDu > > DeleteKetChuyen(@Field("ky") String ky);

    @POST("thuchien_ketchuyen")
    Call < List < KetChuyenSoDu > > ThucHienKetChuyen();


}
