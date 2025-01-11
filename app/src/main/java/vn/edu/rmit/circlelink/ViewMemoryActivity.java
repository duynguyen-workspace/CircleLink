package vn.edu.rmit.circlelink;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;

import vn.edu.rmit.circlelink.model.Memory;

public class ViewMemoryActivity extends AppCompatActivity {

    private ImageView memoryView;
    private TextView categoryTV;
    Memory currentMemory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_memory);

        currentMemory = getIntent().getParcelableExtra("memory");

        categoryTV = findViewById(R.id.categoryTV);
        setCategoryTV();
        setUpMemoryView();
    }

    private void setUpMemoryView() {
        memoryView = findViewById(R.id.memoryViewFull);
        Glide.with(ViewMemoryActivity.this)
                .load(currentMemory.getPath())
                .fitCenter()
                .into(memoryView);
    }

    private void setCategoryTV() {
        categoryTV.setText("Category: " + currentMemory.getCategoryID());
    }


    public void editMemoryAction(View view) {
        MemoryUtils.showCategorySelectionDialog(ViewMemoryActivity.this, currentMemory.getCategoryID(), new MemoryUtils.CategorySelectionCallback() {
            @Override
            public void onCategorySelected(String selectedCategory) {
                if (!selectedCategory.equals(currentMemory.getCategoryID())) {
                    // Category has changed, update the memory and show a Toast
                    String oldCategory = currentMemory.getCategoryID();
                    currentMemory.setCategoryID(selectedCategory);
                    setCategoryTV();
                    MemoryUtils.updateMemory(oldCategory, currentMemory);
                } else {
                    // No change in category
                    Toast.makeText(ViewMemoryActivity.this, "No change in category", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void deleteMemoryAction(View view) {
        new AlertDialog.Builder(ViewMemoryActivity.this)
                .setTitle("Delete Memory")
                .setMessage("Are you sure you want to delete this memory?")
                .setPositiveButton("Delete", (dialog, which) -> {
                    // Call delete method from MemoryUtils
                    MemoryUtils.deleteMemory(currentMemory);
                    finish();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    public void closeAction(View view) {
        finish();
    }
}