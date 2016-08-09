package rnd.plani.co.kr.whenyourepay.BorrowThings;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import rnd.plani.co.kr.whenyourepay.Data.BorrowerData;
import rnd.plani.co.kr.whenyourepay.R;


public class LendThingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lend_things);
        overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out_background);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("빌려주기");

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        }
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations(R.anim.slide_right_in, R.anim.slide_left_out, R.anim.slide_left_in, R.anim.slide_right_out)
                    .add(R.id.container, new ContractThingsFragment())
                    .commit();
        }
    }
    protected void changeSignature(ThingsData data) {
        SignatureThingsFragment f = new SignatureThingsFragment();
        Bundle args = new Bundle();
        args.putSerializable(SignatureThingsFragment.EXTRA_THINGS_DATA,data);
        f.setArguments(args);
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.slide_right_in, R.anim.slide_left_out,R.anim.slide_left_in,R.anim.slide_right_out)
                .replace(R.id.container, f)
                .addToBackStack(null)
                .commit();
    }

    protected void changeSend(BorrowerData data,ThingsData thingsData) {
        SendThingsFragment f = new SendThingsFragment();
        Bundle args = new Bundle();
        args.putSerializable(SendThingsFragment.EXTRA_THINGS_DATA,thingsData);
        args.putSerializable(SendThingsFragment.EXTRA_BORROWER_DATA,data);
        f.setArguments(args);
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.slide_right_in, R.anim.slide_left_out,R.anim.slide_left_in,R.anim.slide_right_out)
                .replace(R.id.container, f)
                .addToBackStack(null)
                .commit();
    }
    protected void changeSuccess() {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            if(getSupportFragmentManager().getBackStackEntryCount()>1) {
                getSupportFragmentManager().popBackStack();
            }else{
                finish();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_left_in_background, R.anim.slide_right_out);
    }
}
