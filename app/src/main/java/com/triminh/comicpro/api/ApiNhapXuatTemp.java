package com.triminh.comicpro.api;



import com.triminh.comicpro.model.phieuxuat.NhapXuatTemp;
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


public interface ApiNhapXuatTemp {
    Gson gson=new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();
    ApiNhapXuatTemp apiNhapXuatTemp=new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiNhapXuatTemp.class);

    @FormUrlEncoded
    @POST("NhapXuatTemp")
    Call < List < NhapXuatTemp > > NhapXuatTemp(@Field("action") String action,
                                        @Field("id") Integer id,
                                        @Field("matruyen") String matruyen,
                                        @Field("soluong") Integer soluong,
                                        @Field("dongia") Double dongia,
                                        @Field("nguoitd") String nguoitd,
                                        @Field("loai") Integer loai,
                                        @Field("madonvi") String madonvi,
                                        @Field("diengiai") String diengiai);
}
