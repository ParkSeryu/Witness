package com.ParkSeryu.munanmunan;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

public class DialogChangeText extends Dialog implements View.OnClickListener {
    private Context mContext;
    private TextView btn_cancel;
    private TextView btn_ok;
    private TextView tvTitle;
    private EditText edtText;
    LinearLayout li;
    InputMethodManager imm;
    static int flag = 0;
    static String contentText = "";

    public DialogChangeText(@NonNull Context context) {
        super(context);
        mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_change_text);
        li = findViewById(R.id.li);
        tvTitle = findViewById(R.id.tvDialogTitle);
        edtText = findViewById(R.id.edtText);
        imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);

        btn_cancel = findViewById(R.id.btn_cancel);
        btn_ok = findViewById(R.id.btn_ok);
        if (flag == 1) {
            tvTitle.setText("상단문구 수정하기");
            flag = 0;
        }
        else tvTitle.setText("하단문구 수정하기");
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
                contentText = edtText.getText().toString();
                if (contentText.isEmpty())
                    Toast.makeText(getContext(), "공백은 입력하실 수 없습니다.", Toast.LENGTH_SHORT).show();
                else {
                    flag = 2;
                    dismiss();
                }
                break;
        }
    }

    private void hideKeyboard() {
        imm.hideSoftInputFromWindow(edtText.getWindowToken(), 0);
    }
}
