package rnd.plani.co.kr.whenyourepay;

import io.realm.RealmObject;

/**
 * Created by RND on 2016-06-16.
 */
public class MyProfile extends RealmObject {
    private String name;
    private String bank;
    private String account;
    private byte[] signature;

    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return name;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getBank() {
        return bank;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getAccount() {
        return account;
    }

    public void setSignature(byte[] byteArray){
        signature = byteArray;
    }
    public byte[] getSignature(){
        return signature;
    }
}
