package rnd.plani.co.kr.whenyourepay.Manager;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import rnd.plani.co.kr.whenyourepay.MyApplication;


/**
 * Created by RND on 2016-04-06.
 */
public class PropertyManager {
    private static PropertyManager instance;
    public static PropertyManager getInstance(){
        if(instance == null){
            instance = new PropertyManager();
        }
        return instance;
    }

    SharedPreferences mPrefs;
    SharedPreferences.Editor mEditor;

    private PropertyManager(){
        mPrefs = PreferenceManager.getDefaultSharedPreferences(MyApplication.getContext());
        mEditor = mPrefs.edit();
    }

    private static final String REG_USER = "user";

    public void setUser(boolean isUser){
        mEditor.putBoolean(REG_USER,isUser);
        mEditor.commit();
    }
    public boolean isUser(){
        return mPrefs.getBoolean(REG_USER,false);
    }

    private static final String REG_NAME = "name";
    public void setName(String name){
        mEditor.putString(REG_NAME,name);
        mEditor.commit();
    }
    public String getName(){
        return mPrefs.getString(REG_NAME,"");
    }

    private static final String REG_PHONE = "phone";
    public void setPhone(String phone){
        mEditor.putString(REG_PHONE,phone);
        mEditor.commit();
    }
    public String getPhone(){
        return mPrefs.getString(REG_PHONE,"");
    }
}
