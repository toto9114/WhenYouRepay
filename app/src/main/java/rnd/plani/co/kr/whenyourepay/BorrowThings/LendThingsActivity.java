package rnd.plani.co.kr.whenyourepay.BorrowThings;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import rnd.plani.co.kr.whenyourepay.Data.BorrowerData;
import rnd.plani.co.kr.whenyourepay.Data.ThingsData;
import rnd.plani.co.kr.whenyourepay.R;
import rnd.plani.co.kr.whenyourepay.Utils;


public class LendThingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lend_things);
        overridePendingTransition(R.anim.slide_up, R.anim.null_animation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        AppBarLayout appBarLayout = (AppBarLayout)findViewById(R.id.appbar);
        appBarLayout.setPadding(0, Utils.getStatusBarHeight(), 0, 0);
        setSupportActionBar(toolbar);




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

    public void setSuccessResult(){
        setResult(RESULT_OK);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.menu_close){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.null_animation, R.anim.slide_down);
    }
}
