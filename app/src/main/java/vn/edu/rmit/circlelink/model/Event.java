package vn.edu.rmit.circlelink.model;

import java.util.Objects;

public class Event {

    private int eventId;
    private String title;
    private String description;

    public Event(int eventId, String title, String description) {
        this.eventId = eventId;
        this.title = title;
        this.description = description;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return eventId == event.eventId && Objects.equals(title, event.title) && Objects.equals(description, event.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventId, title, description);
    }
}
