package vn.edu.rmit.circlelink.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Map;

import vn.edu.rmit.circlelink.R;
import vn.edu.rmit.circlelink.model.Memory;

public class DateSectionAdapter extends RecyclerView.Adapter<DateSectionAdapter.DateViewHolder> {

    private final Context context;
    private final Map<String, ArrayList<Memory>> photosByDate; // Date as key, list of photos as value
    private final MemoryGridAdapter.OnItemListener listener;

    private final ArrayList<String> dates;

    public DateSectionAdapter(Context context, Map<String, ArrayList<Memory>> photosByDate, MemoryGridAdapter.OnItemListener listener, ArrayList<String> dates) {
        this.context = context;
        this.photosByDate = photosByDate;
        this.listener = listener;
        this.dates = dates;
    }

    @NonNull
    @Override
    public DateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull DateViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
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


