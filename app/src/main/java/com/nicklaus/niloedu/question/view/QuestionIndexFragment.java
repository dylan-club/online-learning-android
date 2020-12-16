package com.nicklaus.niloedu.question.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nicklaus.niloedu.MainActivity;
import com.nicklaus.niloedu.R;
import com.nicklaus.niloedu.databinding.FragmentQuestionIndexBinding;
import com.nicklaus.niloedu.utils.ViewUtils;

public class QuestionIndexFragment extends Fragment {

    FragmentQuestionIndexBinding binding;

    public QuestionIndexFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_question_index,
                container,
                false
        );

        initView();

        return binding.getRoot();
    }

    private void initView(){
        ViewUtils.showHeaderAndFooter((MainActivity)requireActivity());

        binding.questionIndexButton.setOnClickListener(v ->
                Navigation.findNavController(v).navigate(R.id.action_questionIndexFragment_to_questionExamFragment));
    }
}