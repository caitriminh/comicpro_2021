package com.triminh.comicpro.api;


import com.triminh.comicpro.model.danhmuc.DonVi;
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


public interface ApiDonVi {
    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();
    ApiDonVi apiDonVi = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiDonVi.class);

    @FormUrlEncoded
    @POST("load_khachhang")
    Call < List < DonVi > > GetDonVi(@Field("option") Integer option,
                                     @Field("madonvi") String madonvi,
                                     @Field("manhom") Integer manhom);

    @FormUrlEncoded
    @POST("insert_donvi")
    Call < List < DonVi > > Insert(@Field("donvi") String donvi,
                                   @Field("diachi") String diachi,
                                   @Field("sodt") String sodt,
                                   @Field("sofax") String sofax,
                                   @Field("email") String email,
                                   @Field("ghichu") String ghichu,
                                   @Field("manhom") Integer manhom,
                                   @Field("nguoitd") String nguoitd);

    @FormUrlEncoded
    @POST("delete_donvi")
    Call < List < DonVi > > Delete(@Field("madonvi") String madonvi);

    @FormUrlEncoded
    @POST("update_donvi")
    Call < List < DonVi > > Update(@Field("madonvi") String madonvi,
                                   @Field("donvi") String donvi,
                                   @Field("diachi") String diachi,
                                   @Field("sodt") String sodt,
                                   @Field("sofax") String sofax,
                                   @Field("email") String email,
                                   @Field("ghichu") String ghichu,
                                   @Field("nguoitd2") String nguoitd2);

}
