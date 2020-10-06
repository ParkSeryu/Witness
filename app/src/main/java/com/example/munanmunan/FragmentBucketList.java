package com.example.munanmunan;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class FragmentBucketList extends Fragment {
    RecyclerView mRecyclerView = null;
    RecyclerViewAdapterBucketList mAdapter = null;
    ArrayList<BucketListItem> mList = new ArrayList<BucketListItem>();
    private DialogBucketList dialogBucketList;
    private ImageButton btnGoCalendar;


    public FragmentBucketList() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bucketlist, container, false);
        TextView toobarText = view.findViewById(R.id.toolbarText);
        toobarText.setText("Bucket List");

        btnGoCalendar = view.findViewById(R.id.btnGoCalendar);
        btnGoCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.flag = 0;
                ((MainActivity)getActivity()).replaceFragment();
            }
        });


        mRecyclerView = view.findViewById(R.id.recyclerView_bucketList);
        mAdapter = new RecyclerViewAdapterBucketList(mList);
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(view.getContext(), 1));

        addItem("롯데월드 가기", ContextCompat.getDrawable(getActivity(), R.drawable.ic_modified), ContextCompat.getDrawable(getActivity(), R.drawable.ic_delete));
        addItem("에버랜드 가기", ContextCompat.getDrawable(getActivity(), R.drawable.ic_modified), ContextCompat.getDrawable(getActivity(), R.drawable.ic_delete));
        addItem("에버랜드 가기", ContextCompat.getDrawable(getActivity(), R.drawable.ic_modified), ContextCompat.getDrawable(getActivity(), R.drawable.ic_delete));
        addItem("에버랜드 가기", ContextCompat.getDrawable(getActivity(), R.drawable.ic_modified), ContextCompat.getDrawable(getActivity(), R.drawable.ic_delete));
        addItem("에버랜드 가기", ContextCompat.getDrawable(getActivity(), R.drawable.ic_modified), ContextCompat.getDrawable(getActivity(), R.drawable.ic_delete));


        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialogBucketList = new DialogBucketList(getContext());
                dialogBucketList.setCancelable(false);
                dialogBucketList.show();
            }
        });


        return view;


           /* 리스트뷰 == > 리사이클러뷰로 변경. 20.09.24.
        ListView bucketListView;
        BucketListViewAdapter bucketListViewAdapter;
        bucketListViewAdapter = new BucketListViewAdapter();
        bucketListView = view.findViewById(R.id.bucketListView);
        bucketListView.setAdapter(bucketListViewAdapter);
        bucketListViewAdapter.addItem("석촌호수가기", ContextCompat.getDrawable(getActivity(), R.drawable.ic_menu_camera), ContextCompat.getDrawable(getActivity(), R.drawable.ic_menu_gallery));
        bucketListViewAdapter.addItem("에버랜드가기", ContextCompat.getDrawable(getActivity(), R.drawable.ic_menu_camera), ContextCompat.getDrawable(getActivity(), R.drawable.ic_menu_gallery));
        */
    }

    public void addItem(String content, Drawable modifisrc, Drawable deletesrc) {
        BucketListItem item = new BucketListItem();

        item.setBucketListContent(content);
        item.setBucketListModified(modifisrc);
        item.setBucketListDelete(deletesrc);

        mList.add(item);
    }
}
