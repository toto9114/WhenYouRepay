package rnd.plani.co.kr.whenyourepay.NavigationDrawer;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by RND on 2016-05-03.
 */
public class MenuAdapter extends BaseExpandableListAdapter {
    List<GroupMenu> items = new ArrayList<>();

    public void add(GroupMenu item) {
        items.add(item);
        notifyDataSetChanged();
    }

    private static final int GROUP_COUNT = 2;
    private static final int TYPE_MENU = 0;
    private static final int TYPE_PUSH_SETTING = 1;

    @Override
    public int getGroupCount() {
        return GROUP_COUNT;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return items.get(groupPosition).menuItem.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return items.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return items.get(groupPosition).menuItem.get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return ((long)groupPosition)<<32|childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupMenuView view;
        if (convertView == null) {
            view = new GroupMenuView(parent.getContext());
        } else {
            view = (GroupMenuView)convertView;
        }
        view.setGroupMenu(items.get(groupPosition));
        return view;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        MenuItem view;
        if (convertView == null) {
            view = new MenuItem(parent.getContext());
        } else {
            view = (MenuItem)convertView;
        }
        view.setData(items.get(groupPosition).menuItem.get(childPosition),groupPosition,isLastChild);
        return view;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
