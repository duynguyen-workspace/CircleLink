package vn.edu.rmit.circlelink.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import vn.edu.rmit.circlelink.R;
import vn.edu.rmit.circlelink.model.Photo;

public class PhotoViewHolder extends RecyclerView.ViewHolder {

    private final ImageView imageView;

    public PhotoViewHolder(@NonNull View itemView, PhotoGridAdapter.OnItemListener onItemListener) {
        super(itemView);
        imageView = itemView.findViewById(R.id.photoItem);
    }

    public void bind(final Photo photo, final Context context, final PhotoGridAdapter.OnItemListener listener) {
        Glide.with(context)
                .load(photo.getPath())
                .centerCrop()
                .into(imageView);

        itemView.setOnClickListener(v -> listener.onImageClick(getAdapterPosition(), photo));
    }
}
