package com.ParkSeryu.munanmunan;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapterAnniversary extends RecyclerView.Adapter<RecyclerViewAdapterAnniversary.ViewHolder> {

    private ArrayList<AnniversaryListItem> AnniverSaryData = null;
    Context context;

    // DB 관련
    MyDBHelper myDBHelper;
    SQLiteDatabase sqlDB;
    Cursor cursor;
    // --db

    private DialogDeleteAnniv dialogDeleteAnniv;

    // 아이템 뷰를 저장하는 뷰홀더 클래스.
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvRemainDay;
        TextView tvWhenDay;
        TextView tvDday;
        String WhatDay, WhenDay, InputDay;
        int yearRepeat, pos;

        ViewHolder(View itemView) {
            super(itemView);

            // 뷰 객체에 대한 참조.
            tvRemainDay = itemView.findViewById(R.id.tvRemainDay);
            tvWhenDay = itemView.findViewById(R.id.tvWhenDay);
            tvDday = itemView.findViewById(R.id.tvDday);

            myDBHelper = new MyDBHelper(context);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    sqlDB = myDBHelper.getWritableDatabase();
                    int position = getAdapterPosition();
                    // AnniverSaryData.remove(position);
                    Log.d("test2", "" + position);

                    cursor = sqlDB.rawQuery("SELECT * FROM anniversary Union SELECT WhatDay, WhenDay, Dday FROM anniversary_user order by WhenDay;", null);

                    for (int i = 0; i <= position; i++) {
                        cursor.moveToNext();
                    }
                    WhatDay = cursor.getString(0);
                    Log.d("test22", "" + WhatDay);


                    cursor = sqlDB.rawQuery("SELECT WhatDay, WhenDay, InputDay, yearRepeat from anniversary_user WHERE WhatDay ='" + WhatDay + "';", null);
                    if (cursor.getCount() != 0) {
                        cursor.moveToNext();
                        WhatDay = cursor.getString(0);
                        WhenDay = cursor.getString(1);
                        InputDay = cursor.getString(2);
                        yearRepeat = cursor.getInt(cursor.getColumnIndex("yearRepeat"));
                        Log.d("test23", "" + WhatDay + "/ " + WhenDay + " / " + yearRepeat + "/ " + InputDay + " / ");
                        dialogDeleteAnniv = new DialogDeleteAnniv(context);
                        dialogDeleteAnniv.setCancelable(false);
                        dialogDeleteAnniv.show();
                        dialogDeleteAnniv.setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialog) {
                                sqlDB = myDBHelper.getWritableDatabase();
                                if (DialogDeleteAnniv.flag == 1) {
                                    if (yearRepeat == 1) {
                                        Log.d("test222", "" + WhatDay + "/ " + WhenDay + " / " + yearRepeat + "/ " + InputDay);
                                        sqlDB.execSQL("DELETE from anniversary_user WHERE WhatDay ='" + WhatDay + "'" + "AND InputDay = '" + InputDay + "';");
                                    } else {
                                        sqlDB.execSQL("DELETE from anniversary_user WHERE WhatDay ='" + WhatDay + "'" + "AND WhenDay = '" + WhenDay + "';");
                                    }
                                    AnniverSaryData.clear();
                                    cursor = sqlDB.rawQuery("SELECT * FROM anniversary Union SELECT WhatDay, WhenDay, Dday FROM anniversary_user order by WhenDay;", null);
                                    while (cursor.moveToNext()) {
                                        addItem(cursor.getString(0), cursor.getString(1), cursor.getString(2));
                                        if (cursor.getString(2).contains("+"))
                                            pos = cursor.getColumnCount();
                                    }
                                    notifyDataSetChanged();
                                    DialogDeleteAnniv.flag = 0;
                                    cursor.close();
                                    sqlDB.close();
                                }
                            }
                        });
                    }
                    return false;
                }
            });

        }
    }

    //생성자에서 데이터 리스트 객체를 전달받음.
    RecyclerViewAdapterAnniversary(ArrayList<AnniversaryListItem> list) {
        AnniverSaryData = list;
    }

    public void addItem(String RemainDay, String WhenDay, String Dday) {
        AnniversaryListItem item = new AnniversaryListItem();

        item.setRemainDay(RemainDay);
        item.setWhenDay(WhenDay);
        item.setDday(Dday);

        AnniverSaryData.add(item);
    }

    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.recyclerview_anniversary_item, parent, false);
        RecyclerViewAdapterAnniversary.ViewHolder vh = new RecyclerViewAdapterAnniversary.ViewHolder(view);

        return vh;
    }

    // onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapterAnniversary.ViewHolder holder, int position) {
        AnniversaryListItem item = AnniverSaryData.get(position);
        holder.tvRemainDay.setText(item.getRemainDay());
        holder.tvWhenDay.setText(item.getWhenDay());
        holder.tvDday.setText(item.getdDay());

        if (holder.tvDday.getText().subSequence(1, 2).equals("+")) {
            holder.tvRemainDay.setTextColor(Color.GRAY);
            holder.tvWhenDay.setTextColor(Color.GRAY);
            holder.tvDday.setTextColor(Color.GRAY);
        } else {
            holder.tvRemainDay.setTextColor(Color.rgb(82, 82, 82));
            holder.tvWhenDay.setTextColor(Color.rgb(82, 82, 82));
            holder.tvDday.setTextColor(Color.rgb(82, 82, 82));
        }
    }


    //getItemCount() - 전체 데이터 갯수 리턴.
    @Override
    public int getItemCount() {
        return AnniverSaryData.size();
    }
}

