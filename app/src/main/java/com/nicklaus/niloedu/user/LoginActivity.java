package com.nicklaus.niloedu.user;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.nicklaus.niloedu.R;
import com.nicklaus.niloedu.databinding.ActivityLoginBinding;
import com.nicklaus.niloedu.user.adapter.ViewPagerAdapter;
import com.nicklaus.niloedu.user.view.LoginFragment;
import com.nicklaus.niloedu.user.view.RegisterFragment;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private final static String[] TITLES = {"登录", "注册"};
    ActivityLoginBinding binding;
    public ViewPager loginViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        loginViewPager = binding.loginViewPager;
        initView();
    }

    private void initView() {

        //设置tabLayout的tabItem
        binding.loginTablayout.setTabMode(TabLayout.MODE_FIXED);
        binding.loginTablayout.addTab(binding.loginTablayout.newTab().setText(R.string.login_tab_text));
        binding.loginTablayout.addTab(binding.loginTablayout.newTab().setText(R.string.register_tab_text));
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new LoginFragment());
        fragments.add(new RegisterFragment());
        //将viewPager和tabLayout动态绑定
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), TITLES, fragments);
        loginViewPager.setAdapter(viewPagerAdapter);
        binding.loginTablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                loginViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        binding.loginTablayout.setupWithViewPager(loginViewPager);
    }
}