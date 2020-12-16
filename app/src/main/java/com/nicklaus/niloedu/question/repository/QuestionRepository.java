package com.nicklaus.niloedu.question.repository;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.nicklaus.niloedu.R;
import com.nicklaus.niloedu.question.entity.Question;
import com.nicklaus.niloedu.utils.JsonParserUtils;
import com.nicklaus.niloedu.utils.VolleySingleton;

import java.util.ArrayList;
import java.util.List;

public class QuestionRepository {

    private MutableLiveData<List<Question>> questionListLive;

    private final Context ctx;

    public QuestionRepository(Context context){
        ctx = context;
        questionListLive = getQuestionListLive();
    }

    public MutableLiveData<List<Question>> getQuestionListLive(){
        if (questionListLive == null){
            questionListLive = new MutableLiveData<>();
            questionListLive.setValue(new ArrayList<>());
        }
        return questionListLive;
    }

    public void fetchQuestions(){
        //构造Json请求
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                ctx.getResources().getString(R.string.question_list_url),
                null, response -> {
            if (JsonParserUtils.responseIsSuccess(response)) {
                questionListLive.setValue(JsonParserUtils.
                        getDataAsListFromResponse(response, Question.class, "questions"));
            } else {
                //TODO
            }
        },
                error -> {}
        );

        VolleySingleton.getInstance(ctx).addToRequestQueue(jsonObjectRequest);
    }
}
