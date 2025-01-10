package vn.edu.rmit.circlelink.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import vn.edu.rmit.circlelink.R;
import vn.edu.rmit.circlelink.model.Photo;

public class PhotoGridAdapter extends RecyclerView.Adapter<PhotoViewHolder> {

    private final Context context;
    private final ArrayList<Photo> photos;
    private final OnItemListener listener;

    public PhotoGridAdapter(Context context, ArrayList<Photo> photos, OnItemListener listener) {
        this.context = context;
        this.photos = photos;
        this.listener = listener;
    }

    @NonNull
    @Override
    public PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.image_item, parent, false);
        return new PhotoViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoViewHolder holder, int position) {
        Photo photo = photos.get(position);
        holder.bind(photo, context, listener);
    }

    @Override
    public int getItemCount() {
        return photos.size();
    }

    public interface OnItemListener {
        void onImageClick(int position, Photo photo);
    }

}

