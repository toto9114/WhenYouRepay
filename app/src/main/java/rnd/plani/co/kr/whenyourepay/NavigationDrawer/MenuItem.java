package rnd.plani.co.kr.whenyourepay.NavigationDrawer;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import rnd.plani.co.kr.whenyourepay.R;


/**
 * Created by RND on 2016-05-03.
 */
public class MenuItem extends FrameLayout{
    ImageView iconView;
    TextView titleView;
    View divider;
    MenuData data;
    public MenuItem(Context context) {
        super(context);
        init();
    }

    private void init(){
        inflate(getContext(), R.layout.view_menu,this);
        iconView = (ImageView)findViewById(R.id.image_menu);
        titleView = (TextView)findViewById(R.id.text_menu);
        divider = (View)findViewById(R.id.divider);
    }
   public void setData(MenuData data,int groupPosition, boolean isLastItem){
       this.data = data;
       titleView.setText(data.title);
       iconView.setImageResource(data.iconId);
       if(isLastItem && groupPosition ==0){
           divider.setVisibility(VISIBLE);
       }else{
           divider.setVisibility(GONE);
       }
   }

}
