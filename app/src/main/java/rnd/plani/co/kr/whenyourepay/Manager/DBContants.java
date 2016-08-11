package rnd.plani.co.kr.whenyourepay.Manager;

import android.provider.BaseColumns;

/**
 * Created by dongja94 on 2016-02-16.
 */
public class DBContants {
    public interface AccountBook extends BaseColumns {
        public static final String TABLE_NAME = "borrowmoney";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_PHONE = "phone";
        public static final String COLUMN_MONEY = "money";
        public static final String COLUMN_INTEREST = "interest";
        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_REPAY_DATE = "repay_date";
        public static final String COLUMN_MEMO = "memo";
        public static final String COLUMN_SIGN = "sign";
        public static final String COLULMN_COMPLETE = "complete";
    }

    public interface Transaction extends BaseColumns {
        public static final String TABLE_NAME = "contract";
        public static final String COLUMN_NAME = "target";
        public static final String COLUMN_REPAY = "repay";
        public static final String COLUMN_REMAIN = "remain";
        public static final String COLUMN_TYPE = "type";
        public static final String COLUMN_DATE = "created";
    }

    public interface Things extends BaseColumns {
        public static final String TABLE_NAME = "borrowthings";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_PHONE = "phone";
        public static final String COLUMN_THINGS = "things";
        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_REPAY_DATE = "repay_date";
        public static final String COLUMN_MEMO = "memo";
        public static final String COLUMN_SIGN = "sign";
        public static final String COLUMN_PICTURE = "picture";
        public static final String COLULMN_COMPLETE = "complete";
    }

    public interface DutchPay extends BaseColumns{
        public static final String TABLE_NAME = "dutch";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_MONEY = "totalmoney";
        public static final String COLUMN_DATE = "date";
        public static final String COLULMN_COMPLETE = "complete";
    }

    public interface PersonData extends BaseColumns{
        public static final String TABLE_NAME = "persondata";
        public static final String COLUMN_PHONE = "phone";
        public static final String COLUMN_DUTCHPAY = "dutchpay";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_MONEY = "money";
        public static final String COLUMN_IS_PAID = "ispaid";
    }

    public interface DutchEvent extends BaseColumns{
        public static final String TABLE_NAME = "dutchevent";
        public static final String COLUMN_DUTCH = "dutch";
        public static final String COLUMN_TITLE = "event_title";
        public static final String COLUMN_MONEY = "money";
    }

    public interface DutchPerson extends BaseColumns{
        public static final String TABLE_NAME = "dutchperson";
        public static final String COLUMN_DUTCH = "dutch";
        public static final String COLUMN_EVENT = "event";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_PHONE = "phone";
        public static final String COLUMN_MONEY = "permoney";
        public static final String COLUMN_ATTENDED = "attended";
    }

    public interface Group extends BaseColumns{
        public static final String TABLE_NAME = "groupdata";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_ACCOUNT = "account";
        public static final String COLUMN_MONEY = "money";
        public static final String COLUMN_TOTAL_MONEY = "total";
    }

    public interface GroupMember extends BaseColumns{
        public static final String TABLE_NAME = "member";
        public static final String COLUMN_GROUP = "groupName";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_PHONE = "phone";
        public static final String COLUMN_MONEY = "due_money";
        public static final String COLUMN_PAID = "paid";
    }

    public static final String IOU_FOLDER_NAME = "whenyourepay";
    public static final String FILE_NAME = "iou_image.jpg";
    public static final int SORT_TYPE_DATE = 1;
}
