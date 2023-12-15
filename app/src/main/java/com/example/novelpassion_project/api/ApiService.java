package com.example.novelpassion_project.api;

import com.example.novelpassion_project.object.TruyenTranh;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public interface ApiService {
//    Gson gson =new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
//    ApiService apiService = new Retrofit.Builder()
//            .baseUrl("https://novelpassionver1.000webhostapp.com/")
//            .addConverterFactory(GsonConverterFactory.create(gson)).build().create(ApiService.class);


    @GET("layTruyen.php")
    Call<ArrayList<TruyenTranh>> getListTruyenTranh();
}
