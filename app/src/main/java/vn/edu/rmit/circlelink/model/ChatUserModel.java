package vn.edu.rmit.circlelink.model;

import com.google.firebase.firestore.PropertyName;

public class ChatUserModel {
    @PropertyName("UserId")
    private String userId;
    @PropertyName("Name")
    private String name;

    public ChatUserModel() {
        userId = "Test";
        name = "Test";
    }

    public ChatUserModel(String userId, String name) {
        this.userId = userId;
        this.name = name;
    }

    @PropertyName("UserId")
    public String getUserId() {
        return userId;
    }

    @PropertyName("UserId")
    public void setUserId(String userId) {
        this.userId = userId;
    }

    @PropertyName("Name")
    public String getName() {
        return name;
    }

    @PropertyName("Name")
    public void setName(String name) {
        this.name = name;
    }
}
