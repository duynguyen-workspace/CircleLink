package vn.edu.rmit.circlelink;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;

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
        String categoryHead = "Category: ";
        String categoryID = currentMemory.getCategoryID();

        SpannableStringBuilder spannable = new SpannableStringBuilder();

        SpannableString categorySpan = new SpannableString(categoryHead);
        categorySpan.setSpan(new StyleSpan(Typeface.ITALIC), 0, categoryHead.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        categorySpan.setSpan(new ForegroundColorSpan(Color.GRAY), 0, categoryHead.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        SpannableString categoryIDSpan = new SpannableString(categoryID);
        categoryIDSpan.setSpan(new StyleSpan(Typeface.BOLD), 0, categoryID.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        categoryIDSpan.setSpan(new ForegroundColorSpan(ContextCompat.getColor(ViewMemoryActivity.this, R.color.light_gray)), 0, categoryID.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        spannable.append(categorySpan);
        spannable.append(categoryIDSpan);

        categoryTV.setText(spannable);
    }


    public void editMemoryAction(View view) {
        MemoryUtils.showCategorySelectionDialog(ViewMemoryActivity.this, currentMemory.getCategoryID(), selectedCategory -> {
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