package rnd.plani.co.kr.whenyourepay.NavigationDrawer;

import android.content.Context;
import android.widget.FrameLayout;
import android.widget.TextView;

import rnd.plani.co.kr.whenyourepay.R;


/**
 * Created by RND on 2016-08-08.
 */
public class GroupMenuView extends FrameLayout {
    TextView titleView;
    public GroupMenuView(Context context) {
        super(context);
        inflate(getContext(), R.layout.view_group_menu,this);
        titleView = (TextView)findViewById(R.id.image_category);
    }

    public void setGroupMenu(GroupMenu groupMenu){
        titleView.setText(groupMenu.title);
    }
}
