package rnd.plani.co.kr.whenyourepay.Contact;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by RND on 2016-07-19.
 */
public class SingleContactPagerAdapter extends FragmentPagerAdapter {
    public SingleContactPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new SingleContactFragment();
            case 1:
                return new DirectFragment();
            case 2:
                return new SingleRecentFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
