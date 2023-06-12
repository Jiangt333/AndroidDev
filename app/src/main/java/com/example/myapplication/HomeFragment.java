package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

import okhttp3.CacheControl;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HomeFragment extends Fragment {
    View tabView;

    ListView listView;
    private Gson gson = new Gson();
    ArrayList<String> QmeBox = new ArrayList<>();

    class Threads_GetBox extends Thread {
        // 获取提问箱列表
        private OkHttpClient client = null;
        @Override
        public void run() {
            String phone = "18060142936";
            String Qme = "Qme";
            client = new OkHttpClient();
            RequestBody body = new FormBody.Builder()
                    .add("phone", phone)
                    .add("state", Qme)
                    .build();
            Request request = new Request.Builder()
                    .url("http://172.27.1.140:8080/question")
                    .post(body)
                    .cacheControl(CacheControl.FORCE_NETWORK)
                    .build();


            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    System.out.println("fail to get box!");
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    System.out.println("start on");

                    if(response.isSuccessful()){//回调的方法执行在子线程。
                        String QmeBoxJson = response.body().string();
                        QmeBox = gson.fromJson(QmeBoxJson, new TypeToken<ArrayList<String>>(){}.getType());
                        System.out.println("congratulation!");
                        System.out.println(QmeBox);
                    }
                    else {
                        System.out.println("wrong");
                    }
                }
            });
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        tabView = inflater.inflate(R.layout.tab_home, container, false);
        Threads_GetBox GetBox = new Threads_GetBox();
        GetBox.start();
        listView = (ListView) tabView.findViewById(R.id.listview_1);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(tabView.getContext(), android.R.layout.simple_list_item_1, QmeBox);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                String result = ((TextView) view).getText().toString();
                Toast.makeText(tabView.getContext(), "您选择的水果是：" + result, Toast.LENGTH_LONG).show();
            }
        });
        return tabView;
    }



//    @Override
//    public void onActivityCreated(Bundle savedInstanceState) {
//        // TODO Auto-generated method stub
//        super.onActivityCreated(savedInstanceState);
//        Threads_GetBox GetBox = new Threads_GetBox();
//        GetBox.start();
//        listView = (ListView) tabView.findViewById(R.id.listview_1);
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(tabView.getContext(), android.R.layout.simple_list_item_1, QmeBox);
//        listView.setAdapter(adapter);
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
//                String result = ((TextView) view).getText().toString();
//                Toast.makeText(tabView.getContext(), "您选择的水果是：" + result, Toast.LENGTH_LONG).show();
//            }
//        });
//
//    }
}
