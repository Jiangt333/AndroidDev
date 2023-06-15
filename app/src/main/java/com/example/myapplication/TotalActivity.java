
package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.content.MutableContextWrapper;
import android.graphics.Color;
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
    private LinearLayout HomeBottomTab;
    private LinearLayout SquareBottomTab;
    private LinearLayout InfoBottomTab;
    private LinearLayout FriendBottomTab;

    // 三个Tab点击对应的Text字
    private TextView HomeBottomTabText;
    private TextView SquareBottomTabText;
    private TextView InfoBottomTabText;
    private TextView FriendBottomTabText;

    // 三个页面的Fragment
    private Fragment HomeFragment;
    private  Fragment SquareFragment;
    private  Fragment InfoFragment;
    private  Fragment FriendFragment;

    // 获取FragmentManager对象
    FragmentManager mFragmentManager = getSupportFragmentManager();
    // 获取FragmentTransaction对象
    FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();

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

    // 初始化控件
    private void initViews() {
        ViewPager = (ViewPager) findViewById(R.id.id_viewpager);

        HomeBottomTab = (LinearLayout) findViewById(R.id.id_homebottomtab);
        SquareBottomTab = (LinearLayout) findViewById(R.id.id_squarebottomtab);
        InfoBottomTab = (LinearLayout) findViewById(R.id.id_infobottomtab);
        FriendBottomTab = (LinearLayout) findViewById(R.id.id_friendbottomtab);

        HomeBottomTabText = (TextView) findViewById(R.id.id_homebottomtab_text);
        SquareBottomTabText = (TextView) findViewById(R.id.id_squarebottomtab_text);
        InfoBottomTabText = (TextView)findViewById(R.id.id_infobottomtab_text);
        FriendBottomTabText = (TextView)findViewById(R.id.id_friendbottomtab_text);
    }
    private void initEvents() {
        // 设置三个Tab点击的点击事件
        HomeBottomTab.setOnClickListener(this);
        SquareBottomTab.setOnClickListener(this);
        InfoBottomTab.setOnClickListener(this);
        FriendBottomTab.setOnClickListener(this);
    }
    private void initDatas() {
        FragmentList = new ArrayList<>();
        // 将三个Fragment加入集合中
        FragmentList.add(new HomeFragment());
        FragmentList.add(new SquareFragment());
        FragmentList.add(new FriendFragment());
        FragmentList.add(new InfoFragment());


        // 初始化适配器
        Adapter = new mFragmentPagerAdapter(mFragmentManager, FragmentList);

        // 设置ViewPager的适配器
        ViewPager.setAdapter(Adapter);

        // 设置ViewPager手指滑动切换页面的监听
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
                System.out.println(position);
                selectTabBtn(position);
            }

            @Override
            // 页面滚动状态改变事件
            public void onPageScrollStateChanged(int state) {

            }
        });
        ViewPager.setCurrentItem(0);
        TextView BottomBarText_home = (TextView)findViewById(R.id.id_homebottomtab_text);
        BottomBarText_home.setTextColor(Color.parseColor("#c47731"));
    }

    @Override
    public void onClick(View v) {
        // 根据点击的Tab进行响应
        if(v.getId() == R.id.id_homebottomtab) {
            selectTabBtn(0);
        }
        if(v.getId() == R.id.id_squarebottomtab) {
            selectTabBtn(1);
        }
        if(v.getId() == R.id.id_friendbottomtab) {
            selectTabBtn(2);
        }
        if(v.getId() == R.id.id_infobottomtab) {
            selectTabBtn(3);
        }

    }

    private void selectTabBtn(int i) {

        // 根据点击的Tab按钮设置对应的响应
        TextView TopBarTitle = (TextView)findViewById(R.id.topbar_title);
        TextView BottomBarText_home = (TextView)findViewById(R.id.id_homebottomtab_text);
        TextView BottomBarText_square = (TextView)findViewById(R.id.id_squarebottomtab_text);
        TextView BottomBarText_info = (TextView)findViewById(R.id.id_infobottomtab_text);
        TextView BottomBarText_friend = (TextView)findViewById(R.id.id_friendbottomtab_text);
        switch (i) {
            case 0:
                TopBarTitle.setText("首 页");
                BottomBarText_home.setTextColor(Color.parseColor("#c47731"));
                BottomBarText_square.setTextColor(Color.parseColor("#000000"));
                BottomBarText_info.setTextColor(Color.parseColor("#000000"));
                BottomBarText_friend.setTextColor(Color.parseColor("#000000"));
                break;
            case 1:
                TopBarTitle.setText("广 场");
                BottomBarText_square.setTextColor(Color.parseColor("#c47731"));
                BottomBarText_home.setTextColor(Color.parseColor("#000000"));
                BottomBarText_info.setTextColor(Color.parseColor("#000000"));
                BottomBarText_friend.setTextColor(Color.parseColor("#000000"));
                break;
            case 2:
                TopBarTitle.setText("交 友");
                BottomBarText_friend.setTextColor(Color.parseColor("#c47731"));
                BottomBarText_home.setTextColor(Color.parseColor("#000000"));
                BottomBarText_square.setTextColor(Color.parseColor("#000000"));
                BottomBarText_info.setTextColor(Color.parseColor("#000000"));
                break;
            case 3:
                TopBarTitle.setText("我 的");
                BottomBarText_info.setTextColor(Color.parseColor("#c47731"));
                BottomBarText_home.setTextColor(Color.parseColor("#000000"));
                BottomBarText_square.setTextColor(Color.parseColor("#000000"));
                BottomBarText_friend.setTextColor(Color.parseColor("#000000"));
                break;

        }
        // 设置当前点击的Tab所对应的页面
        ViewPager.setCurrentItem(i);
    }

}