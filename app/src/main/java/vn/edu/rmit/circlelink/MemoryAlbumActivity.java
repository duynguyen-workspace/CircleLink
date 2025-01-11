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
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import vn.edu.rmit.circlelink.adapter.MemoryMonthAdapter;
import vn.edu.rmit.circlelink.model.Memory;

public class MemoryAlbumActivity extends AppCompatActivity {

    RecyclerView memoriesView, albumsView;
    TextView totalPhotosTV;
    Button pickButton;

    MemoryMonthAdapter memoriesAdapter;

    ArrayList<Memory> memories = new ArrayList<>();

    private static final int READ_PERMISSION = 101;
    private static final int PICK_IMAGE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory_album);

        initWidgets();

        setUpMemories();
        setUpPickMemoryButton();
    }

    private void setUpMemories() {
        memoriesAdapter = new MemoryMonthAdapter(MemoryAlbumActivity.this, MemoryUtils.groupAndSortMemoriesByMonth(memories));
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

            if (ContextCompat.checkSelfPermission(MemoryAlbumActivity.this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
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

        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && null != data) {
            if (data.getClipData() != null) {

                int countOfImages = data.getClipData().getItemCount();
                for (int i = 0; i < countOfImages; i++) {
                    Uri imageURI = data.getClipData().getItemAt(i).getUri();
                    // add image to cloud storage and get URL string
                    processImageURI(imageURI, i);
                }
            } else {

                Uri imageURI = data.getData();
                processImageURI(imageURI);

            }
            Log.d("PhotoList", memories.toString());
            setUpMemories();
            totalPhotosTV.setText("Photos (" + memories.size() + ")");
        } else {
            Toast.makeText(MemoryAlbumActivity.this, "No images picked", Toast.LENGTH_SHORT).show();
        }
    }

    private void processImageURI(Uri imageURI) {

        String imagePath = imageURI.toString();
        String imageName = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")) +
                "_" + LocalTime.now().format(DateTimeFormatter.ofPattern("HHmm")) + ".jpg";
        LocalDate imageUploadDate = LocalDate.now();

        Memory newMemory = new Memory(imageName, imagePath, imageUploadDate, "none");

        Log.d("PhotoName", newMemory.getName());

        memories.add(newMemory);
    }

    private void processImageURI(Uri imageURI, int i) {

        String imagePath = imageURI.toString();
        String imageName = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")) +
                "_" + LocalTime.now().format(DateTimeFormatter.ofPattern("HHmm")) +
                "(" + i + ")" + ".jpg";
        LocalDate imageUploadDate = LocalDate.now();

        Memory newMemory = new Memory(imageName, imagePath, imageUploadDate, "none");

        Log.d("PhotoName", newMemory.getName());

        memories.add(newMemory);
    }

}