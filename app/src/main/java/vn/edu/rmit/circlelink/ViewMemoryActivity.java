package vn.edu.rmit.circlelink;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import vn.edu.rmit.circlelink.model.Memory;

public class ViewMemoryActivity extends AppCompatActivity {

    private ImageView imageView;
    private Memory currentMemory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_memory);

    }

    public void editMemoryAction(View view) {
    }

    public void deleteMemoryAction(View view) {
    }
}