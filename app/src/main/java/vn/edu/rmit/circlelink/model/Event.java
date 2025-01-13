package vn.edu.rmit.circlelink.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.Objects;
import java.util.UUID;

public class Event implements Parcelable {

    private int eventId;
    private String title;
    private String description;

    public Event(int eventId, String title, String description) {
        this.eventId = eventId;
        this.title = title;
        this.description = description;
    }

    public Event(String title, String description) {
        this.eventId = 1010;
        this.title = title;
        this.description = description;
    }

    protected Event(Parcel in) {
        eventId = in.readInt();
        title = in.readString();
        description = in.readString();
    }

    public static final Creator<Event> CREATOR = new Creator<Event>() {
        @Override
        public Event createFromParcel(Parcel in) {
            return new Event(in);
        }

        @Override
        public Event[] newArray(int size) {
            return new Event[size];
        }
    };

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
        return eventId == event.eventId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventId);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(eventId);
        dest.writeString(title);
        dest.writeString(description);
    }
}
