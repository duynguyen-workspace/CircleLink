package vn.edu.rmit.circlelink.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.time.LocalDate;
import java.util.Objects;

public class Group implements Parcelable {

    private int groupId;
    private String ownerId;
    private String name;
    private String type;
    private LocalDate createdDate;

    public Group(int groupId, String ownerId, String name, String type, LocalDate createdDate) {
        this.groupId = groupId;
        this.ownerId = ownerId;
        this.name = name;
        this.type = type;
        this.createdDate = createdDate;
    }

    protected Group(Parcel in) {
        groupId = in.readInt();
        ownerId = in.readString();
        name = in.readString();
        type = in.readString();

        String createdDateString = in.readString();
        if (createdDateString != null) {
            createdDate = LocalDate.parse(createdDateString);
        }
    }

    public static final Creator<Group> CREATOR = new Creator<Group>() {
        @Override
        public Group createFromParcel(Parcel in) {
            return new Group(in);
        }

        @Override
        public Group[] newArray(int size) {
            return new Group[size];
        }
    };

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Group group = (Group) o;
        return groupId == group.groupId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(groupId);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(groupId);
        dest.writeString(ownerId);
        dest.writeString(name);
        dest.writeString(type);

        dest.writeString(createdDate != null ? createdDate.toString() : null);
    }
}
