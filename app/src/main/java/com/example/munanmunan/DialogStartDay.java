package com.example.munanmunan;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DialogStartDay extends Dialog implements View.OnClickListener {
    Context mContext;
    DatePicker datePickerStartDay;
    TextView btn_cancel;
    TextView btn_ok;
    String Year, Month, Day;
    private Calendar calendar;
    public static String TempSaveDay;

    public DialogStartDay(@NonNull Context context) {
        super(context);
        mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_start_day);

        calendar = new GregorianCalendar();

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
                Year = String.valueOf(datePickerStartDay.getYear());
                if (datePickerStartDay.getMonth() + 1 < 10) {
                    Month = "0" + (datePickerStartDay.getMonth() + 1);
                } else {
                    Month = String.valueOf(datePickerStartDay.getMonth() + 1);
                }
                if (datePickerStartDay.getDayOfMonth() + 1 < 10) {
                    Day = "0" + datePickerStartDay.getDayOfMonth();
                } else {
                    Day = String.valueOf(datePickerStartDay.getDayOfMonth());
                }
                long setdate = 365 * datePickerStartDay.getYear() + 30 * datePickerStartDay.getMonth() + datePickerStartDay.getDayOfMonth();
                long currentdate = 365 * calendar.get(Calendar.YEAR) + 30 * calendar.get(Calendar.MONTH) + calendar.get(Calendar.DATE);
                if (setdate <= currentdate) {
                    TempSaveDay = Year + Month + Day;
                    Log.d("test333", Year +"/" + Month + "/" + Day);
                    FragmentMain.dialogOk = 1;
                    dismiss();
                    break;
                } else {
                    Toast.makeText(mContext, "오늘 이전으로 설정 해 주세요!", Toast.LENGTH_SHORT).show();
                }
        }
    }
}

