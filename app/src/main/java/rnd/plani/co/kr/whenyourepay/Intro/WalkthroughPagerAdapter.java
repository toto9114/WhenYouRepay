package rnd.plani.co.kr.whenyourepay.Intro;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by RND on 2016-08-09.
 */
public class WalkthroughPagerAdapter extends FragmentPagerAdapter {
    private static final int WALK_THROUGH_PAGE_COUNT = 2;
    public WalkthroughPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new WalkthroughFragment1();
            case 1:
                return new WalkthroughFragment2();
        }
        return null;
    }

    @Override
    public int getCount() {
        return WALK_THROUGH_PAGE_COUNT;
    }
}
