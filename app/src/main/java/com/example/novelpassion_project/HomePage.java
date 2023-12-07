package com.example.novelpassion_project;


import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;


import android.os.Bundle;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;

public class HomePage extends AppCompatActivity {
    private ViewPager viewPager;
    private CircleIndicator circleIndicator;
    private photoAdapter photoAdapter;
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




    }
    private List<photo> getListPhoto(){
        List<photo> list = new ArrayList<>();
        list.add(new photo(R.drawable.banner1));
        list.add(new photo(R.drawable.banner_2));
        list.add(new photo(R.drawable.banner3));
        list.add(new photo(R.drawable.banner4));
        list.add(new photo(R.drawable.banner5));
        list.add(new photo(R.drawable.banner6));
        return list;
    }
}