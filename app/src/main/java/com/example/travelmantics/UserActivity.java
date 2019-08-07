package com.example.travelmantics;

import androidx.annotation.NonNull;
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
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;


public class UserActivity extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();
//    private static FirebaseUser currentFirebaseUser;
//    private FirebaseDatabase firebaseDatabase;
//    private DatabaseReference databaseReference;
//    private ChildEventListener childEventListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        Log.d(TAG, "onCreate()");
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
        MenuItem menuItem = menu.findItem(R.id.insert_menu);

//        if (FirebaseUtility.isAdmin){
//            menuItem.setVisible(true);
//        } else {
//            menuItem.setVisible(false);
//        }

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
            case R.id.logout_menu:
                AuthUI.getInstance()
                        .signOut(this)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Log.d(TAG, "User logged Out");
                                FirebaseUtility.attachListener();
                            }
                        });
                FirebaseUtility.detachListener();
                return true;

        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    protected void onPause(){
        super.onPause();
        FirebaseUtility.detachListener();
    }

    @Override
    protected void onResume(){
        super.onResume();
        FirebaseUtility.openFirebaseReference("travelDeals", this);
        RecyclerView rvTravelDeals = findViewById(R.id.rvTravelDeals);
        final TravelDealAdapter adapter = new TravelDealAdapter();
        rvTravelDeals.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rvTravelDeals.setLayoutManager(linearLayoutManager);
        FirebaseUtility.attachListener();
    }

    public void showMenu() {
        invalidateOptionsMenu();
    }

}
