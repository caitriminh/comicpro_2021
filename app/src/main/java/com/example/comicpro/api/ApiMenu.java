package com.example.comicpro.api;



import com.example.comicpro.model.menu.Menu;
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


public interface ApiMenu {
    Gson gson=new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();
    ApiMenu apiMenu=new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiMenu.class);

    @FormUrlEncoded
    @POST("Menu")
    Call < List < Menu > > Menu(@Field("loai") Integer loai);


}
