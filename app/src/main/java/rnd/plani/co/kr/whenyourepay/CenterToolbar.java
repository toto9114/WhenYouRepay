package rnd.plani.co.kr.whenyourepay;

import android.content.Context;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * Created by RND on 2016-08-09.
 */
public class CenterToolbar extends FrameLayout {
    TextView titleView;
    public CenterToolbar(Context context) {
        super(context);
        inflate(getContext(), R.layout.view_center_toolbar, this);
        titleView = (TextView)findViewById(R.id.image_category);
    }

    public void setTitle(String title){
        titleView.setText(title);
    }
}
