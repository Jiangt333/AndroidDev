package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity implements  View.OnClickListener{
    private String phonenum = "";
    private String password = "";
    private String con_password = "";
    private EditText Phone;
    private EditText Code;
    private EditText Confirm;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        TextView btnLogin = findViewById(R.id.btn_login);
        TextView btnRegister = findViewById(R.id.btn_register);
        Button NowRegister = findViewById(R.id.btn_vertify);

        Phone = findViewById(R.id.phone);
        Code = findViewById(R.id.code);
        Confirm = findViewById(R.id.confirm);

        btnLogin.setOnClickListener(this::onClick_login);
        btnRegister.setOnClickListener(this);
        NowRegister.setOnClickListener(this::onClick_register);
    }

    public void onClick(View v){

    }
    public void onClick_login(View v){
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
    }
    public void onClick_register(View v){
        phonenum = Phone.getText().toString();
        password = Code.getText().toString();
        con_password = Confirm.getText().toString();
        //搜索数据库，如果有相同的手机号，提示失败

        //若密码与验证密码不同，弹出红色的x

        //若密码与验证密码相同，变为绿色的√

    }
}
