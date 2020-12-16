package com.nicklaus.niloedu.video.adapter;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.nicklaus.niloedu.R;
import com.nicklaus.niloedu.video.entity.Video;

import io.supercharge.shimmerlayout.ShimmerLayout;

public class VideoAdapter extends ListAdapter<Video, VideoAdapter.MyViewHolder> {

    public VideoAdapter(){
        super(DIFF_CALLBACK);
    }

    public static final DiffUtil.ItemCallback<Video> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Video>() {
                @Override
                public boolean areItemsTheSame(
                        @NonNull Video oldVideo, @NonNull Video newVideo) {
                    return oldVideo.getId() == newVideo.getId();
                }
                @Override
                public boolean areContentsTheSame(
                        @NonNull Video oldVideo, @NonNull Video newVideo) {

                    return oldVideo.equals(newVideo);
                }
            };

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_cell, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        holder.itemView.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putParcelable("VIDEO", getItem(holder.getAdapterPosition()));
            //导航到播放页面
            Navigation.findNavController(v).navigate(R.id.action_videoIndexFragment_to_videoPlayFragment, bundle);
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.shimmerLayout.setShimmerColor(0x55ffffff);
        holder.shimmerLayout.setShimmerAngle(0);
        holder.shimmerLayout.startShimmerAnimation();
        Glide.with(holder.itemView)
                .load(getItem(position).getImgUrl())
                .placeholder(R.drawable.ic_baseline_image_24)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        holder.shimmerLayout.stopShimmerAnimation();
                        return false;
                    }
                })
                .into(holder.videoImageView);
        holder.videoTitleTextView.setText(getItem(position).getTitle());
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{
        ShimmerLayout shimmerLayout;
        ImageView videoImageView;
        TextView videoTitleTextView;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            shimmerLayout = itemView.findViewById(R.id.shimmerLayoutVideo);
            videoImageView = itemView.findViewById(R.id.videoImageView);
            videoTitleTextView = itemView.findViewById(R.id.videoTitleTextView);
        }
    }
}
