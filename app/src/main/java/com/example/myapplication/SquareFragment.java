package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class SquareFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View tabView = inflater.inflate(R.layout.tab_square, container, false);
//        TextView txt = (TextView)tabView.findViewById(R.id.test_txt2);
//        txt.setText("广 场");
        return tabView;
    }
}
