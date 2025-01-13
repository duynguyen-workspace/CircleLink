package vn.edu.rmit.circlelink;

import android.graphics.Typeface;
import android.os.Bundle;

import android.util.Log;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
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

    public static float[] groupColumnWeights = {1.0f, 1.0f, 1.0f, 1.0f, 1.0f};
    public static float[] userColumnWeights = {0.5f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f};
    public static float[] eventColumnWeights = {0.5f, 1.5f, 2.0f};

    private TabLayout tabLayout;
    private RecyclerView listView;
    private FloatingActionButton addButton;
    private LinearLayout listHeader;

    private DynamicAdapter adapter;

    // List of events, groups, users
    public static ArrayList<Group> groupList;
    public static ArrayList<User> userList;
    public static ArrayList<Event> eventList;
    public static ArrayList<GroupUserRelationship> groupUserRelationshipList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_super_user);

        groupList = SampleData.createGroupList();
        userList = SampleData.createUserList();
        eventList = SampleData.createEventList();
        groupUserRelationshipList = SampleData.createGroupUserRelationships();

        tabLayout = findViewById(R.id.superUserTabLayout);
        listView = findViewById(R.id.superUserRecyclerView);
        listHeader = findViewById(R.id.tableHeaderLayout);
        addButton = findViewById(R.id.superUserFab);

        setUpTabs();
        handleTabSelection();
        setUpAddButton();

        adapter = new DynamicAdapter();
        listView.setLayoutManager(new LinearLayoutManager(this));
        listView.setAdapter(adapter);
        setupHeaders(new String[]{"Name", "Owner ID", "Type", "Total Members", "Created Date"}, groupColumnWeights);
        loadGroups();
    }

    private void setUpAddButton() {
        addButton.setOnClickListener(v -> {
            int selectedTab = tabLayout.getSelectedTabPosition();
            if (selectedTab == 0) {
                addGroup();
            } else if (selectedTab == 1) {
                addEvent();
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
                            setupHeaders(new String[]{"Name", "Owner ID", "Type", "Total Members", "Created Date"}, groupColumnWeights);
                            loadGroups();
                            break;
                        case 1:
                            setupHeaders(new String[]{"ID", "Title", "Description"}, eventColumnWeights);
                            loadEvents();
                            break;
                        case 2:
                            setupHeaders(new String[]{"ID", "Name", "Email", "Sex", "Birthdate", "Role", "Membership ID"}, userColumnWeights);
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
        tabLayout.addTab(tabLayout.newTab().setText("Groups"));
        tabLayout.addTab(tabLayout.newTab().setText("Events"));
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

    private void addEvent() {
        // Add event logic
    }

    private void addUser() {
        // Add user logic
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

    private void setupHeaders(String[] headers, float[] columnWeights) {
        listHeader.removeAllViews(); // Clear existing headers

        for (int i = 0; i < headers.length; i++) {
            TextView headerView = new TextView(this);
            headerView.setText(headers[i]);
            headerView.setGravity(Gravity.CENTER);
            headerView.setTextSize(16);
            headerView.setTypeface(null, Typeface.BOLD);
            headerView.setLayoutParams(new LinearLayout.LayoutParams(
                    0, // Width is determined by weight
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    columnWeights[i] // Assign weight to each header
            ));
            listHeader.addView(headerView);
        }
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


}