package rnd.plani.co.kr.whenyourepay.BorrowThings;


import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import rnd.plani.co.kr.whenyourepay.Contact.SingleContactActivity;
import rnd.plani.co.kr.whenyourepay.Data.PersonData;
import rnd.plani.co.kr.whenyourepay.Data.ThingsData;
import rnd.plani.co.kr.whenyourepay.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContractThingsFragment extends Fragment {


    public ContractThingsFragment() {
        // Required empty public constructor
    }

    ImageView pictureView, deleteView;
    EditText thingsView;
    TextView nameView;
    private static final int REQUEST_PICTURE = 100;
    private static final int REQUEST_CONTACT = 300;
    private static final int REQUEST_READ_EXTERNAL_STORAGE = 400;

    public static final String EXTRA_RESULT = "result";

    ThingsData thingsData;
    TextView pickView, dateView;
    View view;
    InputMethodManager imm;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_contract_things, container, false);
        View customToolbar = getLayoutInflater(savedInstanceState).inflate(R.layout.view_close_center_toolbar, null);

        TextView titleView = (TextView) customToolbar.findViewById(R.id.image_category);
        ImageView closeView = (ImageView) customToolbar.findViewById(R.id.image_close);
        titleView.setText("물건빌려주기");

        ((LendThingsActivity) getActivity()).getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        ((LendThingsActivity) getActivity()).getSupportActionBar().setCustomView(customToolbar, new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER));
        thingsData = new ThingsData();
        nameView = (TextView) view.findViewById(R.id.edit_name);
        pictureView = (ImageView) view.findViewById(R.id.image_things);
        deleteView = (ImageView) view.findViewById(R.id.image_delete);
        thingsView = (EditText) view.findViewById(R.id.edit_things);
        dateView = (TextView) view.findViewById(R.id.text_desc);
        pickView = (TextView) view.findViewById(R.id.text_get_picture);
        imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        Button btn = (Button) view.findViewById(R.id.btn_return);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(thingsData.borrowerName) || !TextUtils.isEmpty(thingsView.getText().toString())) {
                    thingsData.thingsName = thingsView.getText().toString();

                    if (!TextUtils.isEmpty(thingsData.pictureUri)) {
                        Bitmap bitmap = BitmapFactory.decodeFile(thingsData.pictureUri);
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                        byte[] byteArray = stream.toByteArray();
                        thingsData.picture = byteArray;
                    }
                    ((LendThingsActivity) getActivity()).changeSignature(thingsData);
                } else {
                    Toast.makeText(getContext(), "필수정보를 입력해주세요", Toast.LENGTH_SHORT).show();
                }
            }
        });

        nameView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), SingleContactActivity.class);
                i.putExtra(SingleContactActivity.EXTRA_TYPE, SingleContactActivity.TYPE_THINGS);
                startActivityForResult(i, REQUEST_CONTACT);
            }
        });

        dateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                final Calendar c = Calendar.getInstance();
                DatePickerDialog pickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        c.set(year, monthOfYear, dayOfMonth);
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 MM월 dd일");
                        dateView.setText(sdf.format(c.getTime()));
                        thingsData.repayDate = sdf.format(c.getTime());
                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
                pickerDialog.show();
            }
        });

        pickView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    if (shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {

                    } else {
                        requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_READ_EXTERNAL_STORAGE);
                    }
                }else {
                    startActivityForResult(new Intent(getContext(), GalleryActivity.class), REQUEST_PICTURE);
                }

            }
        });

        deleteView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thingsData.pictureUri = null;
                pickView.setVisibility(View.VISIBLE);
                pictureView.setVisibility(View.GONE);
                deleteView.setVisibility(View.GONE);
            }
        });

        closeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        return view;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQUEST_READ_EXTERNAL_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startActivityForResult(new Intent(getContext(), GalleryActivity.class), REQUEST_PICTURE);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_PICTURE:
                if (resultCode != Activity.RESULT_CANCELED) {
                    pickView.setVisibility(View.GONE);
                    pictureView.setVisibility(View.VISIBLE);
                    deleteView.setVisibility(View.VISIBLE);
                    thingsData.pictureUri = data.getStringExtra(EXTRA_RESULT);
                    Glide.with(getContext()).load(thingsData.pictureUri).into(pictureView);
                }
                break;
            case REQUEST_CONTACT:
                if (resultCode != Activity.RESULT_CANCELED) {
                    PersonData personData = (PersonData) data.getSerializableExtra(EXTRA_RESULT);
                    thingsData.borrowerName = personData.getName();
                    nameView.setText(personData.getName());
                }
                break;
        }
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
