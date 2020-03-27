package com.ece.soccert.utils;

import android.content.Context;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.ece.soccert.R;
import com.ece.soccert.ui.highlights.PlaceholderFragmentHighlight;
import com.ece.soccert.ui.stats.PlaceholderFragmentStats;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_a_label, R.string.tab_b_label};
    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        Log.d("TAG", "getItem: "+position);
        if (position == 0){
            return PlaceholderFragmentHighlight.newInstance(position + 1);
        }else if(position == 1){
            return PlaceholderFragmentStats.newInstance(position + 1);
        }

       return PlaceholderFragmentHighlight.newInstance(position + 1);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return 2;
    }
}