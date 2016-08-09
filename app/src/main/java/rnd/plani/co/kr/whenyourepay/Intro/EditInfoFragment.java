package rnd.plani.co.kr.whenyourepay.Intro;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import io.realm.Realm;
import rnd.plani.co.kr.whenyourepay.MyProfile;
import rnd.plani.co.kr.whenyourepay.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class EditInfoFragment extends Fragment {

    public EditInfoFragment() {
        // Required empty public constructor
    }

    ImageView introView;
    EditText nameView, accountView;
    Realm mRealm;
    Spinner spinner;
    ArrayAdapter<String> mAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_intro, container, false);
        introView = (ImageView) view.findViewById(R.id.image_intro);
        nameView = (EditText) view.findViewById(R.id.text_name);
        accountView = (EditText) view.findViewById(R.id.text_account);

        spinner = (Spinner)view.findViewById(R.id.spinner_bank);
        mAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_dropdown_item,getResources().getStringArray(R.array.bank));
        spinner.setAdapter(mAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(), mAdapter.getItem(position), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mRealm = Realm.getInstance(getContext());

        Button btn = (Button) view.findViewById(R.id.btn_done);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(nameView.getText().toString())) {

                    mRealm.beginTransaction();
                    MyProfile profile = mRealm.createObject(MyProfile.class);
                    profile.setName(nameView.getText().toString());
                    profile.setBank(spinner.getSelectedItem().toString());
                    profile.setAccount(accountView.getText().toString());
                    mRealm.commitTransaction();
                    ((IntroActivity) getActivity()).changeConfirm();
                } else {
                    Toast.makeText(getContext(), "필수정보를 입력하세요", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }
}
