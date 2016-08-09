package rnd.plani.co.kr.whenyourepay.Data;


import java.io.Serializable;

/**
 * Created by dongja94 on 2016-02-16.
 */
public class AccountData implements Serializable,TransactionData {
    public static final long INVALID_ID = -1;
    public long _id = INVALID_ID;
    public String name;
    public String phone;
    public int money;
    public int interest;
    public long date;
    public String repayDate;
    public String memo;
    public byte[] sign;
    public boolean isCompleted;
    public long completeDate;

    @Override
    public long getId() {
        return _id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public long getDate() {
        return date;
    }

    @Override
    public int getPrice() {
        return money;
    }

    @Override
    public String getRepayDate() {
        return repayDate;
    }
}