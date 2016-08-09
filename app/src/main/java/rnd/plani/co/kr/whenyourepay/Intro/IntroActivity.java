package rnd.plani.co.kr.whenyourepay.Intro;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import rnd.plani.co.kr.whenyourepay.R;


public class IntroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.container, new EditInfoFragment())
                    .commit();
        }
    }

    public void changeConfirm() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new ConfirmInfoFragment())
                .addToBackStack(null)
                .commit();
    }

    public void changeSign() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new MySignFragment())
                .addToBackStack(null)
                .commit();
    }
}
