package vn.edu.rmit.circlelink.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;


import vn.edu.rmit.circlelink.ChatActivity;
import vn.edu.rmit.circlelink.R;
import vn.edu.rmit.circlelink.model.ChatUserModel;
import vn.edu.rmit.circlelink.utils.AndroidUtil;
import vn.edu.rmit.circlelink.utils.FirebaseUtil;

public class SearchUserRecyclerAdapter extends FirestoreRecyclerAdapter<ChatUserModel, SearchUserRecyclerAdapter.UserModelViewHolder> {
    Context context;

    public SearchUserRecyclerAdapter(@NonNull FirestoreRecyclerOptions<ChatUserModel> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull UserModelViewHolder holder, int position, @NonNull ChatUserModel model) {
        holder.nameText.setText(model.getName());
        if (model.getUserId().equals(FirebaseUtil.currentUserId())) {
            holder.nameText.setText(model.getName() + " (Me)");
        }

        holder.itemView.setOnClickListener(v -> {
            // navigate to chat activity
            Intent intent = new Intent(context, ChatActivity.class);
            AndroidUtil.passUserModelAsIntent(intent, model);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        });
    }

    @NonNull
    @Override
    public UserModelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.search_user_recycler_row, parent, false);
        return new UserModelViewHolder(view);
    }

    class UserModelViewHolder extends RecyclerView.ViewHolder {
        TextView nameText;
        ImageView profilePic;

        public UserModelViewHolder(@NonNull View itemView) {
            super(itemView);
            nameText = itemView.findViewById(R.id.name_text);
            profilePic = itemView.findViewById(R.id.profile_pic_image_view);
        }
    }
}
