
package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.content.MutableContextWrapper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class TotalActivity extends FragmentActivity implements View.OnClickListener {

    // 声明ViewPager
    private ViewPager ViewPager;
    // 适配器
    private FragmentPagerAdapter Adapter;
    // 装载Fragment的集合
    private List<Fragment> FragmentList;

    // 三个Tab点击对应的布局
    private LinearLayout HomeTab;
    private LinearLayout SquareTab;
    private LinearLayout InfoTab;

    // 三个Tab点击对应的Text字
    private TextView HomeTabText;
    private TextView SquareTabText;
    private TextView InfoTabText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 去掉TitleBar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 指定布局界面
        setContentView(R.layout.activity_total);
        initViews();    // 初始化控件
        initEvents();   // 初始化事件
        initDatas();    // 初始化数据
    }

    private void initDatas() {
        FragmentList = new ArrayList<>();
        // 将三个Fragment加入集合中
        FragmentList.add(new HomeFragment());
        FragmentList.add(new SquareFragment());
        FragmentList.add(new InfoFragment());

        // 初始化适配器
        Adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {     // 获取数据集中与索引对应的数据项；
                return FragmentList.get(position);
            }

            @Override
            public int getCount() {     // 适配器中数据集的数据个数；
                return FragmentList.size();
            }

        };

        // 设置ViewPager的适配器
        ViewPager.setAdapter(Adapter);

        // 设置ViewPager的切换页面监听
        ViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            // 页面滚动事件
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            // 页面选中事件
            @Override
            public void onPageSelected(int position) {
                // 设置position对应的集合中的Fragment
                ViewPager.setCurrentItem(position);
                selectTabBtn(position);
            }

            @Override
            // 页面滚动状态改变事件
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initEvents() {
        // 设置三个Tab点击的点击事件
        HomeTab.setOnClickListener(this);
        SquareTab.setOnClickListener(this);
        InfoTab.setOnClickListener(this);
    }

    // 初始化控件
    private void initViews() {
        ViewPager = (ViewPager) findViewById(R.id.id_viewpager);

        HomeTab = (LinearLayout) findViewById(R.id.id_hometab);
        SquareTab = (LinearLayout) findViewById(R.id.id_squaretab);
        InfoTab = (LinearLayout) findViewById(R.id.id_infotab);

        HomeTabText = (TextView) findViewById(R.id.id_hometab_text);
        SquareTabText = (TextView) findViewById(R.id.id_squaretab_text);
        InfoTabText = (TextView)findViewById(R.id.id_infotab_text);
    }

    @Override
    public void onClick(View v) {
        // 根据点击的Tab切换不同的页面及设置对应的Button为绿色
        if(v.getId() == R.id.id_hometab) {
            selectTabBtn(0);
        }
        if(v.getId() == R.id.id_squaretab) {
            selectTabBtn(1);
        }
        if(v.getId() == R.id.id_infotab) {
            selectTabBtn(2);
        }
    }

    private void selectTabBtn(int i) {
        // 根据点击的Tab按钮设置对应的响应
        TextView TopBarTitle = (TextView)findViewById(R.id.topbar_title);
        switch (i) {
            case 0:
                TopBarTitle.setText("首 页");

                break;
            case 1:
                TopBarTitle.setText("广 场");

                break;
            case 2:
                TopBarTitle.setText("我 的");

                break;
        }
        // 设置当前点击的Tab所对应的页面
        ViewPager.setCurrentItem(i);
    }



}