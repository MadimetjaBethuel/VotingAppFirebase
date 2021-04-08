package com.vouta.plebliciteuserapp.organization;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.ismaeldivita.chipnavigation.ChipNavigationBar;
import com.vouta.plebliciteuserapp.R;
import com.vouta.plebliciteuserapp.organization.fragments.OrgHomeFragment;
import com.vouta.plebliciteuserapp.organization.fragments.OrgNotificationFragment;
import com.vouta.plebliciteuserapp.organization.fragments.OrgResultsFragment;

public class OrgMainActivity extends AppCompatActivity {
    private ChipNavigationBar chipNavigationBar;
    private Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_org_main);

        chipNavigationBar = findViewById(R.id.chipnavigation);
        chipNavigationBar.setItemSelected(R.id.chipnavigation,true);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment,new OrgHomeFragment())
                .commit();
        chipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                switch (i) {
                    case R.id.nave_home :
                        fragment = new OrgHomeFragment();
                        break;
                    case R.id.nav_Results:
                        fragment = new OrgResultsFragment();
                        break;
                    case R.id.nav_Notifications :
                        fragment = new OrgNotificationFragment();
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