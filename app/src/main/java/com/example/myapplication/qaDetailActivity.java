package com.example.myapplication;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class qaDetailActivity extends AppCompatActivity {
//    不能在这里就写这句，否则会闪退！！！！！！
//    TextView TopBarTitle = (TextView)findViewById(R.id.topbar_title);

    class Threads_Answer extends Thread {
        // 写回答/编辑回答
        private OkHttpClient client = null;
        String id = null;
        String server = null;

        @Override
        public void run() {
            client = new OkHttpClient();
            RequestBody body = new FormBody.Builder()
                    .add("id", id)
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
                    if(response.isSuccessful()){//回调的方法执行在子线程。

                    }
                    else {
                        System.out.println("wrong");
                    }
                }
            });
        }
    }
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qa_detail);
        TextView TopBarTitle = (TextView)findViewById(R.id.topbar_title);
        TopBarTitle.setText("回答详情");
        TextView qtext = (TextView)findViewById(R.id.question);
        qtext.setText(Common.questionList.get(Common.nowpos));

        EditText editText = (EditText)findViewById(R.id.answer);
        String question = qtext.getText().toString();
        Common.nowpos = Common.questionList.indexOf(question);
        String state = Common.stateList.get(Common.nowpos);
        int id = Common.idList.get(Common.nowpos);
//        System.out.println("id="+id+" nowpos="+Common.nowpos+" hometabNum="+Common.hometabNum+" state="+state);

        if(Common.hometabNum == 0 && state.equals("0")){        // 提问我但我未回答的
            editText.setEnabled(true);
            editText.setHint("请输入您的回答...");
        }
        if(Common.hometabNum == 1 && state.equals("1")){
            editText.setText(Common.answerList.get(Common.nowpos));
            editText.setEnabled(false);
        }
        if(Common.hometabNum == 2 && state.equals("0")){
            editText.setHint("正在等待回答...");
            editText.setEnabled(false);
        }
        if(Common.hometabNum == 3 && state.equals("1")){
            editText.setText(Common.answerList.get(Common.nowpos));
            editText.setEnabled(false);
        }
    }
}
