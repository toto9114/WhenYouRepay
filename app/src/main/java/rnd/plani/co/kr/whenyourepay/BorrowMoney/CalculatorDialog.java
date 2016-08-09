package rnd.plani.co.kr.whenyourepay.BorrowMoney;


import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import rnd.plani.co.kr.whenyourepay.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class CalculatorDialog extends DialogFragment implements View.OnClickListener {

    public interface OnCalculatorResultListener {
        public void OnCalculatorResult(String result);
    }

    public OnCalculatorResultListener calculatorResultListener;

    public void setOnCalculatorResultListener(OnCalculatorResultListener listener) {
        calculatorResultListener = listener;
    }

    public CalculatorDialog() {
        // Required empty public constructor
    }

    ClipboardManager clipboardManager;
    private Button b1;
    private Button b2;
    private Button b3;
    private Button b4;
    private Button b5;
    private Button b6;
    private Button b7;
    private Button b8;
    private Button b9;
    private Button b_claer;
    private Button b_back;
    private Button b_devide;
    private Button b_plus;
    private Button b_minus;
    private Button b_multi;
    private Button b_equal;

    private TextView operation;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_calulator, container, false);
        clipboardManager = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
        b1 = (Button) view.findViewById(R.id.button_1);
        b1.setOnClickListener(this);
        b2 = (Button) view.findViewById(R.id.button_2);
        b2.setOnClickListener(this);
        b3 = (Button) view.findViewById(R.id.button_3);
        b3.setOnClickListener(this);
        b4 = (Button) view.findViewById(R.id.button_4);
        b4.setOnClickListener(this);
        b5 = (Button) view.findViewById(R.id.button_5);
        b5.setOnClickListener(this);
        b6 = (Button) view.findViewById(R.id.button_6);
        b6.setOnClickListener(this);
        b7 = (Button) view.findViewById(R.id.button_7);
        b7.setOnClickListener(this);
        b8 = (Button) view.findViewById(R.id.button_8);
        b8.setOnClickListener(this);
        b9 = (Button) view.findViewById(R.id.button_9);
        b9.setOnClickListener(this);
        b_claer = (Button) view.findViewById(R.id.button_clear);
        b_claer.setOnClickListener(this);
        b_back = (Button) view.findViewById(R.id.button_back);
        b_back.setOnClickListener(this);
        b_devide = (Button) view.findViewById(R.id.button_devide);
        b_devide.setOnClickListener(this);
        b_plus = (Button) view.findViewById(R.id.button_plus);
        b_plus.setOnClickListener(this);
        b_minus = (Button) view.findViewById(R.id.button_minus);
        b_minus.setOnClickListener(this);
        b_multi = (Button) view.findViewById(R.id.button_multi);
        b_multi.setOnClickListener(this);
        b_equal = (Button) view.findViewById(R.id.button_equal);
        b_equal.setOnClickListener(this);
        operation = (TextView) view.findViewById(R.id.text_operation);

        Button btn = (Button) view.findViewById(R.id.btn_copy);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(operation.getText().toString())) {
                    ClipData data = ClipData.newPlainText("result", operation.getText());
                    clipboardManager.setPrimaryClip(data);
                    dismiss();
                }
            }
        });
        return view;
    }

    @Override
    public void onClick(View v) {
        if (v.equals(b1)) {
            if (operation.getText().length() == 1 && "0".equals(operation.getText())) {
                operation.setText("1");
            } else {
                operation.setText(operation.getText() + "1");
            }
        } else if (v.equals(b2)) {
            if (operation.getText().length() == 1 && "0".equals(operation.getText())) {
                operation.setText("2");
            } else {
                operation.setText(operation.getText() + "2");
            }
        } else if (v.equals(b3)) {
            if (operation.getText().length() == 1 && "0".equals(operation.getText())) {
                operation.setText("3");
            } else {
                operation.setText(operation.getText() + "3");
            }
        } else if (v.equals(b4)) {
            if (operation.getText().length() == 1 && "0".equals(operation.getText())) {
                operation.setText("4");
            } else {
                operation.setText(operation.getText() + "4");
            }
        } else if (v.equals(b5)) {
            if (operation.getText().length() == 1 && "0".equals(operation.getText())) {
                operation.setText("5");
            } else {
                operation.setText(operation.getText() + "5");
            }
        } else if (v.equals(b6)) {
            if (operation.getText().length() == 1 && "0".equals(operation.getText())) {
                operation.setText("6");
            } else {
                operation.setText(operation.getText() + "6");
            }
        } else if (v.equals(b7)) {
            if (operation.getText().length() == 1 && "0".equals(operation.getText())) {
                operation.setText("7");
            } else {
                operation.setText(operation.getText() + "7");
            }
        } else if (v.equals(b8)) {
            if (operation.getText().length() == 1 && "0".equals(operation.getText())) {
                operation.setText("8");
            } else {
                operation.setText(operation.getText() + "8");
            }
        } else if (v.equals(b9)) {
            if (operation.getText().length() == 1 && "0".equals(operation.getText())) {
                operation.setText("9");
            } else {
                operation.setText(operation.getText() + "9");
            }
        } else if (v.equals(b_claer)) {
            operation.setText("0");
        } else if (v.equals(b_back)) {
            if (operation.getText().length() != 0) {
                operation.setText(operation.getText().subSequence(0, operation.getText().length() - 1));
            }
        } else if (v.equals(b_devide)) {
            operation.setText(operation.getText() + "/");
            operatorList.add("/");
        } else if (v.equals(b_plus)) {
            operation.setText(operation.getText() + "+");
            operatorList.add("+");
        } else if (v.equals(b_minus)) {
            operation.setText(operation.getText() + "-");
            operatorList.add("-");
        } else if (v.equals(b_multi)) {
            operation.setText(operation.getText() + "X");
            operatorList.add("X");
        } else if (v.equals(b_equal)) {
            operation.setText(calc(operation.getText().toString()));
        }
    }

    List<String> operatorList = new ArrayList<>();

    private String calc(String exp) {
        ArrayList<Double> numberList = new ArrayList<Double>();
        StringTokenizer st = new StringTokenizer(exp, "X/+-");


        while (st.hasMoreTokens()) {
            double number = Double.parseDouble(st.nextToken());
            numberList.add(number);
            Log.d("aaa", String.valueOf(number));
        }

        double result = numberList.get(0);
        Log.d("aaa", String.valueOf(result));
        for (int i = 0; i < operatorList.size(); i++) {
            String operator = operatorList.get(i);

            if ("X".equals(operator)) {
                result = (result * numberList.get(i + 1));
            } else if ("/".equals(operator)) {
                result = (result / numberList.get(i + 1));
            } else if ("+".equals(operator)) {
                result = (result + numberList.get(i + 1));
            } else if ("-".equals(operator)) {
                result = (result - numberList.get(i + 1));
            }
        }

        operatorList.clear();
        numberList.clear();

        DecimalFormat df = new DecimalFormat("#.#######");

        return df.format(result);
    }
}
