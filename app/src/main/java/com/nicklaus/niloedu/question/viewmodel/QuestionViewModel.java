package com.nicklaus.niloedu.question.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.nicklaus.niloedu.question.entity.Question;
import com.nicklaus.niloedu.question.repository.QuestionRepository;

import java.util.ArrayList;
import java.util.List;

public class QuestionViewModel extends AndroidViewModel {

    private final QuestionRepository questionRepository;
    private MutableLiveData<Integer> userScore;

    public QuestionViewModel(@NonNull Application application) {
        super(application);
        this.questionRepository = new QuestionRepository(application.getApplicationContext());
        userScore = getUserScore();
    }


    public MutableLiveData<List<Question>> getQuestionListLive() {
        return questionRepository.getQuestionListLive();
    }

    public MutableLiveData<Integer> getUserScore() {
        if (userScore == null){
            userScore = new MutableLiveData<>();
        }
        return userScore;
    }


    public void fetchQuestions() {
        questionRepository.fetchQuestions();
    }

    @SuppressWarnings("ConstantConditions")
    public void calculateScore(){
        for (Question question : getQuestionListLive().getValue()) {
            if (question.getQuestionAnswer().equals(question.getUserAnswer())){
                userScore.setValue(userScore.getValue()+1);
            }
        }
    }

    public ArrayList<Question> getUserAnswerArrayList(){
        ArrayList<Question> questions = new ArrayList<>();
        for (Question question : getQuestionListLive().getValue()) {
            questions.add(question);
        }
        return questions;
    }
}
