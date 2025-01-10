package vn.edu.rmit.circlelink.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import vn.edu.rmit.circlelink.R;
import vn.edu.rmit.circlelink.model.Memory;

public class MemoryViewHolder extends RecyclerView.ViewHolder {

    private final ImageView imageView;

    public MemoryViewHolder(@NonNull View itemView, MemoryGridAdapter.OnItemListener onItemListener) {
        super(itemView);
        imageView = itemView.findViewById(R.id.memoryItem);
    }

    public void bind(final Memory memory, final Context context, final MemoryGridAdapter.OnItemListener listener) {
        Glide.with(context)
                .load(memory.getPath())
                .centerCrop()
                .into(imageView);

        itemView.setOnClickListener(v -> listener.onImageClick(getAdapterPosition(), memory));
    }
}
