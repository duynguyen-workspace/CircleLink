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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import vn.edu.rmit.circlelink.adapter.RecyclerAdapter;
import vn.edu.rmit.circlelink.model.Memory;

public class MemoryAlbumActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    TextView totalPhotosTV;
    Button pickButton;

    ArrayList<Uri> uri = new ArrayList<>();
    RecyclerAdapter adapter;

    private ArrayList<Memory> memories = new ArrayList<>();

    private static final int READ_PERMISSION = 101;
    private static final int PICK_IMAGE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory_album);

        initWidgets();

        adapter = new RecyclerAdapter(uri, getApplicationContext());
        recyclerView.setLayoutManager(new GridLayoutManager(MemoryAlbumActivity.this, 4));
        recyclerView.setAdapter(adapter);


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

    private void initWidgets() {
        totalPhotosTV = findViewById(R.id.totalPhotos);
        recyclerView = findViewById(R.id.photosRecyclerView);
        pickButton = findViewById(R.id.pickButton);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && null != data) {
            if (data.getClipData() != null) {

                int countOfImages = data.getClipData().getItemCount();
                for (int i = 0; i < countOfImages; i++) {
                    Uri imageURI = data.getClipData().getItemAt(i).getUri();
                    uri.add(imageURI);
                    processImageURI(imageURI);
                }
                Log.d("PhotoList", memories.toString());
                Log.d("URIList", String.valueOf(uri.size()));
                adapter.notifyDataSetChanged();
                totalPhotosTV.setText("Photos (" + uri.size() + ")");
            } else {

                Uri imageURI = data.getData();
//                uri.add(imageURI);
                processImageURI(imageURI);

            }
            adapter.notifyDataSetChanged();
            totalPhotosTV.setText("Photos (" + uri.size() + ")");
        } else {
            Toast.makeText(MemoryAlbumActivity.this, "No images picked", Toast.LENGTH_SHORT).show();
        }
    }

    private void processImageURI(Uri imageURI) {
        String imagePath = getRealPathFromURI(imageURI);
        LocalDate imageTakenDate = getTakenDateFromURI(imageURI);

        String imageName = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")) + "_" + LocalTime.now().format(DateTimeFormatter.ofPattern("HHmm")) + ".jpg";
        LocalDate imageUploadDate = LocalDate.now();

        Memory newMemory = new Memory(imageName, imagePath, imageUploadDate, imageTakenDate);

//        uri.add(imageURI);      // for display images purpose; should replace with code to get from photo.getPath()
        memories.add(newMemory);
    }

    private String getRealPathFromURI(Uri imageURI) {
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(imageURI, proj, null, null, null);
        if (cursor != null) {
            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String path = cursor.getString(columnIndex);
            cursor.close();
            return path;
        }
        return null;
    }

    private LocalDate getTakenDateFromURI(Uri imageURI) {
        Cursor cursor = getContentResolver().query(imageURI, null, null, null, null);
        if (cursor != null) {
            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_TAKEN);
            cursor.moveToFirst();
            long takenTimestamp = cursor.getLong(columnIndex);
            cursor.close();
            return LocalDate.ofEpochDay(takenTimestamp / (24 * 60 * 60 * 1000));  // Convert timestamp to LocalDate
        }
        return null;
    }
}