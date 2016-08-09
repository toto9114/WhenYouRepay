package rnd.plani.co.kr.whenyourepay.Manager;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import rnd.plani.co.kr.whenyourepay.Data.AccountData;
import rnd.plani.co.kr.whenyourepay.Data.DetailTransData;
import rnd.plani.co.kr.whenyourepay.Data.DutchPayData;
import rnd.plani.co.kr.whenyourepay.Data.DutchPersonData;
import rnd.plani.co.kr.whenyourepay.Data.EventData;
import rnd.plani.co.kr.whenyourepay.Data.PersonData;
import rnd.plani.co.kr.whenyourepay.Data.ThingsData;
import rnd.plani.co.kr.whenyourepay.MyApplication;
import rnd.plani.co.kr.whenyourepay.Data.TransactionData;

/**
 * Created by dongja94 on 2016-02-16.
 */
public class DataManager extends SQLiteOpenHelper {
    private static DataManager instance;

    public static DataManager getInstance() {
        if (instance == null) {
            instance = new DataManager();
        }
        return instance;
    }

    private static final String DB_NAME = "addressbook";
    private static final int DB_VERSION = 1;

    private static final String COMPLETE = "complete";

    private DataManager() {
        super(MyApplication.getContext(), DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String moneyTrans = "CREATE TABLE " + DBContants.AccountBook.TABLE_NAME + "(" +
                DBContants.AccountBook._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                DBContants.AccountBook.COLUMN_NAME + " TEXT NOT NULL," +
                DBContants.AccountBook.COLUMN_PHONE + " TEXT," +
                DBContants.AccountBook.COLUMN_MONEY + " INTEGER," +
                DBContants.AccountBook.COLUMN_INTEREST + " INTEGER," +
                DBContants.AccountBook.COLUMN_DATE + " DATETIME," +
                DBContants.AccountBook.COLUMN_REPAY_DATE + " TEXT," +
                DBContants.AccountBook.COLUMN_MEMO + " TEXT," +
                DBContants.AccountBook.COLUMN_SIGN + " BLOB," +
                DBContants.AccountBook.COLULMN_COMPLETE + " " + COMPLETE + " INTEGER DEFAULT 0" +
                ");";
        db.execSQL(moneyTrans);
        String thingsTrans = "CREATE TABLE " + DBContants.Things.TABLE_NAME + "(" +
                DBContants.Things._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                DBContants.Things.COLUMN_NAME + " TEXT NOT NULL," +
                DBContants.Things.COLUMN_PHONE + " TEXT," +
                DBContants.Things.COLUMN_THINGS + " TEXT," +
                DBContants.Things.COLUMN_DATE + " DATETIME," +
                DBContants.Things.COLUMN_REPAY_DATE + " TEXT," +
                DBContants.Things.COLUMN_MEMO + " TEXT, " +
                DBContants.Things.COLUMN_SIGN + " BLOB, " +
                DBContants.Things.COLUMN_PICTURE + " BLOB," +
                DBContants.Things.COLULMN_COMPLETE + " " + COMPLETE + " INTEGER DEFAULT 0" +
                ");";
        db.execSQL(thingsTrans);

        String dutchPay = "CREATE TABLE " + DBContants.DutchPay.TABLE_NAME + "(" +
                DBContants.DutchPay._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                DBContants.DutchPay.COLUMN_TITLE + " TEXT," +
                DBContants.DutchPay.COLUMN_MONEY + " INTEGER," +
                DBContants.DutchPay.COLULMN_COMPLETE + " " + COMPLETE + " INTEGER DEFAULT 0," +
                DBContants.DutchPay.COLUMN_DATE + " DATETIME" +
                ");";
        db.execSQL(dutchPay);

        String dutchPerson = "CREATE TABLE " + DBContants.PersonData.TABLE_NAME + "(" +
                DBContants.PersonData._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                DBContants.PersonData.COLUMN_DUTCHPAY + " INTEGER," +
                DBContants.PersonData.COLUMN_MONEY + " INTEGER," +
                DBContants.PersonData.COLUMN_NAME + " TEXT," +
                DBContants.PersonData.COLUMN_PHONE + " TEXT," +
                DBContants.PersonData.COLUMN_IS_PAID + " " + COMPLETE + " INTEGER DEFAULT 0" +
                ");";
        db.execSQL(dutchPerson);

        String DetaildutchPerson = "CREATE TABLE " + DBContants.DutchPerson.TABLE_NAME + "(" +
                DBContants.DutchPerson._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                DBContants.DutchPerson.COLUMN_DUTCH + " INTEGER," +
                DBContants.DutchPerson.COLUMN_EVENT + " INTEGER," +
                DBContants.DutchPerson.COLUMN_NAME + " TEXT NOT NULL," +
                DBContants.DutchPerson.COLUMN_PHONE + " TEXT," +
                DBContants.DutchPerson.COLUMN_MONEY + " INTEGER," +
                DBContants.DutchPerson.COLUMN_ATTENDED + " " + COMPLETE + " INTEGER DEFAULT 0" +
                ");";
        db.execSQL(DetaildutchPerson);


        String dutchEvent = "CREATE TABLE " + DBContants.DutchEvent.TABLE_NAME + "(" +
                DBContants.DutchEvent._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                DBContants.DutchEvent.COLUMN_DUTCH + " INTEGER," +
                DBContants.DutchEvent.COLUMN_TITLE + " TEXT," +
                DBContants.DutchEvent.COLUMN_MONEY + " INTEGER" +
                ");";
        db.execSQL(dutchEvent);

        String group = "CREATE TABLE " + DBContants.Group.TABLE_NAME + "(" +
                DBContants.Group._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                DBContants.Group.COLUMN_TITLE + " TEXT NOT NULL," +
                DBContants.Group.COLUMN_DATE + " TEXT," +
                DBContants.Group.COLUMN_ACCOUNT + " TEXT, " +
                DBContants.Group.COLUMN_MONEY + " INTEGER, " +
                DBContants.Group.COLUMN_TOTAL_MONEY + " INTEGER" +
                ");";
        db.execSQL(group);

        String member = "CREATE TABLE " + DBContants.GroupMember.TABLE_NAME + "(" +
                DBContants.GroupMember._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                DBContants.GroupMember.COLUMN_GROUP + " INTEGER," +
                DBContants.GroupMember.COLUMN_NAME + " TEXT," +
                DBContants.GroupMember.COLUMN_PHONE + " TEXT, " +
                DBContants.GroupMember.COLUMN_MONEY + " INTEGER, " +
                DBContants.GroupMember.COLUMN_PAID + " BOOLEAN" +
                ");";
        db.execSQL(member);

        String detailTrans = "CREATE TABLE " + DBContants.Transaction.TABLE_NAME + "(" +
                DBContants.Transaction._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                DBContants.Transaction.COLUMN_NAME + " INTEGER," +
                DBContants.Transaction.COLUMN_REPAY + " INTEGER," +
                DBContants.Transaction.COLUMN_REMAIN + " INTEGER," +
                DBContants.Transaction.COLUMN_TYPE + " INTEGER," +
                DBContants.Transaction.COLUMN_DATE + " TEXT" +
                ");";
        db.execSQL(detailTrans);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//
    }

    public Cursor getTransactionCursor(long id) {
        String columns[] = {DBContants.Transaction.TABLE_NAME + "." + DBContants.Transaction._ID,
                DBContants.AccountBook.COLUMN_NAME,
                DBContants.Transaction.COLUMN_NAME,
                DBContants.Transaction.COLUMN_REPAY,
                DBContants.Transaction.COLUMN_REMAIN,
                DBContants.Transaction.COLUMN_TYPE,
                DBContants.Transaction.COLUMN_DATE
        };
        String tableName = DBContants.Transaction.TABLE_NAME + " INNER JOIN " + DBContants.AccountBook.TABLE_NAME +
                " ON " + DBContants.Transaction.TABLE_NAME + "." + DBContants.Transaction.COLUMN_NAME +
                " = " + DBContants.AccountBook.TABLE_NAME + "." + DBContants.AccountBook._ID;

        String selection = DBContants.Transaction.COLUMN_NAME + " = ?";
        String[] selectionArgs = {"" + id};
        String orderBy = DBContants.Transaction.COLUMN_DATE + " ASC";
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(tableName, columns, selection, selectionArgs, null, null, orderBy);
        return c;
    }

    public List<DetailTransData> getTransactionList(long id) {
        Cursor c = getTransactionCursor(id);
        List<DetailTransData> list = new ArrayList<>();
        int nameIndex = c.getColumnIndex(DBContants.Transaction.COLUMN_NAME);
        int repayIndex = c.getColumnIndex(DBContants.Transaction.COLUMN_REPAY);
        int remainIndex = c.getColumnIndex(DBContants.Transaction.COLUMN_REMAIN);
        int typeIndex = c.getColumnIndex(DBContants.Transaction.COLUMN_TYPE);
        int dateIndex = c.getColumnIndex(DBContants.Transaction.COLUMN_DATE);
        while (c.moveToNext()) {
            DetailTransData data = new DetailTransData();
            data.name = c.getInt(nameIndex);
            data.repay = c.getInt(repayIndex);
            data.remain = c.getInt(remainIndex);
            data.type = c.getInt(typeIndex);
            data.date = c.getString(dateIndex);
            list.add(data);
        }
        c.close();
        return list;
    }

    public static final int TRANSACTION_ADD = 0;
    public static final int TRANSACTION_SUB = 1;
    public static final int TRANSACTION_COMPLETE = 2;

    public void insertTransaction(long id, int repay, int remain, int type, String date) {
        SQLiteDatabase db = getWritableDatabase();

        try {
            db.beginTransaction();
            values.clear();
            values.put(DBContants.Transaction.COLUMN_NAME, id);
            values.put(DBContants.Transaction.COLUMN_REPAY, repay);
            values.put(DBContants.Transaction.COLUMN_REMAIN, remain);
            values.put(DBContants.Transaction.COLUMN_TYPE, type);
            Calendar c = Calendar.getInstance();
            values.put(DBContants.Transaction.COLUMN_DATE, date);
            db.insert(DBContants.Transaction.TABLE_NAME, null, values);

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

//    public Cursor getSortingCursor(){
//        String columns[] = {DBContants.Transaction.TABLE_NAME + "." + DBContants.Transaction._ID,
//                DBContants.AccountBook.COLUMN_NAME,
//                DBContants.Transaction.COLUMN_NAME,
//                DBContants.Transaction.COLUMN_REPAY,
//                DBContants.Transaction.COLUMN_REMAIN,
//                DBContants.Transaction.COLUMN_TYPE,
//                DBContants.Transaction.COLUMN_DATE
//        };
//        String tableName = DBContants.Transaction.TABLE_NAME + " INNER JOIN " + DBContants.AccountBook.TABLE_NAME +
//                " ON " + DBContants.Transaction.TABLE_NAME + "." + DBContants.Transaction.COLUMN_NAME +
//                " = " + DBContants.AccountBook.TABLE_NAME + "." + DBContants.AccountBook._ID;
//
//        String selection = DBContants.Transaction.COLUMN_NAME + " = ?";
//        String[] selectionArgs = {"" + id};
//        String orderBy = DBContants.Transaction.COLUMN_DATE + " ASC";
//        SQLiteDatabase db = getReadableDatabase();
//        Cursor c = db.query(tableName, columns, selection, selectionArgs, null, null, orderBy);
//        return c;
//    }
//    public List<TransactionData> getSortingList(int type){
//        List<TransactionData> list = new ArrayList<TransactionData>();
//        Cursor c = getSortingCursor();
//        int idIndex = c.getColumnIndex(DBContants.AccountBook._ID);
//        int nameIndex = c.getColumnIndex(DBContants.AccountBook.COLUMN_NAME);
//        int moneyIndex = c.getColumnIndex(DBContants.AccountBook.COLUMN_MONEY);
//        int dateIndex = c.getColumnIndex(DBContants.AccountBook.COLUMN_DATE);
//        int memoIndex = c.getColumnIndex(DBContants.AccountBook.COLUMN_MEMO);
//        String tableName = DBContants.AccountBook.TABLE_NAME + " CROSS JOIN " + DBContants.Things.TABLE_NAME;
//        while (c.moveToNext()) {
//            AccountData data = new AccountData();
//            data._id = c.getLong(idIndex);
//            data.name = c.getString(nameIndex);
//            data.money = c.getInt(moneyIndex);
//            data.date = c.getString(dateIndex);
//            data.memo = c.getString(memoIndex);
//            list.add(data);
//        }
//        c.close();
//        return list;
//    }

    public Cursor getContractCursor(int type) {
        SQLiteDatabase db = getReadableDatabase();
        String[] columns = {DBContants.AccountBook.TABLE_NAME + "." + DBContants.AccountBook._ID,
                DBContants.AccountBook.COLUMN_NAME,
                DBContants.AccountBook.COLUMN_PHONE,
                DBContants.AccountBook.COLUMN_MONEY,
                DBContants.AccountBook.COLUMN_INTEREST,
                DBContants.AccountBook.COLUMN_DATE,
                DBContants.AccountBook.COLUMN_REPAY_DATE,
                DBContants.AccountBook.COLUMN_MEMO,
                DBContants.AccountBook.COLUMN_SIGN,
                DBContants.AccountBook.COLULMN_COMPLETE};

        String selection = null;
        String[] selectionArgs = null;
//        if (!TextUtils.isEmpty(keyword)) {
//            selection = DBContants.AccountBook.COLUMN_NAME + " LIKE ? OR "+ DBContants.AccountBook.COLUMN_DATE +" LIKE ?";
//            selectionArgs = new String[]{"%"+keyword+"%", "%" + keyword + "%"};
//        }
        String groupBy = null;
        String having = null;
        String orderBy = null;
        if (type == DBContants.SORT_TYPE_DATE) {
            orderBy = DBContants.AccountBook.COLUMN_DATE + " COLLATE LOCALIZED ASC";
        } else {
            orderBy = DBContants.AccountBook.COLUMN_MONEY + " COLLATE LOCALIZED DESC";
        }
        Cursor c = db.query(DBContants.AccountBook.TABLE_NAME, columns, selection, selectionArgs, groupBy, having, orderBy);
        return c;
    }

    public List<TransactionData> getContractList(int type) {
        List<TransactionData> list = new ArrayList<TransactionData>();
        Cursor c = getContractCursor(type);
        int idIndex = c.getColumnIndex(DBContants.AccountBook._ID);
        int nameIndex = c.getColumnIndex(DBContants.AccountBook.COLUMN_NAME);
        int phoneIndex = c.getColumnIndex(DBContants.AccountBook.COLUMN_PHONE);
        int moneyIndex = c.getColumnIndex(DBContants.AccountBook.COLUMN_MONEY);
        int interestIndex = c.getColumnIndex(DBContants.AccountBook.COLUMN_INTEREST);
        int dateIndex = c.getColumnIndex(DBContants.AccountBook.COLUMN_DATE);
        int repayDateIndex = c.getColumnIndex(DBContants.AccountBook.COLUMN_REPAY_DATE);
        int memoIndex = c.getColumnIndex(DBContants.AccountBook.COLUMN_MEMO);
        int signIndex = c.getColumnIndex(DBContants.AccountBook.COLUMN_SIGN);
        int completeIndex = c.getColumnIndex(DBContants.AccountBook.COLULMN_COMPLETE);

        while (c.moveToNext()) {
            AccountData data = new AccountData();
            data._id = c.getLong(idIndex);
            data.name = c.getString(nameIndex);
            data.phone = c.getString(phoneIndex);
            data.money = c.getInt(moneyIndex);
            data.interest = c.getInt(interestIndex);
            data.date = c.getLong(dateIndex);
            data.repayDate = c.getString(repayDateIndex);
            data.memo = c.getString(memoIndex);
            data.sign = c.getBlob(signIndex);
            if (c.getInt(completeIndex) != 0) {
                data.isCompleted = true;
            } else {
                data.isCompleted = false;
            }
            list.add(data);
        }
        c.close();
        return list;
    }

    public Cursor getContractThingsCursor() {
        SQLiteDatabase db = getReadableDatabase();
        String[] columns = {DBContants.Things.TABLE_NAME + "." + DBContants.AccountBook._ID,
                DBContants.Things.COLUMN_NAME,
                DBContants.Things.COLUMN_PHONE,
                DBContants.Things.COLUMN_THINGS,
                DBContants.Things.COLUMN_DATE,
                DBContants.Things.COLUMN_REPAY_DATE,
                DBContants.Things.COLUMN_MEMO,
                DBContants.Things.COLUMN_SIGN,
                DBContants.Things.COLUMN_PICTURE,
                DBContants.Things.COLULMN_COMPLETE};

        String selection = null;
        String[] selectionArgs = null;
        String groupBy = null;
        String having = null;
        String orderBy = DBContants.Things.COLUMN_DATE + " COLLATE LOCALIZED ASC";

        Cursor c = db.query(DBContants.Things.TABLE_NAME, columns, selection, selectionArgs, groupBy, having, orderBy);
        return c;
    }

    public List<TransactionData> getContractThingsList() {
        List<TransactionData> list = new ArrayList<>();
        Cursor c = getContractThingsCursor();
        int idIndex = c.getColumnIndex(DBContants.Things._ID);
        int nameIndex = c.getColumnIndex(DBContants.Things.COLUMN_NAME);
        int phoneIndex = c.getColumnIndex(DBContants.Things.COLUMN_PHONE);
        int thingsIndex = c.getColumnIndex(DBContants.Things.COLUMN_THINGS);
        int dateIndex = c.getColumnIndex(DBContants.Things.COLUMN_DATE);
        int repayDateIndex = c.getColumnIndex(DBContants.Things.COLUMN_REPAY_DATE);
        int memoIndex = c.getColumnIndex(DBContants.Things.COLUMN_MEMO);
        int signIndex = c.getColumnIndex(DBContants.Things.COLUMN_SIGN);
        int pictureIndex = c.getColumnIndex(DBContants.Things.COLUMN_PICTURE);
        int completeIndex = c.getColumnIndex(DBContants.Things.COLULMN_COMPLETE);
        while (c.moveToNext()) {
            ThingsData data = new ThingsData();
            data._id = c.getLong(idIndex);
            data.borrowerName = c.getString(nameIndex);
            data.thingsName = c.getString(thingsIndex);
            data.date = c.getLong(dateIndex);
            data.repayDate = c.getString(repayDateIndex);
            data.memo = c.getString(memoIndex);
            data.sign = c.getBlob(signIndex);
            data.picture = c.getBlob(pictureIndex);
            if (c.getInt(completeIndex) != 0) {
                data.isCompleted = true;
            } else {
                data.isCompleted = false;
            }
            list.add(data);
        }
        c.close();
        return list;
    }

    ContentValues values = new ContentValues();

    public void insertContract(AccountData data) {
        if (data._id == AccountData.INVALID_ID) {
            SQLiteDatabase db = getWritableDatabase();
            values.clear();
            values.put(DBContants.AccountBook.COLUMN_NAME, data.name);
            values.put(DBContants.AccountBook.COLUMN_PHONE,data.phone);
            values.put(DBContants.AccountBook.COLUMN_MONEY, data.money);
            values.put(DBContants.AccountBook.COLUMN_INTEREST, data.interest);
            Calendar c = Calendar.getInstance();
            values.put(DBContants.AccountBook.COLUMN_DATE, c.getTimeInMillis());
            values.put(DBContants.AccountBook.COLUMN_REPAY_DATE, data.repayDate);
            values.put(DBContants.AccountBook.COLUMN_MEMO, data.memo);
            values.put(DBContants.AccountBook.COLUMN_SIGN, data.sign);
            if (data.isCompleted) {
                values.put(DBContants.AccountBook.COLULMN_COMPLETE, 1);
            }
            db.insert(DBContants.AccountBook.TABLE_NAME, null, values);
        } else {
            updateContract(data);
        }
    }

    public void insertContractThings(ThingsData data) {
        if (data._id == ThingsData.INVALID_ID) {
            SQLiteDatabase db = getWritableDatabase();
            values.clear();
            values.put(DBContants.Things.COLUMN_NAME, data.borrowerName);
            values.put(DBContants.Things.COLUMN_PHONE,data.phone);
            values.put(DBContants.Things.COLUMN_THINGS, data.thingsName);
            Calendar c = Calendar.getInstance();
            values.put(DBContants.Things.COLUMN_DATE, c.getTimeInMillis());
            values.put(DBContants.Things.COLUMN_REPAY_DATE, data.repayDate);
            values.put(DBContants.Things.COLUMN_MEMO, data.memo);
            values.put(DBContants.Things.COLUMN_SIGN, data.sign);
            values.put(DBContants.Things.COLUMN_PICTURE, data.picture);
            if (data.isCompleted) {
                values.put(DBContants.Things.COLULMN_COMPLETE, 1);
            }
            db.insert(DBContants.Things.TABLE_NAME, null, values);
        } else {
            updateContractThings(data);
        }
    }

    public void insertDutchPay(DutchPayData data) {
        if (data._id == DutchPayData.INVALID_ID) {
            SQLiteDatabase db = getWritableDatabase();
            values.clear();
            values.put(DBContants.DutchPay.COLUMN_TITLE, data.title);
            values.put(DBContants.DutchPay.COLUMN_MONEY, data.totalPrice);
            Calendar c = Calendar.getInstance();
            values.put(DBContants.DutchPay.COLUMN_DATE, c.getTimeInMillis());
            if (data.isCompleted) {
                values.put(DBContants.DutchPay.COLULMN_COMPLETE, 1);
            }
//            values.put(DBContants.DutchPay.COLUMN_PEOPLE, data.)
            db.insert(DBContants.DutchPay.TABLE_NAME, null, values);
        } else {
            updateDutchPay(data);
        }
    }

    public void updateDutchPay(DutchPayData data) {
        if (data._id == DutchPayData.INVALID_ID) {
            insertDutchPay(data);
            return;
        }
        SQLiteDatabase db = getWritableDatabase();
        values.clear();
        values.put(DBContants.DutchPay.COLUMN_TITLE, data.title);
        values.put(DBContants.DutchPay.COLUMN_MONEY, data.totalPrice);
        values.put(DBContants.DutchPay.COLUMN_DATE, data.date);
        if (data.isCompleted) {
            values.put(DBContants.DutchPay.COLULMN_COMPLETE, 1);
        }
        String where = DBContants.DutchPay._ID + " = ?";
        String[] args = new String[]{"" + data._id};

        db.update(DBContants.DutchPay.TABLE_NAME, values, where, args);
    }

    public Cursor getDutchPayCursor() {
        SQLiteDatabase db = getReadableDatabase();
        String[] columns = {DBContants.DutchPay.TABLE_NAME + "." + DBContants.DutchPay._ID,
                DBContants.DutchPay.COLUMN_TITLE,
                DBContants.DutchPay.COLUMN_MONEY,
                DBContants.DutchPay.COLULMN_COMPLETE,
                DBContants.DutchPay.COLUMN_DATE};

        String selection = null;
        String[] selectionArgs = null;
        String groupBy = null;
        String having = null;
        String orderBy = DBContants.DutchPay.COLUMN_DATE + " COLLATE LOCALIZED ASC";

        Cursor c = db.query(DBContants.DutchPay.TABLE_NAME, columns, selection, selectionArgs, groupBy, having, orderBy);
        return c;
    }

    public List<TransactionData> getDutchPayList() {
        List<TransactionData> list = new ArrayList<>();
        Cursor c = getDutchPayCursor();
        int idIndex = c.getColumnIndex(DBContants.DutchPay._ID);
        int titleIndex = c.getColumnIndex(DBContants.DutchPay.COLUMN_TITLE);
        int moneyIndex = c.getColumnIndex(DBContants.DutchPay.COLUMN_MONEY);
        int completeIndex = c.getColumnIndex(DBContants.DutchPay.COLULMN_COMPLETE);
        int dateIndex = c.getColumnIndex(DBContants.DutchPay.COLUMN_DATE);

        while (c.moveToNext()) {
            DutchPayData data = new DutchPayData();
            data._id = c.getLong(idIndex);
            data.title = c.getString(titleIndex);
            data.totalPrice = c.getInt(moneyIndex);
            if (c.getInt(completeIndex) > 0) {
                data.isCompleted = true;
            } else {
                data.isCompleted = false;
            }
            data.date = c.getLong(dateIndex);

            list.add(data);
        }
        c.close();
        return list;
    }


    public void insertDutchPayPerson(long id, PersonData data) {
        SQLiteDatabase db = getWritableDatabase();
        if (data._id == PersonData.INVALID_ID) {
            try {
                db.beginTransaction();
                values.clear();
                values.put(DBContants.PersonData.COLUMN_DUTCHPAY, id);
                values.put(DBContants.PersonData.COLUMN_NAME, data.getName());
                values.put(DBContants.PersonData.COLUMN_PHONE,data.getPhone());
                values.put(DBContants.PersonData.COLUMN_MONEY, data.getMoney());
                if (data.isPay()) {
                    values.put(DBContants.PersonData.COLUMN_IS_PAID, 1);
                }

                db.insert(DBContants.PersonData.TABLE_NAME, null, values);

                db.setTransactionSuccessful();
            } finally {
                db.endTransaction();
            }
        } else {
            updateDutchPayPerson(id, data);
        }
    }

    public void updateDutchPayPerson(long id, PersonData data) {
        if (data._id == PersonData.INVALID_ID) {
            insertDutchPayPerson(id, data);
            return;
        }
        SQLiteDatabase db = getWritableDatabase();
        try {
            db.beginTransaction();
            values.clear();
            values.put(DBContants.PersonData.COLUMN_DUTCHPAY, id);
            values.put(DBContants.PersonData.COLUMN_NAME, data.getName());
            values.put(DBContants.PersonData.COLUMN_MONEY, data.getMoney());
            if (data.isPay()) {
                values.put(DBContants.PersonData.COLUMN_IS_PAID, 1);
            }

            String where = DBContants.PersonData._ID + " = ?";
            String[] args = new String[]{"" + data._id};
            db.update(DBContants.PersonData.TABLE_NAME, values, where, args);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    public Cursor getDutchPersonCursor(long id) {
        String columns[] = {DBContants.PersonData.TABLE_NAME + "." + DBContants.PersonData._ID,
                DBContants.PersonData.COLUMN_DUTCHPAY,
                DBContants.PersonData.COLUMN_NAME,
                DBContants.PersonData.COLUMN_PHONE,
                DBContants.PersonData.COLUMN_MONEY,
                DBContants.PersonData.COLUMN_IS_PAID
        };
        String tableName = DBContants.PersonData.TABLE_NAME + " INNER JOIN " + DBContants.DutchPay.TABLE_NAME +
                " ON " + DBContants.PersonData.TABLE_NAME + "." + DBContants.PersonData.COLUMN_DUTCHPAY +
                " = " + DBContants.DutchPay.TABLE_NAME + "." + DBContants.DutchPay._ID;

        String selection = DBContants.PersonData.COLUMN_DUTCHPAY + " = ?";
        String[] selectionArgs = {"" + id};
        String orderBy = null;
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(tableName, columns, selection, selectionArgs, null, null, orderBy);
        return c;
    }

    public List<PersonData> getDutchPersonList(long id) {
        Cursor c = getDutchPersonCursor(id);
        List<PersonData> list = new ArrayList<>();
        int idindex = c.getColumnIndex(DBContants.PersonData._ID);
        int dutchPayIndex = c.getColumnIndex(DBContants.PersonData.COLUMN_DUTCHPAY);
        int nameIndex = c.getColumnIndex(DBContants.PersonData.COLUMN_NAME);
        int phoneIndex = c.getColumnIndex(DBContants.PersonData.COLUMN_PHONE);
        int moneyIndex = c.getColumnIndex(DBContants.PersonData.COLUMN_MONEY);
        int paidIndex = c.getColumnIndex(DBContants.PersonData.COLUMN_IS_PAID);
        while (c.moveToNext()) {
            PersonData data = new PersonData();
            data._id = c.getInt(idindex);
            data.group = c.getInt(dutchPayIndex);
            data.setName(c.getString(nameIndex));
            data.setPhone(c.getString(phoneIndex));
            data.setMoney(c.getInt(moneyIndex));
            data.setIsPay(c.getInt(paidIndex) > 0);
            list.add(data);
        }
        c.close();
        return list;
    }

    public void insertEvent(long id, EventData data) {
        SQLiteDatabase db = getWritableDatabase();
        if (data._id == EventData.INVALID_ID) {
            try {
                db.beginTransaction();
                values.clear();
                values.put(DBContants.DutchEvent.COLUMN_DUTCH, id);
                values.put(DBContants.DutchEvent.COLUMN_TITLE, data.title);
                values.put(DBContants.DutchEvent.COLUMN_MONEY, data.money);
                db.insert(DBContants.DutchEvent.TABLE_NAME, null, values);

                db.setTransactionSuccessful();
            } finally {
                db.endTransaction();
            }
        } else {
            updateEvent(id, data);
        }
    }

    public void updateEvent(long id, EventData data) {
        if (data._id == EventData.INVALID_ID) {
            insertEvent(id, data);
            return;
        }
        SQLiteDatabase db = getWritableDatabase();
        try {
            db.beginTransaction();
            values.clear();
            values.put(DBContants.DutchEvent.COLUMN_DUTCH, id);
            values.put(DBContants.DutchEvent.COLUMN_TITLE, data.title);
            values.put(DBContants.DutchEvent.COLUMN_MONEY, data.money);

            String where = DBContants.DutchEvent._ID + " = ?";
            String[] args = new String[]{"" + data._id};
            db.update(DBContants.DutchEvent.TABLE_NAME, values, where, args);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    public Cursor getDutchEventCursor(long id) {
        String columns[] = {DBContants.DutchEvent.TABLE_NAME + "." + DBContants.DutchEvent._ID,
                DBContants.DutchEvent.COLUMN_TITLE,
                DBContants.DutchEvent.COLUMN_MONEY
        };
        String tableName = DBContants.DutchEvent.TABLE_NAME + " INNER JOIN " + DBContants.DutchPay.TABLE_NAME +
                " ON " + DBContants.DutchEvent.TABLE_NAME + "." + DBContants.DutchEvent.COLUMN_DUTCH +
                " = " + DBContants.DutchPay.TABLE_NAME + "." + DBContants.DutchPay._ID;

        String selection = DBContants.DutchEvent.COLUMN_DUTCH + " = ?";
        String[] selectionArgs = {"" + id};
        String orderBy = null;
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(tableName, columns, selection, selectionArgs, null, null, orderBy);
        return c;
    }

    public List<EventData> getDutchEventList(long id) {
        Cursor c = getDutchEventCursor(id);
        List<EventData> list = new ArrayList<>();
        int idIndex = c.getColumnIndex(DBContants.DutchEvent._ID);
        int titleIndex = c.getColumnIndex(DBContants.DutchEvent.COLUMN_TITLE);
        int moneyIndex = c.getColumnIndex(DBContants.DutchEvent.COLUMN_MONEY);

        while (c.moveToNext()) {
            EventData data = new EventData();
            data._id = c.getInt(idIndex);
            data.title = c.getString(titleIndex);
            data.money = c.getInt(moneyIndex);
            list.add(data);
        }
        c.close();
        return list;
    }

    public void insertAttentedPerson(long dutchPayId, long eventId, DutchPersonData data) {
        SQLiteDatabase db = getWritableDatabase();
        if (data._id == DutchPersonData.INVALID_ID) {
            try {
                db.beginTransaction();
                values.clear();
                values.put(DBContants.DutchPerson.COLUMN_DUTCH, dutchPayId);
                values.put(DBContants.DutchPerson.COLUMN_EVENT, eventId);
                values.put(DBContants.DutchPerson.COLUMN_NAME, data.name);
                values.put(DBContants.DutchPerson.COLUMN_PHONE, data.phone);
                values.put(DBContants.DutchPerson.COLUMN_MONEY, data.dutchMoney);
                if (data.attended) {
                    values.put(DBContants.DutchPerson.COLUMN_ATTENDED, 1);
                } else {
                    values.put(DBContants.DutchPerson.COLUMN_ATTENDED, 0);
                }

                db.insert(DBContants.DutchPerson.TABLE_NAME, null, values);

                db.setTransactionSuccessful();
            } finally {
                db.endTransaction();
            }
        } else {
            updateAttendedPerson(dutchPayId, eventId, data);
        }
    }

    public void updateAttendedPerson(long dutchPayId, long eventId, DutchPersonData data) {
        if (data._id == DutchPersonData.INVALID_ID) {
            insertAttentedPerson(dutchPayId, eventId, data);
            return;
        }
        SQLiteDatabase db = getWritableDatabase();
        try {
            db.beginTransaction();
            values.clear();
            values.put(DBContants.DutchPerson.COLUMN_DUTCH, dutchPayId);
            values.put(DBContants.DutchPerson.COLUMN_EVENT, eventId);
            values.put(DBContants.DutchPerson.COLUMN_NAME, data.name);
            values.put(DBContants.DutchPerson.COLUMN_PHONE, data.phone);
            values.put(DBContants.DutchPerson.COLUMN_MONEY, data.dutchMoney);
            if (data.attended) {
                values.put(DBContants.DutchPerson.COLUMN_ATTENDED, 1);
            } else {
                values.put(DBContants.DutchPerson.COLUMN_ATTENDED, 0);
            }
            String where = DBContants.DutchPerson._ID + " = ?";
            String[] args = new String[]{"" + data._id};
            db.update(DBContants.DutchPerson.TABLE_NAME, values, where, args);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    public Cursor getAttendedCursor(long dutchId, long eventId) {
        String columns[] = {DBContants.DutchPerson.TABLE_NAME + "." + DBContants.DutchPerson._ID,
                DBContants.DutchPerson.COLUMN_EVENT,
                DBContants.DutchPerson.COLUMN_NAME,
                DBContants.DutchPerson.COLUMN_PHONE,
                DBContants.DutchPerson.COLUMN_MONEY,
                DBContants.DutchPerson.COLUMN_ATTENDED
        };
        String tableName = DBContants.DutchPerson.TABLE_NAME + " INNER JOIN " + DBContants.DutchEvent.TABLE_NAME +
                " ON " + DBContants.DutchPerson.TABLE_NAME + "." + DBContants.DutchPerson.COLUMN_EVENT +
                " = " + DBContants.DutchEvent.TABLE_NAME + "." + DBContants.DutchEvent._ID
                + " INNER JOIN " + DBContants.DutchPay.TABLE_NAME + " ON " + DBContants.DutchPerson.TABLE_NAME + "." + DBContants.DutchPerson.COLUMN_DUTCH
                + " = " + DBContants.DutchPay.TABLE_NAME + "." + DBContants.DutchPay._ID;

        String selection = DBContants.DutchPerson.COLUMN_EVENT + " = ?";
        String[] selectionArgs = {"" + eventId};
        String orderBy = null;
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(tableName, columns, selection, selectionArgs, null, null, orderBy);
        return c;
    }

    public List<DutchPersonData> getAttendedList(long dutchPayId, long eventId) {
        Cursor c = getAttendedCursor(dutchPayId, eventId);
        List<DutchPersonData> list = new ArrayList<>();
        int nameIndex = c.getColumnIndex(DBContants.DutchPerson.COLUMN_NAME);
        int phoneIndex = c.getColumnIndex(DBContants.DutchPerson.COLUMN_PHONE);
        int moneyIndex = c.getColumnIndex(DBContants.DutchPerson.COLUMN_MONEY);
        int attendedIndex = c.getColumnIndex(DBContants.DutchPerson.COLUMN_ATTENDED);

        while (c.moveToNext()) {
            DutchPersonData data = new DutchPersonData();
            data.name = c.getString(nameIndex);
            data.phone = c.getString(phoneIndex);
            data.dutchMoney = c.getInt(moneyIndex);
            if (c.getInt(attendedIndex) > 0) {
                data.attended = true;
            } else {
                data.attended = false;
            }
            list.add(data);
        }
        c.close();
        return list;
    }

    public void updateContract(AccountData data) {
        if (data._id == AccountData.INVALID_ID) {
            insertContract(data);
            return;
        }
        SQLiteDatabase db = getWritableDatabase();
        values.clear();
        values.put(DBContants.AccountBook.COLUMN_NAME, data.name);
        values.put(DBContants.AccountBook.COLUMN_MONEY, data.money);
        values.put(DBContants.AccountBook.COLUMN_INTEREST, data.interest);
        values.put(DBContants.AccountBook.COLUMN_DATE, data.date);
        values.put(DBContants.AccountBook.COLUMN_REPAY_DATE, data.repayDate);
        values.put(DBContants.AccountBook.COLUMN_MEMO, data.memo);
        values.put(DBContants.AccountBook.COLUMN_SIGN, data.sign);
        if (data.isCompleted) {
            values.put(DBContants.AccountBook.COLULMN_COMPLETE, 1);
        } else {
            values.put(DBContants.AccountBook.COLULMN_COMPLETE, 0);
        }
        String where = DBContants.AccountBook._ID + " = ?";
        String[] args = new String[]{"" + data._id};
        db.update(DBContants.AccountBook.TABLE_NAME, values, where, args);
    }

    public void updateContractThings(ThingsData data) {
        if (data._id == ThingsData.INVALID_ID) {
            insertContractThings(data);
            return;
        }
        SQLiteDatabase db = getWritableDatabase();
        values.clear();
        values.put(DBContants.Things.COLUMN_NAME, data.borrowerName);
        values.put(DBContants.Things.COLUMN_THINGS, data.thingsName);
        values.put(DBContants.Things.COLUMN_DATE, data.date);
        values.put(DBContants.Things.COLUMN_REPAY_DATE, data.repayDate);
        values.put(DBContants.Things.COLUMN_MEMO, data.memo);
        values.put(DBContants.Things.COLUMN_SIGN, data.sign);
        values.put(DBContants.Things.COLUMN_PICTURE, data.picture);
        if (data.isCompleted) {
            values.put(DBContants.Things.COLULMN_COMPLETE, 1);
        } else {
            values.put(DBContants.Things.COLULMN_COMPLETE, 0);
        }
        String where = DBContants.Things._ID + " = ?";
        String[] args = new String[]{"" + data._id};
        db.update(DBContants.Things.TABLE_NAME, values, where, args);
    }


    public void deleteContract(AccountData data) {
        if (data._id == AccountData.INVALID_ID) {
            return;
        }
        SQLiteDatabase db = getWritableDatabase();
        String where = DBContants.AccountBook._ID + " = ?";
        String[] args = new String[]{"" + data._id};
        db.delete(DBContants.AccountBook.TABLE_NAME, where, args);
    }

    public void deleteThingsContract(ThingsData data) {
        if (data._id == ThingsData.INVALID_ID) {
            return;
        }
        SQLiteDatabase db = getWritableDatabase();
        String where = DBContants.Things._ID + " = ?";
        String[] args = new String[]{"" + data._id};
        db.delete(DBContants.Things.TABLE_NAME, where, args);
    }

    public void deleteDutchData(DutchPayData data) {
        if (data._id == DutchPayData.INVALID_ID) {
            return;
        }
        SQLiteDatabase db = getWritableDatabase();
        String where = DBContants.DutchPay._ID + " = ?";
        String[] args = new String[]{"" + data._id};
        db.delete(DBContants.DutchPay.TABLE_NAME, where, args);
    }

}
