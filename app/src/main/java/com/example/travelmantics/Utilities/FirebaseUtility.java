package com.example.travelmantics.Utilities;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class FirebaseUtility {
    public static FirebaseDatabase firebaseDatabase;
    public static DatabaseReference databaseReference;
    public static FirebaseUtility firebaseUtility;
    public static ArrayList<TravelDeal> travelDeals;

    private FirebaseUtility(){
//        empty
    }

    public static void openFirebaseReference(String reference){
        if ( firebaseUtility == null){
            firebaseUtility = new FirebaseUtility();
            firebaseDatabase = FirebaseDatabase.getInstance();
            travelDeals = new ArrayList<TravelDeal>();

        }

        databaseReference = firebaseDatabase.getReference().child(reference);
    }


}
