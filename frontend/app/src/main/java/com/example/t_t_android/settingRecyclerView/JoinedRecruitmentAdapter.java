package com.example.t_t_android.settingRecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.t_t_android.R;

import java.util.ArrayList;
import java.util.List;

public class JoinedRecruitmentAdapter extends RecyclerView.Adapter<JoinedRecruitmentAdapter.ViewHolder> {
    ArrayList<JoinedRecruitment> itemsJR = new ArrayList<JoinedRecruitment>();

    @NonNull
    @Override
    public JoinedRecruitmentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.joined_recruitment_items, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        JoinedRecruitment item = itemsJR.get(position);
        holder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return itemsJR.size();
    }

    public void addItem(JoinedRecruitment item) {
        itemsJR.add(item);
    }

    public void setItems(ArrayList<JoinedRecruitment> items) {
        this.itemsJR = items;
    }

    public void setItem(int position, JoinedRecruitment item) {
        itemsJR.set(position, item);
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvStartJR;
        TextView tvDestinationJR;
        TextView tvCrewNumberJR;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvStartJR=itemView.findViewById(R.id.tvStartJR);
            tvDestinationJR=itemView.findViewById(R.id.tvDestinationJR);
            tvCrewNumberJR=itemView.findViewById(R.id.tvCrewNumberJR);

        }

        public void setItem(JoinedRecruitment itemJR){
            tvStartJR.setText(itemJR.getStart());
            tvDestinationJR.setText(itemJR.getDestination());
            tvCrewNumberJR.setText(itemJR.getCrewNumber());
        }
    }

}
