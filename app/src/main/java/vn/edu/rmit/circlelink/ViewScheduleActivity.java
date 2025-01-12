package vn.edu.rmit.circlelink;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import vn.edu.rmit.circlelink.model.Schedule;

public class ViewScheduleActivity extends AppCompatActivity {

    private TextView scheduleNameTV, scheduleDateTV, scheduleStartTimeTV, scheduleEndTimeTV,
            scheduleLocationTV, scheduleTimeAlarmTV, scheduleNotesTV;
    private LinearLayout scheduleTimeView;
    private Schedule currentSchedule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_schedule);

        currentSchedule = (Schedule) getIntent().getSerializableExtra("schedule");

        initWidgets();
        setUpDetailView();

    }

    private void initWidgets() {
        scheduleNameTV = findViewById(R.id.scheduleNameTV);
        scheduleDateTV = findViewById(R.id.scheduleDateTV);
        scheduleTimeView = findViewById(R.id.scheduleTimeView);
        scheduleStartTimeTV = findViewById(R.id.scheduleStartTV);
        scheduleEndTimeTV = findViewById(R.id.scheduleEndTV);
        scheduleLocationTV = findViewById(R.id.scheduleLocationTV);
        scheduleTimeAlarmTV = findViewById(R.id.scheduleTimeAlarmTV);
        scheduleNotesTV = findViewById(R.id.scheduleNotesTV);
    }

    private void setUpDetailView() {
        if (currentSchedule != null) {
            scheduleNameTV.setText(currentSchedule.getName());
            scheduleDateTV.setText(CalendarUtils.formattedDateSchedule(currentSchedule.getDate()));
            if (!currentSchedule.isAllDay()) {
                scheduleTimeView.setVisibility(View.VISIBLE);
                scheduleStartTimeTV.setText(CalendarUtils.formattedTime(currentSchedule.getStartTime()));
                scheduleEndTimeTV.setText(CalendarUtils.formattedTime(currentSchedule.getEndTime()));
            }
            scheduleLocationTV.setText(currentSchedule.getLocation());
            if (currentSchedule.getLocation().isEmpty()) {
                scheduleLocationTV.setText("No location");
            }
            scheduleTimeAlarmTV.setText(currentSchedule.getTimeAlarm());
            if (currentSchedule.getNotes().isEmpty()) {
                scheduleNotesTV.setText("No notes");
            } else {
                scheduleNotesTV.setText(currentSchedule.getNotes());
            }
        }
    }


    public void editScheduleAction(View view) {
        Intent intent = new Intent(ViewScheduleActivity.this, CreateScheduleActivity.class);
        intent.putExtra("scheduleToEdit", currentSchedule);
        startActivity(intent);
        finish();
    }

    public void closeAction(View view) {
        finish();
    }

    public void deleteScheduleAction(View view) {

        new AlertDialog.Builder(this)
                .setTitle("Delete Schedule")
                .setMessage("Are you sure you want to delete this schedule?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    Schedule.schedulesList.remove(currentSchedule);
                    finish();
                })
                .setNegativeButton("No", null)
                .show();
    }
}