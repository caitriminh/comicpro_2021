package com.triminh.comicpro.api;


import com.triminh.comicpro.model.danhmuc.TacGia;
import com.triminh.comicpro.model.tuatruyen.TuaTruyen;
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


public interface ApiTacGia {
    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();
    ApiTacGia apiTacGia = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiTacGia.class);

    @POST("load_tacgia")
    Call < List < TacGia > > GetTacGia();

    @FormUrlEncoded
    @POST("delete_tacgia")
    Call < List < TacGia > > Delete(@Field("matacgia") String matacgia);

    @FormUrlEncoded
    @POST("pro_insert_tacgia")
    Call < List < TacGia > > Insert(@Field("tacgia") String tacgia,
                                    @Field("nguoitd") String nguoitd);

    @FormUrlEncoded
    @POST("load_tuatruyen_tacgia")
    Call < List < TuaTruyen > > GetTuaTruyen(@Field("matacgia") String matacgia);

}
