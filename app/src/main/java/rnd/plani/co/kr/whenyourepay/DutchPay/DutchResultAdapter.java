package rnd.plani.co.kr.whenyourepay.DutchPay;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

import rnd.plani.co.kr.whenyourepay.Data.DutchResultData;

/**
 * Created by RND on 2016-06-30.
 */
public class DutchResultAdapter extends BaseAdapter {
    List<DutchResultData> items = new ArrayList<>();

    public void add(DutchResultData data){
        items.add(data);
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
        DutchResultView view;
        if(convertView == null){
            view = new DutchResultView(parent.getContext());
        }else{
            view = (DutchResultView)convertView;
        }
        view.setData(items.get(position));
        return view;
    }
}
