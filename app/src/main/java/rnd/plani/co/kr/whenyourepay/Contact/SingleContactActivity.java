package rnd.plani.co.kr.whenyourepay.Contact;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import rnd.plani.co.kr.whenyourepay.R;

public class SingleContactActivity extends AppCompatActivity {

    public static final String EXTRA_TYPE = "type";
    public static final String TYPE_MONEY = "money";
    public static final String TYPE_THINGS = "things";

    TabLayout tabLayout;
    ViewPager pager;
    SingleContactPagerAdapter pagerAdapter;
    String type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_contact);
        setTitle("Contract");
        overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out_background);

        Intent i = getIntent();
        type = i.getStringExtra(EXTRA_TYPE);
        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        pager = (ViewPager) findViewById(R.id.pager);
        pagerAdapter = new SingleContactPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(pager);
        tabLayout.removeAllTabs();
        tabLayout.addTab(tabLayout.newTab().setText("연락처"));
        tabLayout.addTab(tabLayout.newTab().setText("직접입력"));
        tabLayout.addTab(tabLayout.newTab().setText("최근"));

//        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                switch (tab.getPosition()) {
//                    case 0:
//                        SingleContactFragment contactFragment = new SingleContactFragment();
//                        getSupportFragmentManager().beginTransaction()
//                                .replace(R.id.pager, contactFragment)
//                                .commit();
//                        break;
//                    case 1:
//                        DirectFragment directFragment = new DirectFragment();
//                        getSupportFragmentManager().beginTransaction()
//                                .replace(R.id.pager, directFragment)
//                                .commit();
//                        break;
//                    case 2:
//                        SingleRecentFragment recentFragment = new SingleRecentFragment();
//                        getSupportFragmentManager().beginTransaction()
//                                .replace(R.id.pager, recentFragment)
//                                .commit();
//                        break;
//                }
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//        });

        if (savedInstanceState == null) {
            SingleContactFragment f = new SingleContactFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.pager, f)
                    .commit();
        }
    }

    public String getType(){
        return type;
    }
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_left_in_background, R.anim.slide_right_out);
    }
}
