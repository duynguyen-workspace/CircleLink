package vn.edu.rmit.circlelink.model;

import java.time.LocalDate;
import java.util.Objects;

public class Group {

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
        return groupId == group.groupId && Objects.equals(ownerId, group.ownerId) && Objects.equals(name, group.name) && Objects.equals(type, group.type) && Objects.equals(createdDate, group.createdDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(groupId, ownerId, name, type, createdDate);
    }
}
