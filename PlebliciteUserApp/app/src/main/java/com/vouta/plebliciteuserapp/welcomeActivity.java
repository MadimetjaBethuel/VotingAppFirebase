package com.vouta.plebliciteuserapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.vouta.plebliciteuserapp.organization.OrgMainActivity;
import com.vouta.plebliciteuserapp.organization.activities.RegisterActivity;
import com.vouta.plebliciteuserapp.voter.VoterMainActivity;
import com.vouta.plebliciteuserapp.voter.activities.voterLoginActivity;
import com.vouta.plebliciteuserapp.voter.activities.voterRegisterActivity;

public class welcomeActivity extends AppCompatActivity {

    private Button candidate,voter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        candidate = findViewById(R.id.candidateBtn);
        voter = findViewById(R.id.voterBtn);

        candidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });
        voter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), voterRegisterActivity.class);
                startActivity(intent);
            }
        });


    }
}