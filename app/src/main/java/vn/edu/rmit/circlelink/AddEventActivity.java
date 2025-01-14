package vn.edu.rmit.circlelink;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import vn.edu.rmit.circlelink.model.Event;

public class AddEventActivity extends AppCompatActivity {

    private EditText eventTitleET, eventDesET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        eventTitleET = findViewById(R.id.addTitle);
        eventDesET = findViewById(R.id.addDescription);
    }

    public void addEventAction(View view) {
        Event event = new Event(eventTitleET.getText().toString().trim(),
                eventDesET.getText().toString().trim());

        SuperUserActivity.eventList.add(0, event);
        Toast.makeText(this, "Event Added", Toast.LENGTH_SHORT).show();
        finish();
    }

    public void closeAction(View view) {
        finish();
    }
}