package rnd.plani.co.kr.whenyourepay.DutchPay;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;

import rnd.plani.co.kr.whenyourepay.Data.EventData;
import rnd.plani.co.kr.whenyourepay.R;

/**
 * Created by RND on 2016-06-24.
 */
public class EditEventView extends RecyclerView.ViewHolder {

    EditText editMoney;
    LinearLayout group;

    public EditEventView(View itemView) {
        super(itemView);
        editMoney = (EditText) itemView.findViewById(R.id.edit_money);
        group = (LinearLayout) itemView.findViewById(R.id.check_group);
    }

    int size;
    EventData data;

    public void setData(EventData data) {
        if (data != null) {
            this.data = data;
            size = data.people.size();
            for (int i = 0; i < size; i++) {
                CheckBox checkBox = new CheckBox(itemView.getContext());
                checkBox.setText("" + data.people.get(i).name);
                group.addView(checkBox);
            }
            editMoney.setText("" + data.money);
        }
    }
}
