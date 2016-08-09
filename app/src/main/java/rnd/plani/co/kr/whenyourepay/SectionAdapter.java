package rnd.plani.co.kr.whenyourepay;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import rnd.plani.co.kr.whenyourepay.Data.TransactionData;

/**
 * Created by RND on 2016-07-14.
 */
public class SectionAdapter extends RecyclerView.Adapter implements ContractHeaderView.OnContractHeaderClickListener {
    public static final int TYPE_OVERDUE = 1;
    public static final int TYPE_CONTRACT_HEADER = 2;
    public static final int TYPE_CONTRACT = 3;

    List<TransactionData> overDue = new ArrayList<>();
    List<TransactionData> contract = new ArrayList<>();

    public void clear() {
        overDue.clear();
        contract.clear();
        notifyDataSetChanged();
    }

    public void addOverDue(TransactionData data) {
        overDue.add(data);
        notifyDataSetChanged();
    }

    public void addContract(TransactionData data) {
        contract.add(data);
        notifyDataSetChanged();
    }

    public TransactionData getItemAtPosition(int position) {
        int overDueCount = overDue.size();
        if (position < overDueCount) {
            return overDue.get(position);
        }
        position = position - overDueCount;
        if (position == 0) {
            return null;
        }
        position--;
        if (position < contract.size()) {
            return contract.get(position);
        }
        return null;
    }


    @Override
    public int getItemViewType(int position) {
        int overDueCount = overDue.size();
        if (position < overDueCount) {
            return TYPE_OVERDUE;
        }
        position = position - overDueCount;
        if (position == 0) {
            return TYPE_CONTRACT_HEADER;
        }
        position--;
        if (position < contract.size()) {
            return TYPE_CONTRACT;
        }
        return -1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = null;
        switch (viewType) {
            case TYPE_OVERDUE:
                view = inflater.inflate(R.layout.view_contract, parent, false);
                return new TransactionViewHolder(view);
            case TYPE_CONTRACT_HEADER:
                view = inflater.inflate(R.layout.view_header_contract, parent, false);
                return new ContractHeaderView(view);
            case TYPE_CONTRACT:
                view = inflater.inflate(R.layout.view_contract, parent, false);
                return new TransactionViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int overDueCount = overDue.size();
        if (position < overDueCount) {
            ((TransactionViewHolder) holder).setContractData(overDue.get(position));
            return;
        }
        position = position - overDueCount;
        if (position == 0) {
            ((ContractHeaderView) holder).setContractView(contract.size());
            ((ContractHeaderView) holder).setOnOverDueHeaderClickListener(this);
            return;
        }
        position--;
        if (position < contract.size()) {
            ((TransactionViewHolder) holder).setContractData(contract.get(position));
            return;
        }
    }

    public void sort() {
        Collections.sort(overDue, new Comparator<TransactionData>() {
            @Override
            public int compare(TransactionData item1, TransactionData item2) {
                return item1.getDate() - item2.getDate() > 0 ? 1 : -1;
            }
        });
        Collections.sort(contract, new Comparator<TransactionData>() {
            @Override
            public int compare(TransactionData item1, TransactionData item2) {
                return item1.getDate() - item2.getDate() > 0 ? 1 : -1;
            }
        });
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {

        return overDue.size() + contract.size() + 1;

    }

    public int getOverDueCount() {
        return overDue.size();
    }

    public int getContractCount() {
        return contract.size();
    }


    public ContractHeaderView.OnContractHeaderClickListener contractHeaderClickListener;

    public void setOnContractHeaderClickListener(ContractHeaderView.OnContractHeaderClickListener listener) {
        contractHeaderClickListener = listener;
    }

    @Override
    public void OnContractHeaderClick() {
        if (contractHeaderClickListener != null) {
            contractHeaderClickListener.OnContractHeaderClick();
        }
    }
}
