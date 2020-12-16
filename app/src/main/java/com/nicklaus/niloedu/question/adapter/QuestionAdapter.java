package com.nicklaus.niloedu.question.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.nicklaus.niloedu.R;
import com.nicklaus.niloedu.question.entity.Question;

public class QuestionAdapter extends ListAdapter<Question, QuestionAdapter.QuestionViewHolder> {

    private final String BLANK_CHAR = ". ";
    private final StringBuilder builder = new StringBuilder();
    private final static int A_TAG = 1;
    private final static int B_TAG = 2;
    private final static int C_TAG = 3;
    private final static int D_TAG = 4;
    private final static int DEFAULT_TAG = 0;

    public QuestionAdapter(){
        super(DIFF_CALLBACK);
    }

    public static final DiffUtil.ItemCallback<Question> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Question>() {
                @Override
                public boolean areItemsTheSame(
                        @NonNull Question oldQuestion, @NonNull Question newQuestion) {
                    return oldQuestion.getId() == newQuestion.getId();
                }
                @Override
                public boolean areContentsTheSame(
                        @NonNull Question oldQuestion, @NonNull Question newQuestion) {

                    return oldQuestion.equals(newQuestion);
                }
            };

    @SuppressWarnings("UnnecessaryLocalVariable")
    @NonNull
    @Override
    public QuestionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.question_cell,
                parent,
                false);
        QuestionViewHolder holder = new QuestionViewHolder(view);
        return holder;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onBindViewHolder(@NonNull QuestionViewHolder holder, int position) {
        builder.append(position+1).append(BLANK_CHAR).append(getItem(position).getQuestionTitle());
        holder.questionTitleTextView.setText(builder.toString());
        Question question = getItem(position);
        holder.qaRadioButton.setText(question.getQuestionA());
        holder.qbRadioButton.setText(question.getQuestionB());
        holder.qcRadioButton.setText(question.getQuestionC());
        holder.qdRadioButton.setText(question.getQuestionD());

        //解决recyclerview中radioGroup选中混乱问题，在Question中添加一个checkPosition字段
        holder.answerRadioGroup.setOnCheckedChangeListener(null);
        switch (question.getCheckPosition()){
            case A_TAG:
                holder.answerRadioGroup.check(R.id.qaRadioButton);
                break;
            case B_TAG:
                holder.answerRadioGroup.check(R.id.qbRadioButton);
                break;
            case C_TAG:
                holder.answerRadioGroup.check(R.id.qcRadioButton);
                break;
            case D_TAG:
                holder.answerRadioGroup.check(R.id.qdRadioButton);
                break;
            default:
                holder.answerRadioGroup.clearCheck();
        }

        holder.answerRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId){
                case R.id.qaRadioButton:
                    question.setUserAnswer(holder.qaRadioButton.getText().toString());
                    question.setCheckPosition(A_TAG);
                    break;
                case R.id.qbRadioButton:
                    question.setCheckPosition(B_TAG);
                    question.setUserAnswer(holder.qbRadioButton.getText().toString());
                    break;
                case R.id.qcRadioButton:
                    question.setCheckPosition(C_TAG);
                    question.setUserAnswer(holder.qcRadioButton.getText().toString());
                    break;
                case R.id.qdRadioButton:
                    question.setCheckPosition(D_TAG);
                    question.setUserAnswer(holder.qdRadioButton.getText().toString());
                    break;
                default:
                    question.setCheckPosition(DEFAULT_TAG);
                    question.setUserAnswer("");
                    break;
            }
        });
        builder.setLength(0);
    }

    static class QuestionViewHolder extends RecyclerView.ViewHolder{

        RadioGroup answerRadioGroup;
        TextView questionTitleTextView;
        RadioButton qaRadioButton;
        RadioButton qbRadioButton;
        RadioButton qcRadioButton;
        RadioButton qdRadioButton;

        public QuestionViewHolder(@NonNull View itemView) {
            super(itemView);
            //初始化组件
            questionTitleTextView = itemView.findViewById(R.id.questionTitleTextView);
            answerRadioGroup = itemView.findViewById(R.id.answerRadioGroup);
            qaRadioButton = itemView.findViewById(R.id.qaRadioButton);
            qbRadioButton = itemView.findViewById(R.id.qbRadioButton);
            qcRadioButton = itemView.findViewById(R.id.qcRadioButton);
            qdRadioButton = itemView.findViewById(R.id.qdRadioButton);
        }
    }
}
