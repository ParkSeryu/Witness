package com.example.munanmunan;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

public class BucketListFragment extends Fragment {

    public BucketListFragment() { }

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

        ListView bucketListView;
        BucketListViewAdapter bucketListViewAdapter;
        bucketListViewAdapter = new BucketListViewAdapter();
        bucketListView = view.findViewById(R.id.bucketListView);
        bucketListView.setAdapter(bucketListViewAdapter);

        bucketListViewAdapter.addItem("석촌호수가기", ContextCompat.getDrawable(getActivity(), R.drawable.ic_menu_camera), ContextCompat.getDrawable(getActivity(), R.drawable.ic_menu_gallery));
        bucketListViewAdapter.addItem("에버랜드가기", ContextCompat.getDrawable(getActivity(), R.drawable.ic_menu_camera), ContextCompat.getDrawable(getActivity(), R.drawable.ic_menu_gallery));

        return view;
    }
}
