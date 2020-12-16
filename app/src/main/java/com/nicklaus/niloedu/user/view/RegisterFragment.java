package com.nicklaus.niloedu.user.view;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.nicklaus.niloedu.BaseView.BaseFragment;
import com.nicklaus.niloedu.R;
import com.nicklaus.niloedu.databinding.FragmentRegisterBinding;
import com.nicklaus.niloedu.user.LoginActivity;
import com.nicklaus.niloedu.user.viewModel.UserViewModel;
import com.nicklaus.niloedu.utils.JsonParserUtils;
import com.nicklaus.niloedu.utils.VolleySingleton;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;
import kotlin.text.UStringsKt;

public class RegisterFragment extends BaseFragment {

    FragmentRegisterBinding binding;
    UserViewModel userViewModel;

    public RegisterFragment() {
    }

    @Override
    public View onCreateView(@NotNull @NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_register, container, false);

        userViewModel = new ViewModelProvider(requireActivity(),
                new ViewModelProvider.AndroidViewModelFactory(requireActivity().getApplication()))
                .get(UserViewModel.class);

        initView();

        return binding.getRoot();
    }

    private void initView() {

        //清空所有输入
        clearAllInputs();

        binding.registerButton.setOnClickListener(v -> {

            //禁用按钮防止重复点击
            binding.registerButton.setCheckable(false);

            Map<String, String> registerMap = new HashMap<>();
            String nickname = binding.nicknameEdit.getText().toString();
            String email = binding.emailEdit.getText().toString();
            String password = binding.passwordEdit.getText().toString();
            String confirmPassword = binding.confirmPasswordEdit.getText().toString();

            //非空判断
            if (TextUtils.isEmpty(nickname) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(confirmPassword)){
                Toasty.warning(getContext(), R.string.register_input_is_empty).show();
                //密码前后一致判断
            }else if(!confirmPassword.equals(password)){
                Toasty.warning(getContext(), R.string.register_input_password_is_not_equal_to_confirm).show();
            }else{
                //发送注册请求
                registerMap.put("nickname", nickname);
                registerMap.put("email", email);
                registerMap.put("password", password);
                userViewModel.register(registerMap,(LoginActivity)getActivity());
            }

            //恢复注册按钮
            binding.registerButton.setCheckable(true);
        });
    }

    private void clearAllInputs() {
        binding.nicknameEdit.setText(null);
        binding.emailEdit.setText(null);
        binding.passwordEdit.setText(null);
        binding.confirmPasswordEdit.setText(null);
    }

    @Override
    public void onResume() {
        clearAllInputs();
        super.onResume();
    }
}