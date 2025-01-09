package vn.edu.rmit.circlelink;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Calendar;

public class Register extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    String[] sexes = {"M", "F"};
    Spinner spinner;
    Button btnDatePicker, btnSignUp;
    DatePickerDialog datePickerDialog;
    TextView textViewSignIn;
    TextInputEditText editTextUsername, editTextEmail, editTextPassword, editTextFname, editTextLname, editTextPhone;
    FirebaseAuth mAuth;
    ProgressBar progressBar;
    RadioGroup radioGroup;
    RadioButton radioButton;

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(Register.this, Home.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initDatePicker();

        mAuth = FirebaseAuth.getInstance();
        editTextUsername = findViewById(R.id.username);
        editTextEmail = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.password);
        editTextFname = findViewById(R.id.fname);
        editTextLname = findViewById(R.id.lname);
        editTextPhone = findViewById(R.id.phone);
        btnSignUp = findViewById(R.id.btnSignUp);
        spinner = findViewById(R.id.spinner);
        btnDatePicker = findViewById(R.id.btnDatePicker);
        textViewSignIn = findViewById(R.id.textViewSignIn);
        progressBar = findViewById(R.id.progressBar);
        radioGroup = findViewById(R.id.radioGroup);


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                radioButton = findViewById(checkedId);
            }
        });

        spinner.setOnItemSelectedListener(this);

        ArrayAdapter ad = new ArrayAdapter(this, android.R.layout.simple_spinner_item, sexes);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(ad);

        btnDatePicker.setText(getTodaysDate());

        textViewSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Register.this, Login.class);
                startActivity(intent);
                finish();
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);

                String username, email, password, fname, lname, phone;
                username = editTextUsername.getText().toString().trim();
                email = editTextEmail.getText().toString().trim();
                password = editTextPassword.getText().toString().trim();
                fname = editTextFname.getText().toString().trim();
                lname = editTextLname.getText().toString().trim();
                phone = editTextPhone.getText().toString().trim();

                if (TextUtils.isEmpty(username)) {
                    editTextUsername.setError("Please enter a username");
//                    Toast.makeText(Register.this, "Please enter a username", Toast.LENGTH_SHORT).show();
//                    return;
                }

                if (TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    editTextEmail.setError("Please enter a valid email address");
//                    Toast.makeText(Register.this, "Please enter an email address", Toast.LENGTH_SHORT).show();
//                    return;

                }

                if (TextUtils.isEmpty(password)) {
                    editTextPassword.setError("Please enter a password");
//                    Toast.makeText(Register.this, "Please enter a password", Toast.LENGTH_SHORT).show();
//                    return;

                }

                if (TextUtils.isEmpty(fname)) {
                    editTextFname.setError("Please enter your first name");
//                    Toast.makeText(Register.this, "Please enter your first name", Toast.LENGTH_SHORT).show();
//                    return;

                }

                if (TextUtils.isEmpty(lname)) {
                    editTextLname.setError("Please enter your last name");
//                    Toast.makeText(Register.this, "Please enter your last name", Toast.LENGTH_SHORT).show();
//                    return;

                }

                if (TextUtils.isEmpty(phone)) {
                    editTextPhone.setError("Please enter your last name");
//                    Toast.makeText(Register.this, "Please enter your last name", Toast.LENGTH_SHORT).show();
//                    return;
                }

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);
                                if (task.isSuccessful()) {
                                    Toast.makeText(Register.this, "Account created.",
                                            Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(Register.this, Login.class);
                                    startActivity(intent);
                                    finish();
                                } else {

                                    Toast.makeText(Register.this, "Sign up failed.",
                                            Toast.LENGTH_SHORT).show();

                                }
                            }
                        });
            }
        });
    }

    private String getTodaysDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                month = month + 1;
                String date = makeDateString(day, month, year);
                btnDatePicker.setText(date);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.BUTTON_NEUTRAL;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
    }

    private String makeDateString(int day, int month, int year) {
        return getMonthFormat(month) + " " + day + " " + year;
    }

    private String getMonthFormat(int month) {
        if (month == 1)
            return "Jan";
        if (month == 2)
            return "Feb";
        if (month == 3)
            return "Mar";
        if (month == 4)
            return "Apr";
        if (month == 5)
            return "May";
        if (month == 6)
            return "Jun";
        if (month == 7)
            return "Jul";
        if (month == 8)
            return "Aug";
        if (month == 9)
            return "Sep";
        if (month == 10)
            return "Oct";
        if (month == 11)
            return "Nov";
        if (month == 12)
            return "Dec";

        // default should never happen
        return "Jan";
    }

    public void openDatePicker(View view) {
        datePickerDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                WindowManager.LayoutParams lp = datePickerDialog.getWindow().getAttributes();
                lp.dimAmount = 0.7f;
                datePickerDialog.getWindow().setAttributes(lp);
                datePickerDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            }
        });
        datePickerDialog.show();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
}