package com.nicklaus.niloedu.video.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.nicklaus.niloedu.MainActivity;
import com.nicklaus.niloedu.R;
import com.nicklaus.niloedu.databinding.FragmentVideoIndexBinding;
import com.nicklaus.niloedu.utils.ViewUtils;
import com.nicklaus.niloedu.video.adapter.VideoAdapter;
import com.nicklaus.niloedu.video.viewmodel.VideoViewModel;

public class VideoIndexFragment extends Fragment {

    FragmentVideoIndexBinding binding;
    VideoViewModel videoViewModel;
    VideoAdapter videoAdapter;

    public VideoIndexFragment() {
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_video_index,
                container,
                false);

        initView();

        videoViewModel = new ViewModelProvider(
                requireActivity(),
                new ViewModelProvider.AndroidViewModelFactory(requireActivity().getApplication()))
                .get(VideoViewModel.class);

        videoViewModel.getVideoListLive().observe(requireActivity(), videos -> {
            videoAdapter.submitList(videos);
            binding.swipeRefreshVideo.setRefreshing(false);
        });


        initData();

        return binding.getRoot();
    }



    private void initData(){
        if (videoViewModel.getVideoListLive().getValue() == null){
            videoViewModel.fetchData();
        }
    }

    private void initView(){
        ViewUtils.showHeaderAndFooter((MainActivity) requireActivity());
        videoAdapter = new VideoAdapter();
        binding.videoRecyclerView.setAdapter(videoAdapter);
        binding.videoRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.swipeRefreshVideo.setColorSchemeColors(getResources().getColor(R.color.firstColor, null));
        binding.swipeRefreshVideo.setOnRefreshListener(() -> videoViewModel.fetchData());
    }
}