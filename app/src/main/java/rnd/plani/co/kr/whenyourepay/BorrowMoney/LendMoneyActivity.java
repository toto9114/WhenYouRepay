package rnd.plani.co.kr.whenyourepay.BorrowMoney;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import rnd.plani.co.kr.whenyourepay.Data.AccountData;
import rnd.plani.co.kr.whenyourepay.Data.BorrowerData;
import rnd.plani.co.kr.whenyourepay.R;
import rnd.plani.co.kr.whenyourepay.Utils;

public class LendMoneyActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lend);
        View customToolbar = getLayoutInflater().inflate(R.layout.view_center_toolbar, null);
        TextView titleView = (TextView)customToolbar.findViewById(R.id.text_title);
        titleView.setText("돈빌려주기");
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        appBarLayout.setPadding(0, Utils.getStatusBarHeight(), 0, 0);

        overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out_background);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setCustomView(customToolbar, new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER));
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations(R.anim.slide_right_in, R.anim.slide_left_out, R.anim.slide_left_in, R.anim.slide_right_out)
                    .add(R.id.container, new ContractFragment())
                    .commit();
        }
    }


    protected void changeSignature(AccountData data) {
        SignatureFragment f = new SignatureFragment();
        Bundle args = new Bundle();
        args.putSerializable(SignatureFragment.EXTRA_ACCOUNT_DATA, data);
        f.setArguments(args);

        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.slide_right_in, R.anim.slide_left_out, R.anim.slide_left_in, R.anim.slide_right_out)
                .replace(R.id.container, f)
                .addToBackStack(null)
                .commit();
    }

    protected void changeSend(BorrowerData data, AccountData accountData) {
        SendFragment f = new SendFragment();
        Bundle args = new Bundle();
        args.putSerializable(SendFragment.EXTRA_BORROWER, data);
        args.putSerializable(SendFragment.EXTRA_ACCOUNT_DATA, accountData);
        f.setArguments(args);
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.slide_right_in, R.anim.slide_left_out, R.anim.slide_left_in, R.anim.slide_right_out)
                .replace(R.id.container, f)
                .addToBackStack(null)
                .commit();
    }

    protected void changeSuccess() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_close,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                CalculatorDialog dialog = new CalculatorDialog();
                dialog.setOnCalculatorResultListener(new CalculatorDialog.OnCalculatorResultListener() {
                    @Override
                    public void OnCalculatorResult(String result) {

                    }
                });
                dialog.show(getSupportFragmentManager(), "dialog");
                return true;
            case R.id.menu_close:
                AlertDialog.Builder builder = new AlertDialog.Builder(LendMoneyActivity.this);
                builder.setMessage(R.string.alert_close_borrow_money)
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        })
                        .setNegativeButton("취소",null)
                        .show();

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_left_in_background, R.anim.slide_right_out);
    }
}
