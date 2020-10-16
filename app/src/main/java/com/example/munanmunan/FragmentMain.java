package com.example.munanmunan;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
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
    Calendar calendar, calendarAni;
    SimpleDateFormat simpleDateFormat, simpleDateFormatAnniv;
    RecyclerView recyclerView;
    RecyclerViewAdapterAnniversary recyclerViewAdapterAnniversary;

    private DialogAddAnniv dialogAddAniv;
    private String sYear, sMonth, sDay;
    private Date startDate, currentDate;
    private String sStart, sCurrent;
    private long calDateDays;
    static int dialogOk;

    // 리사이클러뷰에 표시할 데이터 리스트 생성.
    ArrayList<AnniversaryListItem> mList = new ArrayList<>();

    public FragmentMain() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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

        calendar = new GregorianCalendar();
        simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        simpleDateFormatAnniv = new SimpleDateFormat("yyyy. MM. dd ");

        // 리사이클러뷰에 LinearLayoutManager 객체 지정.
        recyclerView = view.findViewById(R.id.RecyclerView_upcoming);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(view.getContext(), 1));

        // 리사이클러뷰에 Adapter 객체 지정.
        recyclerViewAdapterAnniversary = new RecyclerViewAdapterAnniversary(mList);
        recyclerView.setAdapter(recyclerViewAdapterAnniversary);


        dialogStartDay = new DialogStartDay(getContext());
        dialogStartDay.setCancelable(false);

        myDBHelper = new MyDBHelper(getContext());
        sqlDB = myDBHelper.getWritableDatabase();
        //myDBHelper.onUpgrade(sqlDB, 0, 1); // 막 건들이지 말것. 위험

        cursor = sqlDB.rawQuery("SELECT startDay from meetDay;", null);
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
            datePickerSetDate();
        }

        tvMainDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogStartDay.show();
                dialogStartDay.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        sqlDB = myDBHelper.getWritableDatabase();
                        cursor = sqlDB.rawQuery("SELECT startDay from meetDay;", null);

                        if (dialogOk == 1 && cursor.getCount() == 0) {
                            sqlDB.execSQL("insert into meetDay VALUES ('" + DialogStartDay.TempSaveDay + "');");
                            datePickerSetDate();
                            dialogOk = 0;
                        } else if (dialogOk == 1 && cursor.getCount() > 0) {
                            sqlDB.execSQL("update meetDay set startDay = '" + DialogStartDay.TempSaveDay + "';");
                            datePickerSetDate();
                        }

                    }
                });
            }
        });


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

        long calDate;
        int position = 0;
        cursor = sqlDB.rawQuery("SELECT startDay from meetDay;", null);
        cursor.moveToLast();
        sStart = cursor.getString(0);
        sYear = String.valueOf(calendar.get(Calendar.YEAR));
        if ((calendar.get(Calendar.MONTH) + 1) < 10) {
            sMonth = "0" + calendar.get(Calendar.MONTH) + 1;
        } else
            sMonth = String.valueOf(calendar.get(Calendar.MONTH) + 1);

        if ((calendar.get(Calendar.DATE)) < 10) {
            sDay = "0" + calendar.get(Calendar.DATE); // 객체 생성
        } else
            sDay = String.valueOf(calendar.get(Calendar.DATE));

        sCurrent = sYear + sMonth + sDay;
        Log.d("test", sStart + "/" + sCurrent);
        try {
            currentDate = simpleDateFormat.parse(sCurrent);
            startDate = simpleDateFormat.parse(sStart);
            calDate = currentDate.getTime() - startDate.getTime();

            calDateDays = calDate / (24 * 60 * 60 * 1000);
            calDateDays = Math.abs(calDateDays);
            Log.d("test1", calDateDays + "");
            calDateDays += 1;
        } catch (ParseException e) {
            Log.d("exception test1", "" + calDateDays);
        }

        tvMainDay.setText(calDateDays + "일"); // 몇일인가 설정

        //기념일 만들기
        String[] remainDay = {"시작한날", "100일", "200일", "300일", "1주년", "400일", "500일", "600일", "700일", "2주년", "800일", "900일", "1000일",
                "3주년", "1100일", "1200일", "1300일", "1400일", "4주년", "1500일", "1600일", "1700일", "1800일", "5주년", "1900일", "2000일", "2100일", "6주년", "2200일", "2300일",
                "2400일", "2500일", "7주년", "2600일", "2700일", "2800일", "2900일", "8주년", "3000일", "3100일", "3200일", "9주년", "3300일", "3400일", "3500일", "3600일", "10주년"};
        int[] remainDay_int = {1, 100, 200, 300, 0, 400, 500, 600, 700, 0, 800, 900, 1000, 0, 1100, 1200, 1300, 1400, 0, 1500, 1600, 1700, 1800, 0, 1900, 2000, 2100, 0, 2200, 2300,
                2400, 2500, 0, 2600, 2700, 2800, 2900, 0, 3000, 3100, 3200, 0, 3300, 3400, 3500, 3600, 0};
        String whenDay;
        String Dday;

        calendarAni = new GregorianCalendar();

        sqlDB.execSQL("DELETE FROM anniversary"); //초기화
        mList.clear();
        for (int i = 0, j = 0; i < remainDay.length; i++) {
            calendarAni.setTime(startDate); // 기념일 set
            if (remainDay_int[i] == 0) {
                j++;
                calendarAni.add(Calendar.YEAR, j);
            } else
                calendarAni.add(Calendar.DAY_OF_MONTH, remainDay_int[i] - 1);

            whenDay = simpleDateFormatAnniv.format(calendarAni.getTime());

            switch (calendarAni.get(Calendar.DAY_OF_WEEK)) {
                case 1:
                    whenDay += "(일)";
                    break;
                case 2:
                    whenDay += "(월)";
                    break;
                case 3:
                    whenDay += "(화)";
                    break;
                case 4:
                    whenDay += "(수)";
                    break;
                case 5:
                    whenDay += "(목)";
                    break;
                case 6:
                    whenDay += "(금)";
                    break;
                case 7:
                    whenDay += "(토)";
                    break;
                default:
                    whenDay = "오류";
            }

            Date cal = new Date(calendarAni.getTimeInMillis());
            calDate = currentDate.getTime() - cal.getTime();
            if (currentDate.getTime() > cal.getTime()) {
                Dday = "D+";
                position = i;
            } else
                Dday = "D-";
            calDateDays = calDate / (24 * 60 * 60 * 1000);


            if (i == 0)
                calDateDays = Math.abs(calDateDays) + 1;
            else
                calDateDays = Math.abs(calDateDays);

            Dday += calDateDays;
            if (calDate == 0)
                Dday = "오늘";

            sqlDB.execSQL("insert into anniversary VALUES ('" + remainDay[i] + "' , '" +
                    whenDay + "','" +
                    Dday + "');");
            addItem(remainDay[i], whenDay, Dday);

        }

        recyclerViewAdapterAnniversary.notifyDataSetChanged();
        recyclerView.scrollToPosition(position + 1);
        RecyclerViewAdapterAnniversary.go = 0;
        cursor.close();
        sqlDB.close();
    }
}