package vn.edu.rmit.circlelink;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.Query;

import vn.edu.rmit.circlelink.adapter.SearchUserRecyclerAdapter;
import vn.edu.rmit.circlelink.model.ChatUserModel;
import vn.edu.rmit.circlelink.utils.FirebaseUtil;

public class SearchUser extends AppCompatActivity {
    EditText searchInput;
    ImageButton searchButton, backButton;
    RecyclerView recyclerView;
    SearchUserRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_user);

        searchInput = findViewById(R.id.search_username_input);
        searchButton = findViewById(R.id.btnSearchUser);
        backButton = findViewById(R.id.btnBack);
        recyclerView = findViewById(R.id.search_user_recycler_view);

        searchInput.requestFocus();

        backButton.setOnClickListener(v -> {
            onBackPressed();
        });

        searchButton.setOnClickListener(v -> {
            String searchTerm = searchInput.getText().toString();
            if (searchTerm.isEmpty() || searchTerm.length() < 2) {
                searchInput.setError("Invalid Username");
                return;
            }
            setUpSearchRecyclerView(searchTerm);
        });
    }

    private void setUpSearchRecyclerView(String searchTerm) {
        Query query = FirebaseUtil.allUserCollectionReference()
                .whereGreaterThanOrEqualTo("Name", searchTerm)
                .whereLessThanOrEqualTo("Name",searchTerm+'\uf8ff');

        FirestoreRecyclerOptions<ChatUserModel> options = new FirestoreRecyclerOptions.Builder<ChatUserModel>()
                .setQuery(query, ChatUserModel.class).build();

        adapter = new SearchUserRecyclerAdapter(options, getApplicationContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (adapter != null) {
            adapter.startListening();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (adapter != null) {
            adapter.stopListening();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (adapter != null) {
            adapter.startListening();
        }
    }
}