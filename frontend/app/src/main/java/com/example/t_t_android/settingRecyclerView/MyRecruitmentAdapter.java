package com.example.t_t_android.settingRecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.t_t_android.R;

import java.util.ArrayList;

public class MyRecruitmentAdapter extends RecyclerView.Adapter<MyRecruitmentAdapter.ViewHolder> {
    ArrayList<MyRecruitment> items = new ArrayList<MyRecruitment>();

    @NonNull
    @Override
    public MyRecruitmentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.my_recruitment_itmes, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyRecruitmentAdapter.ViewHolder holder, int position) {
        MyRecruitment item = items.get(position);
        holder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(MyRecruitment item) {
        items.add(item);
    }

    public void setItems(ArrayList<MyRecruitment> items) {
        this.items = items;
    }

    public MyRecruitment getItem(int position) {
        return items.get(position);
    }

    public void setItem(int position, MyRecruitment item) {
        items.set(position, item);
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvStartMR;
        TextView tvDestinationMR;
        TextView tvCrewNumberMR;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvStartMR=itemView.findViewById(R.id.tvStartMR);
            tvDestinationMR=itemView.findViewById(R.id.tvDestinationMR);
            tvCrewNumberMR=itemView.findViewById(R.id.tvCrewNumberMR);
        }
        public void setItem(MyRecruitment item){
            tvStartMR.setText("출발지: "+item.getStart());
            tvDestinationMR.setText("목적지: "+item.getDestination());
            tvCrewNumberMR.setText("인원수: "+item.getCrewNumber());
        }
    }
}
