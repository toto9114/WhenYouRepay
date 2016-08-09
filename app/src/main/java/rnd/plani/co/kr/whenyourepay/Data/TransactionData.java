package rnd.plani.co.kr.whenyourepay.Data;

/**
 * Created by RND on 2016-06-23.
 */
public interface TransactionData {
    public long getId();
    public String getName();
    public long getDate();
    public int getPrice();
    public String getRepayDate();
}
