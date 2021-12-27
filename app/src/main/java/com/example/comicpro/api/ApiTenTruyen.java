package com.example.comicpro.api;


import com.example.comicpro.model.tentruyen.TenTruyen;
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


public interface ApiTenTruyen {
    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();
    ApiTenTruyen apiTenTruyen = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiTenTruyen.class);

    @FormUrlEncoded
    @POST("load_tentruyen")
    Call < List < TenTruyen > > GetTenTuyen(@Field("matua") String matua,
                                            @Field("option") Integer option);

    @FormUrlEncoded
    @POST("delete_tentruyen")
    Call < List < TenTruyen > > Delete(@Field("matruyen") String matruyen);

    @FormUrlEncoded
    @POST("insert_tentruyen")
    Call < List < TenTruyen > > Insert(@Field("tentruyen") String tentruyen,
                                       @Field("matua") String matua,
                                       @Field("tap") Integer tap,
                                       @Field("madvt") Integer madvt,
                                       @Field("maloaibia") Integer maloaibia,
                                       @Field("ghichu") String ghichu,
                                       @Field("nguoitd") String nguoitd,
                                       @Field("maquatang") String maquatang,
                                       @Field("sotrang") Integer sotrang,
                                       @Field("ngayxuatban") String ngayxuatban,
                                       @Field("giabia") Double giabia);


    @FormUrlEncoded
    @POST("update_tentruyen")
    Call < List < TenTruyen > > Update(@Field("tentruyen") String tentruyen,
                                       @Field("maloaibia") Integer maloaibia,
                                       @Field("matruyen") String matruyen,
                                       @Field("tap") Integer tap,
                                       @Field("madvt") Integer madvt,
                                       @Field("giabia") Double giabia,
                                       @Field("maquatang") String maquatang,
                                       @Field("ngayxuatban") String ngayxuatban,
                                       @Field("ghichu") String ghichu,
                                       @Field("sotrang") Integer sotrang,
                                       @Field("nguoitd2") String nguoitd2);

}
