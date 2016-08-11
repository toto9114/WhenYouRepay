package rnd.plani.co.kr.whenyourepay;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dongja94 on 2016-02-03.
 */
public class FingerPaintView extends View {
    public FingerPaintView(Context context) {
        super(context);
        init();
    }

    public FingerPaintView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    static class MyDrawObject {
        Path mPath;
        Paint mPaint;

        public void draw(Canvas canvas) {
            canvas.drawPath(mPath, mPaint);
        }
    }

    List<MyDrawObject> mDrawList = new ArrayList<MyDrawObject>();

    Paint mPaint;
    private void init() {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(5);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (MyDrawObject drawer : mDrawList) {
            drawer.draw(canvas);
        }
    }

    public void setPenColor(int color) {
        mPaint.setColor(color);
    }

    MyDrawObject currentDrawer;

    public void undo() {
        if (mDrawList.size() > 0) {
            mDrawList.remove(mDrawList.size() - 1);
            invalidate();
        }
    }
    public void undoAll(){
        if(mDrawList.size()>0){
            mDrawList.removeAll(mDrawList);
            invalidate();
        }
    }

    public boolean isDraw(){
        if(mDrawList.size()>0) {
            return true;
        }else{
            return false;
        }
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getActionMasked();
        switch (action) {
            case MotionEvent.ACTION_DOWN :
                currentDrawer = new MyDrawObject();
                currentDrawer.mPath = new Path();
                currentDrawer.mPath.moveTo(event.getX(), event.getY());
                currentDrawer.mPaint = new Paint(mPaint);
                mDrawList.add(currentDrawer);
                return true;
            case MotionEvent.ACTION_MOVE :
                if (currentDrawer != null) {
                    if(drawingListener!=null){
                        drawingListener.OnDrawing();
                    }
                    currentDrawer.mPath.lineTo(event.getX(), event.getY());
                    invalidate();
                    return true;
                }
            case MotionEvent.ACTION_UP :
                currentDrawer = null;
                return true;
        }
        return super.onTouchEvent(event);
    }

    public interface OnDrawingListener{
        public void OnDrawing();
    }

    public OnDrawingListener drawingListener;

    public void setOnDrawingListener(OnDrawingListener listener){
        drawingListener = listener;
    }
}
