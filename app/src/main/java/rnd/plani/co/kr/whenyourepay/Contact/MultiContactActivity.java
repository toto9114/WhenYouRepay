package rnd.plani.co.kr.whenyourepay.Contact;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import rnd.plani.co.kr.whenyourepay.R;

public class MultiContactActivity extends AppCompatActivity {


    //    public static final int REQUEST_PERSON_LIST = 200;
    public static final String EXTRA_TYPE = "type";

    public static final int TYPE_DUTCH = 1;
    public static final int TYPE_GROUP = 2;

    int type;
    TabLayout tabLayout;
    ViewPager pager;
    MultiContactPagerAdapter pagerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_contact);
        setTitle("Contract");
        overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out_background);

        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        pager = (ViewPager)findViewById(R.id.pager);
        pagerAdapter = new MultiContactPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(pager);
        tabLayout.removeAllTabs();
        tabLayout.addTab(tabLayout.newTab().setText("연락처"));
        tabLayout.addTab(tabLayout.newTab().setText("직접입력"));
        tabLayout.addTab(tabLayout.newTab().setText("최근"));

        type = getIntent().getIntExtra(EXTRA_TYPE, 0);
        if (savedInstanceState == null) {
            MultiContactFragment f = new MultiContactFragment();
            Bundle args = new Bundle();
            args.putInt(MultiContactFragment.EXTRA_TYPE, type);
            f.setArguments(args);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.pager, f)
                    .commit();
        }

    }
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_left_in_background, R.anim.slide_right_out);
    }

}
