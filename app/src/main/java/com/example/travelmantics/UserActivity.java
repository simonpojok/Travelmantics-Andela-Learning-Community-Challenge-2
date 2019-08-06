package com.example.travelmantics;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.travelmantics.Utilities.FirebaseUtility;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;



public class UserActivity extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();
    private static FirebaseUser currentFirebaseUser;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private ChildEventListener childEventListener;
    private RecyclerView rvTravelDeals;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        Log.d(TAG, "onCreate()");
        FirebaseUtility.openFirebaseReference("travelDeals");
        firebaseDatabase = FirebaseUtility.firebaseDatabase;
        databaseReference = FirebaseUtility.databaseReference;

        rvTravelDeals = findViewById(R.id.rvTravelDeals);
        final TravelDealAdapter travelDealAdapter = new TravelDealAdapter();
        rvTravelDeals.setAdapter(travelDealAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(
                this,
                RecyclerView.VERTICAL,
                false
        );
        rvTravelDeals.setLayoutManager(linearLayoutManager);



    }

    public static Intent createUserActivityIntent(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, UserActivity.class);
        return intent;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        Log.d(TAG, "onCreateOptionsMenu");
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.user_activity_menu, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        Log.d(TAG, "onOptionsItemSelected()");
        switch (menuItem.getItemId()){
            case R.id.insert_menu:
                Log.d(TAG, "onOptionsItemSelected() add new notes");
                startActivity(AdministratorActivity.startAdministratorActivity(this));
                return true;

                default:
                    return super.onOptionsItemSelected(menuItem);
        }
    }

}
