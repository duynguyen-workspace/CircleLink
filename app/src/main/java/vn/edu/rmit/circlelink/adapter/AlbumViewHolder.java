package vn.edu.rmit.circlelink.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import vn.edu.rmit.circlelink.R;
import vn.edu.rmit.circlelink.model.Album;

public class AlbumViewHolder extends RecyclerView.ViewHolder {

    private Context context;
    private final ImageView albumCover;
    private final TextView albumName;

    public AlbumViewHolder(Context context, @NonNull View itemView) {
        super(itemView);
        this.context = context;
        albumCover = itemView.findViewById(R.id.albumCoverImage);
        albumName = itemView.findViewById(R.id.albumCoverName);
    }

    void bind(final Album album, AlbumAdapter.OnItemListener listener) {
        Glide.with(context)
                .load(album.getCoverPhoto()) // Album cover image
                .centerCrop()
                .into(albumCover);

        albumName.setText(album.getName());
        albumCover.setColorFilter(context.getResources().getColor(R.color.green_moss)); // Dim effect

        itemView.setOnClickListener(v -> listener.onAlbumClick(album));
    }
}
