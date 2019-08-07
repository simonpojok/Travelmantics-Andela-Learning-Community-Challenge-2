package com.example.travelmantics.Utilities;


import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.travelmantics.AuthenticationActivity;
import com.example.travelmantics.UserActivity;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class FirebaseUtility {
    public static FirebaseDatabase firebaseDatabase;
    public static DatabaseReference databaseReference;
    private static FirebaseUtility firebaseUtility;
    public static FirebaseAuth firebaseAuth;
    public static FirebaseStorage firebaseStorage;
    private static final String TAG = "FirebaseUtility";
    public static StorageReference storageReference;
    public static FirebaseAuth.AuthStateListener authStateListener;
    public static ArrayList<TravelDeal> travelDeals;
    private static final int RC_SIGN_IN_FU = 2019;
    private static UserActivity caller;
    private FirebaseUtility(){}
    public static boolean isAdmin;

    public static void openFirebaseReference(String reference, final UserActivity callerActivity){
        if ( firebaseUtility == null){
            firebaseUtility = new FirebaseUtility();
            firebaseDatabase = FirebaseDatabase.getInstance();
            firebaseAuth =  FirebaseAuth.getInstance();
            caller = callerActivity;

            authStateListener = new FirebaseAuth.AuthStateListener(){
                @Override
                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                    if ( firebaseAuth.getCurrentUser() == null){
                        FirebaseUtility.signIn();
                    } else {
                        String userId = firebaseAuth.getUid();
                        checkAdmin(userId);
                    }

                    Toast.makeText(callerActivity.getBaseContext(), "Welcome back!", Toast.LENGTH_LONG).show();
                }


            };
            connectStorage();
        }
        travelDeals = new ArrayList<>();
        databaseReference = firebaseDatabase.getReference().child(reference);
    }

    private static void connectStorage() {
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference().child("travelDealPictures");
    }

    private static void checkAdmin(String userId) {
        FirebaseUtility.isAdmin = false;
        DatabaseReference ref = firebaseDatabase.getReference().child("administrators").child(userId);
        ChildEventListener listener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                FirebaseUtility.isAdmin = true;
                caller.showMenu();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        ref.addChildEventListener(listener);
    }

    private static void signIn() {
        Log.d(TAG, "onCreate() new user");
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build()
        );

        caller.startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .setIsSmartLockEnabled(false)
                        .build(), RC_SIGN_IN_FU
        );
    }

    public static void attachListener(){
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    public static void detachListener(){
        firebaseAuth.removeAuthStateListener(authStateListener);
    }


}
