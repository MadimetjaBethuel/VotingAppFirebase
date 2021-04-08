package com.vouta.plebliciteuserapp.organization.activities;

import android.content.Intent;
import android.os.Bundle;

import android.view.MenuItem;
import android.view.View;

import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.vouta.plebliciteuserapp.R;
import com.vouta.plebliciteuserapp.organization.activities.CreateSurveyActivity;

public class SurveyMainActivity extends AppCompatActivity {

   private FloatingActionButton fab;
   Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_main);

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), CreateSurveyActivity.class));
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}