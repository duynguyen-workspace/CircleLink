package vn.edu.rmit.circlelink;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import vn.edu.rmit.circlelink.model.Event;

public class EditEventActivity extends AppCompatActivity {

    private EditText editTitleET, editDescriptionET;
    private Event currentEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_event);

        currentEvent = getIntent().getParcelableExtra("event");

        editTitleET = findViewById(R.id.editTitle);
        editDescriptionET = findViewById(R.id.editDescription);

        if (currentEvent != null) {
            editTitleET.setText(currentEvent.getTitle());
            editDescriptionET.setText(currentEvent.getDescription());
        } else {
            Log.e("EditEventActivity", "Cannot get event from Intent.");
        }
    }

    public void saveEditAction(View view) {

        currentEvent.setTitle(editTitleET.getText().toString().trim());
        currentEvent.setDescription(editDescriptionET.getText().toString().trim());

        for (int i = 0; i < SuperUserActivity.eventList.size(); i++) {
            if (currentEvent.equals(SuperUserActivity.eventList.get(i))) {
                SuperUserActivity.eventList.set(i, currentEvent);
                break;
            }
        }

        Intent intent = new Intent();
        intent.putExtra("updatedObject", "event");
        setResult(RESULT_OK, intent);

        Toast.makeText(EditEventActivity.this, "Edit Event successfully!", Toast.LENGTH_SHORT).show();
        finish();
    }

    public void closeAction(View view) {
        finish();
    }
}