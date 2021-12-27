package com.example.comicpro.api;


import com.example.comicpro.model.danhmuc.LoaiBia;
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


public interface ApiLoaiBia {
    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();
    ApiLoaiBia apiLoaiBia = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiLoaiBia.class);

    @POST("load_loaibia")
    Call < List < LoaiBia > > GetLoaiBia();

    @FormUrlEncoded
    @POST("delete_loaibia")
    Call < List < LoaiBia > > Delete(@Field("id") Integer id);

    @FormUrlEncoded
    @POST("insert_loaibia")
    Call < List < LoaiBia > > Insert(@Field("loaibia") String loaibia,
                                     @Field("nguoitd") String nguoitd);


}
