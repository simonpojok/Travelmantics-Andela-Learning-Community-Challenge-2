package com.example.travelmantics;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.travelmantics.Utilities.FirebaseUtility;
import com.example.travelmantics.Utilities.TravelDeal;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class AdministratorActivity extends AppCompatActivity {
    private DatabaseReference databaseReference;
    private EditText travelDealTitle;
    private EditText travelDealDescription;
    private EditText travelDealPrice;
    private ImageView travelDealImage;
    private String TAG = getClass().getSimpleName();
    private TravelDeal travelDeal;
    public static String TRAVEL_DEAL = "travelDeal";
    private static final int   PICTURE_RESULT = 2453;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrator);
        Log.d(TAG, "onCreate()");

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("travelDeals");

        travelDealTitle = findViewById(R.id.travelDealTitle);
        travelDealDescription = findViewById(R.id.travelDealDescription);
        travelDealPrice = findViewById(R.id.travelDealPrice);
        travelDealImage = findViewById(R.id.travelDealImage);

        Intent intent = getIntent();
        travelDeal = (TravelDeal) intent.getSerializableExtra(TRAVEL_DEAL);

        if (travelDeal == null){
            travelDeal = new TravelDeal();
        }

        travelDealTitle.setText(travelDeal.getTravelDealTitle());
        travelDealDescription.setText(travelDeal.getTravelDealDescription());
        travelDealPrice.setText(travelDeal.getTravelDealPrice());
        showImage(travelDeal.getTravelDealImageUrl());
        Button travelDealButton = findViewById(R.id.travelDealButton);

        travelDealButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loadImageIntent = new Intent(Intent.ACTION_GET_CONTENT);
                loadImageIntent.setType("image/jpeg");
                loadImageIntent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                startActivityForResult(loadImageIntent.createChooser(loadImageIntent, "Insert Picture"), PICTURE_RESULT);
            }
        });
        Log.d(TAG, "onStart() done");
    }

    private void showImage(String travelDealImageUrl) {
        Log.d(TAG, "showImage showing image");
        if ( travelDealImageUrl != null && !travelDealImageUrl.isEmpty()){
            int imageWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
            Picasso.with(this)
                    .load(travelDealImageUrl)
                    .resize(imageWidth, imageWidth*2/3)
                    .centerCrop()
                    .into(travelDealImage);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.save_menu:
                Log.d(TAG, "onOptionsItemSelected(MenuItem item) saving travelDeal");
                saveTravelDeal();
                Toast.makeText(this, "Travel Deal Saved", Toast.LENGTH_LONG).show();
                startActivity(UserActivity.createUserActivityIntent(this));
                cleanInputWidgets();
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
        if (FirebaseUtility.isAdmin){
            menu.findItem(R.id.delete_menu).setVisible(true);
            menu.findItem(R.id.save_menu).setVisible(true);
        } else {
            menu.findItem(R.id.delete_menu).setVisible(true);
            menu.findItem(R.id.save_menu).setVisible(true);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        Log.d(TAG, "<<<<<<<<<<<<<<<<<<<  onActivityResult started >>>>>>>>>>>>>>>>>>>>>>>>>");
        super.onActivityResult(requestCode, resultCode, data);
        if ( requestCode == PICTURE_RESULT ){
            if (resultCode == RESULT_OK){
                Log.d(TAG, "onActivityResult started result ok");
                Uri imageUri = data.getData();
                StorageReference ref = FirebaseUtility.storageReference.child(imageUri.getLastPathSegment());
                ref.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Log.d(TAG, "onActivityResult file uploaded well");
                        String url = taskSnapshot.getMetadata().getReference().getDownloadUrl().toString();
                        String pictureName = taskSnapshot.getStorage().getPath();
                        travelDeal.setTravelDealImageUrl(url);
                        travelDeal.setTravelDealImageName(pictureName);
                        Log.d(TAG, "Image URI: " + url + " PictureName " + pictureName);
                        showImage(url);


                    }
                });
            }

        }
    }


    private void deleteTravelDeal() {
        Log.d(TAG, "deleteTravelDeal() start");
        if (travelDeal == null){
            Toast.makeText(this, "Please save the menu before deleting", Toast.LENGTH_LONG).show();
        }
        databaseReference.child(travelDeal.getTravelDealId()).removeValue();
        Log.d(TAG, "deleteTravelDeal() end Image Info: " + travelDeal.getTravelDealImageName());
        if ( travelDeal.getTravelDealImageName() != null && !travelDeal.getTravelDealImageName().isEmpty()){
            StorageReference travelDealImageReference = FirebaseUtility.firebaseStorage.getReference().child(travelDeal.getTravelDealImageName());
            travelDealImageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.d(TAG, "Delete Image, Image deleted");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                   Log.d(TAG, "Delete Image : " + e.getMessage());
                }
            });
        }
    }



    private void saveTravelDeal() {
        Log.d(TAG, "saveTravelDeal() start");
        travelDeal.setTravelDealTitle(travelDealTitle.getText().toString());
        travelDeal.setTravelDealDescription(travelDealDescription.getText().toString());
        travelDeal.setTravelDealPrice(travelDealPrice.getText().toString());
        if ( travelDeal.getTravelDealId() == null){
            Log.d(TAG, "saveTravelDeal() no Id new deal");
            databaseReference.push().setValue(travelDeal);
        } else {
            databaseReference.child(travelDeal.getTravelDealId()).setValue(travelDeal);
            Log.d(TAG, "saveTravelDeal() save");
            Log.d(TAG, travelDeal.toString());
        }
        Log.d(TAG, "saveTravelDeal() end");
    }

    private void cleanInputWidgets(){
        Log.d(TAG, "cleanInputWidgets() start");
        travelDealTitle.setText("");
        travelDealPrice.setText("");
        travelDealDescription.setText("");
        travelDealTitle.requestFocus();
    }

    public static Intent startAdministratorActivity(Context context) {
        Log.d("AdministatorActivity", "startAdministratorActivity() from UserActivity() ");
        Intent adminIntent = new Intent();
        adminIntent.setClass(context, AdministratorActivity.class);
        return adminIntent;
    }

    private void enableEditTexts(boolean isEnabled){
        travelDealTitle.setEnabled(isEnabled);
        travelDealDescription.setEnabled(isEnabled);
        travelDealPrice.setEnabled(isEnabled);
    }
}
