package rnd.plani.co.kr.whenyourepay.MainContract;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import cn.iwgang.familiarrecyclerview.FamiliarRecyclerView;
import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.animators.FadeInDownAnimator;
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;
import rnd.plani.co.kr.whenyourepay.Data.AccountData;
import rnd.plani.co.kr.whenyourepay.Data.DutchPayData;
import rnd.plani.co.kr.whenyourepay.Data.ThingsData;
import rnd.plani.co.kr.whenyourepay.Data.TransactionData;
import rnd.plani.co.kr.whenyourepay.EmptyFooterView;
import rnd.plani.co.kr.whenyourepay.Manager.DBContants;
import rnd.plani.co.kr.whenyourepay.Manager.DataManager;
import rnd.plani.co.kr.whenyourepay.OverDue.OverDueActivity;
import rnd.plani.co.kr.whenyourepay.R;
import rnd.plani.co.kr.whenyourepay.Transaction.DetailMoneyTrans.DetailMoneyTransactionActivity;
import rnd.plani.co.kr.whenyourepay.Transaction.DetailThingsTrans.DetailThingsTransactionActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {


    public MainFragment() {
        // Required empty public constructor
    }

    FamiliarRecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    MainContractAdapter mAdapter;
    TextView alertView;
    TextView countView, dunView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        alertView = (TextView) view.findViewById(R.id.text_alert);
        countView = (TextView) view.findViewById(R.id.text_count_overdue);
        dunView = (TextView) view.findViewById(R.id.text_dun);
        recyclerView = (FamiliarRecyclerView) view.findViewById(R.id.recycler);
        mAdapter = new MainContractAdapter();
        layoutManager = new LinearLayoutManager(getContext(), OrientationHelper.VERTICAL, false);
        recyclerView.addFooterView(new EmptyFooterView(getContext()));
        AlphaInAnimationAdapter overDueAnimationAdapter = new AlphaInAnimationAdapter(mAdapter);
        recyclerView.setAdapter(overDueAnimationAdapter);
        recyclerView.setLayoutManager(layoutManager);
        OverScrollDecoratorHelper.setUpOverScroll(recyclerView, OverScrollDecoratorHelper.ORIENTATION_VERTICAL);
        recyclerView.setItemAnimator(new FadeInDownAnimator());


        recyclerView.setOnItemClickListener(new FamiliarRecyclerView.OnItemClickListener() {
            @Override
            public void onItemClick(FamiliarRecyclerView familiarRecyclerView, View view, int position) {
                TransactionData data = mAdapter.getItemAtPosition(position);
                if (data instanceof AccountData) {
                    Intent intent = new Intent(getContext(), DetailMoneyTransactionActivity.class);
                    intent.putExtra(DetailMoneyTransactionActivity.EXTRA_ACCOUNT_DATA, (AccountData) data);
                    startActivity(intent);
                } else if (data instanceof ThingsData) {
                    Intent intent = new Intent(getContext(), DetailThingsTransactionActivity.class);
                    intent.putExtra(DetailThingsTransactionActivity.EXTRA_THINGS_DATA, (ThingsData) data);
                    startActivity(intent);
                }
//                else {
//                    Intent intent = new Intent(getContext(), DetailDutchActivity.class);
//                    intent.putExtra(DetailDutchActivity.EXTRA_DUTCH_DATA, (DutchPayData) data);
//                    startActivity(intent);
//                }
            }
        });


//        mAdapter.setOnContractHeaderClickListener(new ContractHeaderView.OnContractHeaderClickListener() {
//            @Override
//            public void OnContractHeaderClick() {
//                startActivity(new Intent(getContext(), CompleteActivity.class));
//            }
//        });
//
//        headerView.setOnOverDueHeaderClickListener(new OverDueHeaderView.OnOverDueHeaderClickListener() {
//            @Override
//            public void OnOverDueHeaderClick() {
//                startActivity(new Intent(getContext(), OverDueActivity.class));
//            }
//        });

        dunView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), OverDueActivity.class));
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mAdapter.clear();
        setData();
        mAdapter.sort();
        countView.setText("연체내역 " + overDue + "건 있음");
    }


    int overDue = 0;
    Calendar c = Calendar.getInstance();

    public void setData() {
        overDue = 0;
        if (DataManager.getInstance().getContractList(DBContants.SORT_TYPE_DATE).size() != 0) {
            alertView.setVisibility(View.GONE);
            for (TransactionData data : DataManager.getInstance().getContractList(DBContants.SORT_TYPE_DATE)) {
                AccountData accountData = (AccountData) data;
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 MM월 dd일");
                if (!accountData.isCompleted) {
                    if (accountData.getRepayDate().compareTo(sdf.format(c.getTime())) < 0) {
                        overDue++;
                    } else {
                        mAdapter.add(accountData);
                    }
                }
            }
        }
        if (DataManager.getInstance().getContractThingsList().size() != 0) {
            alertView.setVisibility(View.GONE);
            for (TransactionData data : DataManager.getInstance().getContractThingsList()) {
                ThingsData thingsData = (ThingsData) data;
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 MM월 dd일");
                if (thingsData.getRepayDate().compareTo(sdf.format(c.getTime())) < 0) {
                    overDue++;
                } else {
                    mAdapter.add(thingsData);
                }
            }
        }

        if (DataManager.getInstance().getDutchPayList().size() != 0) {
            alertView.setVisibility(View.GONE);

            for (TransactionData data : DataManager.getInstance().getDutchPayList()) {
                DutchPayData dutchPayData = (DutchPayData) data;
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 MM월 dd일");
//                if (dutchPayData.getRepayDate().compareTo(sdf.format(c.getTime())) < 0) {
                if (sdf.format(new Date(dutchPayData.date)).compareTo(sdf.format(c.getTime())) < 0) {
                    overDue++;
                } else {
                    mAdapter.add(dutchPayData);
                }
            }
        }

        mAdapter.sort();
    }

    private void filter(int type) {
        mAdapter.clear();
//        switch (type) {
//            case FilterPopupWindow.TYPE_MONEY:
//                if (DataManager.getInstance().getContractList(DBContants.SORT_TYPE_DATE).size() != 0) {
//                    alertView.setVisibility(View.GONE);
//                    for (TransactionData data : DataManager.getInstance().getContractList(DBContants.SORT_TYPE_DATE)) {
//                        AccountData accountData = (AccountData) data;
//                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 MM월 dd일");
//                        if (accountData.getRepayDate().compareTo(sdf.format(c.getTime())) < 0) {
//                            overDue++;
//                            mAdapter.addOverDue(accountData);
//                        } else {
//                            if (!accountData.isCompleted) {
//                                mAdapter.addContract(accountData);
//                            }
//                        }
//                    }
//                } else {
//                    alertView.setVisibility(View.VISIBLE);
//                }
//                break;
//            case FilterPopupWindow.TYPE_THINGS:
//                if (DataManager.getInstance().getContractThingsList().size() != 0) {
//                    alertView.setVisibility(View.GONE);
//                    for (TransactionData data : DataManager.getInstance().getContractThingsList()) {
//                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 MM월 dd일");
//                        if (data.getRepayDate().compareTo(sdf.format(c.getTime())) < 0) {
//                            overDue++;
//                            mAdapter.addOverDue(data);
//                        } else {
//                            mAdapter.addContract(data);
//                        }
//                    }
//                } else {
//                    alertView.setVisibility(View.VISIBLE);
//                }
//                break;
//            case FilterPopupWindow.TYPE_DUTCH:
//                if (DataManager.getInstance().getDutchPayList().size() != 0) {
//                    alertView.setVisibility(View.GONE);
//                    for (TransactionData data : DataManager.getInstance().getDutchPayList()) {
//                        DutchPayData dutchPayData = (DutchPayData) data;
//                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 MM월 dd일");
//                        if (sdf.format(new Date(dutchPayData.date)).compareTo(sdf.format(c.getTime())) < 0) {
//                            overDue++;
//                            mAdapter.addOverDue(dutchPayData);
//                        } else {
//                            mAdapter.addContract(dutchPayData);
//                        }
//                    }
//                } else {
//                    alertView.setVisibility(View.VISIBLE);
//                }
//                break;
//            case FilterPopupWindow.TYPE_ALL:
//                setData();
//                break;
//        }
    }
}
