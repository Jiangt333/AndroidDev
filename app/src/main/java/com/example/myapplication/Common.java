package com.example.myapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.myapplication.entity.User;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Common {
    public static String URL = "http://172.17.36.214:8080";
    public static User user;
    public static Bitmap getHttpBitmap(String url) {
        java.net.URL FileUrl = null;
        Bitmap bitmap = null;
        try {
            FileUrl = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            HttpURLConnection conn = (HttpURLConnection) FileUrl.openConnection();
            conn.setConnectTimeout(0);
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }
}
