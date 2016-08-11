package rnd.plani.co.kr.whenyourepay.BorrowMoney;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import rnd.plani.co.kr.whenyourepay.Contact.SingleContactActivity;
import rnd.plani.co.kr.whenyourepay.Data.AccountData;
import rnd.plani.co.kr.whenyourepay.Data.PersonData;
import rnd.plani.co.kr.whenyourepay.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContractFragment extends Fragment {


    public ContractFragment() {
        // Required empty public constructor
    }

    EditText priceView, memoView;
    AccountData accountData;

    public static final String EXTRA_RESULT = "result";
    private static final int REQUEST_CONTACT = 100;

    View view;
    InputMethodManager imm;
    TextView nameView, dateView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if(view!=null){
            if((ViewGroup)view.getParent()!=null)
                ((ViewGroup)view.getParent()).removeView(view);
            return view;
        }
        view = inflater.inflate(R.layout.fragment_contract, container, false);
        imm = (InputMethodManager)view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        accountData = new AccountData();
        nameView = (TextView)view.findViewById(R.id.text_name);
        dateView = (TextView)view.findViewById(R.id.text_desc);
        priceView = (EditText) view.findViewById(R.id.edit_money);
        memoView = (EditText) view.findViewById(R.id.edit_memo);

        nameView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imm.hideSoftInputFromWindow(view.getWindowToken(),0);
                startActivityForResult(new Intent(getContext(), SingleContactActivity.class), REQUEST_CONTACT);
            }
        });

        dateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imm.hideSoftInputFromWindow(view.getWindowToken(),0);
                final Calendar c = Calendar.getInstance();
                DatePickerDialog pickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        c.set(year, monthOfYear, dayOfMonth);
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 MM월 dd일");
                        dateView.setText(sdf.format(c.getTime()));
                        accountData.repayDate = sdf.format(c.getTime());
                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
                pickerDialog.show();
            }
        });
        Button btn = (Button) view.findViewById(R.id.btn_return);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(priceView.getText().toString()) && !TextUtils.isEmpty(accountData.name) && !TextUtils.isEmpty(accountData.getRepayDate())) {
                    accountData.money = Integer.parseInt(priceView.getText().toString());
                    accountData.memo = memoView.getText().toString();
                    accountData.repayDate = dateView.getText().toString();
                    ((LendMoneyActivity) getActivity()).changeSignature(accountData);
                } else {
                    Toast.makeText(getContext(), "필수정보를 입력해주세요", Toast.LENGTH_SHORT).show();
                }
            }
        });

//        btn = (Button) view.findViewById(R.id.btn_contact);
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                Intent intent = new Intent(Intent.ACTION_PICK);
////                intent.setData(ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
////                startActivityForResult(intent, REQUEST_CONTACT);
//                Intent i = new Intent(getContext(), SingleContactActivity.class);
//                i.putExtra(SingleContactActivity.EXTRA_TYPE, SingleContactActivity.TYPE_MONEY);
//                startActivityForResult(i, REQUEST_CONTACT);
//            }
//        });
//
//        btn = (Button) view.findViewById(R.id.btn_direct);
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                DirectlyEditDialog dialog = new DirectlyEditDialog();
//                dialog.setOnButtonClickListener(new OnButtonClickListener() {
//                    @Override
//                    public void OnButtonClick(String name, String phone) {
//                    }
//                });
//                dialog.show(getActivity().getSupportFragmentManager(), "dialog");
//            }
//        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CONTACT && resultCode != Activity.RESULT_CANCELED) {
//            Cursor cursor = getActivity().getContentResolver().query(data.getData(),
//                    new String[]{ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
//                            ContactsContract.CommonDataKinds.Phone.NUMBER}, null, null, null);
//            cursor.moveToFirst();
//            String name = cursor.getString(0);        //0은 이름을 얻어옵니다.
//            accountData.name = name;
//            personView.setName(name);
//            contactSwitcher.showNext();
//            cursor.close();
            PersonData personData = (PersonData)data.getSerializableExtra(EXTRA_RESULT);
            accountData.name = personData.getName();
            accountData.phone = personData.getPhone();
            nameView.setText(personData.getName()+" ("+personData.getPhone()+")");
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void onStart() {
        super.onStart();
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    getActivity().finish();
                    return true;
                } else {
                    return false;
                }
            }
        });
    }
}
