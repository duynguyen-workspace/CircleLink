package vn.edu.rmit.circlelink.model;

public class GroupUserRelationship {

    private int groupId;
    private int userId;

    public GroupUserRelationship(int groupId, int userId) {
        this.groupId = groupId;
        this.userId = userId;
    }

    // Getters and Setters
    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
