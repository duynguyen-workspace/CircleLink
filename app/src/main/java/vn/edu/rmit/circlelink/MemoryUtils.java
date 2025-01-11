package vn.edu.rmit.circlelink;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;

import vn.edu.rmit.circlelink.model.Memory;

public class MemoryUtils {

    public static ArrayList<Memory> currentMemories = new ArrayList<>();

    public static LinkedHashMap<String, ArrayList<Memory>> groupAndSortMemoriesByMonth() {

        currentMemories.sort((m1, m2) -> m2.getCreatedDate().compareTo(m1.getCreatedDate()));
        LinkedHashMap<String, ArrayList<Memory>> groupedMemories = new LinkedHashMap<>();

        for (Memory memory : currentMemories) {
            String month = formatLocalDateToMonthYear(memory.getCreatedDate());
            groupedMemories.computeIfAbsent(month, s -> new ArrayList<>()).add(memory);
        }
        return groupedMemories;
    }

    public static String formatLocalDateToMonthYear(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy");
        return date.format(formatter);
    }

}
