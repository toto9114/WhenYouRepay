package rnd.plani.co.kr.whenyourepay.DutchPay;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.wefika.flowlayout.FlowLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import rnd.plani.co.kr.whenyourepay.Data.DutchPersonData;
import rnd.plani.co.kr.whenyourepay.Data.EventData;
import rnd.plani.co.kr.whenyourepay.Data.PersonData;
import rnd.plani.co.kr.whenyourepay.R;

/**
 * Created by RND on 2016-06-30.
 */
public class EventView extends FrameLayout {
    EditText editMoney;
    FlowLayout group;
    TextView titleView, editTitleView, countView;
    List<DutchPersonData> personList = new ArrayList<>();

    public EventView(Context context, List<PersonData> data,int count) {
        super(context);
        inflate(getContext(), R.layout.view_edit_event, this);
        editMoney = (EditText) findViewById(R.id.edit_money);
        titleView = (TextView) findViewById(R.id.image_category);
        editTitleView = (TextView) findViewById(R.id.text_change_title);
        countView = (TextView) findViewById(R.id.text_count);
        group = (FlowLayout) findViewById(R.id.check_group);

        titleView.setText(count+"차모임");
        countView.setText(""+data.size());
        editTitleView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        if (data != null) {
            for (int i = 0; i < data.size(); i++) {
                final CheckBox checkBox = new CheckBox(getContext());
                checkBox.setText(data.get(i).getName());
                checkBox.setId(i);
                checkBox.setChecked(true);
                DutchPersonData person = new DutchPersonData();
                person.name = data.get(i).getName();
                person.attended = true;

                personList.add(person);
                checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            DutchPersonData data = personList.get(buttonView.getId());
                            data.attended = true;
                            personList.set(buttonView.getId(), data);
                        } else {
                            DutchPersonData data = personList.get(buttonView.getId());
                            data.attended = false;
                            personList.set(buttonView.getId(), data);
                        }
                        int count = 0;
                        for (DutchPersonData dutchPersonData : personList) {
                            if (dutchPersonData.attended) {
                                count++;
                            }
                        }
                        countView.setText("" + count);
                    }
                });

                group.addView(checkBox, FlowLayout.LayoutParams.WRAP_CONTENT, FlowLayout.LayoutParams.WRAP_CONTENT);
            }
        }
    }

    public EventData getData() {
        EventData data = new EventData();
        int count = 0;
        for (DutchPersonData personData : personList) {
            if (personData.attended) {
                count++;
            }
        }
        List<Integer> index = new ArrayList<>();
        int money =0;
        int lessMoney = 0 ;
        if (!TextUtils.isEmpty(editMoney.getText().toString())) {
            data.money = Integer.parseInt(editMoney.getText().toString());
            money = data.money/count;
            if(money %100 !=0){
                money = (money - (money%100))+100;
            }
            for (int i = 0; i < personList.size(); i++) {
                if (personList.get(i).attended) {
                    index.add(i);
                    personList.get(i).dutchMoney = money;
                } else {
                    personList.get(i).dutchMoney = 0;
                }
            }
            lessMoney = data.money - ((index.size()-1)*money);
            if(data.money/count %100 !=0) {
                Random r = new Random();
                personList.get(r.nextInt(index.size() - 1)).dutchMoney = lessMoney;
            }
        } else {
            data.money = 0;
        }
        data.title = titleView.getText().toString();
        data.people = personList;
        return data;
    }
}
