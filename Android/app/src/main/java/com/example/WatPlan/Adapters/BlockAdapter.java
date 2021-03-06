package com.example.WatPlan.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.WatPlan.Activities.MainActivity;
import com.example.WatPlan.Models.Block;
import com.example.WatPlan.Models.BlockFilter;
import com.example.WatPlan.R;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.concurrent.atomic.AtomicBoolean;

public class BlockAdapter extends RecyclerView.Adapter<BlockAdapter.BlockViewHolder> {
    private final MainActivity mainActivity;
    private final ArrayList<Block> blockArrayList;
    private final HashSet<BlockFilter> blockFilterHashSet;
    private static Toast currentToast;

    BlockAdapter(MainActivity mainActivity, ArrayList<Block> blockArrayList, HashSet<BlockFilter> blockFilterHashSet) {
        this.mainActivity = mainActivity;
        this.blockFilterHashSet = blockFilterHashSet;
        this.blockArrayList = blockArrayList;
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
        if (block != null) {
            holder.itemView.setOnClickListener(v -> {
                if(currentToast!=null) currentToast.cancel();
                currentToast = Toast.makeText(mainActivity,block.getTitle(),Toast.LENGTH_LONG);
                currentToast.show();
            });
        }

        if (block == null) {
            holder.setTexts("", "", "", "", "");
        } else {
            int color = mainActivity.getResources().getColor(R.color.invis, null);
            holder.blockLayout.setBackgroundColor(color);

            if (checkFilters(block).get()) {
                String place = block.getPlace()
                        .replace("', '", "\n")
                        .replace("['", "")
                        .replace("']", "");
                holder.setTexts(block.getClassIndex(), block.getSubject(), place,
                        block.getClassType(), block.getTeacher());

                //change colors over here
                if (block.getDate().equals(LocalDate.now().toString())) {
                    holder.blockLayout.setBackgroundColor(mainActivity.getResources().getColor(R.color.orange2, null));
                    holder.blockLayout.setBackground(mainActivity.getResources().getDrawable(R.drawable.outline2, null));
                }

                int c = mainActivity.getSettingsFragment().getColorMap().get(block.getSubject());
                holder.blockLayout.setBackgroundColor(c);
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
}
