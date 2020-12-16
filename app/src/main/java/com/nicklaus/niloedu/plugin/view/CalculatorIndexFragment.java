package com.nicklaus.niloedu.plugin.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.SavedStateViewModelFactory;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nicklaus.niloedu.BaseView.BaseFragment;
import com.nicklaus.niloedu.MainActivity;
import com.nicklaus.niloedu.R;
import com.nicklaus.niloedu.databinding.FragmentCalculatorIndexBinding;
import com.nicklaus.niloedu.plugin.viewmodel.CalculatorViewModel;
import com.nicklaus.niloedu.utils.ViewUtils;

import org.jetbrains.annotations.NotNull;


public class CalculatorIndexFragment extends BaseFragment {

    FragmentCalculatorIndexBinding binding;
    CalculatorViewModel calculatorViewModel;

    public CalculatorIndexFragment() {

    }


    @Override
    public View onCreateView(@NotNull @NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        calculatorViewModel = new ViewModelProvider(requireActivity(),
                new SavedStateViewModelFactory(requireActivity().getApplication(), this))
                .get(CalculatorViewModel.class);

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_calculator_index, container, false);
        binding.setCalculator(calculatorViewModel);
        binding.setLifecycleOwner(requireActivity());


        ViewUtils.showHeaderAndFooter((MainActivity) requireActivity());
        binding.enterCalButton.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(v);
            navController.navigate(R.id.action_calculatorIndexFragment_to_calculatorPlayFragment);
        });

        return binding.getRoot();
    }
}