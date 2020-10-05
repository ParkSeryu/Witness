package com.example.munanmunan;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class FragmentMain extends Fragment {

    // DB 관련
    MyDBHelper myDBHelper;
    SQLiteDatabase sqlDB;
    Cursor cursor;
    // --db

    ImageButton btnGoBcl;
    TextView toolbarText, tvMainDay;
    ImageButton btnPlus;
    DialogStartDay dialogStartDay;
    private DialogAddAnniv dialogAddAniv;
    private String sYear, sMonth, sDay;
    private Calendar calendar;
    private Date startDate, currentDate;
    private String sStart, sCurrent;
    private long calDateDays;
    static int dialogOk;
    SimpleDateFormat simpleDateFormat;
    int Day;

    // 리사이클러뷰에 표시할 데이터 리스트 생성.
    ArrayList<AnniversaryListItem> mList = new ArrayList<>();

    public FragmentMain() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        calendar = new GregorianCalendar();
        simpleDateFormat = new SimpleDateFormat("yyyyMMdd");

        dialogStartDay = new DialogStartDay(getContext());
        dialogStartDay.setCancelable(false);

        myDBHelper = new MyDBHelper(getContext());
        sqlDB = myDBHelper.getWritableDatabase();
        //myDBHelper.onUpgrade(sqlDB, 0, 1); // 막 건들이지 말것. 위험

        cursor = sqlDB.rawQuery("SELECT startDay from meetDay;", null);
        // String s = String.valueOf(cursor.getCount());
        if (cursor.getCount() == 0) {
            dialogStartDay.show();
            dialogStartDay.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    if (dialogOk == 1) {
                        sqlDB.execSQL("insert into meetDay VALUES ('" + DialogStartDay.TempSaveDay + "');");
                        datePickerSetDate();
                        dialogOk = 0;
                    }
                }
            });
        } else {
            if (dialogOk == 1) {
                sqlDB.execSQL("update meetDay set startDay = '" + DialogStartDay.TempSaveDay + "';");
                dialogOk = 0;
            }
            datePickerSetDate();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);


        btnGoBcl = view.findViewById(R.id.goBcl);
        btnGoBcl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.flag = 1;
                ((MainActivity) getActivity()).replaceFragment();
            }
        });

        toolbarText = view.findViewById(R.id.toolbarText);
        toolbarText.setText("Witness");

        btnPlus = view.findViewById(R.id.btnPlus);
        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogAddAniv = new DialogAddAnniv(getContext());
                dialogAddAniv.setCancelable(false);
                dialogAddAniv.show();
            }
        });

        tvMainDay = view.findViewById(R.id.tvMainDay);
        tvMainDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogStartDay.show();
                dialogStartDay.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        if(dialogOk == 1){
                        datePickerSetDate();
                        dialogOk = 0;
                        }

                    }
                });
            }
        });


        // 리사이클러뷰에 LinearLayoutManager 객체 지정.
        RecyclerView recyclerView = view.findViewById(R.id.RecyclerView_upcoming);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(view.getContext(), 1));

        // 리사이클러뷰에 SimpleTextAdapter 객체 지정.
        RecyclerViewAdapterAnniversary recyclerViewAdapterAnniversary = new RecyclerViewAdapterAnniversary(mList);
        recyclerView.setAdapter(recyclerViewAdapterAnniversary);

        addItem("100일", "2020.03.04", "D-84");
        addItem("200일", "2020.02.27", "D-72");
        addItem("300일", "2020.02.27", "D-72");
        addItem("400일", "2020.02.27", "D-72");
        addItem("500일", "2020.02.27", "D-72");
        addItem("600일", "2020.02.27", "D-72");
        addItem("700일", "2020.02.27", "D-72");
        addItem("800일", "2020.02.27", "D-72");
        addItem("900일", "2020.02.27", "D-72");
        addItem("1000일", "2020.02.27", "D-72");
        addItem("110일", "2020.02.27", "D-72");
        addItem("1200일", "2020.02.27", "D-72");

        recyclerViewAdapterAnniversary.notifyDataSetChanged();
        return view;
    }


    public void addItem(String RemainDay, String WhenDay, String Dday) {
        AnniversaryListItem item = new AnniversaryListItem();
        item.setRemainDay(RemainDay);
        item.setWhenDay(WhenDay);
        item.setDday(Dday);

        mList.add(item);
    }

    private void datePickerSetDate() {
        cursor.moveToLast();
        sStart = cursor.getString(0);
        sYear = String.valueOf(calendar.get(Calendar.YEAR));
        if ((calendar.get(Calendar.MONTH) + 1) < 10) {
            sMonth = "0" + String.valueOf(calendar.get(Calendar.MONTH) + 1);
        } else
            sMonth = String.valueOf(calendar.get(Calendar.MONTH) + 1);

        if ((calendar.get(Calendar.DATE)) < 10) {
            sDay = "0" + String.valueOf(calendar.get(Calendar.DATE)); // 객체 생성
        } else
            sDay = String.valueOf(calendar.get(Calendar.DATE));

        sCurrent = sYear + sMonth + sDay;
        Log.d("test", sStart + "/" + sCurrent);
        try {
            currentDate = simpleDateFormat.parse(sCurrent);
            startDate = simpleDateFormat.parse(sStart);
            long calDate = currentDate.getTime() - startDate.getTime();
            calDateDays = calDate / (24 * 60 * 60 * 1000);
            calDateDays = Math.abs(calDateDays);
        } catch (ParseException e) {
            Log.d("exception test", "" + calDateDays);
        }
        tvMainDay.setText(calDateDays + "일");
        cursor.close();
        sqlDB.close();
    }

}