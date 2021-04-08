package com.vouta.plebliciteuserapp.voter.activities;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.DisplayCutout;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.vouta.plebliciteuserapp.R;
import com.vouta.plebliciteuserapp.voter.VoterMainActivity;
import com.vouta.plebliciteuserapp.voter.model.Voters;
import com.vouta.plebliciteuserapp.voter.model.candidateModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class profileViewActivity extends AppCompatActivity {


    private TextView Name, Phone, Email, Description;
    public static final String PROFILE = "PROFILE";
    private ExtendedFloatingActionButton voteBtn;
    candidateModel model;
    private Voters voter;

    private int Votes = 0;

    ArrayList<Voters> votelist = new ArrayList<Voters>();

    private DatabaseReference voteRef, candidateRef, votersRef;
    FirebaseAuth auth;
    String UserId;
    int NumberOfVotes;
    private int VotingState;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_view);

        Name = findViewById(R.id.candidateName);
        Description = findViewById(R.id.descriptionCandidate);
        Email = findViewById(R.id.phoneCandidate);
        Phone = findViewById(R.id.emailCandidate);
        voteBtn = findViewById(R.id.voteBtn);


        model = getIntent().getParcelableExtra(PROFILE);

        auth = FirebaseAuth.getInstance();

        //UserId = auth.getCurrentUser().getUid();

        voteRef = FirebaseDatabase.getInstance().getReference().child("Votes");
        candidateRef = FirebaseDatabase.getInstance().getReference().child("All Users");
        votersRef = FirebaseDatabase.getInstance().getReference().child("VoterUsers");
        getProfile();
        Vote();
        Validate();

    }

    private void getProfile() {


        Name.setText(model.getNameProfile());
        Description.setText(model.getDescriptionProfile());
        Email.setText(model.getEmailProfile());
        Phone.setText(model.getPhoneProfile());


    }

    private void Vote() {

        voteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ValidateAndVote();

            }
        });

    }
    public void Validate(){
        votersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot snap : snapshot.getChildren()) {
                        Voters votes = snap.getValue(Voters.class);
                        votelist.add(votes);

                        for (Voters i : votelist) {

                            if (i.getVotingState() == 1) {
                                voteBtn.hide();
                            }
                        }
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void ValidateAndVote() {

            votersRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        for (DataSnapshot snap : snapshot.getChildren()) {
                            Voters votes = snap.getValue(Voters.class);
                            votelist.add(votes);

                            for (Voters i : votelist ){

                                if (i.getVotingState() == 1){
                                    voteBtn.shrink();
                                    voteBtn.hide();

                                    Toast.makeText(profileViewActivity.this,
                                            "You have Voted", Toast.LENGTH_SHORT).show();


                                }else if (i.getVotingState() == 0){
                                    VoteCandidate();
                                }


                            }
                        }

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(profileViewActivity.this,
                            error.getMessage(),
                            Toast.LENGTH_SHORT).show();

                }
            });

    }

    private void VoteCandidate() {

        String CandidateID = model.getCandidateID();
        NumberOfVotes = model.getNumberOfVotes();
        NumberOfVotes += 1;

        HashMap<String,Object> update = new HashMap<>();
        update.put("NumberOfVotes",NumberOfVotes);

        candidateRef.child(CandidateID).updateChildren(update)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){

                            Toast.makeText(profileViewActivity.this,
                                    "",
                                    Toast.LENGTH_SHORT).show();
                            changeVotingState();

                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(profileViewActivity.this,
                        e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void changeVotingState(){

        VotingState = 1;
        HashMap<String,Object> State = new HashMap<>();
        State.put("VotingState",VotingState);
        votersRef.child(auth.getCurrentUser().getUid()).updateChildren(State);
    }


}
