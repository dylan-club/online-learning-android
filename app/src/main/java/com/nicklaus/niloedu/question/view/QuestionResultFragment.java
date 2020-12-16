package com.nicklaus.niloedu.question.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nicklaus.niloedu.R;
import com.nicklaus.niloedu.databinding.FragmentQuestionResultBinding;
import com.nicklaus.niloedu.question.adapter.QuestionResultAdapter;
import com.nicklaus.niloedu.question.entity.Question;
import com.nicklaus.niloedu.question.entity.QuestionResult;
import com.nicklaus.niloedu.question.viewmodel.QuestionResultViewModel;

import java.util.List;

public class QuestionResultFragment extends Fragment {

    FragmentQuestionResultBinding binding;
    QuestionResultViewModel viewModel;
    QuestionResultAdapter questionResultAdapter;

    public QuestionResultFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_question_result,container,false);

        initView();

        viewModel = new ViewModelProvider(requireActivity(),new ViewModelProvider.AndroidViewModelFactory(getActivity().getApplication())).get(QuestionResultViewModel.class);
        viewModel.getQuestionResultListLive().observe(requireActivity(), questionResults -> questionResultAdapter.submitList(questionResults));

        binding.setResult(viewModel);
        binding.setLifecycleOwner(requireActivity());

        initData();

        return binding.getRoot();
    }

    private void initData() {
        viewModel.getUserScoreLive().setValue(0);
        //TODO:给服务器写回一条答题记录
        Bundle bundle = getArguments();
        if (bundle != null){
            List<Question> userAnswerList = bundle.getParcelableArrayList("userAnswerList");
            viewModel.calculateUserScore(userAnswerList);
        }
    }

    private void initView() {

        questionResultAdapter = new QuestionResultAdapter();
        binding.resultRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.resultRecyclerView.setAdapter(questionResultAdapter);
        View.OnClickListener backToIndexListener = v -> Navigation.findNavController(v).navigate(R.id.action_questionResultFragment_to_questionIndexFragment);
        binding.resultBackToIndexButton.setOnClickListener(backToIndexListener);
        binding.backResultConstraintLayout.setOnClickListener(backToIndexListener);
        binding.resultRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (!binding.resultRecyclerView.canScrollVertically(-1)){
                    //只有在顶部时才展开布局
                    binding.expandableLayout.expand();
                }else {
                    binding.expandableLayout.collapse();
                }
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }
}