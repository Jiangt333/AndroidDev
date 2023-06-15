package com.example.myapplication;

import static com.google.android.material.internal.ContextUtils.getActivity;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;

import androidx.loader.content.CursorLoader;

import com.example.myapplication.entity.User;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Common {
    public static String URL = "http://172.27.98.218:8080";
    public static User user;
    public static String ImagePath = "D:\\myproject\\";
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
    public static String getRealPathFromUri(Context context, Uri uri) {
        String filePath = "";
        String scheme = uri.getScheme();
        if (scheme == null)
            filePath = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            filePath = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            String[] proj = { MediaStore.Images.Media.DATA };
            Cursor cursor = context.getContentResolver().query(uri, proj, null, null, null);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    filePath = cursor.getString(columnIndex);
                }
                cursor.close();
            }
            if (TextUtils.isEmpty(filePath)) {
                filePath = getFilePathForNonMediaUri(context, uri);
            }
        }
        return filePath;
    }
    //非媒体文件中查找
    private static String getFilePathForNonMediaUri(Context context, Uri uri) {
        String filePath = "";
        Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndexOrThrow("_data");
                filePath = cursor.getString(columnIndex);
            }
            cursor.close();
        }
        return filePath;
    }


}
