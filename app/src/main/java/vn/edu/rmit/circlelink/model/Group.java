package vn.edu.rmit.circlelink.model;

import java.time.LocalDate;

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
}
