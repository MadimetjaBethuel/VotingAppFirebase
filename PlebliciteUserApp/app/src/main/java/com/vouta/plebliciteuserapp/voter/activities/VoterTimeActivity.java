package com.vouta.plebliciteuserapp.voter.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vouta.plebliciteuserapp.R;
import com.vouta.plebliciteuserapp.voter.model.Voters;

public class VoterTimeActivity extends AppCompatActivity {


    private DatabaseReference timeRef;
    private TextView time;
    profileViewActivity voteBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voter_time);

        time = findViewById(R.id.votertimeText);


        timeRef = FirebaseDatabase.getInstance().getReference().child("Voting Time");
        voteBtn = new profileViewActivity();
    }

    @Override
    protected void onStart() {
        super.onStart();

        timeRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    String timeVoting = snapshot.child("Time").getValue().toString().trim();

                    time.setText(timeVoting);

                    if (timeVoting.equals("00:00")){
                        voteBtn.Validate();
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}