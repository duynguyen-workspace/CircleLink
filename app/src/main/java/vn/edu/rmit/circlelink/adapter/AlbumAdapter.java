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
import vn.edu.rmit.circlelink.ViewMemoryActivity;
import vn.edu.rmit.circlelink.model.Album;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.AlbumViewHolder> {

    private final Context context;
    private final ArrayList<Album> albums;

    public AlbumAdapter(Context context, ArrayList<Album> albums) {
        this.context = context;
        this.albums = albums;
    }

    @NonNull
    @Override
    public AlbumViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.album_cell, parent, false);
        return new AlbumViewHolder(context, view);
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumViewHolder holder, int position) {
        Album album = albums.get(position);

        holder.albumName.setText(album.getName());

        Glide.with(context)
                .load(album.getCoverPhoto()) // Album cover image
                .centerCrop()
                .into(holder.albumCover);

        holder.albumCover.setColorFilter(new PorterDuffColorFilter(0x60000000, PorterDuff.Mode.SRC_ATOP));

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


    public static class AlbumViewHolder extends RecyclerView.ViewHolder {

        private final ImageView albumCover;
        private final TextView albumName;

        public AlbumViewHolder(Context context, @NonNull View itemView) {
            super(itemView);
            albumCover = itemView.findViewById(R.id.albumCoverImage);
            albumName = itemView.findViewById(R.id.albumCoverName);
        }
    }
}

