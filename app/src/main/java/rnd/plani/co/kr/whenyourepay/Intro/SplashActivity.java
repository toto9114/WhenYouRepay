package rnd.plani.co.kr.whenyourepay.Intro;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;

import rnd.plani.co.kr.whenyourepay.MainActivity;
import rnd.plani.co.kr.whenyourepay.Manager.PropertyManager;
import rnd.plani.co.kr.whenyourepay.R;

public class SplashActivity extends AppCompatActivity {

    Handler mHandler = new Handler(Looper.getMainLooper());
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(PropertyManager.getInstance().isUser()){
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                }else {
                    startActivity(new Intent(SplashActivity.this, WalkthroughActivity.class));
                    finish();
                }
            }
        },1000);
    }
}
