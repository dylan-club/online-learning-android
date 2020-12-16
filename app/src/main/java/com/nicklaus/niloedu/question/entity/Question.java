package com.nicklaus.niloedu.question.entity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.Nullable;

public class Question implements Parcelable {

    private Integer id;

    private String questionTitle;

    private String questionA;

    private String questionB;

    private String questionC;

    private String questionD;

    private String questionAnswer;

    private String userAnswer;

    private int checkPosition;

    protected Question(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        questionTitle = in.readString();
        questionA = in.readString();
        questionB = in.readString();
        questionC = in.readString();
        questionD = in.readString();
        questionAnswer = in.readString();
        userAnswer = in.readString();
        checkPosition = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(id);
        }
        dest.writeString(questionTitle);
        dest.writeString(questionA);
        dest.writeString(questionB);
        dest.writeString(questionC);
        dest.writeString(questionD);
        dest.writeString(questionAnswer);
        dest.writeString(userAnswer);
        dest.writeInt(checkPosition);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Question> CREATOR = new Creator<Question>() {
        @Override
        public Question createFromParcel(Parcel in) {
            return new Question(in);
        }

        @Override
        public Question[] newArray(int size) {
            return new Question[size];
        }
    };

    public int getCheckPosition() {
        return checkPosition;
    }

    public void setCheckPosition(int checkPosition) {
        this.checkPosition = checkPosition;
    }

    public String getUserAnswer() {
        if (userAnswer == null){
            userAnswer = "";
        }
        return userAnswer;
    }

    public void setUserAnswer(String userAnswer) {
        this.userAnswer = userAnswer;
    }

    public Question(){}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getQuestionTitle() {
        return questionTitle;
    }

    public void setQuestionTitle(String questionTitle) {
        this.questionTitle = questionTitle;
    }

    public String getQuestionA() {
        return questionA;
    }

    public void setQuestionA(String questionA) {
        this.questionA = questionA;
    }

    public String getQuestionB() {
        return questionB;
    }

    public void setQuestionB(String questionB) {
        this.questionB = questionB;
    }

    public String getQuestionC() {
        return questionC;
    }

    public void setQuestionC(String questionC) {
        this.questionC = questionC;
    }

    public String getQuestionD() {
        return questionD;
    }

    public void setQuestionD(String questionD) {
        this.questionD = questionD;
    }

    public String getQuestionAnswer() {
        return questionAnswer;
    }

    public void setQuestionAnswer(String questionAnswer) {
        this.questionAnswer = questionAnswer;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        return super.equals(obj);
    }
}
