package com.nicklaus.niloedu.video.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.nicklaus.niloedu.video.entity.Video;
import com.nicklaus.niloedu.video.repository.VideoRepository;

import java.util.List;

public class VideoViewModel extends AndroidViewModel {

    private final VideoRepository videoRepository;

    public VideoViewModel(@NonNull Application application) {
        super(application);
        this.videoRepository = new VideoRepository(application.getApplicationContext());
    }

    public MutableLiveData<List<Video>> getVideoListLive() {
        return videoRepository.getVideoListLive();
    }

    public void fetchData() {
        videoRepository.fetchVideoList();
    }

}
