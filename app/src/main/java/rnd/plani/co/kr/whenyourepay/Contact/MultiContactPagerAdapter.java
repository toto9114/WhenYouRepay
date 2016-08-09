package rnd.plani.co.kr.whenyourepay.Contact;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by RND on 2016-07-19.
 */
public class MultiContactPagerAdapter extends FragmentPagerAdapter {
    public MultiContactPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new MultiContactFragment();
            case 1:
                return new DirectFragment();
            case 2:
                return new MultiRecentFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
