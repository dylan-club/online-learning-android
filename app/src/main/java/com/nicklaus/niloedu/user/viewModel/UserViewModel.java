package com.nicklaus.niloedu.user.viewModel;

import android.app.Activity;
import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.nicklaus.niloedu.user.LoginActivity;
import com.nicklaus.niloedu.user.repository.UserRepository;

import java.util.Map;

public class UserViewModel extends AndroidViewModel {

    private final UserRepository userRepository;

    public UserViewModel(@NonNull Application application) {
        super(application);
        userRepository = new UserRepository(application.getApplicationContext());
    }

    public void register(Map<String, String> registerMap, LoginActivity loginActivity){
        userRepository.register(registerMap, loginActivity);
    }

    public void login(Map<String, String> loginMap, Activity currentActivity){
        userRepository.login(loginMap, currentActivity);
    }
}
