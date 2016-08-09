package rnd.plani.co.kr.whenyourepay.DutchPay;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import rnd.plani.co.kr.whenyourepay.Data.PersonData;
import rnd.plani.co.kr.whenyourepay.R;

/**
 * Created by RND on 2016-07-04.
 */
public class MemberListAdapter extends RecyclerView.Adapter implements OnDelButtonClickListener {
    List<PersonData> items = new ArrayList<>();

    public void add(PersonData data) {
        items.add(data);
        notifyDataSetChanged();
    }

    public void addAll(List<PersonData> list) {
        items.addAll(list);
        notifyDataSetChanged();
    }

    public void clear() {
        items.clear();
    }

    public void remove(int position) {
        items.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.view_person, parent, false);
        return new PersonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((PersonViewHolder) holder).setOnDelButtonClickListener(this);
        ((PersonViewHolder) holder).setName(items.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public OnDelButtonClickListener delButtonClickListener;

    public void setOnDelButtonClickListener(OnDelButtonClickListener listener) {
        delButtonClickListener = listener;
    }

    @Override
    public void onDelButtonClick(View view, int position) {
        if (delButtonClickListener != null) {
            delButtonClickListener.onDelButtonClick(view, position);
        }
    }
}
