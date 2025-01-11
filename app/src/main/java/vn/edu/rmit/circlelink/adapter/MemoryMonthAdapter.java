package vn.edu.rmit.circlelink.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import vn.edu.rmit.circlelink.R;
import vn.edu.rmit.circlelink.model.Memory;

public class MemoryMonthAdapter extends RecyclerView.Adapter<MemoryMonthAdapter.DateViewHolder> {

    private final Context context;
    private final LinkedHashMap<String, ArrayList<Memory>> photosByMonth; // Month as key, list of memories as value
    private final ArrayList<String> months;

    public MemoryMonthAdapter(Context context, LinkedHashMap<String, ArrayList<Memory>> photosByMonth) {
        this.context = context;
        this.photosByMonth = photosByMonth;
        this.months = new ArrayList<>(photosByMonth.keySet());
    }

    @NonNull
    @Override
    public DateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.memories_by_date_section, parent, false);
        return new DateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DateViewHolder holder, int position) {
        String month = months.get(position);
        ArrayList<Memory> memories = photosByMonth.get(month);

        holder.dateLabel.setText(month);

        MemoryGridAdapter memoryGridAdapter = new MemoryGridAdapter(context, memories);
        holder.photosGrid.setLayoutManager(new GridLayoutManager(context, 4));
        holder.photosGrid.setAdapter(memoryGridAdapter);
    }

    @Override
    public int getItemCount() {
        return months.size();
    }

    public static class DateViewHolder extends RecyclerView.ViewHolder {

        private final TextView dateLabel;
        private final RecyclerView photosGrid;

        public DateViewHolder(@NonNull View itemView) {
            super(itemView);
            dateLabel = itemView.findViewById(R.id.dateHeader);
            photosGrid = itemView.findViewById(R.id.memoriesGridRecyclerView);
        }
    }
}


