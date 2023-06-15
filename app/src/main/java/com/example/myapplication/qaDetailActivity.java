package com.example.myapplication;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class qaDetailActivity extends AppCompatActivity {
//    不能在这里就写这句，否则会闪退！！！！！！
//    TextView TopBarTitle = (TextView)findViewById(R.id.topbar_title);

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qa_detail);
        TextView TopBarTitle = (TextView)findViewById(R.id.topbar_title);
        TopBarTitle.setText("回答详情");
        TextView qtext = (TextView)findViewById(R.id.question);
        TextView atext = (TextView)findViewById(R.id.answer);
        qtext.setText(Common.questionList.get(Common.nowpos));
        atext.setText(Common.answerList.get(Common.nowpos));
    }

}
