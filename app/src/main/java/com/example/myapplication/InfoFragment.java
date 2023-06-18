package com.example.myapplication;

import static android.view.KeyEvent.KEYCODE_ENTER;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.Interface.FragmentInterface;
import com.example.myapplication.entity.User;
import com.example.myapplication.tool.Image;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class InfoFragment extends Fragment {
        private User host;
        private ImageView headimg;
        private  View tabView;
        private FragmentInterface Listener;

        public InfoFragment(User host) {
                this.host = host;
        }

        @Nullable
        @Override
        public void onAttach(Context context) {
                super.onAttach(context);
                if (context instanceof FragmentInterface) {
                        Listener = (FragmentInterface) context;
                } else {
                        throw new RuntimeException(context.toString()
                                + " must implement OnListener");
                }
        }

        Handler handler = new Handler(Looper.myLooper()) {
                @Override
                public void handleMessage(@NonNull Message msg) {
                        super.handleMessage(msg);
                        Bundle bundle = (Bundle)msg.obj;//在主线程接收
                        byte[] c=  bundle.getByteArray("bytes");
                        Bitmap bitmap = BitmapFactory.decodeByteArray(c, 0, c.length);
                        headimg.setImageBitmap(bitmap);
                }
        };

        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
                tabView = inflater.inflate(R.layout.tab_info, container, false);
                TextView txt = tabView.findViewById(R.id.name);
                txt.setText(host.getName());
                EditText pass = tabView.findViewById(R.id.showpassword);
                pass.setText(host.getRealpassword());
                headimg = tabView.findViewById(R.id.header);
                if(host.getIschanged()==1) {
                        Getheader();
                }
                else
                {
                        headimg.setImageResource(R.drawable.img_1);
                }
                headimg.setOnClickListener(this::onClickUpload);
                Button ExistBtn = tabView.findViewById(R.id.exist);
                ExistBtn.setOnClickListener(this::onExist);
                pass.setOnKeyListener(this::onPassword);
                return tabView;
        }

        private boolean onPassword(View v, int i, KeyEvent keyEvent) {
                if(i==KEYCODE_ENTER) {
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                        alertDialogBuilder.setTitle("提示");
                        alertDialogBuilder.setMessage("是否确认修改密码？");
                        alertDialogBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                        EditText pass = tabView.findViewById(R.id.showpassword);
                                        String password = pass.getText().toString();
                                        OkHttpClient client = new OkHttpClient();
                                        RequestBody body = new FormBody.Builder()
                                                .add("phone",Common.user.getPhone())
                                                .add("password",password)
                                                .build();
                                        Request request = new Request.Builder()
                                                .url(Common.URL+"/changePassword")
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

                                                        System.out.println("start on");
                                                        if(response.isSuccessful())
                                                        {}
                                                        else
                                                                System.out.println("fail");
                                                }
                                        });
                                        dialog.cancel();

                                }
                        });
                        alertDialogBuilder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                }
                        });
                        alertDialogBuilder.show();
                        return true;
                }
                else
                        return false;

        }


        public void onClickUpload(View v) {
                Listener.photo();
        }
        public void onExist(View v) {
                Listener.exist();
        }
        public void Getheader() {
                OkHttpClient client = new OkHttpClient();
                Bitmap bitmap;
                RequestBody body = new FormBody.Builder()
                        .add("phone", Common.user.getPhone())
                        .build();
                Request request = new Request.Builder()
                        .url(Common.URL + "/getheader")
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

                                System.out.println("start on");
                                if (response.isSuccessful()) {//回调的方法执行在子线程。
                                        byte[] data = response.body().bytes();
                                        Bundle bundle=new Bundle();
                                        bundle.putByteArray("bytes",data);
                                        Message msg=new Message();//发送到主线程
                                        msg.obj=bundle;
                                        handler.sendMessage(msg);

                                } else {
                                        System.out.println("response failed");
                                }
                        }
                });
        }
}