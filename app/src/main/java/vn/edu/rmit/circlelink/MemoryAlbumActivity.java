package vn.edu.rmit.circlelink;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import vn.edu.rmit.circlelink.adapter.AlbumAdapter;
import vn.edu.rmit.circlelink.adapter.MemoryMonthAdapter;
import vn.edu.rmit.circlelink.model.Memory;

public class MemoryAlbumActivity extends AppCompatActivity {

    RecyclerView memoriesView, albumsView;
    TextView totalPhotosTV;
    Button pickButton;

    MemoryMonthAdapter memoriesAdapter;
    AlbumAdapter albumAdapter;

    private static final int READ_PERMISSION = 101;
    private static final int PICK_IMAGE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory_album);

        initWidgets();

        setUpMemories();
        setUpPickMemoryButton();
        setUpAlbums();
    }

    private void setUpAlbums() {
        Log.d("currentAlbums", "Size: " + MemoryUtils.currentAlbums.size());
        albumAdapter = new AlbumAdapter(MemoryAlbumActivity.this, MemoryUtils.currentAlbums);
        albumsView.setLayoutManager(new LinearLayoutManager(MemoryAlbumActivity.this, LinearLayoutManager.HORIZONTAL, false));
        albumsView.setAdapter(albumAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpAlbums();
        setUpMemories();
        if (MemoryUtils.currentMemories != null) {
            totalPhotosTV.setText("Photos (" + MemoryUtils.currentMemories.size() + ")");
        } else {
            totalPhotosTV.setText("");
        }
    }

    private void setUpMemories() {
        Log.d("currentMemories", String.valueOf(MemoryUtils.currentMemories.size()));
        memoriesAdapter = new MemoryMonthAdapter(MemoryAlbumActivity.this, MemoryUtils.groupAndSortMemoriesByMonth());
        memoriesView.setLayoutManager(new LinearLayoutManager(MemoryAlbumActivity.this));
        memoriesView.setAdapter(memoriesAdapter);
    }

    private void initWidgets() {
        totalPhotosTV = findViewById(R.id.totalPhotos);
        albumsView = findViewById(R.id.albumsRecyclerView);
        memoriesView = findViewById(R.id.photosRecyclerView);
        pickButton = findViewById(R.id.pickButton);
    }

    private void setUpPickMemoryButton() {
        pickButton.setOnClickListener(v -> {

            if (ContextCompat.checkSelfPermission(MemoryAlbumActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(MemoryAlbumActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, READ_PERMISSION);
            }

            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            // Show the category selection dialog and pass the selected category
            MemoryUtils.showCategorySelectionDialog(MemoryAlbumActivity.this, "none", selectedCategory -> {
                // Check if multiple images are picked
                if (data.getClipData() != null) {
                    int countOfImages = data.getClipData().getItemCount();
                    // Loop through each image
                    for (int i = 0; i < countOfImages; i++) {
                        Uri imageURI = data.getClipData().getItemAt(i).getUri();
                        // Process each image URI with the selected category
                        processImageURI(imageURI, i, selectedCategory);
                    }
                } else {
                    // If only one image is picked
                    Uri imageURI = data.getData();
                    processImageURI(imageURI, 0, selectedCategory);
                }

                // Log current memories (optional for debugging)
                Log.d("PhotoList", MemoryUtils.currentMemories.toString());

                // Set up memories (e.g., updating UI or refreshing data)
                setUpMemories();
                setUpAlbums();

                // Update the total photos text
                totalPhotosTV.setText("Photos (" + MemoryUtils.currentMemories.size() + ")");
            });
        } else {
            // If no images were picked
            Toast.makeText(this, "No images picked", Toast.LENGTH_SHORT).show();
        }
    }

    private void processImageURI(Uri imageURI, int i, String category) {

        String imagePath = imageURI.toString();
        String imageName;
        if (i == 0) {
            imageName = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")) +
                    "_" + LocalTime.now().format(DateTimeFormatter.ofPattern("HHmmssSS")) + ".jpg";
        } else {
            imageName = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")) +
                    "_" + LocalTime.now().format(DateTimeFormatter.ofPattern("HHmmssSS")) +
                    "(" + i + ")" + ".jpg";
        }
        LocalDate imageUploadDate = LocalDate.now();

        Memory newMemory = new Memory(imageName, imagePath, imageUploadDate, category);

        Log.d("PhotoName", newMemory.getName());
        Log.d("PhotoCategory", newMemory.getCategoryID());

        MemoryUtils.currentMemories.add(newMemory);
        MemoryUtils.addMemoryToAlbum(newMemory.getCategoryID(), newMemory);
    }
}