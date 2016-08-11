package rnd.plani.co.kr.whenyourepay.Intro;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import io.realm.Realm;
import rnd.plani.co.kr.whenyourepay.MyProfile;
import rnd.plani.co.kr.whenyourepay.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConfirmInfoFragment extends Fragment {


    public ConfirmInfoFragment() {
        // Required empty public constructor
    }


    Realm mRealm;
    TextView nameView, bankView, accountView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_confirm_info, container, false);
        nameView= (TextView)view.findViewById(R.id.text_name);
        bankView = (TextView)view.findViewById(R.id.text_bank_account);
        accountView = (TextView)view.findViewById(R.id.text_account);

        mRealm = Realm.getInstance(getContext());
        MyProfile profile = mRealm.where(MyProfile.class).findFirst();

        nameView.setText(profile.getName());
        bankView.setText(profile.getBank());
        accountView.setText(profile.getAccount());

        Button btn = (Button)view.findViewById(R.id.btn_back);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .popBackStack();
            }
        });

        btn = (Button)view.findViewById(R.id.btn_return);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((IntroActivity)getActivity()).changeSign();
            }
        });
        return view;
    }

}
