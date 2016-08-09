package rnd.plani.co.kr.whenyourepay.Contact;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

import rnd.plani.co.kr.whenyourepay.Data.PersonData;

/**
 * Created by RND on 2016-07-14.
 */
public class SingleContactAdapter extends BaseAdapter {
    List<PersonData> items = new ArrayList<>();
    public void add(PersonData data){
        items.add(data);
        notifyDataSetChanged();
    }

    public void addAll(List<PersonData> list){
        items.addAll(list);
        notifyDataSetChanged();
    }

    public void clear(){
        items.clear();
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SingleContactView view;
        if(convertView == null){
            view = new SingleContactView(parent.getContext());
        }else {
            view = (SingleContactView)convertView;
        }
        view.setData(items.get(position));
        return view;
    }
}
