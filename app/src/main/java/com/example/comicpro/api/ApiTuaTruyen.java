package com.example.comicpro.api;


import com.example.comicpro.model.tuatruyen.TuaTruyen;
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


public interface ApiTuaTruyen {
    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();
    ApiTuaTruyen apiTuaTruyen = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiTuaTruyen.class);

    @FormUrlEncoded
    @POST("load_tuatruyen")
    Call<List<TuaTruyen>> GetTuaTruyen(@Field("action") String action);

    @FormUrlEncoded
    @POST("load_tuatruyen_by_matua")
    Call<List<TuaTruyen>> GetTuaTruyenByMaTua(@Field("matua") String matua);

    @FormUrlEncoded
    @POST("insert_tuatruyen")
    Call<List<TuaTruyen>> Insert(@Field("tuatruyen") String tuatruyen,
                                 @Field("maloai") String maloai,
                                 @Field("matacgia") String matacgia,
                                 @Field("manxb") String manxb,
                                 @Field("maquocgia") Integer maquocgia,
                                 @Field("sotap") Integer sotap,
                                 @Field("namxuatban") Integer namxuatban,
                                 @Field("taiban") Integer taiban,
                                 @Field("nguoitd") String nguoitd);

    @FormUrlEncoded
    @POST("delete_tuatruyen")
    Call<List<TuaTruyen>> Delete(@Field("action") String action, @Field("matua") String matua);


    @FormUrlEncoded
    @POST("update_tuatruyen")
    Call<List<TuaTruyen>> Update(@Field("tuatruyen") String tuatruyen,
                                 @Field("matacgia") String matacgia,
                                 @Field("matua") String matua,
                                 @Field("manxb") String manxb,
                                 @Field("maquocgia") Integer maquocgia,
                                 @Field("sotap") Integer sotap,
                                 @Field("namxuatban") Integer namxuatban,
                                 @Field("taiban") Integer taiban,
                                 @Field("ghichu") String ghichu,
                                 @Field("nguoitd2") String nguoitd2,
                                 @Field("theodoi") Boolean theodoi);

}
