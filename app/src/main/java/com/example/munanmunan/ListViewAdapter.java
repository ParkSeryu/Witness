package com.example.munanmunan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import androidx.fragment.app.FragmentActivity;

import java.util.ArrayList;

public class ListViewAdapter extends BaseAdapter {

   private ArrayList<AnniversaryListViewItem> listViewItemList = new ArrayList<AnniversaryListViewItem>();

   //ListViewAdapter의 생성자
   public ListViewAdapter(){

    }


    @Override
    public int getCount() {
        return listViewItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return listViewItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       final int pos = position;
       final Context context = parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.anniversary_listview_item, parent, false);
        }

        TextView remainDay = convertView.findViewById(R.id.tvRemainDay);
        TextView whenDay = convertView.findViewById(R.id.tvWhenDay);
        TextView dDay = convertView.findViewById(R.id.tvDday);

        AnniversaryListViewItem anniversaryListViewItem = listViewItemList.get(position);

        remainDay.setText(anniversaryListViewItem.getRemainDay());
        whenDay.setText(anniversaryListViewItem.getWhenDay());
        dDay.setText(anniversaryListViewItem.getdDay());

       return convertView;
    }


    public void addItem(String remainDay, String whenDay, String dDay) {
        AnniversaryListViewItem item = new AnniversaryListViewItem();
        item.setRemainDay(remainDay);
        item.setWhenDay(whenDay);
        item.setDday(dDay);

        listViewItemList.add(item);
    }

}
