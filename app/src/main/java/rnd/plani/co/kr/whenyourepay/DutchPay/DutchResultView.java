package rnd.plani.co.kr.whenyourepay.DutchPay;

import android.content.Context;
import android.widget.FrameLayout;
import android.widget.TextView;

import rnd.plani.co.kr.whenyourepay.Data.DutchResultData;
import rnd.plani.co.kr.whenyourepay.R;

/**
 * Created by RND on 2016-06-30.
 */
public class DutchResultView extends FrameLayout {
    TextView nameView, countView, moneyView;

    public DutchResultView(Context context) {
        super(context);
        inflate(getContext(), R.layout.view_dutch_result, this);
        nameView = (TextView) findViewById(R.id.text_name);
        countView = (TextView) findViewById(R.id.text_attend);
        moneyView = (TextView) findViewById(R.id.text_money);
    }

    public void setData(DutchResultData data) {
        nameView.setText(data.name);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < data.attendList.size(); i++) {
            if (i == data.attendList.size() - 1) {
                sb.append("" + (i + 1) + "차");
            } else {
                sb.append("" + (i + 1) + "차/");
            }
        }
        countView.setText(sb.toString());
        moneyView.setText("" + data.money);
    }
}
