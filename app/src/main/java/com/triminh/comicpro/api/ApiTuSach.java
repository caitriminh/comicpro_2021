package com.triminh.comicpro.api;


import com.triminh.comicpro.model.tusach.TuSach;
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


public interface ApiTuSach {
    Gson gson=new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();
    ApiTuSach apiTuSach=new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiTuSach.class);

    @FormUrlEncoded
    @POST("TuSach")
    Call < List <TuSach> > TuSach(@Field("action") String action,
                                @Field("id") Integer id,
                                @Field("tusach") String tusach);
}
