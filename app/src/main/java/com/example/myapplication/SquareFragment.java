package com.example.myapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


import java.io.IOException;
import java.util.AbstractList;
import java.util.ArrayList;

import okhttp3.CacheControl;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SquareFragment extends Fragment {
    private ListView listView;
    private View tabView;
    private FriendFragment Attentions = new FriendFragment();
    private SwipeRefreshLayout swipeRefreshLayout;
    class Threads_GetNews extends Thread {
        // 获取提问箱列表
        private OkHttpClient client = null;
        @Override
        public void run() {
            String myPhone = "1";
            client = new OkHttpClient();
            //目前已经有关注的列表，但该如何取他们近期的回答？
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
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                            }
                        });
                    }
                    else {
                        System.out.println("wrong");
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }
            });
        }
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        tabView = inflater.inflate(R.layout.tab_square, container, false);
        listView = tabView.findViewById(R.id.list_atten);

        Threads_GetNews GetNews = new Threads_GetNews();
        GetNews.start();

        return tabView;
    }
}
