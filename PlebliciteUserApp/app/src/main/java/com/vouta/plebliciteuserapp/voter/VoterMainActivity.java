package com.vouta.plebliciteuserapp.voter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.ismaeldivita.chipnavigation.ChipNavigationBar;
import com.vouta.plebliciteuserapp.R;
import com.vouta.plebliciteuserapp.voter.fragments.VoterHomefragment;
import com.vouta.plebliciteuserapp.voter.fragments.VoterNotificatioFragment;
import com.vouta.plebliciteuserapp.voter.fragments.VoterResultsFragment;

public class VoterMainActivity extends AppCompatActivity {
    private ChipNavigationBar chipNavigationBar;
    private Fragment fragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voter_main);
        chipNavigationBar = findViewById(R.id.chipnavigation);
        chipNavigationBar.setItemSelected(R.id.chipnavigation,true);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment,new VoterHomefragment())
                .commit();

        chipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                switch (i) {
                    case R.id.nave_home :
                        fragment = new VoterHomefragment();
                        break;
                    case R.id.nav_Results:
                        fragment = new VoterResultsFragment();
                        break;
                    case R.id.nav_Notifications :
                        fragment = new VoterNotificatioFragment();
                        break;
                }
                if (fragment != null){
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment,fragment)
                            .commit();
                }
            }
        });
    }
}