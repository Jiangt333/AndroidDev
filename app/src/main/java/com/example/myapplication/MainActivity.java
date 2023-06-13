package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mysql.jdbc.log.LogUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import okhttp3.*;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements  View.OnClickListener {

    //数据库连接类
    private static Connection con = null;
    private static PreparedStatement stmt = null;
    private EditText Username;
    private EditText PassWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //登录跳转
        Button btnLogin = findViewById(R.id.bt_login);
        Username = findViewById(R.id.et_1);
        PassWord = findViewById(R.id.et_2);
        btnLogin.setOnClickListener(this);
    }

    class Threads_Login extends Thread {
        private OkHttpClient client = null;
        private BufferedReader in;
        private BufferedWriter out;

        @Override
        public void run() {
            String username = Username.getText().toString();
            String password = PassWord.getText().toString();  //用户发送的信息
            OkHttpClient client = new OkHttpClient();
            RequestBody body = new FormBody.Builder()
                    .add("user",username)
                    .build();
            Request request = new Request.Builder()
                    .url("http://172.17.55.163:8080/login")
                    .post(body)
                    .cacheControl(CacheControl.FORCE_NETWORK)
                    .build();

            System.out.println("yyy");
            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    System.out.println("sorry!");
                }


                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    System.out.println("start on");

                    if(response.isSuccessful()){//回调的方法执行在子线程。
                        String RealPassword = response.body().string();
                        System.out.println("congratulation!");
                        System.out.println("password="+RealPassword);
                        Intent intent = null;
                        if (password.equals(RealPassword)) {
                            intent = new Intent(MainActivity.this, TotalActivity.class);
                            startActivity(intent);
                        } else {

                        }
                    }
                    else {
                        System.out.println("wrong");
                    }
                }

            });

        }
    }
    public void onClick(View v) {
        Threads_Login login = new Threads_Login();
        login.start();
    }
}


