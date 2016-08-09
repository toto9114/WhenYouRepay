package rnd.plani.co.kr.whenyourepay.Data;

import java.io.Serializable;

/**
 * Created by RND on 2016-06-23.
 */
public class PersonData implements Serializable{
    public static final int INVALID_ID = -1;
    public int _id = INVALID_ID;
    public int group;
    private String name;
    private String phone;
    private int money;
    private boolean isPay;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public boolean isPay() {
        return isPay;
    }

    public void setIsPay(boolean isPay) {
        this.isPay = isPay;
    }


}
