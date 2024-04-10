package com.example.myapplication.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.myapplication.fragment.ImageFragment;

public class HomeFragmentPagerAdapter extends FragmentStateAdapter {

    final int PAGE_COUNT = 4;

    ImageFragment imageFragment = new ImageFragment("相册");



    public HomeFragmentPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = imageFragment;
                break;
        }
        return fragment;
    }

    @Override
    public int getItemCount() {
        return PAGE_COUNT;
    }


}
