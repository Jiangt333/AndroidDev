package com.example.myapplication;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import org.jetbrains.annotations.Nullable;

import java.util.List;

class AskListAdapter extends ArrayAdapter<String> {
    private Context context;
    private int resource;
    private List<String> items;

    public AskListAdapter(Context context, int resource, List<String> items) {
        super(context, resource, items);
        this.context = context;
        this.resource = resource;
        this.items = items;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(resource, null);
        }

        // Find the TextView in your listview_item_ask.xml
        TextView textViewQuestion = view.findViewById(R.id.question);

        // Set the text for the TextView
        textViewQuestion.setText(items.get(position));

        return view;
    }
}
