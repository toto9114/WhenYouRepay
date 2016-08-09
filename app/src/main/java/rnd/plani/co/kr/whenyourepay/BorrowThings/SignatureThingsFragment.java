package rnd.plani.co.kr.whenyourepay.BorrowThings;


import android.app.DatePickerDialog;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import rnd.plani.co.kr.whenyourepay.Data.BorrowerData;
import rnd.plani.co.kr.whenyourepay.FingerPaintView;
import rnd.plani.co.kr.whenyourepay.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignatureThingsFragment extends Fragment {


    public SignatureThingsFragment() {
        // Required empty public constructor
    }

    public static final String EXTRA_THINGS_DATA = "things";

    ThingsData thingsData = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments()!=null){
            thingsData = (ThingsData)getArguments().getSerializable(EXTRA_THINGS_DATA);
        }
    }

    TextView thingsView, dateView;
    FingerPaintView paint;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_signature_things, container, false);
        thingsView = (TextView) view.findViewById(R.id.text_things);
        dateView = (TextView) view.findViewById(R.id.text_date);
        paint = (FingerPaintView)view.findViewById(R.id.sign);
        initData();

        Button btn = (Button)view.findViewById(R.id.btn_resign);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paint.undoAll();
            }
        });

        btn = (Button)view.findViewById(R.id.btn_done);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                BorrowerData data = new BorrowerData();
                Bitmap bitmap = getViewBitmap(paint);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                data.byteBitmap = byteArray;
                thingsData.sign = byteArray;
                thingsData.repayDate = dateView.getText().toString(); //상환일자 가져오기
                ((LendThingsActivity)getActivity()).changeSend(data,thingsData);
            }
        });

        btn= (Button)view.findViewById(R.id.btn_edit_date);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                DatePickerDialog pickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        c.set(year, monthOfYear, dayOfMonth);
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 MM월 dd일");
                        dateView.setText(sdf.format(c.getTime()));
                    }
                },c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH));
                pickerDialog.show();
            }
        });
        return view;
    }

    private void initData(){
        if(thingsData != null) {
            thingsView.setText(thingsData.thingsName);
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 MM월 dd일");
            dateView.setText(sdf.format(date));
        }
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
}
