package com.example.munanmunan;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapterBucketList extends RecyclerView.Adapter<RecyclerViewAdapterBucketList.ViewHolder> {
    Context context;
    private ArrayList<BucketListItem> BucketListData = null;
    private DialogBucketList dialogBucketList;

    // 아이템 뷰를 저장하는 뷰홀더 클래스.
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView BucketListContent;
        ImageButton BucketListModified;
        ImageButton BucketListDeleted;

        int flag;

        ViewHolder(View itemView) {
            super(itemView);

            // 뷰 객체에 대한 참조.
            BucketListContent = itemView.findViewById(R.id.bucketListContent);
            BucketListModified = itemView.findViewById(R.id.bucketListModified);
            BucketListDeleted = itemView.findViewById(R.id.bucketListDelete);

            BucketListContent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (flag == 0) {
                        BucketListContent.setPaintFlags(BucketListContent.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                        flag = 1;
                    } else {
                        BucketListContent.setPaintFlags(0);
                        flag = 0;
                    }

                }
            });


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
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        BucketListItem item = BucketListData.get(position);

        holder.BucketListContent.setText(item.getBucketListContent());
        holder.BucketListModified.setImageDrawable(item.getBucketListModified());
        holder.BucketListDeleted.setImageDrawable(item.getBucketListDelete());

        holder.BucketListModified.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogBucketList.flag = 1;
                dialogBucketList = new DialogBucketList(context);
                dialogBucketList.setCancelable(false);
                dialogBucketList.show();
                dialogBucketList.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        holder.BucketListContent.setText(DialogBucketList.content);
                    }
                });
            }
        });


        holder.BucketListDeleted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sqlDB.execSQL("DELETE FROM * WHERE list =" + item.getBucketListContent() )
                BucketListData.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, BucketListData.size());
            }
        });

    }

    //getItemCount() - 전체 데이터 갯수 리턴
    @Override
    public int getItemCount() {
        return BucketListData.size();
    }
}
