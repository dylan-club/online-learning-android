package com.nicklaus.niloedu.plugin.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.SavedStateViewModelFactory;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nicklaus.niloedu.R;
import com.nicklaus.niloedu.databinding.FragmentCalculatorWinBinding;
import com.nicklaus.niloedu.plugin.viewmodel.CalculatorViewModel;

public class CalculatorWinFragment extends Fragment {


    public CalculatorWinFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        CalculatorViewModel calculatorViewModel;
        calculatorViewModel = new ViewModelProvider(requireActivity(),
                new SavedStateViewModelFactory(requireActivity().getApplication(), this))
                .get(CalculatorViewModel.class);
        FragmentCalculatorWinBinding binding;
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_calculator_win, container, false);
        binding.setCalculator(calculatorViewModel);
        binding.setLifecycleOwner(requireActivity());

        binding.winBackToTitleButton.setOnClickListener(v ->
                Navigation.findNavController(v).
                        navigate(R.id.action_calculatorWinFragment_to_calculatorIndexFragment));

        binding.winBackToIndexImgBtn.setOnClickListener(v ->
                Navigation.findNavController(v).
                        navigate(R.id.action_calculatorWinFragment_to_calculatorIndexFragment));

        return binding.getRoot();
    }
}