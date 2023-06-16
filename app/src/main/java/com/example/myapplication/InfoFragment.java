package com.example.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.Interface.FragmentInterface;
import com.example.myapplication.entity.User;
import com.google.gson.Gson;

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
        ImageView headimg;
        private Gson gson = new Gson();
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
                        Bundle bundle = (Bundle)msg.obj;
                        byte[] c=  bundle.getByteArray("bytes");
                        Bitmap bitmap = BitmapFactory.decodeByteArray(c, 0, c.length);
                        headimg.setImageBitmap(bitmap);
                }
        };

        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
                View tabView = inflater.inflate(R.layout.tab_info, container, false);
                EditText txt = tabView.findViewById(R.id.editTextText);
                txt.setText(host.getName());
                headimg = tabView.findViewById(R.id.header);
                headimg.setImageResource(R.drawable.img_1);
                headimg.setOnClickListener(this::onClickUpload);
                headimg.setOnClickListener(this::onClickUpload);
                headimg.setOnClickListener(this::onClickUpload);
                Button ExistBtn = tabView.findViewById(R.id.exist);
                ExistBtn.setOnClickListener(this::onExist);
                //Getheader();
                return tabView;
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
                                        Message msg=new Message();
                                        msg.what=0;
                                        msg.obj=bundle;
                                        handler.sendMessage(msg);

                                } else {
                                        System.out.println("response failed");
                                }
                        }
                });
        }
}