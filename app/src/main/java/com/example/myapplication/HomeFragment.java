package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
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
import java.util.List;

import okhttp3.CacheControl;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HomeFragment extends Fragment implements View.OnClickListener {
    View tabView;

    ListView listView;


    private LinearLayout QmeUnansweredTab;
    private LinearLayout QmeAnsweredTab;
    private LinearLayout meQUnansweredTab;
    private LinearLayout meQAnsweredTab;
    private Gson gson = new Gson();

    List<Questionbox> QBox;

    List<listviewItem> lvItemList;

    class Threads_GetBox extends Thread {
        // 获取提问箱列表
        private OkHttpClient client = null;
        String phone = null;
        String state = null;
        String server = null;
        @Override
        public void run() {


            client = new OkHttpClient();
            RequestBody body = new FormBody.Builder()
                    .add("phone", phone)
                    .add("state", state)
                    .build();
            Request request = new Request.Builder()
                    .url(Common.URL + server)
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
                        String QBoxJson = response.body().string();
                        QBox = gson.fromJson(QBoxJson, new TypeToken<ArrayList<Questionbox>>(){}.getType());
//                        System.out.println("QBox"+QBox);
                        Common.questionList = new ArrayList<>();    // 一定要在这里new，在Common里不要new
                        Common.timeList = new ArrayList<>();
                        Common.answerList = new ArrayList<>();
                        for(Questionbox qb : QBox){
                            Common.questionList.add(qb.getQuestion());
                        }
                        for(Questionbox qb : QBox){
                            Common.timeList.add(qb.getTime());
                        }
                        for(Questionbox qb : QBox){
                            Common.answerList.add(qb.getAnswer());
                        }
                        System.out.println("congratulation!");

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                listView = (ListView) tabView.findViewById(R.id.listview_1);
                                lvItemList = new ArrayList<listviewItem>();
                                InitlvItem();
//                                System.out.println("ql"+Common.questionList);
//                                System.out.println("tl"+Common.timeList);
//                                System.out.println("ll"+lvItemList);
//                                ArrayAdapter<String> adapter = new ArrayAdapter<>(tabView.getContext(), android.R.layout.simple_list_item_1, questionList);
                                mListAdapter adapter = new mListAdapter(tabView.getContext(), R.layout.listview_item, lvItemList);
                                listView.setAdapter(adapter);
                            }
                        });
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
        InitData();
        InitEvents();
        return tabView;
    }

    public void InitEvents(){
        QmeUnansweredTab.setOnClickListener(this);
        QmeAnsweredTab.setOnClickListener(this);
        meQUnansweredTab.setOnClickListener(this);
        meQAnsweredTab.setOnClickListener(this);
    }

    public void InitData(){
        QmeUnansweredTab = (LinearLayout) tabView.findViewById(R.id.id_QmeUnanswered);
        QmeAnsweredTab = (LinearLayout) tabView.findViewById(R.id.id_QmeAnswered);
        meQUnansweredTab = (LinearLayout) tabView.findViewById(R.id.id_meQUnanswered);
        meQAnsweredTab = (LinearLayout) tabView.findViewById(R.id.id_meQAnswered);
        Threads_GetBox GetBox_0 = new Threads_GetBox();
        GetBox_0.phone = "18060142936";
        GetBox_0.state = "0";
        GetBox_0.server = "/gettarget";
        GetBox_0.start();
    }
    private void InitlvItem() {
        int length = Common.questionList.size();
        System.out.println("qlsize="+length);
        int i = 0;
        while(i < length){
            listviewItem lvitem = new listviewItem(Common.questionList.get(i), Common.timeList.get(i));
            lvItemList.add(lvitem);
            i++;
        }
    }


    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.id_QmeUnanswered) {
            selectTabBtn(0);
        }
        if(v.getId() == R.id.id_QmeAnswered) {
            selectTabBtn(1);
        }
        if(v.getId() == R.id.id_meQUnanswered) {
            selectTabBtn(2);
        }
        if(v.getId() == R.id.id_meQAnswered) {
            selectTabBtn(3);
        }

    }
    public void selectTabBtn(int i) {

        // 根据点击的Tab按钮设置对应的响应
        switch (i) {
            case 0:
                Threads_GetBox GetBox_1 = new Threads_GetBox();
                GetBox_1.phone = "18060142936";
                GetBox_1.state = "0";
                GetBox_1.server = "/gettarget";
                GetBox_1.start();
                break;

            case 1:
                Threads_GetBox GetBox_2 = new Threads_GetBox();
                GetBox_2.phone = "18060142936";
                GetBox_2.state = "1";
                GetBox_2.server = "/gettarget";
                GetBox_2.start();
                break;

            case 2:
                Threads_GetBox GetBox_3 = new Threads_GetBox();
                GetBox_3.phone = "18060142936";
                GetBox_3.state = "0";
                GetBox_3.server = "/getsource";
                GetBox_3.start();
                break;

            case 3:
                Threads_GetBox GetBox_4 = new Threads_GetBox();
                GetBox_4.phone = "18060142936";
                GetBox_4.state = "1";
                GetBox_4.server = "/getsource";
                GetBox_4.start();
                break;
        }
    }
}