
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
import android.content.ContentResolver;
import android.content.Intent;
import android.content.MutableContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

import com.example.myapplication.Interface.ImageInterface;
import com.example.myapplication.entity.User;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class TotalActivity extends FragmentActivity implements View.OnClickListener, ImageInterface {

    // 声明ViewPager
    private User host;
    private Bitmap bitmap = null;
    private ViewPager ViewPager;
    // 适配器
    private FragmentPagerAdapter Adapter;
    // 装载Fragment的集合
    private List<Fragment> FragmentList;

    // 三个Tab点击对应的布局
    private LinearLayout HomeBottomTab;
    private LinearLayout SquareBottomTab;
    private LinearLayout InfoBottomTab;

    // 三个Tab点击对应的Text字
    private TextView HomeBottomTabText;
    private TextView SquareBottomTabText;
    private TextView InfoBottomTabText;

    // 三个页面的Fragment
    private Fragment HomeFragment;
    private  Fragment SquareFragment;
    private  Fragment InfoFragment;

    // 获取FragmentManager对象
    FragmentManager mFragmentManager = getSupportFragmentManager();
    // 获取FragmentTransaction对象
    FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        host = Common.user;
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

        HomeBottomTabText = (TextView) findViewById(R.id.id_homebottomtab_text);
        SquareBottomTabText = (TextView) findViewById(R.id.id_squarebottomtab_text);
        InfoBottomTabText = (TextView)findViewById(R.id.id_infobottomtab_text);
    }
    private void initEvents() {
        // 设置三个Tab点击的点击事件
        HomeBottomTab.setOnClickListener(this);
        SquareBottomTab.setOnClickListener(this);
        InfoBottomTab.setOnClickListener(this);
    }
    private void initDatas() {
        FragmentList = new ArrayList<>();
        // 将三个Fragment加入集合中
        FragmentList.add(new HomeFragment());
        FragmentList.add(new SquareFragment());
        FragmentList.add(new InfoFragment(host));

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
        if(v.getId() == R.id.id_infobottomtab) {
            selectTabBtn(2);
        }
    }

    private void selectTabBtn(int i) {

        // 根据点击的Tab按钮设置对应的响应
        TextView TopBarTitle = (TextView)findViewById(R.id.topbar_title);
        TextView BottomBarText_home = (TextView)findViewById(R.id.id_homebottomtab_text);
        TextView BottomBarText_square = (TextView)findViewById(R.id.id_squarebottomtab_text);
        TextView BottomBarText_info = (TextView)findViewById(R.id.id_infobottomtab_text);
        switch (i) {
            case 0:
                TopBarTitle.setText("首 页");
                BottomBarText_home.setTextColor(Color.parseColor("#c47731"));
                BottomBarText_square.setTextColor(Color.parseColor("#000000"));
                BottomBarText_info.setTextColor(Color.parseColor("#000000"));
                break;
            case 1:
                TopBarTitle.setText("广 场");
                BottomBarText_square.setTextColor(Color.parseColor("#c47731"));
                BottomBarText_home.setTextColor(Color.parseColor("#000000"));
                BottomBarText_info.setTextColor(Color.parseColor("#000000"));
                break;
            case 2:
                TopBarTitle.setText("我 的");
                BottomBarText_info.setTextColor(Color.parseColor("#c47731"));
                BottomBarText_home.setTextColor(Color.parseColor("#000000"));
                BottomBarText_square.setTextColor(Color.parseColor("#000000"));
                break;
        }
        // 设置当前点击的Tab所对应的页面
        ViewPager.setCurrentItem(i);
    }

    public void photo() {//调用系统相册选择图片
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, 1000);//打开相册
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {//相册的调用回调
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000) {//判断是否是我们通过photo()发起的
            if (resultCode == PictureActivity.RESULT_OK && data != null) {
                Uri uri = data.getData();
                ContentResolver cr = this.getContentResolver();
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                try {
                    bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));//获取位图
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
                FileOutputStream output;
                try
                {
                    output = openFileOutput("test.png", MODE_PRIVATE);
                    output.write(outputStream.toByteArray());
                    output.close();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                File file = this.getFileStreamPath("test.png");
                OkHttpClient client = new OkHttpClient();
                RequestBody body = RequestBody.create(MediaType.parse("application/octet-stream"), file);
                MultipartBody multipartBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("file", file.getName(), body)
                        .build();
                Request request = new Request.Builder()
                        .url(Common.URL+"/upload")
                        .post(multipartBody)
                        .build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        e.printStackTrace();
                    }
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        System.out.println("start on");
                        if (response.isSuccessful()) {//回调的方法执行在子线程。
                            System.out.println("succeed");
                        } else {
                            System.out.println("fail");
                        }
                    }
                });
            }
        }
    }


}