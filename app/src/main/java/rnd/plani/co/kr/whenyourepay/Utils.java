package rnd.plani.co.kr.whenyourepay;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Environment;
import android.view.View;

import java.io.File;

import rnd.plani.co.kr.whenyourepay.Manager.DBContants;

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

    public static Bitmap getViewBitmap(View v) {
        v.clearFocus();
        v.setPressed(false);
        boolean willNotCache = v.willNotCacheDrawing();
        v.setWillNotCacheDrawing(false);
        int color = v.getDrawingCacheBackgroundColor();
        v.setDrawingCacheBackgroundColor(Color.TRANSPARENT);
        if (color != Color.TRANSPARENT) {
            v.destroyDrawingCache();
        }
        v.buildDrawingCache();
        Bitmap cacheBitmap = v.getDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(cacheBitmap);
        v.destroyDrawingCache();
        v.setWillNotCacheDrawing(willNotCache);
        v.setDrawingCacheBackgroundColor(color);

        return bitmap;
    }

    public static void deleteCompleteFile(){
        File dir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), DBContants.IOU_FOLDER_NAME);
        File saveFile = new File(dir, DBContants.FILE_NAME);
        if(dir.exists()){
            saveFile.delete();
        }
    }
}
