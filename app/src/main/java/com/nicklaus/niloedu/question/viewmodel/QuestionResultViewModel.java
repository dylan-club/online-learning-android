package com.nicklaus.niloedu.question.viewmodel;

import android.app.Application;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.nicklaus.niloedu.question.entity.Question;
import com.nicklaus.niloedu.question.entity.QuestionResult;

import java.util.ArrayList;
import java.util.List;

public class QuestionResultViewModel extends AndroidViewModel {

    private static final String INVALID_ANSWER = "您的答案为空！";

    private MutableLiveData<List<QuestionResult>> questionResultListLive;
    private MutableLiveData<Integer> userScoreLive;

    public QuestionResultViewModel(@NonNull Application application) {
        super(application);
        questionResultListLive = getQuestionResultListLive();
        userScoreLive = getUserScoreLive();
    }

    public MutableLiveData<Integer> getUserScoreLive() {
        if (userScoreLive == null){
            userScoreLive = new MutableLiveData<>();
            userScoreLive.setValue(0);
        }
        return userScoreLive;
    }

    public MutableLiveData<List<QuestionResult>> getQuestionResultListLive() {
        if (questionResultListLive == null){
            questionResultListLive = new MutableLiveData<>();
        }
        return questionResultListLive;
    }

    public void calculateUserScore(List<Question> userAnswers){
        List<QuestionResult> questionResults = new ArrayList<>();
        for (Question userAnswer : userAnswers) {
            if (userAnswer.getUserAnswer().equals("")){
                userAnswer.setUserAnswer(INVALID_ANSWER);
            }
            QuestionResult questionResult = new QuestionResult(userAnswer.getId(),userAnswer.getQuestionTitle(), userAnswer.getQuestionAnswer(),userAnswer.getUserAnswer(),false);
            if (userAnswer.getQuestionAnswer().equals(userAnswer.getUserAnswer())){
                userScoreLive.setValue(userScoreLive.getValue() + 1);
                questionResult.setCorrect(true);
            }
            questionResults.add(questionResult);
        }
        questionResultListLive.setValue(questionResults);
    }
}
