package vn.edu.rmit.circlelink;

import android.os.Bundle;

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

public class SuperUserActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private RecyclerView listView;
    private FloatingActionButton addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_super_user);

        tabLayout = findViewById(R.id.superUserTabLayout);
        listView = findViewById(R.id.superUserRecyclerView);
        addButton = findViewById(R.id.superUserFab);

        listView.setLayoutManager(new LinearLayoutManager(this));

        setUpTabs();
        handleTabSelection();
        setUpAddButton();

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
                switch (tab.getPosition()) {
                    case 0:
                        loadGroups();
                        break;
                    case 1:
                        loadEvents();
                        break;
                    case 2:
                        loadUsers();
                        break;
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
        // Load and set the adapter for groups
        ArrayList<Group> groups = getGroups();
        listView.setAdapter(new GroupsAdapter(groups));
    }

    private void loadEvents() {
        // Load and set the adapter for events
        ArrayList<Event> events = getEvents();
        listView.setAdapter(new EventsAdapter(events));
    }

    private void loadUsers() {
        // Load and set the adapter for users
        ArrayList<User> users = getUsers();
        listView.setAdapter(new UsersAdapter(users));
    }

    private void addEvent() {
        // Add event logic
    }

    private void addUser() {
        // Add user logic
    }

    private ArrayList<Group> getGroups() {
        // Fetch groups from your database or API
        return new ArrayList<>();
    }

    private ArrayList<Event> getEvents() {
        // Fetch events from your database or API
        return new ArrayList<>();
    }

    private ArrayList<User> getUsers() {
        // Fetch users from your database or API
        return new ArrayList<>();
    }

}