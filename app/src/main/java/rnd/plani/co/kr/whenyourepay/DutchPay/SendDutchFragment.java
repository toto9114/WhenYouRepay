package rnd.plani.co.kr.whenyourepay.DutchPay;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import io.realm.Realm;
import rnd.plani.co.kr.whenyourepay.Data.DutchPayData;
import rnd.plani.co.kr.whenyourepay.Data.DutchPersonData;
import rnd.plani.co.kr.whenyourepay.Data.DutchResultData;
import rnd.plani.co.kr.whenyourepay.Data.EventData;
import rnd.plani.co.kr.whenyourepay.Manager.DBContants;
import rnd.plani.co.kr.whenyourepay.Manager.DataManager;
import rnd.plani.co.kr.whenyourepay.MyProfile;
import rnd.plani.co.kr.whenyourepay.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SendDutchFragment extends Fragment {


    public SendDutchFragment() {
        // Required empty public constructor
    }

    public static final String EXTRA_DUTCH_DATA = "dutch";

    DutchPayData dutchPayData;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            dutchPayData = (DutchPayData) getArguments().getSerializable(EXTRA_DUTCH_DATA);
        }
    }

    private static final String FILE_NAME = "iou_image.jpg";
    private static final int REQUEST_WRITE_EXTERNAL_STORAGE = 100;
    private static final int REQUEST_SEND_IMAGE = 200;

    RelativeLayout iou;
    TextView totalView, dateView, bottomDateView, accountView;
    ListView listView;
//    ArrayAdapter<DutchPersonData> mAdapter;
    DutchResultAdapter mAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_send_dutch, container, false);
        for (EventData data : dutchPayData.eventList) {
            Log.i("dutch", "" + data.money);
        }
        for (DutchPersonData data : dutchPayData.eventList.get(0).people) {
            if(data.attended) {
                Log.i("dutch", "name: " + data.name);
            }
        }

        for(EventData data :dutchPayData.eventList){
            for(DutchPersonData person : data.people){
                if(person.attended){
                    Log.i("person",person.name);
                }
            }
        }

//        mAdapter = new ArrayAdapter<DutchPersonData>(getContext(), android.R.layout.simple_list_item_1);
        listView = (ListView) view.findViewById(R.id.listView);
        mAdapter = new DutchResultAdapter();
        listView.setAdapter(mAdapter);
        totalView = (TextView) view.findViewById(R.id.text_total_price);
        dateView = (TextView) view.findViewById(R.id.text_desc);
        bottomDateView = (TextView) view.findViewById(R.id.text_bottom_date);
        accountView = (TextView) view.findViewById(R.id.text_account);
        iou = (RelativeLayout) view.findViewById(R.id.iou);

        Button btn = (Button) view.findViewById(R.id.btn_dun);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataManager.getInstance().insertDutchPay(dutchPayData);
                final long id = DataManager.getInstance().getDutchPayList().get(DataManager.getInstance().getDutchPayList().size() - 1).getId();
                for(int i = 0 ; i<dutchPayData.personList.size() ; i++){
                    DataManager.getInstance().insertDutchPayPerson(id,dutchPayData.personList.get(i));
                }
                for(int i = 0 ; i < dutchPayData.eventList.size() ; i++){
                    DataManager.getInstance().insertEvent(id,dutchPayData.eventList.get(i));
                    long eventId = DataManager.getInstance().getDutchEventList(id).get(DataManager.getInstance().getDutchEventList(id).size()-1)._id;
                    for(int j = 0 ; j<dutchPayData.eventList.get(i).people.size(); j++){
                        if(dutchPayData.eventList.get(i).people.get(j).attended){
                            DataManager.getInstance().insertAttentedPerson(id,eventId,dutchPayData.eventList.get(i).people.get(j));
                        }
                    }
                }
                if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                    } else {
                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_EXTERNAL_STORAGE);
                    }
                } else {
                    saveIOU();
                }
                sendIOU();
            }
        });
        initData();
        return view;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_WRITE_EXTERNAL_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                saveIOU();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_SEND_IMAGE){
            ((DutchPayActivity)getActivity()).changeSuccess();
        }
    }
    Realm mRealm;

    private void initData() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 MM월 dd일");
        dateView.setText(sdf.format(date));
        bottomDateView.setText(sdf.format(date));
//        dutchPayData.date = sdf.format(date);

        mRealm = Realm.getInstance(getContext());
        totalView.setText("" + dutchPayData.totalPrice);
        accountView.setText(mRealm.where(MyProfile.class).findFirst().getName() + " " + mRealm.where(MyProfile.class).findFirst().getAccount());
//        mAdapter.addAll(dutchPayData.personList);
//        DutchResultData result = n
//        for(int i = 0 ; i < dutchPayData.eventList.size() ; i++){
//            EventData eventData = dutchPayData.eventList.get(i);
//            for(int j = 0 ; j< eventData.people.size() ; j++){
//                DutchResultData personal = new DutchResultData();
//                personal.name = person.name;
//                if(person.attended){
//                    personal.attendList.add(i);
//                }
//            }
//        }
        for(int i = 0 ; i< dutchPayData.personList.size() ; i++){
            DutchResultData data = new DutchResultData();
            data.name = dutchPayData.personList.get(i).getName();
            data.attendList = new ArrayList<>();
            for(int j = 0 ; j<dutchPayData.eventList.size() ; j++){
                if(dutchPayData.eventList.get(j).people.get(i).attended){
                    data.attendList.add(j);
                    data.money += dutchPayData.eventList.get(j).people.get(i).dutchMoney;
                }
            }
            dutchPayData.personList.get(i).setMoney(data.money);
            mAdapter.add(data);
        }
    }

    private void saveIOU() {
        File dir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), DBContants.IOU_FOLDER_NAME);
        File saveFile = new File(dir, FILE_NAME);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        try {
            FileOutputStream fos = new FileOutputStream(saveFile);
            Bitmap bitmap = getViewBitmap(iou);
            if (bitmap != null) {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, fos);
                bitmap.recycle();
            }
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendIOU() {
        File dir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), DBContants.IOU_FOLDER_NAME);
        File saveFile = new File(dir, FILE_NAME);
        Uri uri = Uri.fromFile(saveFile);
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("image/jpg");
        intent.putExtra(Intent.EXTRA_STREAM, uri);

        startActivityForResult(Intent.createChooser(intent, "공유"), REQUEST_SEND_IMAGE);
    }

    private Bitmap getViewBitmap(View v) {
        v.clearFocus();
        v.setPressed(false);
        boolean willNotCache = v.willNotCacheDrawing();
        v.setWillNotCacheDrawing(false);
        int color = v.getDrawingCacheBackgroundColor();
        v.setDrawingCacheBackgroundColor(Color.TRANSPARENT);
        if (color != Color.TRANSPARENT) {
            v.destroyDrawingCache();
        }
        v.buildDrawingCache();
        Bitmap cacheBitmap = v.getDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(cacheBitmap);
        v.destroyDrawingCache();
        v.setWillNotCacheDrawing(willNotCache);
        v.setDrawingCacheBackgroundColor(color);

        return bitmap;
    }

    public Bitmap byteArrayToBitmap(byte[] $byteArray) {
        Bitmap bitmap = BitmapFactory.decodeByteArray($byteArray, 0, $byteArray.length);
        return bitmap;
    }
}
