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

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import java.util.List;

public class mListAdapter extends ArrayAdapter<listviewItem> {

    private int resourceId;
    private FragmentActivity mContext;
//    private List<String> mData;

    public mListAdapter(FragmentActivity context, int textViewResourceId, List<listviewItem> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
        mContext = context;
    }

    public void remove(int position) {
        for(int j=Common.lvItemList.size()-1;j>=0;j--) {
            listviewItem item = Common.lvItemList.get(j);
            System.out.println("j="+j);
            System.out.println("nowpos="+Common.nowpos);
            System.out.println("item="+item);
            if (j == Common.nowpos) {
                Common.lvItemList.remove(j);
                Common.adapter.notifyDataSetChanged();
                System.out.println("remove!");
            }
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View mview = LayoutInflater.from(getContext()).inflate(resourceId, null);

        listviewItem lvItem = getItem(position);
        TextView question = (TextView)  mview.findViewById(R.id.question);
        TextView questiontime = (TextView) mview.findViewById(R.id.questiontime);
        question.setText(lvItem.getQuestion());
        questiontime.setText(lvItem.getQuestionTime());

        LinearLayout ll_question = mview.findViewById(R.id.ll_question);
        ll_question.setOnClickListener(new View.OnClickListener() {
            Intent intent;
            @Override
            public void onClick(View view) {
//                Toast.makeText(getContext(),"你点击了第"+position+"项",Toast.LENGTH_SHORT).show();
//                TextView qtext = ll_question.findViewById(R.id.question);
                TextView qtext = (TextView)  view.findViewById(R.id.question);
                String question = qtext.getText().toString();
                System.out.println(question);
                Common.nowpos = Common.questionList.indexOf(question);
                intent = new Intent(mContext, qaDetailActivity.class);
                mContext.startActivity(intent);
            }
        });

        TextView deletebtn = mview.findViewById(R.id.deletebtn);
        deletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView text = (TextView) mview.findViewById(R.id.question);
                String question = text.getText().toString();
                Common.nowpos = Common.questionList.indexOf(question);
//                System.out.println("pos"+Common.nowpos);
//                System.out.println("id"+Common.idList.get(Common.nowpos));
                // 在适配器里要使用getSupportFragmentManager()，要通过FragmentActivity类型来获取，要将原本Context改为FragmentActivity
                new mBottomSheetDialogFragment().show(mContext.getSupportFragmentManager(), "Dialog");
            }
        });

        return mview;
    }


}
