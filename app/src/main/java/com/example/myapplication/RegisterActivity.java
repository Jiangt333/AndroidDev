package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;

import android.view.Gravity;
import android.widget.Button;
import android.widget.EditText;

import android.widget.Toast;

import java.sql.SQLException;
import java.util.Timer;
import java.util.TimerTask;

import com.mob.MobSDK;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class RegisterActivity extends AppCompatActivity {
    private TimerTask timerTask;
    private Timer timer;
    private EditText inputPhone;
    private EditText inputCode;
    private EditText inputPassword;
    private Button get_code;
    private Button commit;
    public String country = "86";
    private String phone;
    private String password;
    private int TIME = 60;
    private static final int CODE_REPEAT = 1;
    private static Connection con = null;
    private static PreparedStatement stmt = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //mob
        MobSDK.init(this, "3805a30a09595", "4126fd577130e07a64873af014315bed");
        MobSDK.submitPolicyGrantResult(true);
        //注册回调
        SMSSDK.registerEventHandler(eventHandle);
        //窗口初始化
        initView();
    }

    //处理获取验证码
    @SuppressLint("HandlerLeak")
    Handler handle = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == CODE_REPEAT) {
                commit.setEnabled(true);
                get_code.setEnabled(true);
                timer.cancel();
                timerTask.cancel();
                TIME = 60;
                get_code.setText("获取验证码");
            } else {
                String showText = "("+TIME + "s)";
                get_code.setText(showText);
            }
        }
    };
    @SuppressLint("HandlerLeak")
    Handler submitHandle=new Handler(){
        @Override
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            @SuppressLint("HandlerLeak") int event = msg.arg1;
            int result=msg.arg2;
            Object data=msg.obj;
            if (result == SMSSDK.RESULT_COMPLETE) {
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                    Toast.makeText(RegisterActivity.this, "验证成功！", Toast.LENGTH_LONG).show();
                    /*注册，向数据库中插入该项*/
                    //将手机号、密码、注册时间加入数据库
                    con = MySQLConnections.getConnection();
                    ResultSet rs = null;
                    try{
                        if (con != null) {
                            String sql = "INSERT INTO user_information (name, pword) VALUES ('"+phone+"', '"+password+"')";
                            stmt = (PreparedStatement) con.prepareStatement(sql);
                            // 关闭事务自动提交 ,这一行必须加上
                            con.setAutoCommit(false);
                            rs = stmt.executeQuery();

                            //清空上次发送的信息
                            rs.next();
                            con.close();
                            rs.close();
                            stmt.close();
                        }
                    } catch(SQLException e){
                        throw new RuntimeException(e);
                    }
                    startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                }else {
                    Toast.makeText(RegisterActivity.this, "验证错误！", Toast.LENGTH_LONG).show();
                }
            }
        }
    };

    EventHandler eventHandle = new EventHandler() {
        @Override
        public void afterEvent(int event, int result, Object data) {
            Message msg = new Message();
            msg.arg1 = event;
            msg.arg2 = result;
            msg.obj = data;
            submitHandle.sendMessage(msg);
        }
    };

    private void initView() {

        inputCode = findViewById(R.id.code);
        inputPhone = findViewById(R.id.phone);
        inputPassword = findViewById(R.id.password);
        get_code = findViewById(R.id.get_code);
        commit = findViewById(R.id.commit);

        get_code.setOnClickListener(view -> {
            phone = inputPhone.getText().toString().trim().replaceAll("/s","");
            if(!TextUtils.isEmpty(phone)){
                //判断手机号格式是否正确，不正确则提示
                if(!isPhoneValid(phone)){
                    Toast.makeText(RegisterActivity.this, "手机号格式错误", Toast.LENGTH_LONG).show();
                    return;
                }
                //判断手机号是否存在于数据库第二行，若存在，弹窗提示无法注册
/*
                if(isPhoneExist(phone)){
                    Toast.makeText(RegisterActivity.this, "该手机号已注册！", Toast.LENGTH_LONG).show();
                    return;
                }
*/
                //发送验证码
                alterWarning();
            }else {
                Toast.makeText(RegisterActivity.this, "请输入手机号", Toast.LENGTH_LONG).show();
            }
        });

        commit.setOnClickListener(view -> {
            String code = inputCode.getText().toString().replaceAll("/s","");
            phone = inputPhone.getText().toString().trim().replaceAll("/s","");
            password = inputPassword.getText().toString().trim().replaceAll("/s","");

            if(TextUtils.isEmpty(code))
            {
                Toast.makeText(RegisterActivity.this,"验证码为空",Toast.LENGTH_LONG).show();
                return;
            }
            else if(phone.length() == 0)
            {
                Toast.makeText(RegisterActivity.this,"请输入手机号",Toast.LENGTH_LONG).show();
                return;
            }
            else if(password.length() == 0)
            {
                Toast.makeText(RegisterActivity.this,"请设置密码",Toast.LENGTH_LONG).show();
                return;
            }
            //验证
            SMSSDK.submitVerificationCode(country,phone,code);
        });
    }

    //判断手机号格式是否正确
    private boolean isPhoneValid(String phone) {
        // 此处根据需求自行修改手机号正则表达式
        String regex = "^1([38][0-9]|4[579]|5[0-3,5-9]|6[6]|7[0135678]|9[89])\\d{8}$";//手机号正则
        return phone.matches(regex);
    }

    //判断手机号是否存在于数据库中
    private boolean isPhoneExist(String phone) {
        con = MySQLConnections.getConnection();
        ResultSet rs = null;
        try {
            if (con != null) {
                String sql = "SELECT * FROM user_information WHERE name = '"+phone+"'";
                stmt = (PreparedStatement) con.prepareStatement(sql);
                stmt.setString(1, phone);
                rs = stmt.executeQuery();
                boolean exists = rs.next();
                rs.close();
                stmt.close();
                con.close();
                return exists;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    private void alterWarning(){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("短信验证");
        builder.setMessage("将发送验证短信到"+phone+"进行验证，请您确认");

        builder.setPositiveButton("确定", (dialogInterface, i) -> {
            dialogInterface.dismiss();//关闭dialog
            SMSSDK.getVerificationCode(country,phone);//发送短信验证码
            Toast.makeText(RegisterActivity.this,"已发送"+i,Toast.LENGTH_LONG).show();
            get_code.setEnabled(false);//获取验证码按钮设置不可点击
            commit.setEnabled(true);//提交按钮可点击
            timer=new Timer();
            timerTask=new TimerTask() {
                @Override
                public void run() {
                    handle.sendEmptyMessage(TIME--);
                }
            };
            timer.schedule(timerTask,0,1000);
        });

        builder.setNegativeButton("取消", (dialogInterface, i) -> {
            dialogInterface.dismiss();
            Toast.makeText(RegisterActivity.this,"已取消"+i,Toast.LENGTH_LONG).show();
        });
        builder.create().show();//创建并展示
    }

    //销毁短信注册
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 注销回调
        SMSSDK.unregisterEventHandler(eventHandle);
    }

}
