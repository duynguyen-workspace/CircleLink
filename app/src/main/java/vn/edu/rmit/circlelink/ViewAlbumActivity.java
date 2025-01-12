package vn.edu.rmit.circlelink;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import vn.edu.rmit.circlelink.adapter.MemoryGridAdapter;
import vn.edu.rmit.circlelink.model.Album;

public class ViewAlbumActivity extends AppCompatActivity {

    private MemoryGridAdapter memoryGridAdapter;
    private RecyclerView albumMemories;
    private TextView albumName;

    Album selectedAlbum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_album);

        String getAlbumName = getIntent().getStringExtra("albumName");

        if (getAlbumName != null) {
            selectedAlbum = MemoryUtils.getAlbumByName(getAlbumName);
        } else {
            Log.e("ViewAlbum", "No album id passed.");
        }


        initWidgets();
        setUpAlbumView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpAlbumView();
    }

    private void setUpAlbumView() {
        albumName.setText(selectedAlbum.getName());

        if (selectedAlbum.getMemories().isEmpty()) {
            findViewById(R.id.noMemoriesTV).setVisibility(View.VISIBLE);
            albumMemories.setVisibility(View.GONE);
        } else {
            findViewById(R.id.noMemoriesTV).setVisibility(View.GONE);
            albumMemories.setVisibility(View.VISIBLE);

            memoryGridAdapter = new MemoryGridAdapter(ViewAlbumActivity.this, selectedAlbum.getMemories());
            albumMemories.setLayoutManager(new GridLayoutManager(ViewAlbumActivity.this, 3));
            albumMemories.setAdapter(memoryGridAdapter);
        }
    }

    private void initWidgets() {
        albumMemories = findViewById(R.id.albumMemoryRecyclerView);
        albumName = findViewById(R.id.albumName);
    }

    public void closeAction(View view) {
        finish();
    }
}