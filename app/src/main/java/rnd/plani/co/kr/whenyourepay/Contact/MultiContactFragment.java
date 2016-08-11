package rnd.plani.co.kr.whenyourepay.Contact;


import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import rnd.plani.co.kr.whenyourepay.Data.DutchPayData;
import rnd.plani.co.kr.whenyourepay.Data.PersonData;
import rnd.plani.co.kr.whenyourepay.DutchPay.RegistFragment;
import rnd.plani.co.kr.whenyourepay.MyProfile;
import rnd.plani.co.kr.whenyourepay.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MultiContactFragment extends Fragment {

    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;
    private static final int PERMISSIONS_REQUEST_READ_SMS = 200;
    public static final String EXTRA_TYPE = "type";

    int type = -1;

    public MultiContactFragment() {
        // Required empty public constructor
    }

    EditText keywordView;
    ListView listView;
    MultiContactAdapter mAdapter;
    String mKeyword;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            type = getArguments().getInt(EXTRA_TYPE);
        }
    }

    MyContactHeaderView addMeView;
    Realm mRealm;
    boolean isMyProfileChecked = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contact, container, false);
        keywordView = (EditText) view.findViewById(R.id.edit_keyword);

        listView = (ListView) view.findViewById(R.id.listView);
        addMeView = new MyContactHeaderView(getContext());
        listView.addHeaderView(addMeView);
        addMeView.setOnCheckedListener(new MyContactHeaderView.OnCheckedListener() {
            @Override
            public void OnChecked(boolean isChecked) {
                isMyProfileChecked = isChecked;
            }
        });


        mAdapter = new MultiContactAdapter();
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listView.setAdapter(mAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mAdapter.notifyDataSetChanged();
            }
        });


        keywordView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mKeyword = s.toString();
                searchContacts();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        Button btn = (Button) view.findViewById(R.id.btn_return);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                DutchPayData data = new DutchPayData();
                List<PersonData> list = new ArrayList<PersonData>();
                if (isMyProfileChecked) {
                    mRealm = Realm.getInstance(getContext());
                    PersonData me = new PersonData();
                    me.setName(mRealm.where(MyProfile.class).findFirst().getName());
                    list.add(me);
                }
                for (int i = 0; i < mAdapter.getCount(); i++) {
                    if (listView.isItemChecked(i)) {
                        list.add(mAdapter.getItem(i));
                    }
                }
                data.personList = list;
                intent.putExtra(RegistFragment.EXTRA_RESULT, data);
                getActivity().setResult(Activity.RESULT_OK, intent);

                getActivity().finish();
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.READ_SMS)) {

            } else {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_SMS}, PERMISSIONS_REQUEST_READ_SMS);
            }
        } else {
            TelephonyManager telManager = (TelephonyManager) getContext().getSystemService(getContext().TELEPHONY_SERVICE);
            addMeView.setPhoneNumber(telManager.getLine1Number());
        }
        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.READ_CONTACTS)) {
                Toast.makeText(getContext(), "should", Toast.LENGTH_SHORT).show();
                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.
                Toast.makeText(getContext(), "request", Toast.LENGTH_SHORT).show();
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.READ_CONTACTS},
                        PERMISSIONS_REQUEST_READ_CONTACTS);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            searchContacts();
        }
        for (int i = 0; i < mAdapter.getCount(); i++) {
            listView.setItemChecked(i, false);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted

                searchContacts();
            } else {
                Toast.makeText(getContext(), "Until you grant the permission, we canot display the names", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        if (requestCode == PERMISSIONS_REQUEST_READ_SMS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                TelephonyManager telManager = (TelephonyManager) getContext().getSystemService(getContext().TELEPHONY_SERVICE);
                addMeView.setPhoneNumber(telManager.getLine1Number());
            } else {
                Toast.makeText(getContext(), "Until you grant the permission, we canot display phone number", Toast.LENGTH_SHORT).show();
            }
        }
    }

    String[] projection = {ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER};
    String selection = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " NOT NULL AND " +
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " != ''";
    String[] selectionArgs = null;
    String sortOrder = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " COLLATE LOCALIZED ASC";

    private void searchContacts() {
        mAdapter.clear();
        ContentResolver resolver = getActivity().getContentResolver();

        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        if (!TextUtils.isEmpty(mKeyword)) {
            uri = Uri.withAppendedPath(ContactsContract.CommonDataKinds.Phone.CONTENT_FILTER_URI, Uri.encode(mKeyword));

        }

        Cursor c = resolver.query(uri, projection, selection, selectionArgs, sortOrder);

        if (c.moveToFirst()) {
            PersonData data = new PersonData();
            data.setName(c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)));
            data.setPhone(c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
            mAdapter.add(data);
            while (c.moveToNext()) {
                data = new PersonData();
                data.setName(c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)));
                data.setPhone(c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));

                mAdapter.add(data);
            }
        }

    }
}
