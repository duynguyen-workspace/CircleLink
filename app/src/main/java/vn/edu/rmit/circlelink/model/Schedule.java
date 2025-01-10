package vn.edu.rmit.circlelink.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Objects;

public class Schedule implements Serializable {
    public static ArrayList<Schedule> schedulesList = new ArrayList<>();
    public static ArrayList<Schedule> schedulesForDate(LocalDate date) {
        ArrayList<Schedule> schedules = new ArrayList<>();

        for (Schedule schedule : schedulesList) {
            if (schedule.getDate().equals(date)) {
                schedules.add(schedule);
            }
        }
        return schedules;
    }

    private String name;
    private LocalDate date;
    private boolean isAllDay;
    private LocalTime startTime;
    private LocalTime endTime;
    private String location;
    private String timeAlarm;
    private String notes;

    public Schedule(String name, LocalDate date, boolean isAllDay, LocalTime startTime, LocalTime endTime, String location, String timeAlarm, String notes) {
        this.name = name;
        this.date = date;
        this.isAllDay = isAllDay;
        this.startTime = startTime;
        this.endTime = endTime;
        this.location = location;
        this.timeAlarm = timeAlarm;
        this.notes = notes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public boolean isAllDay() {
        return isAllDay;
    }

    public void setAllDay(boolean allDay) {
        isAllDay = allDay;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTimeAlarm() {
        return timeAlarm;
    }

    public void setTimeAlarm(String timeAlarm) {
        this.timeAlarm = timeAlarm;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true; // Same reference
        if (obj == null || getClass() != obj.getClass()) return false; // Null or different type
        Schedule schedule = (Schedule) obj;
        return name.equals(schedule.name) && // Compare schedule attributes
                Objects.equals(startTime, schedule.startTime) &&
                Objects.equals(endTime, schedule.endTime);
    }

}
