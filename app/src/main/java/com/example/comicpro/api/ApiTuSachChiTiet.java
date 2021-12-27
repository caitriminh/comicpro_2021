package com.example.comicpro.api;

import com.example.comicpro.model.tusach.TuSachChiTiet;
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


public interface ApiTuSachChiTiet {
    Gson gson=new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();
    ApiTuSachChiTiet apiTuSachChiTiet=new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiTuSachChiTiet.class);

    @FormUrlEncoded
    @POST("TuSachChiTiet")
    Call < List <TuSachChiTiet> > TuSachChiTiet(@Field("action") String action,
                                       @Field("id_tusach") Integer id_tusach,
                                       @Field("matua") String matua);
}
