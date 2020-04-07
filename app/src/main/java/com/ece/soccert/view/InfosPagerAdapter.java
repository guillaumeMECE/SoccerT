package com.ece.soccert.view;

import android.content.Context;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.ece.soccert.R;
import com.ece.soccert.ui.highlights.HighlightFragment;
import com.ece.soccert.ui.maps.MapsFragment;
import com.ece.soccert.ui.stats.StatsFragment;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class InfosPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_a_label, R.string.tab_b_label,R.string.tab_c_label};
   // private static final int[] TAB_TITLES = new int[]{R.string.tab_a_label, R.string.tab_b_label,R.string.tab_c_label};
    private final Context mContext;
    private int idResult;

    public InfosPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    public InfosPagerAdapter(Context context, FragmentManager fm, int idResult) {
        super(fm);
        mContext = context;
        Log.d("ID", "SectionPagerAdapter CTOR: id = "+idResult);
        this.idResult = idResult;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        Log.d("TAG", "getItem: " + position);
        if (position == 0){
            Log.d("ID", "SectionPagerAdapter: id = "+idResult);
            return HighlightFragment.newInstance(position + 1,idResult);
        }else if(position == 1){
            return StatsFragment.newInstance(position + 1,idResult);
        }else if(position == 2){
            return MapsFragment.newInstance(position + 1,idResult);
        }

       return StatsFragment.newInstance(position + 1,idResult);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return 3;
    }
}