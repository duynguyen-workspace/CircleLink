package vn.edu.rmit.circlelink.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import vn.edu.rmit.circlelink.R;
import vn.edu.rmit.circlelink.ViewAlbumActivity;
import vn.edu.rmit.circlelink.model.Album;

public class CategoryAlbumAdapter extends RecyclerView.Adapter<CategoryAlbumAdapter.CategoryAlbumViewHolder> {

    private final Context context;
    private final ArrayList<Album> albums;

    public CategoryAlbumAdapter(Context context, ArrayList<Album> albums) {
        this.context = context;
        this.albums = albums;
    }

    @NonNull
    @Override
    public CategoryAlbumViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.album_cell, parent, false);
        return new CategoryAlbumViewHolder(context, view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAlbumViewHolder holder, int position) {
        Album album = albums.get(position);

        holder.albumName.setText(album.getName());

        if (album.getCoverPhoto() != null) {
            Glide.with(context)
                    .load(album.getCoverPhoto()) // Album cover image
                    .centerCrop()
                    .into(holder.albumCover);
        }

//        holder.albumCover.setColorFilter(new PorterDuffColorFilter(0x90000000, PorterDuff.Mode.SRC_ATOP));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ViewAlbumActivity.class);
                intent.putExtra("albumId", album.getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return albums.size();
    }


    public static class CategoryAlbumViewHolder extends RecyclerView.ViewHolder {

        private final ImageView albumCover;
        private final TextView albumName;

        public CategoryAlbumViewHolder(Context context, @NonNull View itemView) {
            super(itemView);
            albumCover = itemView.findViewById(R.id.albumCoverImage);
            albumName = itemView.findViewById(R.id.albumCoverName);
        }
    }
}

