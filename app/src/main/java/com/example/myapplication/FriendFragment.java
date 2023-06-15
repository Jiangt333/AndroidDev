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

public class FriendFragment extends Fragment {
    private ListView listView;
    private ListView listView_new;
    private View tabView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Gson gson = new Gson();
    public ArrayList<String> Atten = new ArrayList<>();//存储我关注的人
    public ArrayList<String> Fans = new ArrayList<>();//存储关注我的人

    class Threads_GetAtten extends Thread {
        // 获取提问箱列表
        private OkHttpClient client = null;
        @Override
        public void run() {
            String myPhone = "1";
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
                        String AttenJson = response.body().string();
                        Atten = gson.fromJson(AttenJson, new TypeToken<ArrayList<String>>(){}.getType());
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ArrayAdapter<String> adapter = new ArrayAdapter<>(tabView.getContext(), android.R.layout.simple_list_item_1, Atten);
                                listView.setAdapter(adapter);
                                System.out.println(Atten);
                                swipeRefreshLayout.setRefreshing(false);
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

    class Threads_GetFans extends Thread {
        // 获取提问箱列表
        private OkHttpClient client = null;
        @Override
        public void run() {
            String myPhone = "1";
            client = new OkHttpClient();
            RequestBody body = new FormBody.Builder()
                    .add("myfans", myPhone)
                    .build();
            Request request = new Request.Builder()
                    .url(Common.URL+"/square/myfans")
                    .post(body)
                    .cacheControl(CacheControl.FORCE_NETWORK)
                    .build();

            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    System.out.println("fail to get fan!");
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    System.out.println("start on");

                    if(response.isSuccessful()){//回调的方法执行在子线程。
                        String FansJson = response.body().string();
                        Fans = gson.fromJson(FansJson, new TypeToken<ArrayList<String>>(){}.getType());
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ArrayAdapter<String> adapterFans = new ArrayAdapter<>(tabView.getContext(), android.R.layout.simple_list_item_1, Fans);
                                listView_new.setAdapter(adapterFans);
                                System.out.println(Fans);
                                swipeRefreshLayout.setRefreshing(false);
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

    private void showInputDialog() {
        LayoutInflater layoutInflater = LayoutInflater.from(tabView.getContext());
        View promptView = layoutInflater.inflate(R.layout.input_dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(tabView.getContext());
        alertDialogBuilder.setView(promptView);

        final EditText editText = (EditText) promptView.findViewById(R.id.inputEditText);

        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int id) {
                        //当前手机号与当前用户昵称
                        String source = "1";
                        String sourceName = "zhangchw";

                        // 处理输入内容
                        String inputText = editText.getText().toString().trim();
                        //Toast.makeText(tabView.getContext(), "正在查找-ID：" + inputText, Toast.LENGTH_SHORT).show();
                        //----
                        OkHttpClient client = new OkHttpClient();
                        RequestBody body = new FormBody.Builder()
                                .add("source",source)
                                .add("sourceName",sourceName)
                                .add("target",inputText)
                                .build();
                        Request request = new Request.Builder()
                                .url(Common.URL+"/square/add")
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
                                if (response.isSuccessful()) {//回调的方法执行在子线程。
                                    String res = response.body().string();
                                    System.out.println(res);
                                    if(res.equals("repeated")){
                                        Looper.prepare();
                                        Toast.makeText(tabView.getContext(), "请勿重复添加~", Toast.LENGTH_SHORT).show();
                                        Looper.loop();
                                    }else if(res.equals("successful")) {
                                        Looper.prepare();
                                        Toast.makeText(tabView.getContext(), "添加成功-ID!", Toast.LENGTH_SHORT).show();
                                        Looper.loop();
                                    }
                                } else {
                                    Looper.prepare();
                                    Toast.makeText(tabView.getContext(), "该用户不存在，请检查好友ID", Toast.LENGTH_SHORT).show();
                                    System.out.println("response failed");
                                    Looper.loop();
                                }
                            }});
                        //----
                    }
                })
                .setNegativeButton("取消",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    private AbsListView.OnScrollListener scrollListener = new AbsListView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {}
        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            swipeRefreshLayout.setEnabled(firstVisibleItem == 0);
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        tabView = inflater.inflate(R.layout.tab_friend, container, false);
        listView = tabView.findViewById(R.id.list_atten);
        listView_new = tabView.findViewById(R.id.list_new);
        Button tBtn = tabView.findViewById(R.id.toggleButton);
        Button nBtn = tabView.findViewById(R.id.newsButton);
        Button aBtn = tabView.findViewById(R.id.addButton);
        swipeRefreshLayout = tabView.findViewById(R.id.swipeRefreshLayout);

        Threads_GetAtten GetAtten = new Threads_GetAtten();
        GetAtten.start();

        Threads_GetFans GetFans = new Threads_GetFans();
        GetFans.start();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent,View view,int i,long l){
                String result = ((TextView) view).getText().toString();
                Toast.makeText(tabView.getContext(),result,Toast.LENGTH_LONG).show();
            }
        });
        tBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int visibility = listView.getVisibility();
                listView.setVisibility(visibility == View.VISIBLE ? View.GONE : View.VISIBLE);
            }
        });
        nBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int visibility = listView_new.getVisibility();
                listView_new.setVisibility(visibility == View.VISIBLE ? View.GONE : View.VISIBLE);
            }
        });

        aBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInputDialog();
            }
        });

        listView.setOnScrollListener(scrollListener);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Threads_GetAtten GetAtten = new Threads_GetAtten();
                GetAtten.start();

                Threads_GetFans GetFans = new Threads_GetFans();
                GetFans.start();
            }
        });

        return tabView;
    }
}
