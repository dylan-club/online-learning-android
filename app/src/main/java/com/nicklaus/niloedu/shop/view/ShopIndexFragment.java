package com.nicklaus.niloedu.shop.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.nicklaus.niloedu.MainActivity;
import com.nicklaus.niloedu.R;
import com.nicklaus.niloedu.databinding.FragmentShopIndexBinding;
import com.nicklaus.niloedu.shop.adapter.GoodsAdapter;
import com.nicklaus.niloedu.shop.viewmodel.GoodsViewModel;
import com.nicklaus.niloedu.utils.ViewUtils;

public class ShopIndexFragment extends Fragment {

    FragmentShopIndexBinding binding;
    GoodsViewModel viewModel;
    GoodsAdapter goodsAdapter;

    public ShopIndexFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_shop_index,container,false);
        initView();

        viewModel = new ViewModelProvider(requireActivity(),
                new ViewModelProvider.AndroidViewModelFactory(requireActivity().getApplication()))
                .get(GoodsViewModel.class);

        viewModel.getGoodsListLive().observe(requireActivity(), goods -> {
            goodsAdapter.submitList(goods);
            binding.swipeRefreshShop.setRefreshing(false);
        });

        initData();
        return binding.getRoot();
    }

    private void initData() {
        viewModel.fetchGoodsList();
    }

    private void initView() {
        ViewUtils.showHeaderAndFooter((MainActivity) requireActivity());
        goodsAdapter = new GoodsAdapter();
        binding.recyclerViewShop.setAdapter(goodsAdapter);;
        binding.recyclerViewShop.setLayoutManager(new GridLayoutManager(requireContext(), 2));
        binding.swipeRefreshShop.setColorSchemeColors(getResources().getColor(R.color.firstColor, null));
        binding.swipeRefreshShop.setOnRefreshListener(() -> viewModel.fetchGoodsList());
    }
}