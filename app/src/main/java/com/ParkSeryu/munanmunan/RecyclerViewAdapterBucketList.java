package com.ParkSeryu.munanmunan;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapterBucketList extends RecyclerView.Adapter<RecyclerViewAdapterBucketList.ViewHolder> {
    Context context;
    private ArrayList<BucketListItem> BucketListData = null;
    private DialogBucketList dialogBucketList;

    // DB 관련
    MyDBHelper myDBHelper;
    SQLiteDatabase sqlDB;
    Cursor cursor;
    // --db

    // 아이템 뷰를 저장하는 뷰홀더 클래스.
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView BucketListContent;
        ImageButton BucketListModified;
        ImageButton BucketListDeleted;

        ViewHolder(final View itemView) {
            super(itemView);


            View view = LayoutInflater.from(context).inflate(R.layout.dialog_bucket_list, null, false);
            final EditText editTextBL = view.findViewById(R.id.edtBucketList);

            // 뷰 객체에 대한 참조.
            BucketListContent = itemView.findViewById(R.id.bucketListContent);
            BucketListModified = itemView.findViewById(R.id.bucketListModified);
            BucketListDeleted = itemView.findViewById(R.id.bucketListDelete);
        }
    }

    //생성자에서 데이터 리스트 객체를 전달받음.
    RecyclerViewAdapterBucketList(ArrayList<BucketListItem> list) {
        BucketListData = list;
    }

    //onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.recyclerview_bucketlist_item, parent, false);
        RecyclerViewAdapterBucketList.ViewHolder vh = new RecyclerViewAdapterBucketList.ViewHolder(view);
        return vh;
    }

    // onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        myDBHelper = new MyDBHelper(context);
        BucketListItem item = BucketListData.get(position);

        holder.BucketListContent.setText(item.getBucketListContent());
        holder.BucketListContent.setPaintFlags(item.getBucketListclear());
        holder.BucketListModified.setImageDrawable(item.getBucketListModified());
        holder.BucketListDeleted.setImageDrawable(item.getBucketListDelete());


        holder.BucketListContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sqlDB = myDBHelper.getWritableDatabase();
                cursor = sqlDB.rawQuery("SELECT list, InputDay from bucketListDay;", null);
                for (int i = 0; i <= position; i++) {
                    cursor.moveToNext();
                }
                String matchList = cursor.getString(0);
                String matchDay = cursor.getString(1);

                if (holder.BucketListContent.getPaintFlags() == Paint.STRIKE_THRU_TEXT_FLAG || holder.BucketListContent.getPaintFlags() == 1299) {
                    holder.BucketListContent.setPaintFlags(0);
                    sqlDB.execSQL("update bucketListDay set clear = '" + 0 + "'" + "WHERE list = '" + matchList + "'"
                            + "AND InputDay = '" + matchDay
                            + "';");
                } else {
                    Log.d("test22", holder.BucketListContent.getPaintFlags() + "/" + Paint.STRIKE_THRU_TEXT_FLAG);
                    holder.BucketListContent.setPaintFlags(holder.BucketListContent.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    sqlDB.execSQL("update bucketListDay set clear = '" + 1 + "'" + "WHERE list = '" + matchList + "'"
                            + "AND InputDay = '" + matchDay
                            + "';");
                }

                sqlDB.close();
                cursor.close();
            }
        });


        holder.BucketListModified.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogBucketList.flag = 1;
                dialogBucketList = new DialogBucketList(context);
                dialogBucketList.setCancelable(false);
                //editTextBL.setText(BucketListData.get(get));
                dialogBucketList.show();
                dialogBucketList.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        sqlDB = myDBHelper.getWritableDatabase();
                        holder.BucketListContent.setText(DialogBucketList.content);
                        cursor = sqlDB.rawQuery("SELECT list, InputDay FROM bucketListDay;", null);
                        for (int i = 0; i <= position; i++) {
                            cursor.moveToNext();
                        }
                        String matchList = cursor.getString(0);
                        String matchDay = cursor.getString(1);

                        sqlDB.execSQL("update bucketListDay set list = '" + DialogBucketList.content + "'" + "WHERE list = '" + matchList + "'"
                                + "AND InputDay = '" + matchDay
                                + "';");
                        sqlDB.close();
                        cursor.close();
                    }
                });

            }
        });


        holder.BucketListDeleted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sqlDB = myDBHelper.getWritableDatabase();
                cursor = sqlDB.rawQuery("SELECT list, InputDay FROM bucketListDay;", null);
                for (int i = 0; i <= position; i++) {
                    cursor.moveToNext();
                }
                String matchList = cursor.getString(0);
                String matchDay = cursor.getString(1);

                sqlDB.execSQL("DELETE FROM bucketListDay WHERE list = '" + matchList + "'" + "AND InputDay = '" + matchDay + "';");
                BucketListData.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, BucketListData.size());


                sqlDB.close();
                cursor.close();
            }

        });


    }

    //getItemCount() - 전체 데이터 갯수 리턴
    @Override
    public int getItemCount() {
        return BucketListData.size();
    }
}
