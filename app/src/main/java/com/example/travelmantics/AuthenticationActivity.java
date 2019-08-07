package com.example.travelmantics;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;


public class AuthenticationActivity extends AppCompatActivity {
    private static final int RC_SIGN_IN = 100;
    private final  String TAG = getClass().getSimpleName();
    FirebaseUser user;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate()");


        if ( user == null ){
            Log.d(TAG, "onCreate() new user");
            List<AuthUI.IdpConfig> providers = Arrays.asList(
                    new AuthUI.IdpConfig.EmailBuilder().build(),
                    new AuthUI.IdpConfig.GoogleBuilder().build()
            );

            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setAvailableProviders(providers)
                            .build(), RC_SIGN_IN
            );

        } else {
            Log.d(TAG, "onCreate() user already signed in");
            startActivity(UserActivity.createUserActivityIntent(this));
            finish();
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN ){
            IdpResponse response = IdpResponse.fromResultIntent(data);
            if (resultCode == RESULT_OK ) {
                Log.d(TAG, "onActivityResult() Successfully signed in");
                user = FirebaseAuth.getInstance().getCurrentUser();
                startActivity(UserActivity.createUserActivityIntent(this));
                finish();
            } else {
                Log.d(TAG, "failed");

            }
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void signOut(){
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.d(TAG, "signout");
                    }
                });
    }

    public static Intent createAuthenticationIntent(Context context){
        Intent intent = new Intent();
        intent.setClass(context, AuthenticationActivity.class);
        return intent;
    }


}
