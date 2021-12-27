package com.example.comicpro.api;
import com.example.comicpro.model.phieunhap.PhieuNhap;
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


public interface ApiPhieuNhap {
    Gson gson=new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();
    ApiPhieuNhap apiPhieuNhap=new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiPhieuNhap.class);

    @FormUrlEncoded
    @POST("load_phieunhap")
    Call < List < PhieuNhap > > GetPhieuNhap(@Field("option") Integer option,
                                     @Field("tungay") String tungay,
                                     @Field("denngay") String denngay,
                                     @Field("nguoitd") String nguoitd);

    @FormUrlEncoded
    @POST("delete_phieunhapxuat")
    Call < List < PhieuNhap > > Delete(@Field("maphieu") String maphieu);


}
