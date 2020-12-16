package com.nicklaus.niloedu.user.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.nicklaus.niloedu.BaseView.BaseFragment;
import com.nicklaus.niloedu.MainActivity;
import com.nicklaus.niloedu.R;
import com.nicklaus.niloedu.databinding.FragmentLoginBinding;
import com.nicklaus.niloedu.user.viewModel.UserViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;

public class LoginFragment extends BaseFragment {

    FragmentLoginBinding binding;
    UserViewModel viewModel;

    public LoginFragment() {
    }


    @Override
    public View onCreateView(@NotNull @NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false);

        viewModel = new ViewModelProvider(requireActivity(),
                new ViewModelProvider.AndroidViewModelFactory(requireActivity().getApplication()))
                .get(UserViewModel.class);

        binding.loginButton.setOnClickListener(v -> {
            String nickname = binding.loginNicknameEdit.getText().toString().trim();
            String password = binding.loginPasswordEdit.getText().toString().trim();
            Map<String, String> loginMap = new HashMap<>();
            loginMap.put("nickname", nickname);
            loginMap.put("password", password);
            viewModel.login(loginMap, requireActivity());
        });

        return binding.getRoot();
    }
}