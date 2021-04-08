package com.vouta.plebliciteuserapp.organization.activities;

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

public class OrgTimeActivity extends AppCompatActivity {

    private TextView time;
    private DatabaseReference timeRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_org_time);

        time = findViewById(R.id.timeText);

        timeRef = FirebaseDatabase.getInstance().getReference().child("Voting Time");

    }

    @Override
    protected void onStart() {
        super.onStart();

        timeRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    String timeVote = snapshot.child("Time").getValue().toString().trim();

                    time.setText(timeVote);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}