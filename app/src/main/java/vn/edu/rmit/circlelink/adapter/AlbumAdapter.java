package vn.edu.rmit.circlelink.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import vn.edu.rmit.circlelink.R;
import vn.edu.rmit.circlelink.model.Album;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumViewHolder> {

    private final Context context;
    private final ArrayList<Album> albums;
    private final OnItemListener listener;

    public AlbumAdapter(Context context, ArrayList<Album> albums, OnItemListener listener) {
        this.context = context;
        this.albums = albums;
        this.listener = listener;
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
        holder.bind(album, listener);
    }

    @Override
    public int getItemCount() {
        return albums.size();
    }

    public interface OnItemListener {
        void onAlbumClick(Album album);
    }
}

