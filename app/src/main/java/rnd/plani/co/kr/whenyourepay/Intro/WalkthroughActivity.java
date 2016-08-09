package rnd.plani.co.kr.whenyourepay.Intro;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.viewpagerindicator.CirclePageIndicator;

import rnd.plani.co.kr.whenyourepay.R;

public class WalkthroughActivity extends AppCompatActivity {

    ViewPager pager;
    WalkthroughPagerAdapter mAdapter;
    CirclePageIndicator indicator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walkthrough);
        pager = (ViewPager)findViewById(R.id.pager);
        mAdapter = new WalkthroughPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(mAdapter);
        indicator = (CirclePageIndicator)findViewById(R.id.indicator);
        indicator.setViewPager(pager);

        Button btn = (Button)findViewById(R.id.btn_start);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WalkthroughActivity.this, IntroActivity.class));
            }
        });
    }
}
