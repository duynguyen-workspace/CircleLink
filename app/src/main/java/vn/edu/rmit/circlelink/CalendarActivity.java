package vn.edu.rmit.circlelink;

import static vn.edu.rmit.circlelink.CalendarUtils.daysInMonthArray;
import static vn.edu.rmit.circlelink.CalendarUtils.monthYearFromDate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.util.ArrayList;

import vn.edu.rmit.circlelink.adapter.CalendarAdapter;
import vn.edu.rmit.circlelink.adapter.ScheduleAdapter;
import vn.edu.rmit.circlelink.model.Schedule;

public class CalendarActivity extends AppCompatActivity implements CalendarAdapter.OnItemListener{

    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;
    private ListView scheduleListView;
    private Button newScheduleButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        initWidgets();
        CalendarUtils.selectedDate = LocalDate.now();
        setMonthView();
    }

    private void initWidgets() {
        calendarRecyclerView = findViewById(R.id.calendarRecyclerView);
        monthYearText = findViewById(R.id.monthYearTV);
        scheduleListView = findViewById(R.id.scheduleListView);
        newScheduleButton = findViewById(R.id.newScheduleButton);
    }

    private void setMonthView() {
        monthYearText.setText(monthYearFromDate(CalendarUtils.selectedDate));
        newScheduleButton.setText("Add Schedule :  " + CalendarUtils.formattedDateSchedule(CalendarUtils.selectedDate));
        ArrayList<LocalDate> daysInMonth = daysInMonthArray(CalendarUtils.selectedDate);

        CalendarAdapter calendarAdapter = new CalendarAdapter(daysInMonth, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(CalendarActivity.this, 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);
        setScheduleAdapter();
    }

    public void previousMonthAction(View view) {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.minusMonths(1);
        setMonthView();
    }

    public void nextMonthAction(View view) {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusMonths(1);
        setMonthView();
    }

    @Override
    public void onItemClick(int position, LocalDate date) {
        if (date != null) {
            CalendarUtils.selectedDate = date;
            setMonthView();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setScheduleAdapter();
    }

    private void setScheduleAdapter() {
        ArrayList<Schedule> dailySchedules = Schedule.schedulesForDate(CalendarUtils.selectedDate);
        if (dailySchedules.isEmpty()) {
            findViewById(R.id.noSchedulesTV).setVisibility(View.VISIBLE);
            scheduleListView.setVisibility(View.GONE);
        } else {
            findViewById(R.id.noSchedulesTV).setVisibility(View.GONE);
            scheduleListView.setVisibility(View.VISIBLE);

            CalendarUtils.selectedDateSchedules = dailySchedules;
            ScheduleAdapter scheduleAdapter = new ScheduleAdapter(CalendarActivity.this, dailySchedules);
            scheduleListView.setAdapter(scheduleAdapter);
        }
    }

    public void newScheduleAction(View view) {
        startActivity(new Intent(CalendarActivity.this, CreateScheduleActivity.class));
    }
}