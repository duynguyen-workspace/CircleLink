package vn.edu.rmit.circlelink;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import vn.edu.rmit.circlelink.model.Event;
import vn.edu.rmit.circlelink.model.Group;
import vn.edu.rmit.circlelink.model.User;

public class SortUtils {

    // Sort events by title (ascending or descending)
    public static void sortEventsByTitle(ArrayList<Event> events, boolean isAscending) {
        Comparator<Event> titleComparator = Comparator.comparing(Event::getTitle, String.CASE_INSENSITIVE_ORDER);
        if (!isAscending) {
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

    // Sort users by name (ascending or descending)
    public static void sortUsersByName(ArrayList<User> users, boolean isAscending) {
        Comparator<User> comparator = Comparator.comparing(User::getName, String.CASE_INSENSITIVE_ORDER);
        if (!isAscending) {
            comparator = comparator.reversed();
        }
        Collections.sort(users, comparator);
    }

    // Sort users by date (ascending or descending)
    public static void sortUsersByDate(ArrayList<User> users, boolean isAscending) {
        Comparator<User> comparator = Comparator.comparing(User::getBirthDate);
        if (!isAscending) {
            comparator = comparator.reversed();
        }
        Collections.sort(users, comparator);
    }
}
