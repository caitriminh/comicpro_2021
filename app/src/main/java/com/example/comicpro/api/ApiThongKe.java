package com.example.comicpro.api;


import com.example.comicpro.model.tentruyen.ChiTietTenTruyen_TheoNXB;
import com.example.comicpro.model.thongke.ThongKeTheoNCC;
import com.example.comicpro.model.thongke.ThongKeTheoNXB;
import com.example.comicpro.model.thongke.ThongKeTheoNam;
import com.example.comicpro.model.thongke.ThongKeTheoThang;
import com.example.comicpro.model.thongke.ThongKeTheoTuaTruyen;
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


public interface ApiThongKe {
    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();
    ApiThongKe apiThongKe = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiThongKe.class);

    @POST("load_thongke_tuatruyen")
    Call < List < ThongKeTheoTuaTruyen > > GetThongKeTuaTruyen();

    @FormUrlEncoded
    @POST("load_thongke_theothang")
    Call < List < ThongKeTheoThang > > GetThongKeTheoThang(@Field("option") Integer option);

    @FormUrlEncoded
    @POST("load_thongke_theonam")
    Call < List < ThongKeTheoNam > > GetThongKeTheoNam(@Field("action") String action);

    @POST("load_thongke_nxb")
    Call < List < ThongKeTheoNXB > > GetThongKeTheoNXB();

    @FormUrlEncoded
    @POST("load_chitiet_tentruyen_nxb")
    Call < List < ChiTietTenTruyen_TheoNXB > > GetTenTruyenNXB(@Field("manxb") String manxb);

    @FormUrlEncoded
    @POST("GetThongKeNCC")
    Call < List < ThongKeTheoNCC > > GetThongKeNCC(@Field("action") String action,
                                                   @Field("madonvi") String madonvi);


}
