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
import android.widget.ImageView;
import android.widget.Toast;


import com.example.myapplication.entity.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;

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
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, 1000);//打开相册
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {//相册的调用回调
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000) {//判断是否是我们通过photo()发起的
            if (resultCode == PictureActivity.RESULT_OK && data != null & data.getData() != null) {
                Uri uri = data.getData();
                ContentResolver cr = this.getContentResolver();
                try {
                    Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));//获取位图
                    ImageView iv= (ImageView)findViewById (R.id.imageView);
                    iv.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
                //Bitmap bitmap= BitmapFactory.decodeFile (img_path);
                File file = new File(uri.getPath());
                //ContentResolver cr = this.getContentResolver();
                //Picasso.get().load(uri).into(mImageView);
                OkHttpClient client = new OkHttpClient();
                MediaType mediaType = MediaType.parse("application/octet-stream");
                RequestBody body = RequestBody.create(mediaType, file);
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
                            System.out.println("response success");
                        } else {
                            System.out.println("response failed");
                        }
                    }
                    //发起网络请求，传入base64数据
                    //getImgBase64(Base64.encodeToString(outputStream.toByteArray(),Base64.DEFAULT));
                });
            }
        }
    }


}
