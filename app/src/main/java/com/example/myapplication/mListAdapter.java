package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class mListAdapter extends ArrayAdapter<listviewItem> {

    private int resourceId;
//    private Context mContext;
//    private List<String> mData;

    public mListAdapter(Context context, int textViewResourceId,
                        List<listviewItem> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        listviewItem lvItem = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, null);
        TextView question = (TextView)  view.findViewById(R.id.question);
        TextView time = (TextView) view.findViewById(R.id.time);
        System.out.println(lvItem.getQuestion());
        System.out.println(lvItem.getTime());
        question.setText(lvItem.getQuestion());
        time.setText(lvItem.getTime());
        return view;
    }
}
