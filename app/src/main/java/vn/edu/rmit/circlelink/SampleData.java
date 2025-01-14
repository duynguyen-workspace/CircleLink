package vn.edu.rmit.circlelink;

import java.time.LocalDate;
import java.util.ArrayList;

import vn.edu.rmit.circlelink.model.Event;
import vn.edu.rmit.circlelink.model.Group;
import vn.edu.rmit.circlelink.model.GroupUserRelationship;
import vn.edu.rmit.circlelink.model.User;

public class SampleData {

    public static ArrayList<Event> createEventList() {
        ArrayList<Event> events = new ArrayList<>();

        events.add(new Event(1, "Charity Run", "A charity event to raise funds for local causes"));
        events.add(new Event(2, "Tech Talk", "A tech talk on the latest trends in AI"));
        events.add(new Event(3, "Music Concert", "A live concert with local bands"));

        return events;
    }

    public static ArrayList<Group> createGroupList() {
        ArrayList<Group> groups = new ArrayList<>();

        groups.add(new Group(1, "user1", "Community Helpers", "Non-Profit", LocalDate.of(2023, 5, 20)));
        groups.add(new Group(2, "user2", "Tech Innovators", "Technology", LocalDate.of(2022, 8, 15)));
        groups.add(new Group(3, "user3", "Music Lovers", "Hobby", LocalDate.of(2024, 1, 5)));

        return groups;
    }

    public static ArrayList<User> createUserList() {
        ArrayList<User> users = new ArrayList<>();

        users.add(new User(1, 1, 101, "john.doe@example.com", "password123", "John Doe", "Male", LocalDate.of(1990, 2, 15)));
        users.add(new User(2, 2, 102, "jane.smith@example.com", "password456", "Jane Smith", "Female", LocalDate.of(1992, 6, 25)));
        users.add(new User(3, 1, 103, "bob.jones@example.com", "password789", "Bob Jones", "Male", LocalDate.of(1985, 11, 30)));

        return users;
    }

    // Sample data for the relationship between Groups and Users
    public static ArrayList<GroupUserRelationship> createGroupUserRelationships() {
        ArrayList<GroupUserRelationship> relationships = new ArrayList<>();

        // Represent the relationships as (groupId, userId)
        relationships.add(new GroupUserRelationship(1, 1)); // John is in Community Helpers
        relationships.add(new GroupUserRelationship(1, 2)); // Jane is in Community Helpers
        relationships.add(new GroupUserRelationship(2, 2)); // Jane is in Tech Innovators
        relationships.add(new GroupUserRelationship(3, 3)); // Bob is in Music Lovers
        relationships.add(new GroupUserRelationship(2, 1)); // John is in Tech Innovators

        return relationships;
    }
}


