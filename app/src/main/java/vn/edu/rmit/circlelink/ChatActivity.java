package vn.edu.rmit.circlelink;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Calendar;
import java.util.TimeZone;

import vn.edu.rmit.circlelink.model.ChatMessageModel;
import vn.edu.rmit.circlelink.model.ChatUserModel;
import vn.edu.rmit.circlelink.model.ChatroomModel;
import vn.edu.rmit.circlelink.utils.AndroidUtil;
import vn.edu.rmit.circlelink.utils.FirebaseUtil;

public class ChatActivity extends AppCompatActivity {
    ChatUserModel otherUser;
    String chatroomId;
    ChatroomModel chatroomModel;
    EditText messageInput;
    ImageButton btnSendMessage, btnBack;
    TextView otherUsername;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        // get ChatUserModel
        otherUser = AndroidUtil.getUserModelFromIntent(getIntent());
        chatroomId = FirebaseUtil.getChatroomId(FirebaseUtil.currentUserId(), otherUser.getUserId());

        messageInput = findViewById(R.id.chat_message_input);
        btnSendMessage = findViewById(R.id.btnSendMessage);
        btnBack = findViewById(R.id.btnBack);
        otherUsername = findViewById(R.id.other_username);
        recyclerView = findViewById(R.id.chat_recycler_view);

        btnBack.setOnClickListener(v -> {
            onBackPressed();
        });

        otherUsername.setText(otherUser.getName());

        btnSendMessage.setOnClickListener(v -> {
            String message = messageInput.getText().toString().trim();
            if (message.isEmpty())
                return;
            sendMessageToUser(message);
        });

        getOrCreateChatroomModel();
    }

    private void sendMessageToUser(String message) {
        chatroomModel.setLastMessageTimestamp(new Timestamp(System.currentTimeMillis()));
        chatroomModel.setLastMessageSenderId(FirebaseUtil.currentUserId());
        FirebaseUtil.getChatroomReference(chatroomId).set(chatroomModel);

        ChatMessageModel chatMessageModel = new ChatMessageModel(message, FirebaseUtil.currentUserId(), com.google.firebase.Timestamp.now());
        FirebaseUtil.getChatroomMessageReference(chatroomId).add(chatMessageModel)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if (task.isSuccessful()) {
                            messageInput.setText("");
                        }
                    }
                });
    }

    private void getOrCreateChatroomModel() {
        FirebaseUtil.getChatroomReference(chatroomId).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                chatroomModel = task.getResult().toObject(ChatroomModel.class);
                if (chatroomModel == null) {
                    // first time chat
                    chatroomModel = new ChatroomModel(
                            chatroomId,
                            Arrays.asList(FirebaseUtil.currentUserId(), otherUser.getUserId()),
                            new Timestamp(System.currentTimeMillis()),
                            "");
                    FirebaseUtil.getChatroomReference(chatroomId).set(chatroomModel);
                }
            }
        });
    }
}