package com.nicklaus.niloedu.video.view;

import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.VideoView;

import com.nicklaus.niloedu.BaseView.BaseFragment;
import com.nicklaus.niloedu.MainActivity;
import com.nicklaus.niloedu.R;
import com.nicklaus.niloedu.utils.ViewUtils;

import org.jetbrains.annotations.NotNull;

public class VideoFragment extends BaseFragment {

    public VideoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_video, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        ViewUtils.setHomeToolbarTitle((MainActivity)requireActivity(),requireView());
    }
}