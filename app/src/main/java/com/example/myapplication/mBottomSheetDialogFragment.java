package com.example.myapplication;

import android.os.Bundle;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.gson.reflect.TypeToken;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import androidx.fragment.app.FragmentPagerAdapter;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.CacheControl;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class mBottomSheetDialogFragment extends BottomSheetDialogFragment implements View.OnClickListener{

    class Threads_DeleteItem extends Thread {
        // 删除提问箱列表中的某项
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
                        System.out.println("delete!");
                        Common.adapter.remove(Common.nowpos);
                    }
                    else {
                        System.out.println("wrong");
                    }
                }
            });
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet, container, false);
        Button deletebutton = view.findViewById(R.id.deletebutton);
        deletebutton.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
            DeleteItem();
    }

    public void DeleteItem(){
        Threads_DeleteItem DeleteItem = new Threads_DeleteItem();
        DeleteItem.id = Common.idList.get(Common.nowpos).toString();
        DeleteItem.server = "/DeleteItem";
        DeleteItem.start();
    }
}
