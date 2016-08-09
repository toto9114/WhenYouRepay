package rnd.plani.co.kr.whenyourepay.Contact;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import rnd.plani.co.kr.whenyourepay.Data.PersonData;
import rnd.plani.co.kr.whenyourepay.R;

/**
 * Created by RND on 2016-07-13.
 */
public class MultiRecentAdapter extends BaseAdapter {
    List<PersonData> items = new ArrayList<>();

    public void add(PersonData data) {
        items.add(data);
        notifyDataSetChanged();
    }

    public void addAll(List<PersonData> list) {
        items.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public PersonData getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MultiContactView view;
        if (convertView == null) {
            view = new MultiContactView(parent.getContext());
        } else {
            view = (MultiContactView) convertView;
        }
        CheckBox checkBox = (CheckBox) view.findViewById(R.id.checkBox_name);
        TextView phoneView = (TextView) view.findViewById(R.id.text_phone);
        checkBox.setChecked(((ListView) parent).isItemChecked(position));
        checkBox.setFocusable(false);
        checkBox.setClickable(false);

        checkBox.setText(items.get(position).getName());
        phoneView.setText(items.get(position).getPhone());
        return view;
    }
}
