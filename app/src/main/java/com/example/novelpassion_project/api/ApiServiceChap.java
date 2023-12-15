package com.example.novelpassion_project.api;

import com.example.novelpassion_project.object.ChapTruyen;
import com.example.novelpassion_project.object.TruyenTranh;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiServiceChap {


    @GET("layChap.php")
    Call<ArrayList<ChapTruyen>> getListChap(@Query("id") int id);
//@Path("id") int id
}
