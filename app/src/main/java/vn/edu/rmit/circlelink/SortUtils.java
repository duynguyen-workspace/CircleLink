package vn.edu.rmit.circlelink;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import vn.edu.rmit.circlelink.model.Event;

public class SortUtils {

    // Sort events by title (ascending or descending)
    public static void sortEventsByTitle(ArrayList<Event> events, boolean ascending) {
        Comparator<Event> titleComparator = new Comparator<Event>() {
            @Override
            public int compare(Event e1, Event e2) {
                return e1.getTitle().compareTo(e2.getTitle());
            }
        };

        if (!ascending) {
            titleComparator = titleComparator.reversed();
        }

        events.sort(titleComparator);
    }
}
