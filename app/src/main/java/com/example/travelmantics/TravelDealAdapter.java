package com.example.travelmantics;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelmantics.Utilities.FirebaseUtility;
import com.example.travelmantics.Utilities.TravelDeal;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TravelDealAdapter extends  RecyclerView.Adapter<TravelDealAdapter.TravelDealViewHolder>{
    private final String TAG = getClass().getSimpleName();
    private ArrayList<TravelDeal> travelDeals;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private ChildEventListener childEventListener;
    private ImageView travelDealImage;

    public TravelDealAdapter() {
        Log.d(TAG, "onCreateViewHolder()");
        firebaseDatabase = FirebaseUtility.firebaseDatabase;
        databaseReference = FirebaseUtility.databaseReference;
        travelDeals = FirebaseUtility.travelDeals;

        childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.d(TAG, "onCreateViewHolder() onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s)");
                TravelDeal td = dataSnapshot.getValue(TravelDeal.class);
                td.setTravelDealId(dataSnapshot.getKey());
                Log.d(TAG, td.getTravelDealTitle());
                travelDeals.add(td);
                Log.d(TAG, "<<<<<<<<<<<<<<<< deal added >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
                notifyItemInserted(travelDeals.size() - 1);
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

       databaseReference.addChildEventListener(childEventListener);
    }

    @NonNull
    @Override
    public TravelDealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "<<<<<<<<<<<<<<<< onCreateViewHolder >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

        Context context = parent.getContext();
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.travel_deal_row, parent, false);
        return new TravelDealViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TravelDealViewHolder holder, int position) {
        Log.d(TAG, "<<<<<<<<<<<<<<<< onBindViewHolder >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        TravelDeal travelDeal = travelDeals.get(position);
        holder.bind(travelDeal);
        Log.d(TAG, "<<<<<<<<<<<<<<<< onBindViewHolder done>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "<<<<<<<<<<<<<<<< getItemCount() >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        return travelDeals.size();
    }

    public class TravelDealViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final String ViewHolderTag = getClass().getSimpleName();
        private  TextView travelDealTitle;
        private  TextView travelDealDescription;
        private  TextView travelDealPrice;


        public TravelDealViewHolder(@NonNull View itemView) {
            super(itemView);
            Log.d(ViewHolderTag, "TravelDealViewHolder()");
            travelDealTitle = itemView.findViewById(R.id.travelDealTitle);
            travelDealDescription = itemView.findViewById(R.id.travelDealDescription);
            travelDealPrice = itemView.findViewById(R.id.travelDealPrice);
            travelDealImage = itemView.findViewById(R.id.travelDealImage);

            itemView.setOnClickListener(this);
            Log.d(ViewHolderTag, "TravelDealViewHolder() done ");
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            Log.d(ViewHolderTag, "TravelDealViewHolder onClick");
            Log.d(ViewHolderTag, String.valueOf(position));
            Intent onClickIntent = new Intent(view.getContext(), AdministratorActivity.class);
            onClickIntent.putExtra(AdministratorActivity.TRAVEL_DEAL, travelDeals.get(position));
            view.getContext().startActivity(onClickIntent);

        }

        private void showImage(String url){
            if ( url != null && !url.isEmpty()){
                Picasso.with(travelDealImage.getContext())
                        .load(url)
                        .resize(160, 160)
                        .centerCrop()
                        .into(travelDealImage);
            }
        }

        public void bind(TravelDeal deal){
            travelDealTitle.setText(deal.getTravelDealTitle());
            travelDealDescription.setText(deal.getTravelDealDescription());
            travelDealPrice.setText(deal.getTravelDealPrice());
            showImage(deal.getTravelDealImageUrl());
        }
    }
}
