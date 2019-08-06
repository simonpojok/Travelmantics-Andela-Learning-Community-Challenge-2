package com.example.travelmantics;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.travelmantics.Utilities.TravelDeal;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdministratorActivity extends AppCompatActivity {
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private EditText travelDealTitle;
    private EditText travelDealDescription;
    private EditText travelDealPrice;
    private String TAG = getClass().getSimpleName();
    private TravelDeal travelDeal;
    public static String TRAVEL_DEAL = "travelDeal";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrator);
        Log.d(TAG, "onCreate()");

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("travelDeals");

        travelDealTitle = findViewById(R.id.travelDealTitle);
        travelDealDescription = findViewById(R.id.travelDealDescription);
        travelDealPrice = findViewById(R.id.travelDealPrice);

        Intent intent = getIntent();
        travelDeal = null;
//    intent.getSerializableExtra(TRAVEL_DEAL);

        if (travelDeal == null){
            travelDeal = new TravelDeal();
        }

        travelDealTitle.setText(travelDeal.getTravelDealtitle());
        travelDealDescription.setText(travelDeal.getTravelDealdescription());
        travelDealPrice.setText(travelDeal.getTravelDealprice());
        Log.d(TAG, "onStart() done");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.save_menu:
                Log.d(TAG, "onOptionsItemSelected(MenuItem item) saving travelDeal");
                saveTravelDeal();
                Toast.makeText(this, "Travel Deal Saved", Toast.LENGTH_LONG).show();
                startActivity(UserActivity.createUserActivityIntent(this));
                return true;

            case R.id.delete_menu:
                Log.d(TAG, "onOptionsItemSelected(MenuItem item) deleting TravelDeal");
                deleteTravelDeal();
                Toast.makeText(this, "Travel Deal Deleted", Toast.LENGTH_LONG).show();
                startActivity(UserActivity.createUserActivityIntent(this));
                return true;

                default:
                    return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        Log.d(TAG, "onCreateOptionsMenu start");
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.admin_activity_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void deleteTravelDeal() {
        Log.d(TAG, "deleteTravelDeal() start");
        if (travelDeal == null){
            Toast.makeText(this, "Please save the menu before deleting", Toast.LENGTH_LONG).show();
        }
        databaseReference.child(travelDeal.getTravelDealId()).removeValue();
        Log.d(TAG, "deleteTravelDeal() end");
    }



    private void saveTravelDeal() {
        Log.d(TAG, "saveTravelDeal() start");
        travelDeal.setTravelDealtitle(travelDealTitle.getText().toString());
        travelDeal.setTravelDealdescription(travelDealDescription.getText().toString());
        travelDeal.setTravelDealprice(travelDealPrice.getText().toString());
        if ( travelDeal.getTravelDealId() == null){
            databaseReference.push().setValue(travelDeal);
        } else {
            databaseReference.child(travelDeal.getTravelDealId()).setValue(travelDeal);
        }
        cleanInputWidgets();
        Log.d(TAG, "saveTravelDeal() end");
    }

    private void cleanInputWidgets(){
        Log.d(TAG, "cleanInputWidgets() start");
        travelDealTitle.setText("");
        travelDealPrice.setText("");
        travelDealDescription.setText("");
        travelDealTitle.requestFocus();
    }
}
