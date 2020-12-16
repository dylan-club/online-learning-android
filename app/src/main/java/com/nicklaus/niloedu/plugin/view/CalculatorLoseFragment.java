package com.nicklaus.niloedu.plugin.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.SavedStateViewModelFactory;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.nicklaus.niloedu.R;
import com.nicklaus.niloedu.databinding.FragmentCalculatorLoseBinding;
import com.nicklaus.niloedu.plugin.viewmodel.CalculatorViewModel;

public class CalculatorLoseFragment extends Fragment {

    public CalculatorLoseFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        CalculatorViewModel calculatorViewModel;
        calculatorViewModel = new ViewModelProvider(requireActivity(),
                new SavedStateViewModelFactory(requireActivity().getApplication(), this))
                .get(CalculatorViewModel.class);
        FragmentCalculatorLoseBinding binding;
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_calculator_lose, container, false);
        binding.setCalculator(calculatorViewModel);
        binding.setLifecycleOwner(requireActivity());

        binding.loseBackToTitleButton.setOnClickListener(v ->
                Navigation.findNavController(v).
                        navigate(R.id.action_calculatorLoseFragment_to_calculatorIndexFragment));

        binding.loseBackToIndexImgBtn.setOnClickListener(v ->
                Navigation.findNavController(v).
                        navigate(R.id.action_calculatorLoseFragment_to_calculatorIndexFragment));

        return binding.getRoot();
    }
}