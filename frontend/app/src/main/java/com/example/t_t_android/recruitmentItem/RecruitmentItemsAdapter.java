package com.example.t_t_android.recruitmentItem;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.t_t_android.R;

import java.util.ArrayList;

public class RecruitmentItemsAdapter extends RecyclerView.Adapter<RecruitmentItemsAdapter.ViewHolder> {

    ArrayList<RecruitmentItems> itemsRI = new ArrayList<>();

    @NonNull
    @Override
    public RecruitmentItemsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.fragment_recruitment_items, parent, false);

        return new RecruitmentItemsAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecruitmentItemsAdapter.ViewHolder holder, int position) {
        RecruitmentItems item = itemsRI.get(position);
        holder.setItem(item);
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
        TextView tvTitleRI, tvDateRI, tvStartRI, tvDestinationRI, tvCrewNumberRI;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitleRI = itemView.findViewById(R.id.title_tv);
            tvDateRI = itemView.findViewById(R.id.time_tv);
            tvStartRI = itemView.findViewById(R.id.tvStartRI);
            tvDestinationRI = itemView.findViewById(R.id.tvDestinationRI);
            tvCrewNumberRI = itemView.findViewById(R.id.tvCrewNumberRI);

        }

        public void setItem(RecruitmentItems itemRI) {
            Log.e(itemRI.getTitle(), itemRI.getDate());
            tvTitleRI.setText(itemRI.getTitle());
            tvDateRI.setText(itemRI.getDate());
            tvStartRI.setText("출발지: "+itemRI.getStart());
            tvDestinationRI.setText("목적지: "+itemRI.getDestination());
            tvCrewNumberRI.setText("인원수: "+itemRI.getCrewNumber() + " / 4");
        }
    }
}
