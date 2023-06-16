package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.CacheControl;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AskActivity extends AppCompatActivity {
    private EditText askText;//提问处
    private ListView answeredList;//已回答过的问题列表
    private Button commitBtn;
    private String question;
    private ImageButton backBtn;

    class Threads_Ask extends Thread {
        // 提交提问
        private OkHttpClient client = null;
        @Override
        public void run() {
            String myPhone = "1";
            question = askText.getText().toString();
            client = new OkHttpClient();
            RequestBody body = new FormBody.Builder()
                    .add("myattention", myPhone)
                    .build();
            Request request = new Request.Builder()
                    .url(Common.URL+"/square/myattention")
                    .post(body)
                    .cacheControl(CacheControl.FORCE_NETWORK)
                    .build();

            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    System.out.println("fail to get attention!");
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    System.out.println("start on");

                    if(response.isSuccessful()){//回调的方法执行在子线程。
                        /*

                        String AttenJson = response.body().string();

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                            }
                        });

                        */
                    }
                    else {
                        System.out.println("wrong");
                    }
                }
            });
        }
    }

    class Threads_Ans extends Thread {
        // 获取提问箱列表
        private OkHttpClient client = null;
        @Override
        public void run() {
            String myPhone = "1";
            question = askText.getText().toString();
            client = new OkHttpClient();
            RequestBody body = new FormBody.Builder()
                    .add("myattention", myPhone)
                    .build();
            Request request = new Request.Builder()
                    .url(Common.URL+"/square/myattention")
                    .post(body)
                    .cacheControl(CacheControl.FORCE_NETWORK)
                    .build();

            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    System.out.println("fail to get attention!");
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    System.out.println("start on");

                    if(response.isSuccessful()){//回调的方法执行在子线程。
                        /*

                        String AttenJson = response.body().string();

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                            }
                        });

                        */
                    }
                    else {
                        System.out.println("wrong");
                    }
                }
            });
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask);
        askText = findViewById(R.id.askPlace);
        answeredList = findViewById(R.id.answered);
        commitBtn = findViewById(R.id.commit);
        backBtn = findViewById(R.id.backButton);

        String target = getIntent().getStringExtra("target");//手机号
        String targetName = getIntent().getStringExtra("targetName");//昵称

        TextView TopBarTitle = (TextView)findViewById(R.id.topbar_title);
        TopBarTitle.setText(targetName + " 的 回 答");

        /*
        Threads_Ans ans = new Threads_Ans();
        ans.start();
        */
        commitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(askText.length() != 0){
                    Threads_Ask ask = new Threads_Ask();
                    ask.start();
                }else{
                    Toast.makeText(AskActivity.this, "请输入你的提问", Toast.LENGTH_SHORT).show();
                }
            }
        });
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
