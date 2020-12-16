package com.nicklaus.niloedu.video.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.nicklaus.niloedu.MainActivity;
import com.nicklaus.niloedu.R;
import com.nicklaus.niloedu.databinding.FragmentVideoPlayBinding;
import com.nicklaus.niloedu.utils.ViewUtils;
import com.nicklaus.niloedu.video.entity.Video;

import cn.jzvd.JzvdStd;

public class VideoPlayFragment extends Fragment {

    private FragmentVideoPlayBinding binding;
    private JzvdStd videoPlayer;

    public VideoPlayFragment() {
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_video_play,
                container,
                false);

        initView();

        return binding.getRoot();
    }

    private void initView(){
        //初始化播放器
        videoPlayer = binding.videoPlayer;
        ViewUtils.hideHeaderAndFooter((MainActivity)requireActivity());
        if (getArguments() != null){
            Video video = getArguments().getParcelable("VIDEO");
            //设置播放源
            videoPlayer.setUp(video.getVideoUrl(),video.getTitle());
            //设置预览图(原生的imageView不支持uri图片链接，所以使用Glide图片加载库)
            Glide.with(requireActivity()).load(video.getImgUrl()).into(videoPlayer.posterImageView);
            //设置预览图自适应
            videoPlayer.posterImageView.setAdjustViewBounds(true);
            binding.playTitleTextView.setText(video.getTitle());
            videoPlayer.fullscreenButton.setVisibility(View.GONE);
        }

        //绑定返回键的监听器
        binding.playBackToIndexButton.setOnClickListener(v ->
                ViewUtils.showBackToOriginalFragmentAlertDialog(
                requireContext(),
                R.string.video_play_toast_back_to_index_image,
                v,
                R.id.action_videoPlayFragment_to_videoIndexFragment
        ));
    }

    @Override
    public void onPause() {
        super.onPause();
        JzvdStd.releaseAllVideos();
    }
}