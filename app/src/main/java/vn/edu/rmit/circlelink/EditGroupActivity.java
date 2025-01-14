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
import vn.edu.rmit.circlelink.model.Group;

public class EditGroupActivity extends AppCompatActivity {

    private EditText editNameET, editTypeET;
    private Group currentGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_group);

        currentGroup = getIntent().getParcelableExtra("group");

        editNameET = findViewById(R.id.editGroupName);
        editTypeET = findViewById(R.id.editGroupType);

        if (currentGroup != null) {
            editNameET.setText(currentGroup.getName());
            editTypeET.setText(currentGroup.getType());
        } else {
            Log.e("EditGroupActivity", "Cannot get group from Intent.");
        }
    }

    public void saveEditAction(View view) {
        currentGroup.setName(editNameET.getText().toString().trim());
        currentGroup.setType(editTypeET.getText().toString().trim());

        for (int i = 0; i < SuperUserActivity.groupList.size(); i++) {
            if (currentGroup.equals(SuperUserActivity.groupList.get(i))) {
                SuperUserActivity.groupList.set(i, currentGroup);
                break;
            }
        }

        Intent intent = new Intent();
        intent.putExtra("updatedObject", "group");
        setResult(RESULT_OK, intent);

        Toast.makeText(EditGroupActivity.this, "Edit Group successfully!", Toast.LENGTH_SHORT).show();
        finish();
    }

    public void closeAction(View view) {
        finish();
    }
}