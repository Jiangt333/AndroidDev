package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AskActivity extends AppCompatActivity {
    private EditText askPlace;//提问处
    private ListView answered;//已回答过的问题列表
    private Button commit;

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
                String myPhone = "1";//从适配器中传入的参数，应为所选择用户的手机号
                client = new OkHttpClient();
                //目前已经有关注的列表，但该如何取他们近期的回答？
                RequestBody body = new FormBody.Builder()
                        .add("phone", myPhone)
                        .build();
                Request request = new Request.Builder()
                        .url(Common.URL + "/ask/acquire")
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

                        if (response.isSuccessful()) {//回调的方法执行在子线程。
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                }
                            });
                        } else {
                            System.out.println("wrong");
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    }
                });
            }
        }
    }

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask);
        answered.findViewById(R.id.answered);
        askPlace.findViewById(R.id.askPlace);
        commit.findViewById(R.id.commit);
    }
}
