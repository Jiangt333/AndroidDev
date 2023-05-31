package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.content.MutableContextWrapper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

//    private Button HomeBtn;
//    private Button SquareBtn;
//    private Button MyinfoBtn;

    // 得到TabHost对象实例
    private TabHost tabhost;
    private TextView topbarText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tabhost = (TabHost)findViewById(R.id.mytab);
        // 调用 TabHost.setup()
        tabhost.setup();
        // 创建Tab标签
        tabhost.addTab(tabhost.newTabSpec("one").setIndicator("首页").setContent(R.id.tab1));
        tabhost.addTab(tabhost.newTabSpec("two").setIndicator("广场").setContent(R.id.tab2));
        tabhost.addTab(tabhost.newTabSpec("three").setIndicator("我的").setContent(R.id.tab3));

        tabhost.setOnTabChangedListener(new TabHost.OnTabChangeListener(){
            public void onTabChanged (String tabId) {
                topbarText = findViewById(R.id.topbarText);
                // onTabChanged是一个抽象类方法，控制tab页面切换响应，这里进行重写
                int pos = tabhost.getCurrentTab();
                if(pos == 0){
                    topbarText.setText("首 页");
                }
                if(pos == 1){
                    topbarText.setText("广 场");
                }
                if(pos == 2){
                    topbarText.setText("我 的");
                }
            }

        });

    }

//    public void onClickTab(){
//        topbarText = findViewById(R.id.topbarText);
//
//    }
}
