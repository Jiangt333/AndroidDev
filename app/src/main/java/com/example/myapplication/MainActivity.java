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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements  View.OnClickListener {

    // 数据库连接类
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
        private Socket client = null;
        private BufferedReader in;
        private BufferedWriter out;
        @Override
        public void run() {
            String username = Username.getText().toString();
            String password = PassWord.getText().toString();  //用户发送的信息
            ResultSet rs = null;
            try {
                con = MySQLConnections.getConnection();
                if (con != null) {
                    String sql = "select * from user_information where name='"+username+"'";
                    stmt = (PreparedStatement) con.prepareStatement(sql);
                    // 关闭事务自动提交 ,这一行必须加上
                    con.setAutoCommit(false);
                    rs = stmt.executeQuery();
                    //清空上次发送的信息
                    rs.next();
                    String RealPassword = rs.getString(3);
                    System.out.println("password=" + RealPassword);
                    con.close();
                    rs.close();
                    stmt.close();
                    Intent intent = null;
                    if (password.equals(RealPassword)) {
                        intent = new Intent(MainActivity.this, TotalActivity.class);
                        startActivity(intent);
                    }
                }
            } catch (SQLException e){
                throw new RuntimeException(e);
            }

            //以登录为例，但登录可以直接调数据库
//            try {
//                client = new Socket("172.27.43.168", 3430);
//                in = new BufferedReader(new InputStreamReader(client.getInputStream()));
//                out = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
//                //@前面的内容用来区分请求，后面的内容可以根据具体情况改变，在后端实现
//                String msg = "Login" + "@" + username + " " + password;
//                out.write(msg);
//                out.flush();
//                client.shutdownOutput();
//                String recv = "";
//                recv = in.readLine();
//                //这里先采用简单的响应
//                if(recv.equals("yes")) {
//                    Intent intent = null;
//                    intent = new Intent(MainActivity.this, LoginActivity.class);
//                    startActivity(intent);
//                }
//                else {
//
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }

        }
    }

    public void onClick(View v){
        Threads_Login login = new Threads_Login();
        login.start();
    }
}