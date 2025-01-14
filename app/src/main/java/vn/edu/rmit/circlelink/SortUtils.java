package vn.edu.rmit.circlelink;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import vn.edu.rmit.circlelink.model.Event;
import vn.edu.rmit.circlelink.model.Group;

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

    // Sort groups by name (ascending or descending)
    public static void sortGroupsByName(ArrayList<Group> groups, boolean isAscending) {
        Comparator<Group> comparator = Comparator.comparing(Group::getName, String.CASE_INSENSITIVE_ORDER);
        if (!isAscending) {
            comparator = comparator.reversed();
        }
        Collections.sort(groups, comparator);
    }

    // Sort groups by date (ascending or descending)
    public static void sortGroupsByDate(ArrayList<Group> groups, boolean isAscending) {
        Comparator<Group> comparator = Comparator.comparing(Group::getCreatedDate);
        if (!isAscending) {
            comparator = comparator.reversed();
        }
        Collections.sort(groups, comparator);
    }
}
