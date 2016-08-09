package rnd.plani.co.kr.whenyourepay.MainContract;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import rnd.plani.co.kr.whenyourepay.Data.TransactionData;
import rnd.plani.co.kr.whenyourepay.R;
import rnd.plani.co.kr.whenyourepay.TransactionViewHolder;

/**
 * Created by RND on 2016-08-09.
 */
public class MainContractAdapter extends RecyclerView.Adapter {
    List<TransactionData> items = new ArrayList<>();

    public void add(TransactionData data){
        items.add(data);
        notifyDataSetChanged();
    }

    public void clear(){
        items.clear();
        notifyDataSetChanged();
    }

    public void sort() {
        Collections.sort(items, new Comparator<TransactionData>() {
            @Override
            public int compare(TransactionData item1, TransactionData item2) {
                return item1.getDate() - item2.getDate() > 0 ? 1 : -1;
            }
        });
        notifyDataSetChanged();
    }
    public TransactionData getItemAtPosition(int positon){
        return items.get(positon);
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.view_contract,parent,false);
        return new TransactionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((TransactionViewHolder)holder).setContractData(items.get(position));

    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
