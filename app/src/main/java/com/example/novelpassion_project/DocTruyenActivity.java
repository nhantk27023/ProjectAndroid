package com.example.novelpassion_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.novelpassion_project.adapter.ChapTruyenAdapter;
import com.example.novelpassion_project.api.ApiClientAnh;
import com.example.novelpassion_project.api.ApiClientChap;
import com.example.novelpassion_project.api.ApiServiceAnh;
import com.example.novelpassion_project.api.ApiServiceChap;
import com.example.novelpassion_project.object.ChapTruyen;
import com.example.novelpassion_project.object.TruyenTranh;
import com.google.gson.JsonIOException;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DocTruyenActivity extends AppCompatActivity {
    ArrayList<String> arr ;
    ImageView imgAnh;
    ArrayList<String> arrUrlAnh;
    TextView txvSoTrang;
    int soTrang,soTrangDangDoc;
    ChapTruyen  chapTruyen;
    String idChap;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_truyen);
        init();
        anhXa();
        setUp();
        setClick();
        int id=Integer.parseInt(idChap);
        readItemsAnh(id);

    }

    public void left(View view) {
        docTheoTrang(-1);
    }

    public void right(View view) {
        docTheoTrang(1);
    }

    private void init(){
        Bundle b=getIntent().getBundleExtra("data");
        idChap=b.getString("idChap");
        arrUrlAnh=new ArrayList<>();
//        soTrangDangDoc=1;
//        soTrang=arrUrlAnh.size();

    }

    private void anhXa(){
        imgAnh=findViewById(R.id.imgAnh);
        txvSoTrang=findViewById(R.id.txvSoTrang);
    }
    private void setUp(){


    }
    private void setClick(){

    }
    private void docTheoTrang(int i){
        soTrangDangDoc=soTrangDangDoc+i;
        if(soTrangDangDoc==0){
            soTrangDangDoc=1;
        }
        if(soTrangDangDoc>soTrang){
            soTrangDangDoc=soTrang;
        }
        txvSoTrang.setText(soTrangDangDoc+" / "+soTrang);
        Glide.with(this).load(arrUrlAnh.get(soTrangDangDoc-1)).into(imgAnh);

    }
    private void readItemsAnh(int idd)throws JsonIOException {
        arrUrlAnh=new ArrayList<>();
        arr=new ArrayList<>();
        ApiServiceAnh apiServiceAnh= ApiClientAnh.getClient().create(ApiServiceAnh.class);
        final Call<ArrayList<String>> call = apiServiceAnh.getListAnh(idd);
        call.enqueue(new Callback<ArrayList<String>>() {
            @Override
            public void onResponse(Call<ArrayList<String>> call, Response<ArrayList<String>> response) {
                try {
                    //arrUrlAnh.clear();
                    arr.addAll(response.body());
                    for(int i=0;i<arr.size();i++){
                        arrUrlAnh.add(arr.get(i).toString());
                    }
                    soTrangDangDoc=1;
                    soTrang=arrUrlAnh.size();
                    docTheoTrang(0);



                   // Toast.makeText(DocTruyenActivity.this,"Thành công",Toast.LENGTH_SHORT).show();


                }catch (Exception e){
                  //  Toast.makeText(DocTruyenActivity.this,"Lỗi chổ kết nối nè",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<String>> call, Throwable t) {
               // Toast.makeText(DocTruyenActivity.this,"Đang update...",Toast.LENGTH_SHORT).show();

            }
        });


    }

    public void backChap(View view) {
        finish();
    }
}