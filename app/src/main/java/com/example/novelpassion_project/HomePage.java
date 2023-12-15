package com.example.novelpassion_project;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.novelpassion_project.adapter.TruyenTranhAdapter;
import com.example.novelpassion_project.api.ApiClient;
import com.example.novelpassion_project.api.ApiService;
import com.example.novelpassion_project.interfaces.LayTruyenVe;
import com.example.novelpassion_project.object.TruyenTranh;
import com.google.gson.JsonIOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomePage extends AppCompatActivity implements LayTruyenVe {
    DrawerLayout drawerLayout;
    ImageView menu;
    Button button;
    TextView name;
    private ViewPager viewPager;
    private CircleIndicator circleIndicator;
    private photoAdapter photoAdapter;
    private GridView gdvDSTruyen;
    String data;
    LayTruyenVe layTruyenVe;
    private TruyenTranhAdapter adapter;
    ArrayList<TruyenTranh> truyenTranhArrayList;
    EditText edtTimkiem;
    ArrayList<TruyenTranh> arr =new ArrayList<TruyenTranh>();
    LinearLayout home, art, call, mes, list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        viewPager=findViewById(R.id.viewpager);
        circleIndicator=findViewById(R.id.circle_indicator);
        photoAdapter= new photoAdapter(this,getListPhoto());
        viewPager.setAdapter(photoAdapter);
        circleIndicator.setViewPager(viewPager);
        photoAdapter.registerDataSetObserver(circleIndicator.getDataSetObserver());
        init();
        anhXa();
        setUp();
        setClick();
        readItems();






    }
    private List<photo> getListPhoto(){
        List<photo> list = new ArrayList<>();
        list.add(new photo(R.drawable.banner5));
        list.add(new photo(R.drawable.banner_2));
        list.add(new photo(R.drawable.banner3));
        list.add(new photo(R.drawable.banner4));
        list.add(new photo(R.drawable.banner5));
        list.add(new photo(R.drawable.banner6));
        return list;
    }
    private void init(){
        truyenTranhArrayList = new ArrayList<>();
    }

    private void anhXa(){
        gdvDSTruyen=findViewById(R.id.gdvDSTruyen);
        edtTimkiem= findViewById(R.id.edtTimkiem);
     drawerLayout = findViewById(R.id.drawerLayout);
       menu = findViewById(R.id.menu);
        button = findViewById(R.id.login);
        name = findViewById(R.id.name);
 //       home = findViewById(R.id.home);
//        art = findViewById(R.id.art);
//       call = findViewById(R.id.call);
//        mes = findViewById(R.id.list);
//       list = findViewById(R.id.art);
        // Nhận dữ liệu từ Intent
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("USERNAME")) {
            String username = intent.getStringExtra("USERNAME");
           name.setText(username);
           button.setText(username);
        } else {
            name.setText("Guest");
        }

    }
    private void setUp(){
        gdvDSTruyen.setAdapter(adapter);
    }
    private void setClick(){
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDrawer(drawerLayout);
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirecActivity(HomePage.this, LoginActivity.class);
                //startActivity(new Intent(HomePage.this,LoginActivity.class));
            }
        });
//        home.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                recreate();
//            }
//        });
//        art.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                recreate();
//            }
//        });
//        call.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                recreate();
//            }
//        });
//        mes.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                recreate();
//            }
//        });
//        list.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                recreate();
//            }
//        });
        edtTimkiem.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String s=edtTimkiem.getText().toString();
                adapter.sortTruyen(s);

            }
        });
        gdvDSTruyen.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               TruyenTranh truyenTranh=truyenTranhArrayList.get(position);
                 Bundle b=new Bundle();
                 b.putSerializable("truyen",truyenTranh);
                Intent intent =new Intent(HomePage.this, ChapActivity.class);
                intent.putExtra("data",b);
                startActivity(intent);
            }
        });
    }

    @Override
    public void batDau() {
        Toast.makeText(this,"Đang load...",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void ketThuc(String data) {
        try {
            truyenTranhArrayList.clear();
            JSONArray arr=new JSONArray(data);
            for(int i=0;i<arr.length();i++){
                JSONObject o= arr.getJSONObject(i);
                truyenTranhArrayList.add(new TruyenTranh(o));

            }
            adapter =new TruyenTranhAdapter(this,0,truyenTranhArrayList);
            gdvDSTruyen.setAdapter(adapter);

        }catch (JSONException e){

        }

    }

    @Override
    public void biLoi() {
        Toast.makeText(this,"Lỗi kết nối",Toast.LENGTH_SHORT).show();

    }

    private void readItems()throws JsonIOException{
        arr.clear();
        ApiService apiService= ApiClient.getClient().create(ApiService.class);
        final Call<ArrayList<TruyenTranh>> call = apiService.getListTruyenTranh();
        call.enqueue(new Callback<ArrayList<TruyenTranh>>() {
            @Override
            public void onResponse(Call<ArrayList<TruyenTranh>> call, Response<ArrayList<TruyenTranh>> response) {
                try {
                    truyenTranhArrayList.clear();
                    arr.addAll(response.body());
                    for(int i=0;i<arr.size();i++){
                        String id1=arr.get(i).getId();
                        String tenTruyen1= arr.get(i).getTenTruyen();
                        String tenChap1= arr.get(i).getTenChap();
                        String LinkAnh1= arr.get(i).getLinkAnh();
                        truyenTranhArrayList.add(new TruyenTranh(id1,tenTruyen1,tenChap1,LinkAnh1));
                    }
                    adapter =new TruyenTranhAdapter(HomePage.this,0,truyenTranhArrayList);
                    gdvDSTruyen.setAdapter(adapter);


                    Toast.makeText(HomePage.this,"Thành công",Toast.LENGTH_SHORT).show();


                }catch (Exception e){
                    Toast.makeText(HomePage.this,"Lỗi chổ kết nối nè",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<TruyenTranh>> call, Throwable t) {
                Toast.makeText(HomePage.this,"Đang update...",Toast.LENGTH_SHORT).show();

            }
        });


    }

    public static void openDrawer(DrawerLayout drawerLayout)
    {
        drawerLayout.openDrawer(GravityCompat.START);
    }
    public static void closeDrawer(DrawerLayout drawerLayout)
    {
        if(drawerLayout.isDrawerOpen(GravityCompat.START))
        {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }
    public static void redirecActivity(Activity activity, Class secondActivity)
    {
        Intent intent = new Intent(activity,secondActivity);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
        activity.finish();
    }
    @Override
    protected void onPause()
    {
        super.onPause();
        closeDrawer(drawerLayout);
    }

    public void update(View view) {
        try {
            batDau();

           readItems();



        }catch (Exception e){
            biLoi();
        }
    }
}