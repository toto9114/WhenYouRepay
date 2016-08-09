package rnd.plani.co.kr.whenyourepay.Data;


import java.io.Serializable;
import java.util.List;

/**
 * Created by RND on 2016-06-24.
 */
public class DutchPayData implements Serializable,TransactionData {
    public static final long INVALID_ID = -1;
    public long _id = INVALID_ID;
    public String title;
    public int totalPrice;
    public long date;
    public boolean isCompleted;
    public List<PersonData> personList;
    public List<EventData> eventList;

    @Override
    public long getId() {
        return _id;
    }

    @Override
    public String getName() {
        return title;
    }

    @Override
    public long getDate() {
        return date;
    }

    @Override
    public int getPrice() {
        return totalPrice;
    }

    @Override
    public String getRepayDate() {
        return null;
    }
}
