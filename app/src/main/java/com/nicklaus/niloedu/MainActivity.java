package com.nicklaus.niloedu;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.nicklaus.niloedu.BaseView.BaseActivity;
import com.nicklaus.niloedu.databinding.ActivityMainBinding;
import com.nicklaus.niloedu.user.LoginActivity;
import com.nicklaus.niloedu.user.entity.User;
import com.nicklaus.niloedu.user.viewModel.MainViewModel;

import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;

public class MainActivity extends BaseActivity {
    ActivityMainBinding binding;
    MainViewModel viewModel;
    public BottomNavigationView bottomNavigationView;
    public Toolbar homeToolbar;
    public TextView homeToolbarTitle;
    public DrawerLayout drawerLayout;
    CircleImageView avatarImage;
    TextView nicknameTextView;
    TextView creditTextView;
    Button loginFirstButton;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        bottomNavigationView = binding.bottomNavigationView;
        homeToolbar = binding.homeToolbar;
        homeToolbarTitle = binding.homeToolbarTitle;
        drawerLayout = binding.drawerLayout;
        initView();
        viewModel = new ViewModelProvider(this,new ViewModelProvider.AndroidViewModelFactory(getApplication()))
                .get(MainViewModel.class);
        viewModel.getLoginUserLive().observe(this, user -> {
            if (user != null){
                nicknameTextView.setVisibility(View.VISIBLE);
                creditTextView.setVisibility(View.VISIBLE);
                binding.footerQuitButton.setVisibility(View.VISIBLE);
                loginFirstButton.setVisibility(View.INVISIBLE);
                Glide.with(MainActivity.this).load(user.getAvatar()).into(avatarImage);
                nicknameTextView.setText("用户名：" +user.getNickname());
                creditTextView.setText("积分：" + user.getCredit());
            }
        });
        initData();
    }

    private void initData() {
        viewModel.getLoginInfo();
    }

    private void initView(){
        View headerView = binding.navigationView.inflateHeaderView(R.layout.side_nav_header);
        avatarImage = headerView.findViewById(R.id.avatar_image);
        nicknameTextView = headerView.findViewById(R.id.nickname);
        creditTextView = headerView.findViewById(R.id.credit);
        loginFirstButton = headerView.findViewById(R.id.loginFirstButton);
        //仅登录可见
        nicknameTextView.setVisibility(View.INVISIBLE);
        creditTextView.setVisibility(View.INVISIBLE);
        binding.footerQuitButton.setVisibility(View.INVISIBLE);


        //设置导航栏
        setSupportActionBar(homeToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_apps_24);
        }
        NavController navController = Navigation.findNavController(this,R.id.fragment);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);

        //设置侧边栏
        //退出按钮事件绑定
        binding.footerQuitButton.setOnClickListener(v -> {
            //设置0.1秒的渐变动画
            Animation animation=new AlphaAnimation(1.0f,0.5f);
            animation.setDuration(100);
            binding.footerQuitButton.startAnimation(animation);

            //提示用户是否要退出账号
            new MaterialAlertDialogBuilder(this)
                    .setTitle(R.string.home_quit_dialog_message)
                    .setCancelable(false)
                    .setPositiveButton(R.string.home_quit_dialog_positive_button_text, (dialog, which) -> {
                        //清除缓存
                        viewModel.clearUserCache();
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    })
                    .setNegativeButton(R.string.home_quit_dialog_negative_button_text,((dialog, which) ->
                            Toasty.info(this, R.string.home_quit_dialog_toast_text).show()))
                    .show();
        });

        binding.footerBackButton.setOnClickListener(v -> {
            //设置0.1秒的渐变动画
            Animation animation=new AlphaAnimation(1.0f,0.5f);
            animation.setDuration(100);
            binding.footerBackButton.startAnimation(animation);

            //关闭侧边栏
            drawerLayout.closeDrawers();
        });

        //登录键事件绑定
        loginFirstButton.setOnClickListener(v -> {
            //清除缓存
            viewModel.clearUserCache();
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }

    @SuppressWarnings("SwitchStatementWithTooFewBranches")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //划出侧边栏的提示按钮
        switch (item.getItemId()){
            case android.R.id.home:
                binding.drawerLayout.openDrawer(GravityCompat.START);
                break;
            default:
        }
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        return super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
    }
}