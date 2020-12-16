package com.nicklaus.niloedu.question.view;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nicklaus.niloedu.MainActivity;
import com.nicklaus.niloedu.R;
import com.nicklaus.niloedu.databinding.FragmentQuestionExamBinding;
import com.nicklaus.niloedu.question.adapter.QuestionAdapter;
import com.nicklaus.niloedu.question.entity.Question;
import com.nicklaus.niloedu.question.viewmodel.QuestionViewModel;
import com.nicklaus.niloedu.utils.ViewUtils;

import java.util.Collections;
import java.util.List;

import es.dmoral.toasty.Toasty;

public class QuestionExamFragment extends Fragment {

    FragmentQuestionExamBinding binding;
    QuestionAdapter questionAdapter;
    QuestionViewModel viewModel;

    public QuestionExamFragment() {
    }

    @SuppressLint("CheckResult")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_question_exam,
                container,
                false
        );

        initView();

        viewModel = new ViewModelProvider(
                requireActivity(),
                new ViewModelProvider.AndroidViewModelFactory(requireActivity().getApplication())
        ).get(QuestionViewModel.class);

        viewModel.getQuestionListLive().observe(requireActivity(), questions -> {
            questionAdapter.submitList(questions);
        });

        viewModel.getUserScore().setValue(0);

        initData();

        binding.examSubmitButton.setOnClickListener(v -> {
            //使用一个dialog来提示用户是否要提交答案
            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
            builder.setTitle(R.string.submit_dialog_title_message);
            builder.setCancelable(false);
            builder.setPositiveButton(R.string.submit_dialog_positive_message, (dialog, which) -> {
                //将用户输入的数据保存在bundle中
                binding.examSubmitButton.setClickable(false);
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("userAnswerList", viewModel.getUserAnswerArrayList());
                //跳转到答案解析页面
                Navigation.findNavController(v).navigate(R.id.action_questionExamFragment_to_questionResultFragment, bundle);
            });
            builder.setNegativeButton(R.string.submit_dialog_negative_message, (dialog, which) -> Toasty
                    .warning(requireContext(),R.string.question_exam_back_toast_message).show());
            AlertDialog dialog = builder.create();
            dialog.show();
        });

        return binding.getRoot();
    }

    private void initData() {
        viewModel.fetchQuestions();
    }

    @SuppressLint("CheckResult")
    private void initView(){
        ViewUtils.hideHeaderAndFooter((MainActivity)requireActivity());
        binding.examSubmitButton.setClickable(true);
        binding.examBackToIndexButton.setOnClickListener(v ->
                ViewUtils.showBackToOriginalFragmentAlertDialog(
                        requireContext(),
                        R.string.question_exam_back_toast_message,
                        v,
                        R.id.action_questionExamFragment_to_questionIndexFragment
                ));

        binding.questionRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!binding.questionRecyclerView.canScrollVertically(-1) ||
                        !binding.questionRecyclerView.canScrollVertically(1)){
                    //如果不可以上移或者下移就展开布局
                    binding.expandableLayoutHeader.expand();
                }else{
                    //否则折叠布局
                    binding.expandableLayoutHeader.collapse();
                }
            }
        });
        questionAdapter = new QuestionAdapter();
        binding.questionRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.questionRecyclerView.setAdapter(questionAdapter);
    }
}