package com.example.comicpro.api;



import com.example.comicpro.model.danhmuc.QuocGia;
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


public interface ApiQuocGia {
    Gson gson=new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();
    ApiQuocGia apiQuocGia=new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiQuocGia.class);

    @FormUrlEncoded
    @POST("Nation")
    Call < List < QuocGia > > QuocGia(@Field("action") String action,
                                   @Field("id") Integer id,
                                   @Field("quocgia") String quocgia,
                                   @Field("nguoitd") String nguoitd, @Field("nguoitd2") String nguoitd2);

}
