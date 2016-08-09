package rnd.plani.co.kr.whenyourepay.NavigationDrawer;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IntDef;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import rnd.plani.co.kr.whenyourepay.MyProfile;
import rnd.plani.co.kr.whenyourepay.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnMenuItemSelectedListener} interface
 * to handle interaction events.
 */
public class MenuFragment extends Fragment { //drawerLayout 메뉴 관리

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({MenuFragment.MENU_ID_OVERDUE, MenuFragment.MENU_ID_COMPLETE, MenuFragment.MENU_ID_RECOMMEND, MENU_ID_EVALUATE})
    public @interface MenuMode {
    }

    private OnMenuItemSelectedListener mListener;
    public static final int MENU_ID_OVERDUE = 0;
    public static final int MENU_ID_COMPLETE = 1;
    public static final int MENU_ID_RECOMMEND = 2;
    public static final int MENU_ID_EVALUATE = 3;

    private static final String[] GROUP_TITLE = {"거래내역", "앱"};
    private static final String[] CONTACT_MENU_TITLE = {"연체된 거래내역", "완료된 거래내역"};
    private static final int[] MENU_ICON = {R.drawable.ic_alarm_black, R.drawable.ic_done_black};

    private static final String[] APP_MENU_TITLE = {"추천하기", "평가하기"};
    private static final int[] APP_MENU_ICON = {R.drawable.ic_send_black, R.drawable.ic_star_black};

    ExpandableListView listView;
    Context context;
    MenuAdapter mAdapter;

    TextView nameView, accountView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new MenuAdapter();
        for (int i = 0; i < 2; i++) {
            GroupMenu groupMenu = new GroupMenu();
            groupMenu.title = GROUP_TITLE[i];
            List<MenuData> itemList = new ArrayList<>();
            if (i == 0) {
                for (int j = 0; j < 2; j++) {
                    MenuData data = new MenuData();
                    data.id = j;
                    data.title = CONTACT_MENU_TITLE[j];
                    data.iconId = MENU_ICON[j];
                    itemList.add(data);
                }
            } else if (i == 1) {
                for (int j = 0; j < 2; j++) {
                    MenuData data = new MenuData();
                    data.id = i * 2 + j;
                    data.title = APP_MENU_TITLE[j];
                    data.iconId = APP_MENU_ICON[j];
                    itemList.add(data);
                }
            }
            groupMenu.menuItem = itemList;
            mAdapter.add(groupMenu);
        }
    }

    Realm mRealm;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container, false);

        context = getContext();
        listView = (ExpandableListView) view.findViewById(R.id.listView);
        nameView = (TextView)view.findViewById(R.id.text_name);
        accountView = (TextView)view.findViewById(R.id.text_account);

        mRealm = Realm.getInstance(getContext());
        listView.setAdapter(mAdapter);
        listView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
                listView.expandGroup(groupPosition);
            }
        });

        listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                MenuData item = (MenuData) mAdapter.getChild(groupPosition, childPosition);
                selectMenu(item.id);
                return false;
            }
        });

        for (int i = 0; i < mAdapter.getGroupCount(); i++) {
            listView.expandGroup(i);
        }
        MyProfile profile = mRealm.where(MyProfile.class).findFirst();
        nameView.setText(profile.getName());
        accountView.setText(profile.getBank() + " " + profile.getAccount());
        return view;
    }

    public void selectMenu(@MenuMode int menuId) {
        if (mListener != null) {
            mListener.onMenuItemSelected(menuId);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnMenuItemSelectedListener) {
            mListener = (OnMenuItemSelectedListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnMenuItemSelectedListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnMenuItemSelectedListener {
        void onMenuItemSelected(@MenuMode int menuId);
    }
}
