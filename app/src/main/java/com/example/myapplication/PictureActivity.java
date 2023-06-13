package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.example.myapplication.entity.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PictureActivity extends AppCompatActivity {

    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog);
        btn = findViewById(R.id.bt);
        btn.setOnClickListener(this::onClickUpload);
    }
    public void onClickUpload(View v) {
        photo();
    }
    public void photo() {//调用系统相册选择图片
        Intent intent = new Intent();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
        } else {
            intent.setAction(Intent.ACTION_GET_CONTENT);
        }
        intent.setType("image/*");
        startActivityForResult(intent, 1000);//打开相册
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {//相册的调用回调
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000) {//判断是否是我们通过photo()发起的
            if (resultCode == PictureActivity.RESULT_OK && null != data) {
                Uri uri = data.getData();
                ContentResolver cr = this.getContentResolver();
                try {
                    Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));//获取位图
                    //将位图转为字节数组后再转为base64
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG,100, outputStream);
                    //发起网络请求，传入base64数据
                    getImgBase64(Base64.encodeToString(outputStream.toByteArray(),Base64.DEFAULT));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    void getImgBase64(String imgBase64) {
        new Thread() {//开线程
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                RequestBody body = new FormBody.Builder()
                        .add("pic",imgBase64)
                        .build();
                Request request = new Request.Builder()
                        .url(Common.URL+"/uploadAudio")
                        .post(body)
                        .cacheControl(CacheControl.FORCE_NETWORK)
                        .build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        e.printStackTrace();
                    }
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        System.out.println("start on");
                        if(response.isSuccessful()){//回调的方法执行在子线程。
                            System.out.println("response success");
                        }
                        else {
                            System.out.println("response failed");
                        }
                    }
                });

            }
        }.start();
    }

}
