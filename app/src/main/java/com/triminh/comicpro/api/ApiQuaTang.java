package com.triminh.comicpro.api;
import com.triminh.comicpro.model.danhmuc.QuaTang;
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

public interface ApiQuaTang {
    Gson gson=new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();
    ApiQuaTang apiQuaTang=new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiQuaTang.class);

    @POST("loadQuaTang")
    Call < List < QuaTang > > GetQuaTang();

    @FormUrlEncoded
    @POST("deleteQuaTang")
    Call < List < QuaTang > > Delete(@Field("maquatang") String maquatang);

    @FormUrlEncoded
    @POST("InsertQuaTang")
    Call < List < QuaTang > > Insert(@Field("action") String action,
                                     @Field("maquatang") String maquatang,
                                     @Field("quatang") String quatang,
                                     @Field("nguoitao") String nguoitao,
                                     @Field("nguoisua") String nguoisua);



}
