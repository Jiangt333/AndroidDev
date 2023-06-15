package com.example.myapplication;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class mListAdapter extends ArrayAdapter<listviewItem> {

    private int resourceId;
    private Context mContext;
//    private List<String> mData;

    public mListAdapter(Context context, int textViewResourceId, List<listviewItem> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
        mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(getContext()).inflate(resourceId, null);

        listviewItem lvItem = getItem(position);
        TextView question = (TextView)  view.findViewById(R.id.question);
        TextView time = (TextView) view.findViewById(R.id.time);
        question.setText(lvItem.getQuestion());
        time.setText(lvItem.getTime());

        LinearLayout ll_question = view.findViewById(R.id.ll_question);

        ll_question.setOnClickListener(new View.OnClickListener() {
            Intent intent;
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),"你点击了第"+position+"项",Toast.LENGTH_SHORT).show();

                intent = new Intent(mContext, qaDetailActivity.class);

                mContext.startActivity(intent);
                System.out.println("yes");
            }
        });

        return view;
    }


}
