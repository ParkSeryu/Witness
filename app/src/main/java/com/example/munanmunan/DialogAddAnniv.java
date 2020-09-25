package com.example.munanmunan;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

public class DialogAddAnniv extends Dialog implements View.OnClickListener {
    private Context mContext;
    private TextView btn_cancel;
    private TextView btn_ok;
    private EditText edtNewAnniv;
    InputMethodManager imm;
    LinearLayout li;

    public DialogAddAnniv(@NonNull Context context) {
        super(context);
        mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_add_anniversary);

        li = findViewById(R.id.li);
        imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);

        edtNewAnniv = findViewById(R.id.edtNewAnniv);

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
                ((MainActivity) mContext).finish();
                break;
        }
    }

    private void hideKeyboard(){
        imm.hideSoftInputFromWindow(edtNewAnniv.getWindowToken(), 0);
    }


}