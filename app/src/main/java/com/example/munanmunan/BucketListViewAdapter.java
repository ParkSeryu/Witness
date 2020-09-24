package com.example.munanmunan;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class BucketListViewAdapter extends BaseAdapter {
    int flag = 0;
    // Adapter에 추가된 데이터를 저장하기 위한 ArrayList
    private ArrayList<BucketListViewItem> listViewItemList = new ArrayList<BucketListViewItem>();
    private DialogBucketList dialogBucketList;

    //ListViewAdapter의 생성자
    public BucketListViewAdapter() {

    }

    //Adpater에 사용되는 데이터의 개수를 리턴.:필수구현
    @Override
    public int getCount() {
        return listViewItemList.size();
    }

    // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴. : 필수 구현
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        // listview_item Layout을 inflate하여 convertview 참조 획득
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.bucketlist_listview_item, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된) 으로부터 위젯에 대한 참조 획득
        final TextView BucketListContentView = convertView.findViewById(R.id.bucketListContent);
        ImageButton BucketListModifiedView = convertView.findViewById(R.id.bucketListModified);
        ImageButton BucketListDeleteView = convertView.findViewById(R.id.bucketListDelete);


        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        BucketListViewItem bucketListViewItem = listViewItemList.get(position);

        //아이템 내 각 위젯에 데이터 반영
        BucketListContentView.setText(bucketListViewItem.getBucketListContent());
        BucketListModifiedView.setImageDrawable(bucketListViewItem.getBucketListModified());
        BucketListDeleteView.setImageDrawable(bucketListViewItem.getBucketListDelete());

        BucketListContentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag == 0) {
                    BucketListContentView.setPaintFlags(BucketListContentView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    flag = 1;
                } else {
                    BucketListContentView.setPaintFlags(0);
                    flag = 0;
                }

            }
        });

        BucketListModifiedView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogBucketList = new DialogBucketList(context);
                dialogBucketList.setCancelable(false);
                dialogBucketList.show();
            }
        });

        BucketListDeleteView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BucketListContentView.setText("d에에에에");
            }
        });

        return convertView;
    }

    // 지정한 위치(position)에 있는 데이터와 관계된 아이템(row)의 ID를 리턴. : 필수 구현
    @Override
    public long getItemId(int position) {
        return position;
    }

    // 지정한 위치(position)에 있는 데이터 리턴 : 필수 구현
    @Override
    public Object getItem(int position) {
        return listViewItemList.get(position);
    }

    // 아이템 데이터 추가를 위한 함수, 개발자가 원하는대로 작성 가능
    public void addItem(String content, Drawable modifiedImage, Drawable deleteImage) {
        BucketListViewItem bucketListViewItem = new BucketListViewItem();
        bucketListViewItem.setBucketListContent(content);
        bucketListViewItem.setBucketListModified(modifiedImage);
        bucketListViewItem.setBucketListDelete(deleteImage);

        listViewItemList.add(bucketListViewItem);
    }


}
