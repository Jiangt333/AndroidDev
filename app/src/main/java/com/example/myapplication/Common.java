package com.example.myapplication;

import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class Common {

    public static String URL = "http://172.17.22.225:8080";

    public static List<Questionbox> QBox = new ArrayList<>();

    public static List<listviewItem> lvItemList = new ArrayList<listviewItem>();
    public static ListView listView;
    public static mListAdapter adapter;


    public static ArrayList<Integer> idList = new ArrayList<>();
    public static ArrayList<String> questionList = new ArrayList<>();
    public static ArrayList<String> answerList = new ArrayList<>();
    public static ArrayList<String> sourcephoneList = new ArrayList<>();
    public static ArrayList<String> targetphoneList = new ArrayList<>();
    public static ArrayList<String> questiontimeList = new ArrayList<>();
    public static ArrayList<String> answertimeList = new ArrayList<>();
//    public static ArrayList<Integer> idList;
//    public static ArrayList<String> questionList;
//    public static ArrayList<String> answerList;
//    public static ArrayList<String> sourcephoneList;
//    public static ArrayList<String> targetphoneList;
//    public static ArrayList<String> questiontimeList;
//    public static ArrayList<String> answertimeList ;
    public static int nowpos;   // 当前选中的问题在列表中的索引值

    public static mBottomSheetDialogFragment BottomSheet = new mBottomSheetDialogFragment();

    public static boolean deletingItemFlag = false;


}
