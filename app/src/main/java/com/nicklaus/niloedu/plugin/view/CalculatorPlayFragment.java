package com.nicklaus.niloedu.plugin.view;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.SavedStateViewModelFactory;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.nicklaus.niloedu.MainActivity;
import com.nicklaus.niloedu.R;
import com.nicklaus.niloedu.databinding.FragmentCalculatorPlayBinding;
import com.nicklaus.niloedu.plugin.viewmodel.CalculatorViewModel;
import com.nicklaus.niloedu.utils.ViewUtils;

import es.dmoral.toasty.Toasty;


public class CalculatorPlayFragment extends Fragment {

    FragmentCalculatorPlayBinding binding;
    CalculatorViewModel calculatorViewModel;

    public CalculatorPlayFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewUtils.hideHeaderAndFooter((MainActivity)requireActivity());
        calculatorViewModel = new ViewModelProvider(requireActivity(),
                new SavedStateViewModelFactory(requireActivity().getApplication(), this))
                .get(CalculatorViewModel.class);

        //初始化题目
        calculatorViewModel.generator();
        calculatorViewModel.getCurrentScore().setValue(0);

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_calculator_play,container, false);
        binding.setCalculator(calculatorViewModel);
        binding.setLifecycleOwner(requireActivity());
        initView();
        return binding.getRoot();
    }

    @SuppressWarnings("ConstantConditions")
    private void initView(){

        //回退键的监听器
        binding.backToCalIndexImgBtn.setOnClickListener(v ->
                ViewUtils.showBackToOriginalFragmentAlertDialog(
                requireContext(),
                R.string.calculator_play_toast_warning,
                v,
                R.id.action_calculatorPlayFragment_to_calculatorIndexFragment
        ));

        //设置数字键通用监听器
        StringBuilder builder = new StringBuilder();
        @SuppressLint("NonConstantResourceId") View.OnClickListener listener = v -> {
            switch (v.getId()){
                case R.id.button0:
                    builder.append("0");
                    break;
                case R.id.button1:
                    builder.append("1");
                    break;
                case R.id.button2:
                    builder.append("2");
                    break;
                case R.id.button3:
                    builder.append("3");
                    break;
                case R.id.button4:
                    builder.append("4");
                    break;
                case R.id.button5:
                    builder.append("5");
                    break;
                case R.id.button6:
                    builder.append("6");
                    break;
                case R.id.button7:
                    builder.append("7");
                    break;
                case R.id.button8:
                    builder.append("8");
                    break;
                case R.id.button9:
                    builder.append("9");
                    break;
                case R.id.buttonClear:
                    builder.setLength(0);
                    break;
                default:
            }

            if (builder.length() == 0){
                binding.userInputTextView.setText(getString(R.string.calculator_play_input_indicator));
            }else{
                binding.userInputTextView.setText(builder.toString());
            }
        };

        binding.button0.setOnClickListener(listener);
        binding.button1.setOnClickListener(listener);
        binding.button2.setOnClickListener(listener);
        binding.button3.setOnClickListener(listener);
        binding.button4.setOnClickListener(listener);
        binding.button5.setOnClickListener(listener);
        binding.button6.setOnClickListener(listener);
        binding.button7.setOnClickListener(listener);
        binding.button8.setOnClickListener(listener);
        binding.button9.setOnClickListener(listener);
        binding.buttonClear.setOnClickListener(listener);

        binding.buttonSubmit.setOnClickListener(v -> {
            //如果没有回答直接点确定，默认失败
            if (builder.length() == 0){
                builder.append("-1");
            }

            //如果答对了
            if (Integer.valueOf(builder.toString()).intValue() ==
                    calculatorViewModel.getAnswer().getValue()) {
                calculatorViewModel.answerCorrect();
                builder.setLength(0);
                binding.userInputTextView.setText(getString(R.string.calculator_play_answer_correct_message));
            }else {
                //答错了
                NavController controller = Navigation.findNavController(v);
                if (calculatorViewModel.winFlag){
                    controller.navigate(R.id.action_calculatorPlayFragment_to_calculatorWinFragment);
                    calculatorViewModel.winFlag = false;
                    calculatorViewModel.save();
                }else {
                    controller.navigate(R.id.action_calculatorPlayFragment_to_calculatorLoseFragment);
                }
            }
        });
    }
}