package com.example.munanmunan;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DialogStartDay extends Dialog implements View.OnClickListener {
    private Context mContext;
    DatePicker datePickerStartDay;
    private TextView btn_cancel;
    private TextView btn_ok;

    public DialogStartDay(@NonNull Context context) {
        super(context);
        mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_start_day);

        datePickerStartDay = findViewById(R.id.datePickerStartDay);

        btn_cancel = findViewById(R.id.btn_cancel);
        btn_ok = findViewById(R.id.btn_ok);

        btn_cancel.setOnClickListener(this);
        btn_ok.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_cancel:
                dismiss();
                break;
            case R.id.btn_ok:
                String s = String.valueOf(datePickerStartDay.getYear());
                String d = String.valueOf(datePickerStartDay.getMonth());



                Toast.makeText(mContext, s + d, Toast.LENGTH_SHORT).show();
                break;
        }
    }
}

