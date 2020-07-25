package com.example.civitets.adapters;

import com.example.civitets.fragments.OffersFragment;
import com.example.civitets.fragments.SitesFragment;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class TabsAdapter extends FragmentStatePagerAdapter {

    public TabsAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        Fragment fragment;

        switch(i) {
            case 0:
                fragment = new SitesFragment();
                break;
            case 1:
                fragment = new OffersFragment();
                break;
            default:
                fragment = null;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "LUGARES";
            case 1:
                return "NOTICIAS";
        }
        return null;
    }
}