package rnd.plani.co.kr.whenyourepay;

/**
 * Created by RND on 2016-08-09.
 */
public class Utils {
    public static int getStatusBarHeight() {
        int result = 0;
        int resourceId = MyApplication.getContext().getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = MyApplication.getContext().getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}
