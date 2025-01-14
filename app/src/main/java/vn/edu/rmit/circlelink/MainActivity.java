package vn.edu.rmit.circlelink;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.app.Application;
import com.stripe.android.PaymentConfiguration;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PaymentConfiguration.init(
                getApplicationContext(),
                "pk_test_51Qg0pMHJsLoOY2Ah7P1d5Tv6HU8W4jGvYFf4lCAuxWh1oz5cVDpDNUBrtVB7P3CURkb2a0QIO8ZSBUVIFhYZtZdP00Uh8mK5yv"
        );

        setContentView(R.layout.activity_main);

        Button btnGetStarted = findViewById(R.id.btnGetStarted);

        btnGetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Login.class);
                startActivity(intent);
                finish();
            }
        });
    }
}