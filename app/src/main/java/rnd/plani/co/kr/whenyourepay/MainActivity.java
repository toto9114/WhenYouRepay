package rnd.plani.co.kr.whenyourepay;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import rnd.plani.co.kr.whenyourepay.BorrowMoney.LendMoneyActivity;
import rnd.plani.co.kr.whenyourepay.BorrowThings.LendThingsActivity;
import rnd.plani.co.kr.whenyourepay.DutchPay.DutchPayActivity;
import rnd.plani.co.kr.whenyourepay.NavigationDrawer.MenuFragment;

public class MainActivity extends AppCompatActivity implements MenuFragment.OnMenuItemSelectedListener {
    DrawerLayout drawerLayout;
    FloatingActionsMenu mainMenu;
    FloatingActionButton moneyView, thingsView, dutchView;
    ImageView opacityView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        mainMenu = (FloatingActionsMenu) findViewById(R.id.floating_menu);
        moneyView = (FloatingActionButton) findViewById(R.id.btn_money);
        thingsView = (FloatingActionButton) findViewById(R.id.btn_things);
        dutchView = (FloatingActionButton) findViewById(R.id.btn_dutch);
        opacityView = (ImageView) findViewById(R.id.image_opacity);

        appBarLayout.setPadding(0, getStatusBarHeight(), 0, 0);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
//        setDrawerWidth();

        opacityView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (opacityView.getVisibility() == View.VISIBLE) {
                    opacityView.setVisibility(View.GONE);
                    mainMenu.collapse();
                }
            }
        });
        mainMenu.bringToFront();
        mainMenu.setOnFloatingActionsMenuUpdateListener(new FloatingActionsMenu.OnFloatingActionsMenuUpdateListener() {
            @Override
            public void onMenuExpanded() {
                setOpacity(true);
            }

            @Override
            public void onMenuCollapsed() {
                setOpacity(false);
            }

        });

        moneyView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LendMoneyActivity.class));
                mainMenu.collapseImmediately();
            }
        });

        thingsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LendThingsActivity.class));
                mainMenu.collapseImmediately();
            }
        });

        dutchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, DutchPayActivity.class));
                mainMenu.collapseImmediately();
            }
        });
    }

    public void setOpacity(boolean isOpacity) {
        if (isOpacity) {
            Animation animation = new AlphaAnimation(0, 1);
            animation.setDuration(200);
            opacityView.setVisibility(View.VISIBLE);
            opacityView.setAnimation(animation);
        } else {
            Animation animation = new AlphaAnimation(1, 0);
            animation.setDuration(200);
            opacityView.setVisibility(View.GONE);
            opacityView.setAnimation(animation);
        }
    }
//
//    private void setDrawerWidth() {
//        DisplayMetrics displayMetrics = new DisplayMetrics();
//
//        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
//        float dipWidth = (displayMetrics.widthPixels / displayMetrics.density);
//
//        Resources resources = getResources();
//        float width = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipWidth - 56, resources.getDisplayMetrics());
//        DrawerLayout.LayoutParams params = (DrawerLayout.LayoutParams) drawerLayout.getLayoutParams();
//        params.width = (int) (width);
//        drawerLayout.setLayoutParams(params);
//    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMenuItemSelected(@MenuFragment.MenuMode int menuId) {

    }
}
