package com.example.WatPlan.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.WatPlan.Activities.MainActivity;
import com.example.WatPlan.Models.Block;
import com.example.WatPlan.Models.BlockFilter;
import com.example.WatPlan.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.concurrent.atomic.AtomicBoolean;

public class BlockAdapter extends RecyclerView.Adapter<BlockAdapter.BlockViewHolder> {
    private MainActivity mainActivity;
    private ArrayList<Block> blockArrayList;
    private HashSet<BlockFilter> blockFilterHashSet;

    BlockAdapter(MainActivity mainActivity, ArrayList<Block> blockArrayList, HashSet<BlockFilter> blockFilterHashSet) {
        this.mainActivity = mainActivity;
        this.blockFilterHashSet = blockFilterHashSet;
        this.blockArrayList = blockArrayList;
    }

    static class BlockViewHolder extends RecyclerView.ViewHolder {
        ArrayList<TextView> textViews = new ArrayList<>();
        RelativeLayout blockLayout;

        BlockViewHolder(@NonNull View itemView) {
            super(itemView);
            int[] views = new int[]{R.id.indexTextView, R.id.subjectTextView,
                    R.id.roomTextView, R.id.typeTextView, R.id.teacherTextView};
            for (int view : views) textViews.add(itemView.findViewById(view));
            blockLayout = itemView.findViewById(R.id.blockLayout);
        }

        private void setTexts(String... strings) {
            for (int i = 0; i < textViews.size(); i++)
                textViews.get(i).setText(strings[i]);
        }
    }

    @NonNull
    @Override
    public BlockAdapter.BlockViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_block, parent, false);
        return new BlockViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull BlockAdapter.BlockViewHolder holder, int position) {
        Block block = blockArrayList.get(position);

        if (block == null) holder.setTexts("", "", "", "", "");
        else {
            int color = mainActivity.getResources().getColor(R.color.invis);
            holder.blockLayout.setBackgroundColor(color);

            if (checkFilters(block).get()) {
                String place = block.getPlace()
                        .replace("', '", "\n")
                        .replace("['", "")
                        .replace("']", "");
                holder.setTexts(block.getClassIndex(), block.getSubject(), place,
                        block.getClassType(), block.getTeacher());

                //change colors over here
                color = mainActivity.getResources().getColor(R.color.orange2);
                holder.blockLayout.setBackgroundColor(color);
            } else holder.setTexts("", "", "", "", "");
        }
    }

    private AtomicBoolean checkFilters(Block block) {
        AtomicBoolean result = new AtomicBoolean(true);
        blockFilterHashSet.forEach(blockFilter ->
                result.set(blockFilter.filter(block) && result.get()));
        return result;
    }

    @Override
    public int getItemCount() {
        return blockArrayList.size();
    }
}