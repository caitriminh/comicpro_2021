package com.triminh.comicpro.api;


import com.triminh.comicpro.model.StatusDto;
import com.triminh.comicpro.model.lichphathanh.CTLichPhatHanh;
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


public interface ApiCTLichPhatHanh {
    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();
    ApiCTLichPhatHanh apiCtLichPhatHanh = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiCTLichPhatHanh.class);

    @FormUrlEncoded
    @POST("load_ct_lichphathanh")
    Call<List<CTLichPhatHanh>> GetCTLichPhatHanh(@Field("malich") String malich);

    @FormUrlEncoded
    @POST("delete_ct_lichphathanh")
    Call<List<CTLichPhatHanh>> Delete(@Field("id") Integer id);

    @FormUrlEncoded
    @POST("update_damua_lichphathanh")
    Call<List<CTLichPhatHanh>> DaMua(@Field("id") Integer id);

    @FormUrlEncoded
    @POST("UpdateCTLichPhatHanh")
    Call<List<StatusDto>> UpdateCTLichPhatHanh(@Field("id") Integer id,
                                               @Field("ngayphathanh") String ngayphathanh,
                                               @Field("tuatruyen") String tuatruyen,
                                               @Field("giabia") double giabia,
                                               @Field("nguoitd2") String nguoitd2);


    @FormUrlEncoded
    @POST("CreateCTLichPhatHanh")
    Call<List<StatusDto>> CreateCTLichPhatHanh(@Field("malich") String malich,
                                               @Field("ngayphathanh") String ngayphathanh,
                                               @Field("tuatruyen") String tuatruyen,
                                               @Field("giabia") double giabia,
                                               @Field("nguoitd") String nguoitd);

}
