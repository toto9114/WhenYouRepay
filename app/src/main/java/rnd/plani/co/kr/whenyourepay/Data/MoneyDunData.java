package rnd.plani.co.kr.whenyourepay.Data;

import io.realm.RealmObject;

/**
 * Created by RND on 2016-07-15.
 */
public class MoneyDunData extends RealmObject{
    private long _id;
    private long date;

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }
}
