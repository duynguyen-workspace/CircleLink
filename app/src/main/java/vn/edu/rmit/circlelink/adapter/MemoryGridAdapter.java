package vn.edu.rmit.circlelink.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import vn.edu.rmit.circlelink.R;
import vn.edu.rmit.circlelink.model.Memory;

public class MemoryGridAdapter extends RecyclerView.Adapter<MemoryViewHolder> {

    private final Context context;
    private final ArrayList<Memory> memories;
    private final OnItemListener listener;

    public MemoryGridAdapter(Context context, ArrayList<Memory> memories, OnItemListener listener) {
        this.context = context;
        this.memories = memories;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MemoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.memory_item, parent, false);
        return new MemoryViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull MemoryViewHolder holder, int position) {
        Memory memory = memories.get(position);
        holder.bind(memory, context, listener);
    }

    @Override
    public int getItemCount() {
        return memories.size();
    }

    public interface OnItemListener {
        void onImageClick(int position, Memory memory);
    }

}

