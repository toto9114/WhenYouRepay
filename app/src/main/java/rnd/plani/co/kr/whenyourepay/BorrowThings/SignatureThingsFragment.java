package rnd.plani.co.kr.whenyourepay.BorrowThings;


import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import rnd.plani.co.kr.whenyourepay.Data.BorrowerData;
import rnd.plani.co.kr.whenyourepay.Data.ThingsData;
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
        if (getArguments() != null) {
            thingsData = (ThingsData) getArguments().getSerializable(EXTRA_THINGS_DATA);
        }
    }

    TextView thingsView, dateView;
    ImageView resignView;
    FingerPaintView paint;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_signature_things, container, false);

        View customToolbar = getLayoutInflater(savedInstanceState).inflate(R.layout.view_center_toolbar, null);
        TextView titleView = (TextView)customToolbar.findViewById(R.id.image_category);
        titleView.setText("물건빌려주기");
        ((LendThingsActivity)getActivity()).getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        ((LendThingsActivity)getActivity()).getSupportActionBar().setCustomView(customToolbar, new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER));
        ((LendThingsActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setHasOptionsMenu(true);

        thingsView = (TextView) view.findViewById(R.id.text_things);
        dateView = (TextView) view.findViewById(R.id.text_desc);
        resignView = (ImageView) view.findViewById(R.id.image_resign);

        paint = (FingerPaintView) view.findViewById(R.id.sign);
        paint.setOnDrawingListener(new FingerPaintView.OnDrawingListener() {
            @Override
            public void OnDrawing() {
                resignView.setVisibility(View.VISIBLE);
            }
        });

        resignView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resignView.setVisibility(View.GONE);
                paint.undoAll();
            }
        });
        initData();


        Button btn = (Button) view.findViewById(R.id.btn_return);
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
                ((LendThingsActivity) getActivity()).changeSend(data, thingsData);
            }
        });

        return view;
    }

    private void initData() {
        if (thingsData != null) {
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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_close,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home){
            getActivity().getSupportFragmentManager().popBackStack();
            return true;
        }
        if(id == R.id.menu_close){
            getActivity().finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
