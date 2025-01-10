package vn.edu.rmit.circlelink;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void directToAlbum(View view) {
        Intent intent = new Intent(MainActivity.this, MemoryAlbumActivity.class);
        startActivity(intent);
    }

    public void directToCalendar(View view) {
        Intent intent = new Intent(MainActivity.this, CalendarActivity.class);
        startActivity(intent);
    }
}