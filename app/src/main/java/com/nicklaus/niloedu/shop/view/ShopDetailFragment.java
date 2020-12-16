package com.nicklaus.niloedu.shop.view;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.nicklaus.niloedu.MainActivity;
import com.nicklaus.niloedu.R;
import com.nicklaus.niloedu.databinding.FragmentShopDetailBinding;
import com.nicklaus.niloedu.shop.entity.Goods;
import com.nicklaus.niloedu.utils.ViewUtils;

public class ShopDetailFragment extends Fragment {

    FragmentShopDetailBinding binding;

    public ShopDetailFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_shop_detail,
                container,
                false);

        initView();

        return binding.getRoot();
    }

    @SuppressLint("SetTextI18n")
    private void initView() {
        ViewUtils.hideHeaderAndFooter((MainActivity)requireActivity());
        Bundle bundle = getArguments();
        if (bundle != null){
            Goods goods = bundle.getParcelable("goods");
            //初始化商品信息
            Glide.with(requireActivity())
                    .load(goods.getGoodsImageUrl())
                    .placeholder(R.drawable.ic_baseline_image_24)
                    .into(binding.detailGoodsCoverImage);
            binding.detailGoodsTitleTextView.setText(goods.getGoodsTitle());
            binding.detailGoodsDescriptionTextView.setText(goods.getGoodsDetail());
            binding.detailGoodsCreditTextView.setText(goods.getGoodsCredit() + getString(R.string.goods_credit_title_text));
            binding.detailGoodsAmountTextView.setText(getString(R.string.goods_amount_title_text) + goods.getGoodsAmount());
        }
        View.OnClickListener backToIndexListener = v -> Navigation.findNavController(v).navigate(R.id.action_shopDetailFragment_to_shopIndexFragment);
        binding.detailBackToIndexLayout.setOnClickListener(backToIndexListener);
        binding.detailBackToIndexButton.setOnClickListener(backToIndexListener);

        //TODO:往服务器写回一条用户购买记录
    }
}