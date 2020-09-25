package com.example.munanmunan;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

public class DialogBucketList extends Dialog implements View.OnClickListener {
    private Context mContext;
    private TextView btn_cancel;
    private TextView btn_ok;
    private TextView tvTitle;
    private EditText edtBucketList;
    static int flag = 0;

    public DialogBucketList(@NonNull Context context) {
        super(context);
        mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_bucket_list);
        tvTitle = findViewById(R.id.tvDialogTitle);
        edtBucketList = findViewById(R.id.edtBucketList);

        btn_cancel = findViewById(R.id.btn_cancel);
        btn_ok = findViewById(R.id.btn_ok);
        if (flag == 1) {
            tvTitle.setText("버킷리스트 수정하기");
            btn_ok.setText("수정");
            flag = 0;
        }
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
                ((MainActivity) mContext).finish();
                break;
        }
    }
}
