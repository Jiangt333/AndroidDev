package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements  View.OnClickListener {

    private Button BtnLogin;
    private EditText Username;
    private EditText PassWord;
    final MyHelper dbHelper=new MyHelper(MainActivity.this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        //登录跳转
        BtnLogin = findViewById(R.id.bt_login);
        Username = findViewById(R.id.et_1);
        PassWord = findViewById(R.id.et_2);
        BtnLogin.setOnClickListener(this);
    }

    public void onClick(View v){
        String username = Username.getText().toString();
        String password = PassWord.getText().toString();
        Intent intent = null;

        if(username.equals("zhangcw") && password.equals("123456")) {
            intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }
        else
        {

        }
    }
}