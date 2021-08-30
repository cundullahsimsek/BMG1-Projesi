package com.app.alisverissepeti.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.app.alisverissepeti.fragment.Fragment1;
import com.app.alisverissepeti.fragment.Fragment2;
import com.app.alisverissepeti.fragment.Fragment3;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private CharSequence Titles[];
    private int NumbOfTabs;

    public ViewPagerAdapter(FragmentManager fm, CharSequence mTitles[], int mNumbOfTabsumb) {
        super(fm);
        this.Titles = mTitles;
        this.NumbOfTabs = mNumbOfTabsumb;

    }

    @Override
    public Fragment getItem(int position) {

        if (position == 0) {
            Fragment1 tab1 = new Fragment1();
            return tab1;
        } else if (position == 1) {
            Fragment2 tab2 = new Fragment2();
            return tab2;
        } else {
            Fragment3 tab2 = new Fragment3();
            return tab2;
        }

    }

    @Override
    public CharSequence getPageTitle(int position) {
        return Titles[position];
    }

    @Override
    public int getCount() {
        return NumbOfTabs;
    }
}
