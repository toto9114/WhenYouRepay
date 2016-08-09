package rnd.plani.co.kr.whenyourepay.Contact;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import rnd.plani.co.kr.whenyourepay.Data.DutchPayData;
import rnd.plani.co.kr.whenyourepay.Data.PersonData;
import rnd.plani.co.kr.whenyourepay.DutchPay.RegistFragment;
import rnd.plani.co.kr.whenyourepay.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DirectFragment extends Fragment {

    public interface OnDirectFragmentListener {
        public void OnDirectFragment(PersonData data);
    }

    public OnDirectFragmentListener directFragmentListener;

    public void setOnDirectFragmentListener(OnDirectFragmentListener listener) {
        directFragmentListener = listener;
    }

    public DirectFragment() {
        // Required empty public constructor
    }

    EditText nameView, phoneView;

    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_direct, container, false);

        nameView = (EditText) view.findViewById(R.id.edit_name);
        phoneView = (EditText) view.findViewById(R.id.edit_phone);
        Button btn = (Button) view.findViewById(R.id.btn_add);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(nameView.getText().toString())) {
                    PersonData data = new PersonData();
                    data.setName(nameView.getText().toString());
                    data.setPhone(phoneView.getText().toString());
//                    directFragmentListener.OnDirectFragment(data);
                    Intent intent = new Intent();
                    DutchPayData dutchPayData = new DutchPayData();
                    List<PersonData> list = new ArrayList<PersonData>();
                    list.add(data);
                    dutchPayData.personList = list;
                    intent.putExtra(RegistFragment.EXTRA_RESULT, dutchPayData);
                    getActivity().setResult(Activity.RESULT_OK, intent);
                    getActivity().finish();
                }
            }
        });
        return view;
    }

}
