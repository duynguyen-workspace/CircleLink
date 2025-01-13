package vn.edu.rmit.circlelink;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;

import android.util.Log;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import vn.edu.rmit.circlelink.adapter.DynamicAdapter;
import vn.edu.rmit.circlelink.model.Event;
import vn.edu.rmit.circlelink.model.Group;
import vn.edu.rmit.circlelink.model.GroupUserRelationship;
import vn.edu.rmit.circlelink.model.User;

public class SuperUserActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private RecyclerView listView;
    private FloatingActionButton addButton;

    private DynamicAdapter adapter;
    private ActivityResultLauncher<Intent> editActivityLauncher;

    // List of events, groups, users
    public static ArrayList<Group> groupList;
    public static ArrayList<User> userList;
    public static ArrayList<Event> eventList;
    public static ArrayList<GroupUserRelationship> groupUserRelationshipList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_super_user);

        editActivityLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    // Handle the result from EditActivity
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            // Extract the updated object from the Intent
                            String updatedObject = data.getStringExtra("updatedObject");

                            // Update your data list
                            if ("group".equals(updatedObject)) {
                                loadGroups();
                            } else if ("user".equals(updatedObject)) {
                                loadUsers();
                            } else if ("event".equals(updatedObject)) {
                                loadEvents();
                            }
                        }
                    }
                });

        groupList = SampleData.createGroupList();
        userList = SampleData.createUserList();
        eventList = SampleData.createEventList();
        groupUserRelationshipList = SampleData.createGroupUserRelationships();

        tabLayout = findViewById(R.id.superUserTabLayout);
        listView = findViewById(R.id.superUserRecyclerView);
        addButton = findViewById(R.id.superUserFab);

        setUpTabs();
        handleTabSelection();
        setUpAddButton();

        adapter = new DynamicAdapter(editActivityLauncher);
        listView.setLayoutManager(new LinearLayoutManager(this));
        listView.setAdapter(adapter);
        loadEvents();
    }

    private void setUpAddButton() {
        addButton.setOnClickListener(v -> {
            int selectedTab = tabLayout.getSelectedTabPosition();
            if (selectedTab == 0) {
                addEventForm();
            } else if (selectedTab == 1) {
                addGroup();
            } else {
                addUser();
            }
        });
    }

    private void handleTabSelection() {
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (isActivityValid()) {
                    switch (tab.getPosition()) {
                        case 0:
                            loadEvents();
                            break;
                        case 1:
                            loadGroups();
                            break;
                        case 2:
                            loadUsers();
                            break;
                    }
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
    }

    private void setUpTabs() {
        tabLayout.addTab(tabLayout.newTab().setText("Events"));
        tabLayout.addTab(tabLayout.newTab().setText("Groups"));
        tabLayout.addTab(tabLayout.newTab().setText("Users"));
    }

    private void loadGroups() {
        if (isActivityValid()) {
            adapter.setData(new ArrayList<Object>(groupList));
        }
    }

    private void loadEvents() {
        if (isActivityValid()) {
            adapter.setData(new ArrayList<Object>(eventList));
        }
    }

    private void loadUsers() {
        if (isActivityValid()) {
            adapter.setData(new ArrayList<Object>(userList));
        }
    }

    private void addGroup() {
        // Add group logic
    }

    private void addEventForm() {
        Intent intent = new Intent(this, AddEventActivity.class);
        startActivity(intent);
    }

    private void addUser() {
        Intent intent = new Intent(this, AddUserActivity.class);
        startActivity(intent);
    }

    private ArrayList<Group> getGroups() {
        // Fetch groups from your database or API
        return groupList;
    }

    private ArrayList<Event> getEvents() {
        // Fetch events from your database or API
        return eventList;
    }

    private ArrayList<User> getUsers() {
        // Fetch users from your database or API
        return userList;
    }

    private boolean isActivityValid() {
        return !isFinishing() && !isDestroyed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Nullify references when the activity is destroyed
        adapter = null;
        tabLayout = null;
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (adapter != null && isActivityValid()) {
            adapter.setData(new ArrayList<>()); // Clear data when activity is stopped
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        int selectedTabPosition = tabLayout.getSelectedTabPosition();

        switch (selectedTabPosition) {
            case 0: // Events tab
                loadEvents();
                break;
            case 1: // Groups tab
                loadGroups();
                break;
            case 2: // Users tab
                loadUsers();
                break;
        }
    }
}