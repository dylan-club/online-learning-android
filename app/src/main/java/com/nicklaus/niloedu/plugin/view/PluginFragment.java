package com.nicklaus.niloedu.plugin.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nicklaus.niloedu.BaseView.BaseFragment;
import com.nicklaus.niloedu.MainActivity;
import com.nicklaus.niloedu.R;
import com.nicklaus.niloedu.utils.ViewUtils;

import org.jetbrains.annotations.NotNull;

public class PluginFragment extends BaseFragment {

    public PluginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_plugin, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        ViewUtils.setHomeToolbarTitle((MainActivity)requireActivity(),requireView());
    }
}