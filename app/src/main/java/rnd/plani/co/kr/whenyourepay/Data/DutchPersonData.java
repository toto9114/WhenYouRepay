package rnd.plani.co.kr.whenyourepay.Data;

import java.io.Serializable;

/**
 * Created by RND on 2016-06-30.
 */
public class DutchPersonData implements Serializable{
    public static final long INVALID_ID = -1;
    public long _id = INVALID_ID;
    public String name;
    public String phone;
    public boolean attended;
    public int dutchMoney;
}