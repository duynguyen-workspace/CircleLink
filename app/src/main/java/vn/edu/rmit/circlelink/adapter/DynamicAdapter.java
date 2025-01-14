package vn.edu.rmit.circlelink.adapter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import vn.edu.rmit.circlelink.EditEventActivity;
import vn.edu.rmit.circlelink.EditGroupActivity;
import vn.edu.rmit.circlelink.EditUserActivity;
import vn.edu.rmit.circlelink.R;
import vn.edu.rmit.circlelink.SuperUserActivity;
import vn.edu.rmit.circlelink.model.Event;
import vn.edu.rmit.circlelink.model.Group;
import vn.edu.rmit.circlelink.model.GroupUserRelationship;
import vn.edu.rmit.circlelink.model.User;

public class DynamicAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Object> dataList;
    private ActivityResultLauncher<Intent> editActivityLauncher;

    private final int GROUP_TYPE = 0;
    private final int USER_TYPE = 1;
    private final int EVENT_TYPE = 2;

    private String formatDate(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return date.format(formatter);
    }

    public DynamicAdapter(ActivityResultLauncher<Intent> editActivityLauncher) {
        this.editActivityLauncher = editActivityLauncher;
    }

    public void setData(ArrayList<Object> data) {
        this.dataList = data;
        notifyDataSetChanged(); // Refresh RecyclerView
    }

    @Override
    public int getItemViewType(int position) {
        Object data = dataList.get(position);
        if (data instanceof Group) {
            return GROUP_TYPE;
        } else if (data instanceof User) {
            return USER_TYPE;
        } else if (data instanceof Event) {
            return EVENT_TYPE;
        }
        return -1;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view;

        if (viewType == GROUP_TYPE) {
            view = inflater.inflate(R.layout.item_group, parent, false);
            return new GroupViewHolder(view);
        } else if (viewType == USER_TYPE) {
            view = inflater.inflate(R.layout.item_user, parent, false);
            return new UserViewHolder(view);
        } else if (viewType == EVENT_TYPE) {
            view = inflater.inflate(R.layout.item_event, parent, false);
            return new EventViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Object data = dataList.get(position);

        if (holder instanceof GroupViewHolder) {
            Group group = (Group) data;
            ((GroupViewHolder) holder).bind(group);
        } else if (holder instanceof UserViewHolder) {
            User user = (User) data;
            ((UserViewHolder) holder).bind(user);
        } else if (holder instanceof EventViewHolder) {
            Event event = (Event) data;
            ((EventViewHolder) holder).bind(event);
        }

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class GroupViewHolder extends RecyclerView.ViewHolder {

        private TextView groupName;
        private TextView ownerId;
        private TextView type;
        private TextView memberCount;
        private TextView createdDate;
        private ImageButton editButton, deleteButton;


        public GroupViewHolder(@NonNull View itemView) {
            super(itemView);

            groupName = itemView.findViewById(R.id.groupName);
            ownerId = itemView.findViewById(R.id.ownerId);
            type = itemView.findViewById(R.id.groupType);
            memberCount = itemView.findViewById(R.id.memberCount);
            createdDate = itemView.findViewById(R.id.createdDate);
            editButton = itemView.findViewById(R.id.editButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);

        }

        public void bind(Group group) {
            groupName.setText(group.getName());
            ownerId.setText("Owner ID: " + group.getOwnerId());
            type.setText("Group Type: " + group.getType());
            memberCount.setText(getMemberCount(group.getGroupId()) + " Members");
            createdDate.setText("Date created: " + formatDate(group.getCreatedDate()));

            editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(itemView.getContext(), EditGroupActivity.class);
                    intent.putExtra("group", group);
                    editActivityLauncher.launch(intent);
                }
            });

            deleteButton.setOnClickListener(v -> new AlertDialog.Builder(itemView.getContext())
                    .setTitle("Confirm Deletion")
                    .setMessage("Are you sure you want to delete this event?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Proceed with deletion if the user confirms
                            SuperUserActivity.groupList.remove(group);
                            dataList.remove(group);
                            notifyDataSetChanged();
                            Toast.makeText(itemView.getContext(), group.getName() + " deleted", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("No", null)
                    .show());
        }

        private int getMemberCount(int groupId) {

            int count = 0;

            for (GroupUserRelationship relationship : SuperUserActivity.groupUserRelationshipList) {
                if (relationship.getGroupId() == groupId) {
                    count++;
                }
            }
            return count;
        }
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {

        private TextView userId;
        private TextView name;
        private TextView email;
        private TextView sex;
        private TextView birthDate;
        private TextView role;
        private TextView membershipId;
        private ImageButton editButton, deleteButton;


        public UserViewHolder(@NonNull View itemView) {
            super(itemView);

            userId = itemView.findViewById(R.id.userId);
            name = itemView.findViewById(R.id.userName);
            email = itemView.findViewById(R.id.userEmail);
            sex = itemView.findViewById(R.id.userSex);
            birthDate = itemView.findViewById(R.id.userBirthDate);
            role = itemView.findViewById(R.id.userRole);
            membershipId = itemView.findViewById(R.id.userMembership);
            editButton = itemView.findViewById(R.id.editButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }

        public void bind(User user) {
            userId.setText(String.valueOf(user.getUserId()));
            name.setText(user.getName());
            email.setText(user.getEmail());
            sex.setText(user.getSex());
            birthDate.setText("Date of birth: " + formatDate(user.getBirthDate()));
            role.setText(String.valueOf(user.getRoleString()));
            membershipId.setText("Membership ID: " + user.getMembershipId());

            editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(itemView.getContext(), EditUserActivity.class);
                    intent.putExtra("user", user);
                    editActivityLauncher.launch(intent);
                }
            });

            deleteButton.setOnClickListener(v -> new AlertDialog.Builder(itemView.getContext())
                    .setTitle("Confirm Deletion")
                    .setMessage("Are you sure you want to delete this event?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        // Proceed with deletion if the user confirms
                        SuperUserActivity.userList.remove(user);
                        dataList.remove(user);
                        notifyDataSetChanged();
                        Toast.makeText(itemView.getContext(), user.getName() + " deleted", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("No", null)
                    .show());
        }
    }

    public class EventViewHolder extends RecyclerView.ViewHolder {

        private TextView eventTitle;
        private TextView eventDescription;
        private ImageButton editButton, deleteButton;


        public EventViewHolder(@NonNull View itemView) {
            super(itemView);

            eventTitle = itemView.findViewById(R.id.eventTitle);
            eventDescription = itemView.findViewById(R.id.eventDescription);
            editButton = itemView.findViewById(R.id.editButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }

        public void bind(Event event) {
            eventTitle.setText(event.getTitle());
            eventDescription.setText(event.getDescription());

            editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(itemView.getContext(), EditEventActivity.class);
                    intent.putExtra("event", event);
                    editActivityLauncher.launch(intent);
                }
            });

            deleteButton.setOnClickListener(v -> new AlertDialog.Builder(itemView.getContext())
                    .setTitle("Confirm Deletion")
                    .setMessage("Are you sure you want to delete this event?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Proceed with deletion if the user confirms
                            SuperUserActivity.eventList.remove(event);
                            dataList.remove(event);
                            notifyDataSetChanged();
                            Toast.makeText(itemView.getContext(), event.getTitle() + " deleted", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("No", null)
                    .show());
        }
    }
}
