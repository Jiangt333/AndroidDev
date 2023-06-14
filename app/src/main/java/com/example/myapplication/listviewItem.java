package com.example.myapplication;

public class listviewItem {
    private String question;

    private String time;
    public listviewItem() {

    }

    public listviewItem(String question, String time) {
        this.question = question;
        this.time= time;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setTime(String time) {
        this.time = time;
    }


    public String getQuestion() {
        return question;
    }

    public String getTime() {
        return time;
    }

}
