package vn.edu.rmit.circlelink.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import vn.edu.rmit.circlelink.MemoryAlbumActivity;
import vn.edu.rmit.circlelink.R;
import vn.edu.rmit.circlelink.model.Memory;

public class MemoryGridAdapter extends RecyclerView.Adapter<MemoryGridAdapter.MemoryViewHolder> {

    private final Context context;
    private final ArrayList<Memory> memories;

    public MemoryGridAdapter(Context context, ArrayList<Memory> memories) {
        this.context = context;
        this.memories = memories;
    }

    @NonNull
    @Override
    public MemoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.memory_item, parent, false);
        return new MemoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MemoryViewHolder holder, int position) {
        Memory memory = memories.get(position);

        Glide.with(context)
                .load(memory.getPath())
                .centerCrop()
                .into(holder.imageView);

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MemoryAlbumActivity.class);     // replace with ViewMemoryActivity
                intent.putExtra("memory", memory);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return memories.size();
    }

    public static class MemoryViewHolder extends RecyclerView.ViewHolder {

        private final ImageView imageView;

        public MemoryViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.memoryItem);
        }
    }

}

