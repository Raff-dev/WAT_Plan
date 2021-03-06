package com.example.WatPlan.Adapters;

import android.graphics.Point;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.WatPlan.Activities.MainActivity;
import com.example.WatPlan.Models.Block;
import com.example.WatPlan.Models.BlockFilter;
import com.example.WatPlan.Models.Day;
import com.example.WatPlan.R;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class DayAdapter extends RecyclerView.Adapter<DayAdapter.DayViewHolder> {
    private ArrayList<Day> dayArrayList;
    private MainActivity mainActivity;
    private HashSet<BlockFilter> blockFilterHashSet;

    public DayAdapter(MainActivity mainActivity, ArrayList<Day> dayArrayList, HashSet<BlockFilter> blockFilterHashSet) {
        this.dayArrayList = dayArrayList;
        this.blockFilterHashSet = blockFilterHashSet;
        this.mainActivity = mainActivity;
    }

    public static class DayViewHolder extends RecyclerView.ViewHolder {
        TextView dateTextView;
        TextView dayNameTextView;
        RecyclerView blockRecyclerView;
        View dayLayout;

        public DayViewHolder(@NonNull View itemView) {
            super(itemView);
            dayLayout = itemView.findViewById(R.id.dayLayout);
            dateTextView = itemView.findViewById(R.id.dayDateTextView);
            dayNameTextView = itemView.findViewById(R.id.dayNameTextView);
            blockRecyclerView = itemView.findViewById(R.id.blockRecyclerView);
        }
    }

    @NonNull
    @Override
    public DayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_day, parent, false);
        View dayLayout = v.findViewById(R.id.dayLayout);
        Point size = new Point();
        mainActivity.getWindowManager().getDefaultDisplay().getSize(size);

        dayLayout.getLayoutParams().width = (size.x - 120) / 5;
        return new DayViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DayViewHolder holder, int position) {
        Day day = dayArrayList.get(position);
        String date = day.getDate();
        //color today
        if (date.equals(LocalDate.now().toString()))
            holder.dayLayout.setBackgroundColor(mainActivity.getResources().getColor(R.color.orange3));
        //cut date to mm-dd format
        if (date.length() > 5) date = date.substring(5);

        ArrayList<String> dayNames = new ArrayList<>(Arrays.asList(
                "Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"));
        String dayName = dayNames.get(position);
        holder.dateTextView.setText(date);
        holder.dayNameTextView.setText(dayName);

        ArrayList<Block> blockArrayList = day.getBlockArrayList();
        BlockAdapter blockAdapter = new BlockAdapter(mainActivity, blockArrayList, blockFilterHashSet);

        holder.blockRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager blockLayoutManager = new LinearLayoutManager(mainActivity) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        holder.blockRecyclerView.setLayoutManager(blockLayoutManager);
        holder.blockRecyclerView.setAdapter(blockAdapter);
    }

    @Override
    public int getItemCount() {
        return dayArrayList.size();
    }


}
