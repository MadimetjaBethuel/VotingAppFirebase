package com.vouta.plebliciteuserapp.organization.fragments;

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
import com.vouta.plebliciteuserapp.organization.activities.CreateSurveyActivity;
import com.vouta.plebliciteuserapp.organization.activities.OrgProfileActivity;
import com.vouta.plebliciteuserapp.organization.activities.OrgTimeActivity;
import com.vouta.plebliciteuserapp.organization.activities.SurveyMainActivity;

public class OrgHomeFragment extends Fragment {

    View v;
    private CardView cardProfile,cardTime,cardSurvey,cardReports;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.org_home_fragment,container,false);

        cardProfile = v.findViewById(R.id.CardProfile);
        cardTime = v.findViewById(R.id.CardTime);
        cardSurvey = v.findViewById(R.id.CardSurveyOrg);
        cardReports = v.findViewById(R.id.CardReports);

        cardProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), OrgProfileActivity.class);
                startActivity(intent);

            }
        });
        cardSurvey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SurveyMainActivity.class);
                startActivity(intent);
            }
        });
        cardTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), OrgTimeActivity.class);
                startActivity(intent);
            }
        });

        return v;

    }
}
