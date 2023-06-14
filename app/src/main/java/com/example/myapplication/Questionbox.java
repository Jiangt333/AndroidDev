package com.example.myapplication;

public class Questionbox {

        private int Id;

        private String sourcephone;		// 提问的人

        private String targetphone;		// 被问的人

        private String targetname;

        private String question;

        private String answer;

        private String state;
        private String time;

        public String getSourcePhone() {
                return sourcephone;
        }
        public void setSourcePhone(String sourcephone) {
                this.sourcephone = sourcephone;
        }
        public String getTargetPhone() {
                return targetphone;
        }
        public void setTargetPhone(String targetphone) {
                this.targetphone = targetphone;
        }
        public String getTargetName() {
                return targetname;
        }
        public void setTargetName(String targetname) {
                this.targetname = targetname;
        }
        public String getQuestion() {
                return question;
        }
        public void setQuestion(String question) {
                this.question = question;
        }
        public String getAnswer() {
                return answer;
        }
        public void setAnswer(String answer) {
                this.answer = answer;
        }
        public String getState() {
                return state;
        }
        public void setState(String state) {
                this.state = state;
        }
        public String getTime() {
                return time;
        }
        public void setTime(String time) {
                this.time = time;
        }
}

