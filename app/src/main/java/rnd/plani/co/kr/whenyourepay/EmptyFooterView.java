package rnd.plani.co.kr.whenyourepay;

import android.content.Context;
import android.widget.FrameLayout;

/**
 * Created by RND on 2016-07-18.
 */
public class EmptyFooterView extends FrameLayout {
    public EmptyFooterView(Context context) {
        super(context);
        inflate(getContext(), R.layout.view_empty_footer,this);
    }
}
