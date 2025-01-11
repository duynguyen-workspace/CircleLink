package vn.edu.rmit.circlelink;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
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

        String albumId = getIntent().getStringExtra("albumId");

        if (albumId != null) {
            selectedAlbum = MemoryUtils.getAlbumById(albumId);
//            if (selectedAlbum != null) {
//                Log.d("ViewAlbum", "Album id: " + selectedAlbum.getId());
//                Log.d("ViewAlbum", "Album name: " + selectedAlbum.getName());
//                Log.d("ViewAlbum", "Album size: " + selectedAlbum.getMemories().size());
//            } else {
//                Log.e("ViewAlbum", "Album not found.");
//                // Show an error or placeholder
//            }
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

        memoryGridAdapter = new MemoryGridAdapter(ViewAlbumActivity.this, selectedAlbum.getMemories());
        albumMemories.setLayoutManager(new GridLayoutManager(ViewAlbumActivity.this, 4));
        albumMemories.setAdapter(memoryGridAdapter);
    }

    private void initWidgets() {
        albumMemories = findViewById(R.id.albumMemoryRecyclerView);
        albumName = findViewById(R.id.albumName);
    }

    public void closeAction(View view) {
        finish();
    }
}