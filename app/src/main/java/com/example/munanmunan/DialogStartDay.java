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
    private Context mContext;
    DatePicker datePickerStartDay;
    private TextView btn_cancel;
    private TextView btn_ok;
    private String Year, Month, Day, Temp;
    MyDBHelper myDBHelper;
    SQLiteDatabase sqlDB;

    public DialogStartDay(@NonNull Context context) {
        super(context);
        mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_start_day);

        myDBHelper = new MyDBHelper(getContext());
        sqlDB = myDBHelper.getWritableDatabase();


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
                    Month = "0" + String.valueOf(datePickerStartDay.getMonth() + 1);
                } else {
                    Month = String.valueOf(datePickerStartDay.getMonth() + 1);
                }
                if (datePickerStartDay.getDayOfMonth() + 1 < 10) {
                    Day = "0" + String.valueOf(datePickerStartDay.getDayOfMonth());
                } else {
                    Day = String.valueOf(datePickerStartDay.getDayOfMonth());
                }
                Temp = Year + Month + Day;

                Cursor cursor;
                cursor = sqlDB.rawQuery("SELECT startDay from meetDay;", null);
                if (cursor.getCount() == 0) {
                    sqlDB.execSQL("insert into meetDay VALUES ('" + Temp + "');");
                } else {
                    sqlDB.execSQL("update meetDay set startDay = '" + Temp + "';");
                }

                cursor.close();
                sqlDB.close();
                dismiss();
                break;
        }
    }
}

