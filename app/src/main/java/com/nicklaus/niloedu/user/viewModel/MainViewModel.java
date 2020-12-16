package com.nicklaus.niloedu.user.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.nicklaus.niloedu.user.entity.User;
import com.nicklaus.niloedu.user.repository.UserRepository;

public class MainViewModel extends AndroidViewModel {

    private final UserRepository userRepository;

    public MainViewModel(@NonNull Application application) {
        super(application);
        userRepository = new UserRepository(application.getApplicationContext());
    }

    public MutableLiveData<User> getLoginUserLive(){
        return userRepository.getLoginUserLive();
    }

    public void getLoginInfo(){
        userRepository.getLoginInfo();
    }

    public void clearUserCache(){
        userRepository.clearUserCache();
    }
}
