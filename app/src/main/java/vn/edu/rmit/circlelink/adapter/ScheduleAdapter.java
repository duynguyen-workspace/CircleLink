package vn.edu.rmit.circlelink.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import vn.edu.rmit.circlelink.CalendarUtils;
import vn.edu.rmit.circlelink.R;
import vn.edu.rmit.circlelink.ViewScheduleActivity;
import vn.edu.rmit.circlelink.model.Schedule;

public class ScheduleAdapter extends ArrayAdapter<Schedule> {
    public ScheduleAdapter(@NonNull Context context, List<Schedule> schedules) {
        super(context, 0, schedules);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Schedule schedule = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.schedule_cell, parent, false);
        }

        TextView scheduleCellNameTV = convertView.findViewById(R.id.scheduleCellNameTV);
        TextView scheduleCellTimeTV = convertView.findViewById(R.id.scheduleCellTimeTV);

        String scheduleTitle = schedule.getName();
        scheduleCellNameTV.setText(scheduleTitle);

        if (schedule.isAllDay()) {
            scheduleCellTimeTV.setText("All day");
        } else {
            if (schedule.getStartTime().equals(schedule.getEndTime())){
                scheduleCellTimeTV.setText(CalendarUtils.formattedTime(schedule.getStartTime()));
            } else {
                scheduleCellTimeTV.setText(
                        CalendarUtils.formattedTime(schedule.getStartTime()) + " - " + CalendarUtils.formattedTime(schedule.getEndTime())
                );
            }
        }

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ViewScheduleActivity.class);
                intent.putExtra("schedule", schedule);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getContext().startActivity(intent);
            }
        });

        return convertView;
    }
}
