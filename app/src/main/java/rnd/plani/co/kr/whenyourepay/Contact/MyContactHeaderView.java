package rnd.plani.co.kr.whenyourepay.Contact;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.TextView;

import io.realm.Realm;
import rnd.plani.co.kr.whenyourepay.MyProfile;
import rnd.plani.co.kr.whenyourepay.R;

/**
 * Created by RND on 2016-07-13.
 */
public class MyContactHeaderView extends FrameLayout {
    public interface OnCheckedListener{
        public void OnChecked(boolean isChecked);
    }

    public OnCheckedListener checkedListener;
    public void setOnCheckedListener(OnCheckedListener listener){
        checkedListener = listener;
    }

    TextView nameView, bankAccountView, phoneView;
    Realm mRealm;
    CheckBox checkBox;


    public MyContactHeaderView(Context context) {
        super(context);
        inflate(getContext(), R.layout.view_mycontact_header, this);
        nameView = (TextView) findViewById(R.id.text_name);
        bankAccountView = (TextView) findViewById(R.id.text_bank_account);
        phoneView = (TextView) findViewById(R.id.text_phone);
        checkBox = (CheckBox) findViewById(R.id.checkBox);

        mRealm = Realm.getInstance(getContext());
        nameView.setText(mRealm.where(MyProfile.class).findFirst().getName());
        bankAccountView.setText(mRealm.where(MyProfile.class).findFirst().getBank() + "/" + mRealm.where(MyProfile.class).findFirst().getAccount());
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(checkedListener!= null){
                    checkedListener.OnChecked(isChecked);
                }
            }
        });
    }

    public MyContactHeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate(getContext(), R.layout.view_mycontact_header, this);
        nameView = (TextView) findViewById(R.id.text_name);
        bankAccountView = (TextView) findViewById(R.id.text_bank_account);
        phoneView = (TextView) findViewById(R.id.text_phone);
        checkBox = (CheckBox) findViewById(R.id.checkBox);

        mRealm = Realm.getInstance(getContext());
        nameView.setText(mRealm.where(MyProfile.class).findFirst().getName());
        bankAccountView.setText(mRealm.where(MyProfile.class).findFirst().getBank() + "/" + mRealm.where(MyProfile.class).findFirst().getAccount());
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (checkedListener != null) {
                    checkedListener.OnChecked(isChecked);
                }
            }
        });
    }

    public void setPhoneNumber(String number){
        phoneView.setText(number);
    }
}


