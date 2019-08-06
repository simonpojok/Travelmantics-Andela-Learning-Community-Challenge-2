package com.example.travelmantics;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;


public class UserActivity extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();
    private static FirebaseUser currentFirebaseUser;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        Log.d(TAG, "onCreate()");
        TextView userEmail = findViewById(R.id.user_email);
        TextView userDisplayName = findViewById(R.id.user_display_name);

        userEmail.setText(currentFirebaseUser.getEmail());
        userDisplayName.setText(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getEmail());



    }

    public static Intent createUserActivityIntent(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, UserActivity.class);
        return intent;
    }

}
