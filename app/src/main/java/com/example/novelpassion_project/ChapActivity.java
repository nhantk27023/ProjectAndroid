package com.example.novelpassion_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.novelpassion_project.adapter.ChapTruyenAdapter;
import com.example.novelpassion_project.adapter.TruyenTranhAdapter;
import com.example.novelpassion_project.api.ApiClient;
import com.example.novelpassion_project.api.ApiClientChap;
import com.example.novelpassion_project.api.ApiService;
import com.example.novelpassion_project.api.ApiServiceChap;
import com.example.novelpassion_project.object.ChapTruyen;
import com.example.novelpassion_project.object.TruyenTranh;
import com.google.gson.JsonIOException;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChapActivity extends AppCompatActivity {
    ArrayList<ChapTruyen> arr =new ArrayList<ChapTruyen>();

    TextView txvTenTruyens;
    ImageView imgAnhTruyens,imgAnhTruyens1;
    TruyenTranh truyenTranh;
    ListView listView;
    ArrayList<ChapTruyen>  arrChap;
    ChapTruyenAdapter chapTruyenAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chap);
        init();
        anhXa();
        setUp();
        setClick();
        int id=Integer.parseInt(truyenTranh.getId().toString().trim());
       // Toast.makeText(ChapActivity.this,"Thành công"+id,Toast.LENGTH_SHORT).show();

        readItemsChap(id);

    }
    private void init(){
        Bundle b=getIntent().getBundleExtra("data");
        truyenTranh=(TruyenTranh)b.getSerializable("truyen");

        arrChap=new ArrayList<>();

//        for(int i=0;i<20;i++){
//            arrChap.add(new ChapTruyen("Chapter "+i,"20-10-2023"));
//        }
//        chapTruyenAdapter=new ChapTruyenAdapter(this,0,arrChap);
    };
    private void anhXa(){
        imgAnhTruyens=findViewById(R.id.imgAnhTruyens);
      imgAnhTruyens1=findViewById(R.id.imgAnhTruyens1);
        txvTenTruyens=findViewById(R.id.txvTenTruyens);
        listView=findViewById(R.id.lsvDanhSach);
    };
    private void setUp(){
        txvTenTruyens.setText(truyenTranh.getTenTruyen());
        Glide.with(this).load(truyenTranh.getLinkAnh()).into(imgAnhTruyens);
        Glide.with(this).load(truyenTranh.getLinkAnh()).into(imgAnhTruyens1);
     //   listView.setAdapter(chapTruyenAdapter);

    };
    private void setClick(){
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle b=new Bundle();
                b.putString("idChap",arrChap.get(position).getId());
                Intent intent =new Intent(ChapActivity.this, DocTruyenActivity.class);
                intent.putExtra("data",b);
                startActivity(intent);
               // startActivity(new Intent(ChapActivity.this,DocTruyenActivity.class));
            }
        });
    };
    private void readItemsChap(int idd)throws JsonIOException {
        arr.clear();
        ApiServiceChap apiServiceChap= ApiClientChap.getClient().create(ApiServiceChap.class);
        final Call<ArrayList<ChapTruyen>> call = apiServiceChap.getListChap(idd);
        call.enqueue(new Callback<ArrayList<ChapTruyen>>() {
              @Override
            public void onResponse(Call<ArrayList<ChapTruyen>> call, Response<ArrayList<ChapTruyen>> response) {
                try {
                    arrChap.clear();
                    arr.addAll(response.body());
                    for(int i=0;i<arr.size();i++){
                        String id1= arr.get(i).getId();
                        String tenChap1= arr.get(i).getTenChap();
                        String ngayDang1= arr.get(i).getNgayDang();
                        arrChap.add(new ChapTruyen(id1,tenChap1,ngayDang1));
                    }
                    chapTruyenAdapter=new ChapTruyenAdapter(ChapActivity.this,0,arrChap);
                    listView.setAdapter(chapTruyenAdapter);


                   // Toast.makeText(ChapActivity.this,"Thành công",Toast.LENGTH_SHORT).show();


                }catch (Exception e){
                    //Toast.makeText(ChapActivity.this,"Lỗi chổ kết nối nè",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<ChapTruyen>> call, Throwable t) {
                //Toast.makeText(ChapActivity.this,"Đang update...",Toast.LENGTH_SHORT).show();

            }
        });


    }

    public void back(View view) {
        finish();
    }
}