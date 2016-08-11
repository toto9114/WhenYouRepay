package rnd.plani.co.kr.whenyourepay.BorrowMoney;


import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.text.NumberFormat;

import rnd.plani.co.kr.whenyourepay.Data.AccountData;
import rnd.plani.co.kr.whenyourepay.Data.BorrowerData;
import rnd.plani.co.kr.whenyourepay.FingerPaintView;
import rnd.plani.co.kr.whenyourepay.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignatureFragment extends Fragment {


    public SignatureFragment() {
        // Required empty public constructor
    }

    public static final String EXTRA_ACCOUNT_DATA = "account";
    FingerPaintView paint;

    AccountData accountData = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Bundle args = getArguments();
            accountData = (AccountData) args.getSerializable(EXTRA_ACCOUNT_DATA);
        }
    }

    TextView nameView, priceView, krPriceView, dateView;

    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if(view!=null){
            if((ViewGroup)view.getParent()!=null)
                ((ViewGroup)view.getParent()).removeView(view);
            return view;
        }
        view = inflater.inflate(R.layout.fragment_signature, container, false);
        paint = (FingerPaintView) view.findViewById(R.id.sign);
        priceView = (TextView) view.findViewById(R.id.text_money);
        nameView = (TextView) view.findViewById(R.id.text_name);
        krPriceView = (TextView) view.findViewById(R.id.text_kr_money);
        dateView = (TextView) view.findViewById(R.id.text_desc);

        NumberFormat nf = NumberFormat.getInstance();
        if (accountData != null) {
            nameView.setText(accountData.getName());
            priceView.setText(nf.format(accountData.money));
            dateView.setText(accountData.repayDate);
            krPriceView.setText("(금 "+ convertHangul(String.valueOf(accountData.money))+")");
        }



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
                accountData.sign = byteArray;
                accountData.repayDate = dateView.getText().toString(); //변제일자 가져오기
                ((LendMoneyActivity) getActivity()).changeSend(data, accountData);
            }
        });
        btn = (Button) view.findViewById(R.id.btn_resign);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paint.undoAll();
            }
        });

        return view;
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

    public String convertHangul(String money){
        String[] han1 = {"","일","이","삼","사","오","육","칠","팔","구"};
        String[] han2 = {"","십","백","천"};
        String[] han3 = {"","만","억","조","경"};

        StringBuffer result = new StringBuffer();
        int len = money.length();
        for(int i=len-1; i>=0; i--){
            result.append(han1[Integer.parseInt(money.substring(len-i-1, len-i))]);
            if(Integer.parseInt(money.substring(len-i-1, len-i)) > 0)
                result.append(han2[i%4]);
            if(i%4 == 0)
                result.append(han3[i/4]);
        }

        return result.toString()+"원";
    }

}
