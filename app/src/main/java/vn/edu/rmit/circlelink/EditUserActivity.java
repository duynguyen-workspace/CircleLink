package vn.edu.rmit.circlelink;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Calendar;
import java.util.Locale;

import vn.edu.rmit.circlelink.model.User;

public class EditUserActivity extends AppCompatActivity {

    private Spinner sexSpinner;
    private TextView editBirthdate;
    private EditText editNameET, editEmailET;
    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);

        currentUser = getIntent().getParcelableExtra("user");

        editNameET = findViewById(R.id.editUserName);
        editEmailET = findViewById(R.id.editUserEmail);
        sexSpinner = findViewById(R.id.editUserSex);
        editBirthdate = findViewById(R.id.editBirthdate);

        setupSexSpinner();
        setupBirthdatePicker();

        if (currentUser != null) {
            editNameET.setText(currentUser.getName());
            editEmailET.setText(currentUser.getEmail());

            // Set the birthdate field
            LocalDate birthDate = currentUser.getBirthDate(); // Assuming this is a LocalDate
            if (birthDate != null) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                editBirthdate.setText(birthDate.format(formatter));
            } else {
                editBirthdate.setText("Select Date");
            }

            // Set the Spinner selection
            String userSex = currentUser.getSex(); // Assuming getSex() returns "Male", "Female", or "Other"
            if (userSex != null) {
                int spinnerPosition = ((ArrayAdapter<String>) sexSpinner.getAdapter()).getPosition(userSex);
                sexSpinner.setSelection(spinnerPosition);
            }

        } else {
            Log.e("EditUserActivity", "Cannot get user from Intent.");
        }

    }

    private void setupSexSpinner() {
        // Create an array of options for the Spinner
        String[] sexOptions = {"Male", "Female", "Other"};

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, sexOptions);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the Spinner
        sexSpinner.setAdapter(adapter);

        // Set a listener to handle item selection
        sexSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Handle the selection (optional)
                String selectedSex = sexOptions[position];
                Toast.makeText(EditUserActivity.this, "Selected: " + selectedSex, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });
    }

    private void setupBirthdatePicker() {
        editBirthdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the current birthdate as LocalDate (set from currentUser previously)
                String birthDateString = editBirthdate.getText().toString();
                LocalDate birthDate = LocalDate.parse(birthDateString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));

                // Open DatePickerDialog with the existing date pre-populated
                int year = birthDate.getYear();
                int month = birthDate.getMonthValue() - 1; // Month is 0-based in DatePicker
                int dayOfMonth = birthDate.getDayOfMonth();

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        EditUserActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                // When the user selects a date, update the TextView
                                LocalDate newDate = LocalDate.of(year, monthOfYear + 1, dayOfMonth); // Month is 1-based in LocalDate
                                editBirthdate.setText(newDate.toString());
                            }
                        },
                        year, month, dayOfMonth
                );

                // Show the DatePickerDialog
                datePickerDialog.show();
            }
        });
    }

    public void saveEditAction(View view) {

        currentUser.setName(editNameET.getText().toString().trim());
        currentUser.setEmail(editEmailET.getText().toString().trim());

        // Save birthdate as LocalDate
        String birthDateString = editBirthdate.getText().toString().trim();
        try {
            LocalDate birthDate = LocalDate.parse(birthDateString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            currentUser.setBirthDate(birthDate);
        } catch (DateTimeParseException e) {
            Log.e("EditUserActivity", "Invalid date format: " + birthDateString);
            // Optionally, show a Toast or an error message to the user
        }

        String selectedSex = (String) sexSpinner.getSelectedItem();
        currentUser.setSex(selectedSex);

        for (int i = 0; i < SuperUserActivity.userList.size(); i++) {
            if (currentUser.equals(SuperUserActivity.userList.get(i))) {
                SuperUserActivity.userList.set(i, currentUser);
                break;
            }
        }

        Intent intent = new Intent();
        intent.putExtra("updatedObject", "user");
        setResult(RESULT_OK, intent);

        Toast.makeText(EditUserActivity.this, "Edit User successfully!", Toast.LENGTH_SHORT).show();
        finish();
    }

    public void closeAction(View view) {
        finish();
    }
}