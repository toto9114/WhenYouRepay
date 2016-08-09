package rnd.plani.co.kr.whenyourepay;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * Created by RND on 2016-07-19.
 */
public class OverDueHeaderView extends FrameLayout {
    public interface OnOverDueHeaderClickListener{
        public void OnOverDueHeaderClick();
    }

    public OnOverDueHeaderClickListener overDueHeaderClickListener;
    public void setOnOverDueHeaderClickListener(OnOverDueHeaderClickListener listener){
        overDueHeaderClickListener = listener;
    }
    TextView overDueView;
    public OverDueHeaderView(Context context) {
        super(context);
        inflate(getContext(), R.layout.view_header_overdue,this);
        overDueView = (TextView)findViewById(R.id.text_overdue);
        Button btn = (Button)findViewById(R.id.btn_dun);
        btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (overDueHeaderClickListener != null) {
                    overDueHeaderClickListener.OnOverDueHeaderClick();
                }
            }
        });
    }
    public void setOverDueView(int count){
        overDueView.setText("연체내역 (" + count + "건)");

    }
}
