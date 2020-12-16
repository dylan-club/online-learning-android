package com.nicklaus.niloedu.question.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.nicklaus.niloedu.R;
import com.nicklaus.niloedu.question.entity.Question;
import com.nicklaus.niloedu.question.entity.QuestionResult;

public class QuestionResultAdapter extends ListAdapter<QuestionResult, QuestionResultAdapter.QuestionResultViewHolder> {

    public QuestionResultAdapter(){
        super(DIFF_CALLBACK);
    }

    public static final DiffUtil.ItemCallback<QuestionResult> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<QuestionResult>() {
                @Override
                public boolean areItemsTheSame(
                        @NonNull QuestionResult oldQuestionResult, @NonNull QuestionResult newQuestionResult) {
                    return oldQuestionResult.getId() == newQuestionResult.getId();
                }
                @Override
                public boolean areContentsTheSame(
                        @NonNull QuestionResult oldQuestionResult, @NonNull QuestionResult newQuestionResult) {

                    return oldQuestionResult.equals(newQuestionResult);
                }
            };

    @SuppressWarnings("UnnecessaryLocalVariable")
    @NonNull
    @Override
    public QuestionResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.answer_cell,parent,false);
        QuestionResultViewHolder holder = new QuestionResultViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionResultViewHolder holder, int position) {
        holder.resultNumberTV.setText(String.valueOf(position + 1));
        QuestionResult questionResult = getItem(position);
        holder.resultTitleTV.setText(questionResult.getQuestionTitle());
        holder.userAnswerTV.setText(questionResult.getUserAnswer());
        if (questionResult.isCorrect()){
            holder.answerHintTV.setTextColor(holder.itemView.getResources().getColor(R.color.correct_answer_color,null));
            holder.userAnswerTV.setTextColor(holder.itemView.getResources().getColor(R.color.correct_answer_color,null));
        }else{
            holder.answerHintTV.setTextColor(holder.itemView.getResources().getColor(R.color.wrong_answer_color,null));
            holder.userAnswerTV.setTextColor(holder.itemView.getResources().getColor(R.color.wrong_answer_color,null));
        }
        holder.correctAnswerTV.setText(questionResult.getCorrectAnswer());
    }

    static class QuestionResultViewHolder extends RecyclerView.ViewHolder{

        TextView resultNumberTV;
        TextView resultTitleTV;
        TextView userAnswerTV;
        TextView answerHintTV;
        TextView correctAnswerTV;

        public QuestionResultViewHolder(@NonNull View itemView) {
            super(itemView);
            //初始化组件
            resultNumberTV = itemView.findViewById(R.id.resultNumberTextView);
            resultTitleTV = itemView.findViewById(R.id.resultTitleTextView);
            userAnswerTV = itemView.findViewById(R.id.userAnswerTextView);
            answerHintTV = itemView.findViewById(R.id.answerHintTextView);
            correctAnswerTV = itemView.findViewById(R.id.correctAnswerTextView);
        }
    }
}
