package vn.edu.rmit.circlelink.utils;

import android.content.Intent;

import vn.edu.rmit.circlelink.ChatActivity;
import vn.edu.rmit.circlelink.model.ChatUserModel;

public class AndroidUtil {
    public static void passUserModelAsIntent(Intent intent, ChatUserModel model) {
        intent.putExtra("name", model.getName());
        intent.putExtra("userId", model.getUserId());
    }

    public static ChatUserModel getUserModelFromIntent(Intent intent) {
        ChatUserModel model = new ChatUserModel();
        model.setName(intent.getStringExtra("name"));
        model.setUserId(intent.getStringExtra("userId"));
        return model;
    }
}
