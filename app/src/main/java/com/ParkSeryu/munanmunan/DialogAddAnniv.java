package com.ParkSeryu.munanmunan;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class DialogAddAnniv extends Dialog implements View.OnClickListener {
    Context mContext;
    TextView btn_cancel;
    TextView btn_ok;
    private EditText edtNewAnniv;
    DatePicker datePickerAddAniv;
    private CheckBox cbRepeatYear;
    InputMethodManager imm;
    LinearLayout li;
    String strAniv, Year, Month, Day;
    String checkboxFlag;
    Calendar calendar;
    static String TempSaveDay[] = new String[3];

    public DialogAddAnniv(@NonNull Context context) {
        super(context);
        mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_add_anniversary);

        calendar = new GregorianCalendar();

        li = findViewById(R.id.li);
        imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);

        edtNewAnniv = findViewById(R.id.edtNewAnniv);
        cbRepeatYear = findViewById(R.id.cbRepeatYear);
        datePickerAddAniv = findViewById(R.id.datePickerAddAniv);

        btn_cancel = findViewById(R.id.btn_cancel);
        btn_ok = findViewById(R.id.btn_ok);
        li.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);
        btn_ok.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        hideKeyboard();

        switch (v.getId()) {
            case R.id.btn_cancel:
                dismiss();
                break;
            case R.id.btn_ok:
                if (cbRepeatYear.isChecked()) {
                    checkboxFlag = "1";
                } else {
                    checkboxFlag = "0";
                }

                strAniv = edtNewAnniv.getText().toString();
                Year = String.valueOf(datePickerAddAniv.getYear());
                if (datePickerAddAniv.getMonth() + 1 < 10) {
                    Month = "0" + (datePickerAddAniv.getMonth() + 1);
                } else {
                    Month = String.valueOf(datePickerAddAniv.getMonth() + 1);
                }
                if (datePickerAddAniv.getDayOfMonth() + 1 < 10) {
                    Day = "0" + datePickerAddAniv.getDayOfMonth();
                } else {
                    Day = String.valueOf(datePickerAddAniv.getDayOfMonth());
                }

                if (strAniv.isEmpty()) {
                    Toast.makeText(getContext(), "기념일 명을 입력해주세요!", Toast.LENGTH_SHORT).show();
                } else {
                    TempSaveDay[0] = Year + Month + Day;
                    TempSaveDay[1] = strAniv;
                    TempSaveDay[2] = checkboxFlag;
                    FragmentMain.dialogOk = 2;
                    dismiss();
                    break;
                }
        }
    }

    private void hideKeyboard() {
        imm.hideSoftInputFromWindow(edtNewAnniv.getWindowToken(), 0);
    }
}

