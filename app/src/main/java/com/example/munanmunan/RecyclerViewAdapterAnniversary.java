package com.example.munanmunan;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapterAnniversary extends RecyclerView.Adapter<RecyclerViewAdapterAnniversary.ViewHolder> {

    private ArrayList<AnniversaryListItem> AnniverSaryData = null;
    static int colorPosition;
    // 아이템 뷰를 저장하는 뷰홀더 클래스.
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvRemainDay;
        TextView tvWhenDay;
        TextView tvDday;


        ViewHolder(View itemView){
            super(itemView);

            // 뷰 객체에 대한 참조.
            tvRemainDay = itemView.findViewById(R.id.tvRemainDay);
            tvWhenDay = itemView.findViewById(R.id.tvWhenDay);
            tvDday = itemView.findViewById(R.id.tvDday);

        }
    }

    //생성자에서 데이터 리스트 객체를 전달받음.
    RecyclerViewAdapterAnniversary(ArrayList<AnniversaryListItem> list){
        AnniverSaryData = list;
    }

    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
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

        if(position < colorPosition)
        {
            holder.tvRemainDay.setTextColor(Color.GRAY);
            holder.tvWhenDay.setTextColor(Color.GRAY);
            holder.tvDday.setTextColor(Color.GRAY);
        }
    }


    //getItemCount() - 전체 데이터 갯수 리턴.
    @Override
    public int getItemCount() {
        return AnniverSaryData.size();
    }
}

