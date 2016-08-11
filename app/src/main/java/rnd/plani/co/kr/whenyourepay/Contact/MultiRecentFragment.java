package rnd.plani.co.kr.whenyourepay.Contact;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import io.realm.Realm;
import rnd.plani.co.kr.whenyourepay.Data.AccountData;
import rnd.plani.co.kr.whenyourepay.Data.DutchPayData;
import rnd.plani.co.kr.whenyourepay.Data.PersonData;
import rnd.plani.co.kr.whenyourepay.Data.ThingsData;
import rnd.plani.co.kr.whenyourepay.Data.TransactionData;
import rnd.plani.co.kr.whenyourepay.DutchPay.RegistFragment;
import rnd.plani.co.kr.whenyourepay.Manager.DBContants;
import rnd.plani.co.kr.whenyourepay.Manager.DataManager;
import rnd.plani.co.kr.whenyourepay.MyProfile;
import rnd.plani.co.kr.whenyourepay.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MultiRecentFragment extends Fragment {


    public MultiRecentFragment() {
        // Required empty public constructor
    }

    ListView listView;
    MultiRecentAdapter mAdapter;

    public static final String EXTRA_TYPE = "type";
    int type = -1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            type = getArguments().getInt(EXTRA_TYPE);
        }
    }

    MyContactHeaderView myContactView;
    Realm mRealm;
    boolean isMyContactChecked = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recent, container, false);

        myContactView = (MyContactHeaderView) view.findViewById(R.id.view_add_me);
        listView = (ListView) view.findViewById(R.id.listView);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        mAdapter = new MultiRecentAdapter();
        listView.setAdapter(mAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mAdapter.notifyDataSetChanged();
            }
        });

        myContactView.setOnCheckedListener(new MyContactHeaderView.OnCheckedListener() {
            @Override
            public void OnChecked(boolean isChecked) {
                isMyContactChecked = isChecked;
            }
        });
        Button btn = (Button) view.findViewById(R.id.btn_return);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type == MultiContactActivity.TYPE_GROUP) {
//                    Intent intent = new Intent();
//                    GroupData data = new GroupData();
//                    List<String> list = new ArrayList<String>();
//                    for (int i = 0; i < mAdapter.getCount(); i++) {
//                        if (listView.isItemChecked(i)) {
//                            list.add(mAdapter.getItemAtPosition(i));
//                        }
//                    }
//                    data.setPersonList(list);
//                    intent.putExtra(RegistGroupInfoFragment.EXTRA_RESULT, data);
//                    getActivity().setResult(Activity.RESULT_OK, intent);
                } else {
                    Intent intent = new Intent();
                    DutchPayData data = new DutchPayData();
                    List<PersonData> list = new ArrayList<PersonData>();
                    if (isMyContactChecked) {
                        mRealm = Realm.getInstance(getContext());
                        PersonData me = new PersonData();
                        me.setName(mRealm.where(MyProfile.class).findFirst().getName());
                        list.add(me);
                    }
                    for (int i = 0; i < mAdapter.getCount(); i++) {
                        if (listView.isItemChecked(i)) {
                            PersonData personData = new PersonData();
                            personData.setName(mAdapter.getItem(i).getName());
                            personData.setPhone(mAdapter.getItem(i).getPhone());
                            list.add(personData);
                        }
                    }
                    data.personList = list;
                    intent.putExtra(RegistFragment.EXTRA_RESULT, data);
                    getActivity().setResult(Activity.RESULT_OK, intent);
                }
                getActivity().finish();
            }
        });
        initData();
        return view;
    }

    List<TransactionData> list = new ArrayList<>();

    @Override
    public void onResume() {
        super.onResume();
        for(int i = 0 ; i<mAdapter.getCount() ; i++){
            listView.setItemChecked(i,false);
        }
    }

    private void initData() {
        if (DataManager.getInstance().getContractList(DBContants.SORT_TYPE_DATE).size() != 0) {
            for (TransactionData data : DataManager.getInstance().getContractList(DBContants.SORT_TYPE_DATE)) {
                AccountData accountData = (AccountData) data;
                list.add(accountData);
            }
        }
        if (DataManager.getInstance().getContractThingsList().size() != 0) {
            for (TransactionData data : DataManager.getInstance().getContractThingsList()) {
                ThingsData thingsData = (ThingsData) data;
                list.add(thingsData);
            }
        }
        if (DataManager.getInstance().getDutchPayList().size() != 0) {
            for (TransactionData data : DataManager.getInstance().getDutchPayList()) {
                DutchPayData dutchPayData = (DutchPayData) data;
                list.add(dutchPayData);
            }
        }
        sort();
        List<PersonData> nameList = new ArrayList<>();

        for (int i = 0 ; i < list.size() ; i++) {
                addRecentList(list.get(i),i);
        }
        for(TransactionData s: list){
            PersonData personData = new PersonData();
            if(s instanceof AccountData) {
                s = (AccountData)s;
                personData.setName(((AccountData) s).name);
                personData.setPhone(((AccountData) s).phone);
            }else if(s instanceof ThingsData){
                s = (ThingsData)s;
                personData.setName(((ThingsData) s).borrowerName);
                personData.setPhone(((ThingsData) s).phone);
            }else{

            }
            nameList.add(personData);
        }
        mAdapter.addAll(nameList);
    }

    private void addRecentList(TransactionData data,int position) {
        for(int i = position+1 ; i<list.size() ; i++){
            if(list.get(i).getName().equals(data.getName())){
//                mAdapter.add(data.getName());
                list.remove(i);
            }
        }
    }

    public void sort() {
        Collections.sort(list, new Comparator<TransactionData>() {
            @Override
            public int compare(TransactionData item1, TransactionData item2) {
                return item1.getDate() - item2.getDate() > 0 ? -1 : 1;
            }
        });
    }
}
