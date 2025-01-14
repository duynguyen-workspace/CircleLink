package vn.edu.rmit.circlelink;

import android.app.DatePickerDialog;
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

import vn.edu.rmit.circlelink.model.User;

public class AddUserActivity extends AppCompatActivity {

    private EditText nameET, emailET, passwordET;
    private Spinner roleSpinner, sexSpinner;
    private TextView selectBirthdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        nameET = findViewById(R.id.addUserName);
        emailET = findViewById(R.id.addUserEmail);
        passwordET = findViewById(R.id.addUserPassword);
        roleSpinner = findViewById(R.id.selectUserRole);
        sexSpinner = findViewById(R.id.selectUserSex);
        selectBirthdate = findViewById(R.id.selectUserBirthdate);

        setupRoleSpinner();
        setupSexSpinner();
        setupBirthdatePicker();
    }

    private void setupRoleSpinner() {
        // Create an array of options for the Spinner
        String[] roleOptions = {"Regular User", "Admin"};

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, roleOptions);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        roleSpinner.setAdapter(adapter);

        // Set a listener to handle item selection
        roleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Handle the selection (optional)
                String selectedRole = roleOptions[position];
                Toast.makeText(AddUserActivity.this, "Selected role: " + selectedRole, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });
    }

    private void setupSexSpinner() {
        // Create an array of options for the Spinner
        String[] sexOptions = {"Male", "Female", "Other"};

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, sexOptions);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sexSpinner.setAdapter(adapter);

        // Set a listener to handle item selection
        sexSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Handle the selection (optional)
                String selectedSex = sexOptions[position];
                Toast.makeText(AddUserActivity.this, "Selected sex: " + selectedSex, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });
    }

    private void setupBirthdatePicker() {
        selectBirthdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the current date (or default to today's date if none selected)
                String birthDateString = selectBirthdate.getText().toString();
                int year, month, dayOfMonth;

                if (birthDateString.isEmpty() || birthDateString.equals("Select here to select date of birth")) {
                    // If no date has been selected before, default to today's date
                    LocalDate today = LocalDate.now();
                    year = today.getYear();
                    month = today.getMonthValue() - 1; // Month is 0-based in DatePicker
                    dayOfMonth = today.getDayOfMonth();
                } else {
                    // If there's an existing date, parse it and set the DatePicker accordingly
                    LocalDate birthDate = LocalDate.parse(birthDateString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    year = birthDate.getYear();
                    month = birthDate.getMonthValue() - 1; // Month is 0-based in DatePicker
                    dayOfMonth = birthDate.getDayOfMonth();
                }

                // Create and show the DatePickerDialog
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        AddUserActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                // When the user selects a date, update the TextView
                                LocalDate selectedDate = LocalDate.of(year, monthOfYear + 1, dayOfMonth); // Month is 1-based in LocalDate
                                selectBirthdate.setText(selectedDate.toString());
                            }
                        },
                        year, month, dayOfMonth
                );

                // Show the DatePickerDialog
                datePickerDialog.show();
            }
        });
    }

    public void addUserAction(View view) {
        User user = new User(emailET.getText().toString().trim(),
                passwordET.getText().toString().trim(),
                nameET.getText().toString().trim(),
                (String) sexSpinner.getSelectedItem());

        user.setRoleString((String) roleSpinner.getSelectedItem());

        String birthDateString = selectBirthdate.getText().toString().trim();
        try {
            LocalDate birthDate = LocalDate.parse(birthDateString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            user.setBirthDate(birthDate);
        } catch (DateTimeParseException e) {
            Log.e("AddUserActivity", "Invalid date format: " + birthDateString);
        }
        SuperUserActivity.userList.add(0, user);
        Toast.makeText(this, "User Added", Toast.LENGTH_SHORT).show();
        finish();
    }

    public void closeAction(View view) {
        finish();
    }
}