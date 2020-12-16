package com.nicklaus.niloedu.question.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nicklaus.niloedu.BaseView.BaseFragment;
import com.nicklaus.niloedu.MainActivity;
import com.nicklaus.niloedu.R;
import com.nicklaus.niloedu.utils.ViewUtils;

import org.jetbrains.annotations.NotNull;


public class QuestionFragment extends BaseFragment {

    public QuestionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_question, container, false);
    }

    @Override
    public void onStart() {
        ViewUtils.setHomeToolbarTitle((MainActivity)requireActivity(),requireView());
        super.onStart();
    }
}