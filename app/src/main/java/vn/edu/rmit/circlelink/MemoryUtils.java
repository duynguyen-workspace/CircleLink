package vn.edu.rmit.circlelink;

import android.content.Context;
import android.util.Log;

import androidx.appcompat.app.AlertDialog;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import vn.edu.rmit.circlelink.model.Album;
import vn.edu.rmit.circlelink.model.Memory;

public class MemoryUtils {

    private static final String[] categories = {"Travel", "Birthdays", "Hangouts", "Celebrations", "Holidays"};

    public static ArrayList<Memory> currentMemories = new ArrayList<>();
    public static ArrayList<Album> categoryAlbums = createAlbumsFromCategories();

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

    public static void updateMemory(String oldCategory, Memory updatedMemory) {
        int index = currentMemories.indexOf(updatedMemory);
        if (index != -1) {
            currentMemories.set(index, updatedMemory);
            Log.d("UpdateMem", "Memory is updated in list.");
        }

        Album oldAlbum = getCategoryAlbum(oldCategory);
        if (oldAlbum != null) {
            // Use a separate list to track memories to be removed
            ArrayList<Memory> memoriesToRemove = new ArrayList<>();
            for (Memory memory : oldAlbum.getMemories()) {
                if (memory.getPath().equals(updatedMemory.getPath())) {
                    memoriesToRemove.add(memory);
                }
            }
            // Now remove the memories outside the loop
            oldAlbum.getMemories().removeAll(memoriesToRemove);
            Log.d("UpdateMem", "Memory removed from the old album.");
        }

        Album newAlbum = getCategoryAlbum(updatedMemory.getCategoryID());
        if (newAlbum != null) {
            newAlbum.getMemories().add(updatedMemory);
            Log.d("UpdateMem", "Memory added to the new album.");
        } else {
            Log.e("UpdateMem", "New album not found for category: " + updatedMemory.getCategoryID());
        }

//        ArrayList<Memory> memories = album.getMemories();
//        for (int i = 0; i < memories.size(); i++) {
//            if (memories.get(i).getPath().equals(updatedMemory.getPath())) {
//                memories.set(i, updatedMemory);  // Replace the old memory with the updated one
//                break;  // No need to continue once the memory is updated in the album
//            }
//        }
    }

    public static void deleteMemory(Memory memory) {
        // Remove from currentMemories list
        if (currentMemories.contains(memory)) {
            currentMemories.remove(memory);
        }

        // Find the corresponding album and remove from it
        for (Album album : categoryAlbums) {
            if (album.getMemories().contains(memory)) {
                album.getMemories().remove(memory);  // Remove from album
                break;  // No need to continue once the memory is removed from the album
            }
        }
    }


    public static Album getAlbumById(String albumId) {
        for (Album album : categoryAlbums) {
            if (album.getId().equals(albumId)) {
                return album;
            }
        }
        return null;
    }

    public static Album getCategoryAlbum(String category) {
        for (Album album : categoryAlbums) {
            if (album.getName().equals(category)) {
                return album;
            }
        }
        return null;
    }

    public static void addMemoryToAlbum(String albumName, Memory memory) {
        Album album = getCategoryAlbum(albumName);
        if (album != null) {
            album.getMemories().add(memory);
        } else {
            Log.e("MemoryUtils", "Album not found: " + albumName);
        }
    }

    public static ArrayList<Album> createAlbumsFromCategories() {
        ArrayList<Album> albums = new ArrayList<>();
        for (String category : categories) {
            ArrayList<Memory> memories = new ArrayList<>();
            // For now, we're initializing the albums without any memories
            Album album = new Album(category, memories);
            albums.add(album);
        }
        return albums;
    }

    public static void addCustomAlbum(Album newAlbum) {
        // Add the album to the list
        categoryAlbums.add(newAlbum);
        Log.d("MemoryUtils", "New custom album added: " + newAlbum.getName());
    }

}
