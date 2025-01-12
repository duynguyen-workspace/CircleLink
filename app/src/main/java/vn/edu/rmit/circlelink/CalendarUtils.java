package vn.edu.rmit.circlelink;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import vn.edu.rmit.circlelink.model.Schedule;

public class CalendarUtils {
    public static LocalDate selectedDate;
    public static ArrayList<Schedule> selectedDateSchedules;

    public static ArrayList<LocalDate> daysInMonthArray(LocalDate date) {
        ArrayList<LocalDate> daysInMonthArray = new ArrayList<>();
        YearMonth yearMonth = YearMonth.from(date);

        int daysInMonth = yearMonth.lengthOfMonth();

        LocalDate firstOfMonth = CalendarUtils.selectedDate.withDayOfMonth(1);
        int dayOfWeek = firstOfMonth.getDayOfWeek().getValue();

        for (int i = 1; i <= 42; i++) {
            if (i <= dayOfWeek || i > daysInMonth + dayOfWeek) {
                daysInMonthArray.add(null);
            } else {
                daysInMonthArray.add(LocalDate.of(selectedDate.getYear(), selectedDate.getMonth(),i - dayOfWeek));
            }
        }

        return daysInMonthArray;
    }

    public static String monthYearFromDate(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy");
        return date.format(formatter);
    }

    public static boolean isScheduleAvailable(LocalDate date) {
        // Assuming you have a list of schedules
        for (Schedule schedule : Schedule.schedulesList) {
            if (schedule.getDate().equals(date)) {
                return true; // Schedule exists for this date
            }
        }
        return false; // No schedule for this date
    }


    public static String formattedDate(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");
        return date.format(formatter);
    }

    public static String formattedDateSchedule(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE, MMM dd");
        return date.format(formatter);
    }

//    public static String formattedTime(LocalTime time) {
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");
//        return time.format(formatter);
//    }

    public static String formattedTime(LocalTime time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return time.format(formatter);
    }
}
