package com.example.t_t_android;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.t_t_android.recruitmentItem.RecruitmentItems;
import com.example.t_t_android.recruitmentItem.RecruitmentItemsAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class RecruitmentFragment extends Fragment {
    Context context;
    MainActivity activity;
    FloatingActionButton writeBtn;

    private RecyclerView RecruitmentRV;
    private RecruitmentItemsAdapter recruitmentItemsAdapter;

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        activity = (MainActivity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_recruitment, container,false);
        context = container.getContext();
        RecruitmentRV = root.findViewById(R.id.RecruitmentItem);

        writeBtn = root.findViewById(R.id.writeBtn);
        writeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                activity.onFragmentChange(1);
            }
        });

        LinearLayoutManager layoutManagerRI = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        RecruitmentRV.setLayoutManager(layoutManagerRI);
        recruitmentItemsAdapter = new RecruitmentItemsAdapter();
        recruitmentItemsAdapter.addItem(new RecruitmentItems("터미널 갈 사람", "20230922","공주대학교 천안캠퍼스", "천안복합터미널","4"));
        recruitmentItemsAdapter.addItem(new RecruitmentItems("터미널 갈 사람 구함", "20230303","공주대학교 천안캠퍼스", "천안복합터미널","2"));
        recruitmentItemsAdapter.addItem(new RecruitmentItems("공주대에서 터미널 갈 사람", "20230315","공주대", "천안복합터미널","3"));
        recruitmentItemsAdapter.addItem(new RecruitmentItems("터미널 갈 사람", "20230922","공주대학교 천안캠퍼스", "천안복합터미널","4"));
        recruitmentItemsAdapter.addItem(new RecruitmentItems("터미널 갈 사람 구함", "20230303","공주대학교 천안캠퍼스", "천안복합터미널","2"));
        recruitmentItemsAdapter.addItem(new RecruitmentItems("공주대에서 터미널 갈 사람", "20230315","공주대", "천안복합터미널","3"));
        DividerItemDecoration dividerItemDecorationRI = new DividerItemDecoration(context, layoutManagerRI.getOrientation());
        RecruitmentRV.addItemDecoration(new RecyclerViewDecoration(60));
        RecruitmentRV.addItemDecoration(dividerItemDecorationRI);
        RecruitmentRV.setAdapter(recruitmentItemsAdapter);



        return root;
    }
}

class RecyclerViewDecoration extends RecyclerView.ItemDecoration {

    private final int divHeight;

    public RecyclerViewDecoration(int divHeight)
    {
        this.divHeight = divHeight;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state)
    {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.top = divHeight;
    }
}