package rnd.plani.co.kr.whenyourepay.Data;


import java.io.Serializable;

/**
 * Created by RND on 2016-06-22.
 */
public class ThingsData implements Serializable, TransactionData {
    public static final long INVALID_ID = -1;
    public long _id = INVALID_ID;
    public String borrowerName;
    public String phone;
    public String thingsName;
    public String memo;
    public long date;
    public String repayDate;
    public String pictureUri;
    public byte[] picture;
    public byte[] sign;
    public boolean isCompleted;

    @Override
    public long getId() {
        return _id;
    }

    @Override
    public String getName() {
        return borrowerName;
    }

    @Override
    public long getDate() {
        return date;
    }

    @Override
    public int getPrice() {
        return 0;
    }

    @Override
    public String getRepayDate() {
        return repayDate;
    }
}
