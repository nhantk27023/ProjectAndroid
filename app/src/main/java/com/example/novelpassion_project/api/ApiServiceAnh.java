package com.example.novelpassion_project.api;

import com.example.novelpassion_project.object.ChapTruyen;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiServiceAnh {
    @GET("layAnh.php")
    Call<ArrayList<String>> getListAnh(@Query("idChap") int id);
//    Call<ArrayList<String>> getListAnh();
}
