package com.vouta.plebliciteuserapp.voter.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.vouta.plebliciteuserapp.R;
import com.vouta.plebliciteuserapp.organization.activities.SurveyMainActivity;
import com.vouta.plebliciteuserapp.voter.activities.VoterSurveysActivity;
import com.vouta.plebliciteuserapp.voter.activities.VoterTimeActivity;
import com.vouta.plebliciteuserapp.voter.activities.profilesActivity;

public class VoterHomefragment extends Fragment {

    Context context;
    View v;
    private CardView profileCard,surveyCard,timerCard;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.voter_home_fragment,container,false);

        profileCard = v.findViewById(R.id.candidateCardProfile);
        surveyCard = v.findViewById(R.id.CardSurvey);
        timerCard = v.findViewById(R.id.voterCardTime);

        profileCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profile = new Intent(getContext(),profilesActivity.class);
                startActivity(profile);
            }
        });
        surveyCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent survey = new Intent(getContext(), VoterSurveysActivity.class);
                startActivity(survey);

            }
        });
        timerCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent survey = new Intent(getContext(), VoterTimeActivity.class);
                startActivity(survey);

            }
        });


        return v;



    }

}
