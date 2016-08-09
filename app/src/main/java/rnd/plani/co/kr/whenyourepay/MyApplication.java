package rnd.plani.co.kr.whenyourepay;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

/**
 * Created by RND on 2016-04-06.
 */
public class MyApplication extends Application {
    private static Context context;
    private static volatile Activity currentActivity = null;
    @Override
    public void onCreate() {
        super.onCreate();
        context = this;

    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        context = null;
    }
    public static Context getContext(){
        return context;
    }
}
