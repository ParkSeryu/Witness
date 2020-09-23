package com.example.munanmunan;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class MainFragment extends Fragment {

    public MainFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ListView listView;
        ListViewAdapter adapter;
        adapter = new ListViewAdapter();
        listView = view.findViewById(R.id.listView_upcoming);
        listView.setAdapter(adapter);

        adapter.addItem("100일", "2020.03.04", "D-84");
        adapter.addItem("200일", "2020.02.27", "D-72");
        return view;

    }
}