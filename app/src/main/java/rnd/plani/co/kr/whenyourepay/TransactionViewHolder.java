package rnd.plani.co.kr.whenyourepay;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import rnd.plani.co.kr.whenyourepay.Data.AccountData;
import rnd.plani.co.kr.whenyourepay.Data.DutchPayData;
import rnd.plani.co.kr.whenyourepay.Data.ThingsData;
import rnd.plani.co.kr.whenyourepay.Data.TransactionData;

/**
 * Created by RND on 2016-04-11.
 */
public class TransactionViewHolder extends RecyclerView.ViewHolder {
    TextView categoryView, nameView, moneyView, dateView;

    public OnItemLongClickListener onItemLongClickListener;

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        onItemLongClickListener = listener;
    }

    public TransactionViewHolder(View itemView) {
        super(itemView);
        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (onItemLongClickListener != null) {
                    onItemLongClickListener.onItemLongClick(v, getAdapterPosition());
                }
                return true;
            }
        });
        categoryView = (TextView) itemView.findViewById(R.id.text_category);
        nameView = (TextView) itemView.findViewById(R.id.text_name);
        moneyView = (TextView) itemView.findViewById(R.id.text_money);
        dateView = (TextView) itemView.findViewById(R.id.text_date);
    }

    AccountData data;

    public void setContractData(TransactionData data) {
        NumberFormat nf = NumberFormat.getInstance();
        if (data instanceof AccountData) {
            AccountData accountData = (AccountData) data;
            categoryView.setText("금전거래");
            nameView.setText(accountData.name);
            moneyView.setText(nf.format(accountData.money)+"원");

            SimpleDateFormat sdf = new SimpleDateFormat("M월 d일");
            Date date = new Date(accountData.date);
            dateView.setText(sdf.format(date));
        } else if (data instanceof ThingsData) {
            ThingsData thingsData = (ThingsData) data;
            categoryView.setText("물건거래");
            nameView.setText(thingsData.borrowerName);
            moneyView.setText(thingsData.thingsName);
            SimpleDateFormat sdf = new SimpleDateFormat("M월 d일");
            Date date = new Date(thingsData.date);
            dateView.setText(sdf.format(date));
        } else {
            DutchPayData dutchPayData = (DutchPayData) data;
            categoryView.setText("더치페이");
            nameView.setText(dutchPayData.title);
            moneyView.setText(nf.format(dutchPayData.totalPrice)+"원");
            SimpleDateFormat sdf = new SimpleDateFormat("M월 d일");
            Date date = new Date(dutchPayData.date);
            dateView.setText(sdf.format(date));
        }
    }
}
