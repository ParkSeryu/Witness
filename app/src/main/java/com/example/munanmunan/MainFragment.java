package com.example.munanmunan;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainFragment extends Fragment {

    buttonClick click;
    ImageButton btnGoBcl;
    TextView toolbarText, tvMainDay;
    Button btnPlus;
    private DialogSelectDay dialogSelectDay;
    private DialogAddAnniv dialogAddAniv;

    interface buttonClick {
        void buttonClicked(View v);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        click = (buttonClick) activity;
    }

    // 리사이클러뷰에 표시할 데이터 리스트 생성.
    ArrayList<AnniversaryListViewItem> mList = new ArrayList<>();

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


        btnGoBcl = view.findViewById(R.id.goBcl);
        btnGoBcl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click.buttonClicked(v);
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
                dialogSelectDay = new DialogSelectDay(getContext());
                dialogSelectDay.setCancelable(false);
                dialogSelectDay.show();
            }
        });


        // 리사이클러뷰에 LinearLayoutManager 객체 지정.
        RecyclerView recyclerView = view.findViewById(R.id.RecyclerView_upcoming);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        recyclerView.addItemDecoration(new DividerItemDecoration(view.getContext(), 1));

        // 리사이클러뷰에 SimpleTextAdapter 객체 지정.
        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(mList);
        recyclerView.setAdapter(recyclerViewAdapter);


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

        recyclerViewAdapter.notifyDataSetChanged();
        return view;

    }

    public void addItem(String RemainDay, String WhenDay, String Dday) {
        AnniversaryListViewItem item = new AnniversaryListViewItem();
        item.setRemainDay(RemainDay);
        item.setWhenDay(WhenDay);
        item.setDday(Dday);

        mList.add(item);

    }
}