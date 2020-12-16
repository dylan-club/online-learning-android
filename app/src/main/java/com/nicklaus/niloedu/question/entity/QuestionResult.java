package com.nicklaus.niloedu.question.entity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.Nullable;

public class QuestionResult implements Parcelable {

    private Integer id;
    private String questionTitle;
    private String correctAnswer;
    private String userAnswer;
    private boolean isCorrect;

    public QuestionResult(){}


    public QuestionResult(Integer id, String questionTitle, String correctAnswer, String userAnswer, boolean isCorrect) {
        this.id = id;
        this.questionTitle = questionTitle;
        this.correctAnswer = correctAnswer;
        this.userAnswer = userAnswer;
        this.isCorrect = isCorrect;
    }


    protected QuestionResult(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        questionTitle = in.readString();
        correctAnswer = in.readString();
        userAnswer = in.readString();
        isCorrect = in.readByte() != 0;
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
        dest.writeString(correctAnswer);
        dest.writeString(userAnswer);
        dest.writeByte((byte) (isCorrect ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<QuestionResult> CREATOR = new Creator<QuestionResult>() {
        @Override
        public QuestionResult createFromParcel(Parcel in) {
            return new QuestionResult(in);
        }

        @Override
        public QuestionResult[] newArray(int size) {
            return new QuestionResult[size];
        }
    };

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

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public String getUserAnswer() {
        return userAnswer;
    }

    public void setUserAnswer(String userAnswer) {
        this.userAnswer = userAnswer;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        return super.equals(obj);
    }
}
