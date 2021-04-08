package com.vouta.plebiscite;



import android.os.Bundle;
import android.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;
import com.vouta.plebiscite.Fragments.HomeFragment;
import com.vouta.plebiscite.Fragments.LiveResultsFragment;
import com.vouta.plebiscite.Fragments.RegisterCandidateFragment;
import com.vouta.plebiscite.Fragments.TimerFragment;
import com.vouta.plebiscite.activities.AdminLogin;

public class MainActivity extends AppCompatActivity  {

    private ChipNavigationBar chipNavigationBar;
    private Fragment fragment;
    private Toolbar toolbar;
    private AdminLogin Login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        chipNavigationBar = findViewById(R.id.chipNavigation);
        chipNavigationBar.setItemSelected(R.id.chipNavigation, true);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment, new RegisterCandidateFragment())
                .commit();

        chipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                switch (i) {
                    case R.id.nav_register:
                        fragment = new RegisterCandidateFragment();
                        break;
                    case R.id.nav_timer:
                        fragment = new TimerFragment();
                        break;
                    case R.id.nav_graphs:
                        fragment = new LiveResultsFragment();
                        break;

                }
                if (fragment != null) {
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment, fragment)
                            .commit();
                }
            }

        });

    }



}