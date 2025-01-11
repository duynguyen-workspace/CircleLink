package vn.edu.rmit.circlelink;

import android.content.Context;
import android.util.Log;

import androidx.appcompat.app.AlertDialog;

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
    private static final String[] categories = {"Travel", "Birthdays", "Hangouts", "Celebrations", "Holidays"};

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

    public static void showCategorySelectionDialog(Context context, CategorySelectionCallback callback) {
        // Define the categories
        final String[] categories = {"Travel", "Birthdays", "Hangouts", "Celebrations", "Holidays"};
        final String[] selectedCategory = {null}; // Temporary holder for the selected category

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Select a Category (optional)");

        // Use single-choice items (radio button)
        builder.setSingleChoiceItems(categories, -1, (dialog, which) -> {
            selectedCategory[0] = categories[which];
        });

        builder.setPositiveButton("Confirm", (dialog, which) -> {
            if (selectedCategory[0] != null) {
                callback.onCategorySelected(selectedCategory[0]);
            } else {
                callback.onCategorySelected("none"); // Default to "none" if nothing is selected
            }
            dialog.dismiss();
        });

        builder.setNegativeButton("Skip", (dialog, which) -> {
            callback.onCategorySelected("none"); // Default to "none" on cancel
            dialog.dismiss();
        });

        builder.create().show();
    }

    public interface CategorySelectionCallback {
        void onCategorySelected(String category);
    }

}
