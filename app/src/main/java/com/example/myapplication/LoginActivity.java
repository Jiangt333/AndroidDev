package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.content.MutableContextWrapper;
import android.os.Bundle;
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
//    private TextView topbarText = findViewById(R.id.topbarText);
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

    }
}