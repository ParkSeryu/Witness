package com.example.munanmunan;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FragmentMain extends Fragment {

    ImageButton btnGoBcl;
    TextView toolbarText, tvMainDay;
    ImageButton btnPlus;
    private DialogMainDay dialogMainDay;
    private DialogAddAnniv dialogAddAniv;

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
                ((MainActivity)getActivity()).replaceFragment();
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
                dialogMainDay = new DialogMainDay(getContext());
                dialogMainDay.setCancelable(false);
                dialogMainDay.show();
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
}