package vn.edu.rmit.circlelink;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.time.LocalTime;

import vn.edu.rmit.circlelink.model.Schedule;

public class CreateScheduleActivity extends AppCompatActivity {

    private LinearLayout scheduleTimeView;
    private EditText scheduleNameET, scheduleLocationET, scheduleNotesET;
    //    private TextView scheduleDateTV, scheduleTimeTV;
    private TextView scheduleDateTV, scheduleStartTime, scheduleEndTime, timeAlarmTV;
    private Switch allDaySwitch;

    private LocalTime selectedStartTime = null;
    private LocalTime selectedEndTime = null;

    private Schedule editSchedule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_schedule);

        Intent intent = getIntent();
        editSchedule = (Schedule) intent.getSerializableExtra("scheduleToEdit");

        initWidgets();

        setUpSwitch();
        setScheduleDate();
        setUpTimeAlarmOptions();

        if (editSchedule != null) editScheduleSetUp();
    }

    private void editScheduleSetUp() {
        scheduleNameET.setText(editSchedule.getName());
        if (!editSchedule.isAllDay()) {
            allDaySwitch.setChecked(false);
            scheduleTimeView.setVisibility(View.VISIBLE);
            scheduleStartTime.setText(CalendarUtils.formattedTime(editSchedule.getStartTime()));
            scheduleEndTime.setText(CalendarUtils.formattedTime(editSchedule.getEndTime()));
        }
        timeAlarmTV.setText(editSchedule.getTimeAlarm());
        scheduleLocationET.setText(editSchedule.getLocation());
        if (!editSchedule.getNotes().isEmpty()) scheduleNotesET.setText(editSchedule.getNotes());
    }

    private void initWidgets() {
        scheduleNameET = findViewById(R.id.scheduleNameET);
        scheduleDateTV = findViewById(R.id.scheduleDateTV);
        scheduleTimeView = findViewById(R.id.scheduleTimeView);
        allDaySwitch = findViewById(R.id.allDaySwitch);
        scheduleStartTime = findViewById(R.id.scheduleStartTime);
        scheduleEndTime = findViewById(R.id.scheduleEndTime);
        scheduleLocationET = findViewById(R.id.scheduleLocationET);
        scheduleNotesET = findViewById(R.id.scheduleNotesET);
        timeAlarmTV = findViewById(R.id.timeAlarmTV);
    }

    private void setUpSwitch() {
        allDaySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    selectedStartTime = null;
                    selectedEndTime = null;

                    scheduleTimeView.setVisibility(View.GONE);
                } else {
                    selectedStartTime = LocalTime.of(8, 0);
                    selectedEndTime = LocalTime.of(17, 0);

                    scheduleTimeView.setVisibility(View.VISIBLE);
                    setUpTimePicker();
                }
            }
        });
    }

    private void setScheduleDate() {
        scheduleDateTV.setText(CalendarUtils.formattedDateSchedule(CalendarUtils.selectedDate));
    }

    private void setUpTimePicker() {
        scheduleStartTime.setText(CalendarUtils.formattedTime(selectedStartTime));
        scheduleEndTime.setText(CalendarUtils.formattedTime(selectedEndTime));

        scheduleStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePicker(selectedStartTime, (view, hourOfDay, minute) -> {
                    selectedStartTime = LocalTime.of(hourOfDay, minute);
                    scheduleStartTime.setText(CalendarUtils.formattedTime(selectedStartTime));

                    if (selectedEndTime.isBefore(selectedStartTime)) {
                        selectedEndTime = selectedStartTime;
                        scheduleEndTime.setText(CalendarUtils.formattedTime(selectedEndTime));
                    }
                });
            }
        });

        scheduleEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePicker(selectedEndTime, (view, hourOfDay, minute) -> {
                    selectedEndTime = LocalTime.of(hourOfDay, minute);
                    scheduleEndTime.setText(CalendarUtils.formattedTime(selectedEndTime));

                    if (selectedEndTime.isBefore(selectedStartTime)) {
                        selectedStartTime = selectedEndTime;
                        scheduleStartTime.setText(CalendarUtils.formattedTime(selectedStartTime));
                    }
                });
            }
        });

    }

    private void showTimePicker(LocalTime initialTime, TimePickerDialog.OnTimeSetListener listener) {
        new TimePickerDialog(
                this,
                (TimePicker view, int hourOfDay, int minute) -> listener.onTimeSet(view, hourOfDay, minute),
                initialTime.getHour(),
                initialTime.getMinute(),
                true // 24-hour format
        ).show();
    }

    private void setUpTimeAlarmOptions() {
        String[] alarmOptions = {
                "No alarm",              // Default option
                "10 minutes before",
                "30 minutes before",
                "1 hour before",
                "1 day before"
        };

        timeAlarmTV.setText(alarmOptions[0]);
        timeAlarmTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int[] selectedOption = {0};

                AlertDialog.Builder builder = new AlertDialog.Builder(CreateScheduleActivity.this);
                builder.setTitle("Select Alarm Time")
                        .setSingleChoiceItems(alarmOptions, selectedOption[0], (dialog, which) -> {
                            selectedOption[0] = which;
                        })
                        .setPositiveButton("OK", (dialog, which) -> {
                            timeAlarmTV.setText(alarmOptions[selectedOption[0]]);
                        })
                        .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

                builder.create().show();
            }
        });
    }

    public void saveScheduleAction(View view) {
        Schedule.schedulesList.remove(editSchedule);
        String scheduleName = scheduleNameET.getText().toString();
        boolean isAllDay = true;
        if (!allDaySwitch.isChecked()) {
            isAllDay = false;
        }
        String scheduleLocation = scheduleLocationET.getText().toString();
        String scheduleTimeAlarm = timeAlarmTV.getText().toString();
        String scheduleNotes = scheduleNotesET.getText().toString();

        Schedule newSchedule = new Schedule(scheduleName, CalendarUtils.selectedDate,
                isAllDay, selectedStartTime, selectedEndTime, scheduleLocation, scheduleTimeAlarm, scheduleNotes);
        Schedule.schedulesList.add(newSchedule);
        finish();
    }

    public void cancelAction(View view) {
        finish();
    }
}