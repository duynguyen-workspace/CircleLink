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

        ArrayList<Memory> sortMemories = currentMemories;

        sortMemories.sort((m1, m2) -> m2.getCreatedDate().compareTo(m1.getCreatedDate()));
        LinkedHashMap<String, ArrayList<Memory>> groupedMemories = new LinkedHashMap<>();

        for (Memory memory : sortMemories) {
            String month = formatLocalDateToMonthYear(memory.getCreatedDate());
            groupedMemories.computeIfAbsent(month, s -> new ArrayList<>()).add(memory);
        }
        return groupedMemories;
    }

    public static String formatLocalDateToMonthYear(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy");
        return date.format(formatter);
    }

    public static void showCategorySelectionDialog(Context context, String previousCategory, CategorySelectionCallback callback) {

        // Find the index of the previous category, or use -1 if it's not found
        int selectedIndex = -1;
        for (int i = 0; i < categories.length; i++) {
            if (categories[i].equals(previousCategory)) {
                selectedIndex = i;
                break;
            }
        }

        // Temporary holder for the selected category
        final String[] selectedCategory = {previousCategory};

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Select a Category (optional)");

        // Use single-choice items (radio button)
        builder.setSingleChoiceItems(categories, selectedIndex, (dialog, which) -> {
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

    public static void updateMemoryInList(Memory updatedMemory) {
        int index = currentMemories.indexOf(updatedMemory);
        if (index != -1) {
            currentMemories.set(index, updatedMemory);
        }
    }


    public static void deleteMemory(Memory memoryToDelete) {
        if (memoryToDelete != null && currentMemories != null) {
            boolean isRemoved = currentMemories.remove(memoryToDelete);
            if (isRemoved) {
                Log.d("MemoryUtils", "Memory deleted successfully.");
            } else {
                Log.d("MemoryUtils", "Memory not found in list.");
            }
        } else {
            Log.d("MemoryUtils", "Memory list or memory to delete is null.");
        }
    }
}
