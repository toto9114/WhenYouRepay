package rnd.plani.co.kr.whenyourepay.Contact;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import rnd.plani.co.kr.whenyourepay.BorrowMoney.ContractFragment;
import rnd.plani.co.kr.whenyourepay.Data.AccountData;
import rnd.plani.co.kr.whenyourepay.Data.PersonData;
import rnd.plani.co.kr.whenyourepay.Data.ThingsData;
import rnd.plani.co.kr.whenyourepay.Data.TransactionData;
import rnd.plani.co.kr.whenyourepay.Manager.DBContants;
import rnd.plani.co.kr.whenyourepay.Manager.DataManager;
import rnd.plani.co.kr.whenyourepay.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SingleRecentFragment extends Fragment {


    public SingleRecentFragment() {
        // Required empty public constructor
    }

    ListView listView;
    SingleContactAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_single_recent, container, false);
        listView = (ListView) view.findViewById(R.id.listView);
        mAdapter = new SingleContactAdapter();
        listView.setAdapter(mAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent();
                i.putExtra(ContractFragment.EXTRA_RESULT, (PersonData) mAdapter.getItem(position));
                getActivity().setResult(Activity.RESULT_OK, i);
                getActivity().finish();
            }
        });
        initData();
        return view;
    }

    List<TransactionData> list = new ArrayList<>();

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

            }
        }
        sort();
        List<PersonData> nameList = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            addRecentList(list.get(i), i);
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

    private void addRecentList(TransactionData data, int position) {
        for (int i = position + 1; i < list.size(); i++) {
            if (list.get(i).getName().equals(data.getName())) {
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
