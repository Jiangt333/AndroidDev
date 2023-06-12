package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class InfoFragment extends Fragment {
        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
                View tabView = inflater.inflate(R.layout.tab_info, container, false);
//                TextView txt = (TextView)tabView.findViewById(R.id.test_txt3);
//                txt.setText("我 的");

                return tabView;
        }
}
