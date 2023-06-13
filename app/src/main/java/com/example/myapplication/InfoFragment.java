package com.example.myapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.entity.User;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class InfoFragment extends Fragment {
        private User host;
        public  InfoFragment(User host)
        {
                this.host=host;
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
                View tabView = inflater.inflate(R.layout.tab_info, container, false);
                TextView txt = tabView.findViewById(R.id.textView5);
                txt.setText(host.getName());
                ImageView img = tabView.findViewById(R.id.header);
                /*Bitmap bitmap = Common.getLoacalBitmap("res/drawble/img_1.png");
                img.setImageBitmap(bitmap);*/
                img.setImageResource(R.drawable.img_1);
                return tabView;
        }



}
