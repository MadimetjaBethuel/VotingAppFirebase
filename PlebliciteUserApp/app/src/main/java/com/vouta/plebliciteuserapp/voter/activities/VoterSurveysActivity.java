package com.vouta.plebliciteuserapp.voter.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.ColorSpace;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.vouta.plebliciteuserapp.R;
import com.vouta.plebliciteuserapp.organization.model.Question;
import com.vouta.plebliciteuserapp.voter.adapter.SurveyAdapter;
import com.vouta.plebliciteuserapp.voter.model.candidateModel;
import com.vouta.plebliciteuserapp.voter.model.survey;

import java.util.ArrayList;
import java.util.List;

public class VoterSurveysActivity extends AppCompatActivity {

    private DatabaseReference surveyRef,candidateRef;
    private FirebaseDatabase database;
    private RecyclerView surveyRecycler;
    private SurveyAdapter surveyAdapter;

    private ArrayList<survey> surveyslist = new ArrayList<>();
    private List<candidateModel> lists = new ArrayList<>();
    candidateModel model;
    String  Orgid;
    String SavedKey;
    String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voter_surveys);



        database = FirebaseDatabase.getInstance();
        surveyRef = database.getReference().child("Surveys");
        candidateRef = database.getReference().child("All Users");
        surveyRecycler = findViewById(R.id.surveyRecycler);
        SavedKey = surveyRef.getKey();
        surveyRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        surveyAdapter = new SurveyAdapter(getApplicationContext(),surveyslist);
        surveyRecycler.setAdapter(surveyAdapter);




       getSurveys();

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    public void getSurveys() {


            surveyRef.child(SavedKey).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    surveyslist.clear();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        survey surveys = new survey();
                        surveys.setName(dataSnapshot.child("Name").getValue().toString());
                        surveyslist.add(surveys);


                    }
                    surveyAdapter = new SurveyAdapter(getApplicationContext(), surveyslist);
                    surveyRecycler.setAdapter(surveyAdapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

    private void getOrgId(){
        candidateRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for (DataSnapshot snap : snapshot.getChildren()){
                        candidateModel list =snap.getValue(candidateModel.class);
                        lists.add(list);

                        lists.clear();
                        key = list != null ? list.getCandidateID() : null;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }}