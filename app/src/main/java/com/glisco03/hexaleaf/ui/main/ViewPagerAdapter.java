package com.glisco03.hexaleaf.ui.main;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.glisco03.hexaleaf.ui.home.HomeFragment;
import com.glisco03.hexaleaf.ui.modes.ModesFragment;

public class ViewPagerAdapter extends FragmentStateAdapter {

    public ViewPagerAdapter(FragmentActivity activity) {
        super(activity);
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) {
            return new HomeFragment();
        } else {
            return new ModesFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}