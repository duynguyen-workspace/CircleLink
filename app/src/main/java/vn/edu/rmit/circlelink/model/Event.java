package vn.edu.rmit.circlelink.model;

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
}
