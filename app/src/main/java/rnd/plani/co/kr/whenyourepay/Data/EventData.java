package rnd.plani.co.kr.whenyourepay.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by RND on 2016-06-29.
 */
public class EventData implements Serializable{
    public static final long INVALID_ID = -1;
    public long _id = INVALID_ID;
    public String title;
    public int money;
    public List<DutchPersonData> people;
}
