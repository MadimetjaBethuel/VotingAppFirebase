package com.vouta.plebliciteuserapp.voter.activities;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vouta.plebliciteuserapp.R;
import com.vouta.plebliciteuserapp.voter.adapter.ProfilesAdapter;



import com.vouta.plebliciteuserapp.voter.model.candidateModel;


import java.util.ArrayList;
import java.util.List;

public class profilesActivity extends AppCompatActivity {

    private List<candidateModel> lists = new ArrayList<>();
    private RecyclerView recyclerView;
    private DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profiles);


        recyclerView = findViewById(R.id.profile_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ProfilesAdapter profilesAdapter = new ProfilesAdapter(profilesActivity.this,lists);
        recyclerView.setAdapter(profilesAdapter);

        reference = FirebaseDatabase.getInstance()
                .getReference()
                .child("All Users");
        getlistOfCandidates();
    }
    private void  getlistOfCandidates(){

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    lists.clear();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()){

                        candidateModel list = dataSnapshot.getValue(candidateModel.class);
                        lists.add(list);
                    }
                }
                ProfilesAdapter profilesAdapter = new ProfilesAdapter(profilesActivity.this,lists);
                recyclerView.setAdapter(profilesAdapter);
                profilesAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }





    @Override
    protected void onStart() {
        super.onStart();
        getlistOfCandidates();

    }

    protected void onStop() {
        super.onStop();
    }
}