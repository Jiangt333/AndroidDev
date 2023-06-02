package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MainActivity extends AppCompatActivity implements  View.OnClickListener {

    //数据库连接类
    private static Connection con = null;
    private static PreparedStatement stmt = null;
    private EditText Username;
    private EditText PassWord;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //登录跳转
        Button btnLogin = findViewById(R.id.bt_login);
        Username = findViewById(R.id.et_1);
        PassWord = findViewById(R.id.et_2);
        btnLogin.setOnClickListener(this);
    }
    class Threads_Login extends Thread {
        @Override
        public void run() {
            String username = Username.getText().toString();
            String password = PassWord.getText().toString();  //用户发送的信息
            ResultSet rs = null;
            try {
                con = MySQLConnections.getConnection();
                if (con != null) {
                    String sql = "select * from user";
                    stmt = (PreparedStatement) con.prepareStatement(sql);
                    // 关闭事务自动提交 ,这一行必须加上
                    con.setAutoCommit(false);
                    rs = stmt.executeQuery();
                    //清空上次发送的信息
                    rs.next();
                    String RealPassword = rs.getString(2);
                    con.close();
                    rs.close();
                    stmt.close();
                    Intent intent = null;
                    if (password.equals(RealPassword)) {
                        intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                }
            } catch (SQLException e){
                throw new RuntimeException(e);
            }

        }
    }

    public void onClick(View v){
        Threads_Login login = new Threads_Login();
        login.start();
    }
}