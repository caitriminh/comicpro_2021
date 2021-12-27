package com.example.comicpro.api;



import com.example.comicpro.model.lichphathanh.LichPhatHanh;
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


public interface ApiLichPhatHanh {
    Gson gson=new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();
    ApiLichPhatHanh apiLichPhatHanh=new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiLichPhatHanh.class);

    @FormUrlEncoded
    @POST("load_lichphathanh")
    Call < List < LichPhatHanh > > GetLichPhatHanh(@Field("action") String action,
                                                   @Field("nam") Integer nam);

    @FormUrlEncoded
    @POST("insert_lichphathanh")
    Call < List < LichPhatHanh > > Insert(@Field("ngaythang") String ngaythang,
                                          @Field("manxb") String manxb,
                                          @Field("ghichu") String ghichu,
                                          @Field("nguoitd") String nguoitd);

    @FormUrlEncoded
    @POST("delete_lichphathanh")
    Call < List < LichPhatHanh > > Delete(@Field("malich") String malich);

    @FormUrlEncoded
    @POST("GetNamLichPhatHanh")
    Call < List < LichPhatHanh > > GetNam(@Field("action") String action);
}
