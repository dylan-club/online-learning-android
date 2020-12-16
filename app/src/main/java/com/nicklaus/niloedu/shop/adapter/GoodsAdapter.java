package com.nicklaus.niloedu.shop.adapter;

import android.annotation.SuppressLint;
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
import com.nicklaus.niloedu.shop.entity.Goods;

import io.supercharge.shimmerlayout.ShimmerLayout;

public class GoodsAdapter extends ListAdapter<Goods, GoodsAdapter.GoodsViewHolder> {

    public GoodsAdapter(){
        super(DIFF_CALLBACK);
    }

    public static final DiffUtil.ItemCallback<Goods> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Goods>() {
                @Override
                public boolean areItemsTheSame(@NonNull Goods oldItem, @NonNull Goods newItem) {
                    return oldItem.getId().equals(newItem.getId());
                }

                @Override
                public boolean areContentsTheSame(@NonNull Goods oldItem, @NonNull Goods newItem) {
                    return oldItem.equals(newItem);
                }
            };

    @NonNull
    @Override
    public GoodsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(
                        R.layout.goods_cell,
                        parent,
                        false
                );

        GoodsViewHolder holder = new GoodsViewHolder(view);
        //跳转到商品详情页面
        holder.itemView.setOnClickListener(v -> {
            //传递商品信息数据
            Bundle bundle = new Bundle();
            bundle.putParcelable("goods",getItem(holder.getAdapterPosition()));
            Navigation.findNavController(v).navigate(R.id.action_shopIndexFragment_to_shopDetailFragment, bundle);
        });
        return holder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull GoodsViewHolder holder, int position) {
        //设置加载动画
        holder.shimmerLayoutGoods.setShimmerColor(0x55ffffff);
        holder.shimmerLayoutGoods.setShimmerAngle(0);
        holder.shimmerLayoutGoods.startShimmerAnimation();
        //加载商品图片
        Glide.with(holder.itemView)
                .load(getItem(position).getGoodsImageUrl())
                .placeholder(R.drawable.ic_baseline_image_24)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        holder.shimmerLayoutGoods.stopShimmerAnimation();
                        return false;
                    }
                })
                .into(holder.indexGoodsCoverImage);
        //加载商品简介
        holder.indexGoodsTitleTextView.setText(getItem(position).getGoodsTitle());
        holder.indexGoodsCreditTextView.setText(getItem(position).getGoodsCredit() + holder.itemView.getResources().getString(R.string.goods_credit_title_text));
        holder.indexGoodsAmountTextView.setText(holder.itemView.getResources().getString(R.string.goods_amount_title_text) + getItem(position).getGoodsAmount());
    }

    static class GoodsViewHolder extends RecyclerView.ViewHolder{

        ShimmerLayout shimmerLayoutGoods;
        ImageView indexGoodsCoverImage;
        TextView indexGoodsTitleTextView;
        TextView indexGoodsCreditTextView;
        TextView indexGoodsAmountTextView;

        public GoodsViewHolder(@NonNull View itemView) {
            super(itemView);
            shimmerLayoutGoods = itemView.findViewById(R.id.shimmerLayoutGoods);
            indexGoodsCoverImage = itemView.findViewById(R.id.indexGoodsCoverImage);
            indexGoodsTitleTextView = itemView.findViewById(R.id.indexGoodsTitleTextView);
            indexGoodsCreditTextView = itemView.findViewById(R.id.indexGoodsCreditTextView);
            indexGoodsAmountTextView = itemView.findViewById(R.id.indexGoodsAmountTextView);
        }
    }
}
