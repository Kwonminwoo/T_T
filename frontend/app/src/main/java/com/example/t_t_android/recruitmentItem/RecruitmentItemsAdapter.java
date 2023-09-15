package com.example.t_t_android.recruitmentItem;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.t_t_android.MainActivity;
import com.example.t_t_android.R;
import com.example.t_t_android.RecruitmentFragment;

import java.io.Serializable;
import java.util.ArrayList;

public class RecruitmentItemsAdapter extends RecyclerView.Adapter<RecruitmentItemsAdapter.ViewHolder> implements Serializable {

    ArrayList<RecruitmentItems> itemsRI = new ArrayList<>();
    public static final String ARG_RECRUIT_INFO = "recruitInfo";
    private Context context;
    private MainActivity activity;
    RecruitmentFragment recruitmentFragment = new RecruitmentFragment();

    @NonNull
    @Override
    public RecruitmentItemsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.recruitment_items, parent, false);

        context = parent.getContext();
        activity = (MainActivity) context;

        return new RecruitmentItemsAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecruitmentItemsAdapter.ViewHolder holder, int position) {
        RecruitmentItems item = itemsRI.get(position);
        holder.setItem(item);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable(ARG_RECRUIT_INFO, item);

                FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
                RecruitmentPostFragment recruitmentPostFragment = new RecruitmentPostFragment();
                recruitmentPostFragment.setArguments(bundle);
                transaction.replace(R.id.main_containers, recruitmentPostFragment);
                transaction.commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemsRI.size();
    }

    public void addItem(RecruitmentItems item) {
        itemsRI.add(item);
    }

    public void setItems(ArrayList<RecruitmentItems> items) {
        this.itemsRI = items;
    }

    public void setItem(int position, RecruitmentItems item) {
        itemsRI.set(position, item);
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvTitleRI, tvDateRI, tvTimeRI, tvStartRI, tvDestinationRI, tvCrewNumberRI;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitleRI = itemView.findViewById(R.id.title_tv);
            tvDateRI = itemView.findViewById(R.id.date_tv);
            tvTimeRI = itemView.findViewById(R.id.time_tv);
            tvStartRI = itemView.findViewById(R.id.tvStartRI);
            tvDestinationRI = itemView.findViewById(R.id.tvDestinationRI);
            tvCrewNumberRI = itemView.findViewById(R.id.tvCrewNumberRI);
        }

        public void setItem(RecruitmentItems itemRI) {
            tvTitleRI.setText(itemRI.getTitle());
            tvDateRI.setText(itemRI.getDate());
            tvTimeRI.setText(itemRI.getTime());
            tvStartRI.setText(String.format("출발지: 위도:%.3f 경도:%.3f", itemRI.getStartLon(), itemRI.getStartLat()));
            tvDestinationRI.setText(String.format("목적지: 위도:%.3f 경도:%.3f", itemRI.getEndLon(), itemRI.getEndLat()));
            tvCrewNumberRI.setText("인원수: "+itemRI.getCrewNum() + " / 4");
        }
    }
}
