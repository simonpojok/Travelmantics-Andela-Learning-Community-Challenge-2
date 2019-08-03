package com.example.travelmantics;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class AuthenticationActivity extends AppCompatActivity {
    private static final int RC_SIGN_IN = 100;
    Button signInButton;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (firebaseAuth.getCurrentUser() != null){
            // Start signed in activity
            // startActivity(UserActivity.createIntent(this, null));
            finish();
        }
    }
}
