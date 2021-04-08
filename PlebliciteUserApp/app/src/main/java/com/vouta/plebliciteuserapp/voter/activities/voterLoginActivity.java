package com.vouta.plebliciteuserapp.voter.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.vouta.plebliciteuserapp.R;
import com.vouta.plebliciteuserapp.voter.VoterMainActivity;

import java.util.HashMap;
import java.util.Map;

import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;

public class voterLoginActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    DatabaseReference voterProfiles;
    FirebaseAuth.AuthStateListener authStateListener;


    private EditText Email,Password;
    private CircularProgressButton Login;
    private String email,password;

    private String VoterId;
    private int VotingState;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voter_login);

        Email = findViewById(R.id.LogEmail);
        Password = findViewById(R.id.LogPassword);
        Login = findViewById(R.id.VoterBtnLogin);

        auth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = auth.getCurrentUser();
        if (firebaseUser != null && firebaseUser.isEmailVerified()){
            startActivity(new Intent(getApplicationContext(),VoterMainActivity.class));

           // VoterId = auth.getCurrentUser().getUid();
        }


        voterProfiles = FirebaseDatabase.getInstance().getReference().child("VoterUsers");
        VoterId = auth.getCurrentUser().getUid();
        VotingState = 0;


        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login.startAnimation();
                Validate();
                LoginVoter();
                SaveDataTorealTime();

            }
        });

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    Intent intent = new Intent(getApplicationContext(), VoterMainActivity.class);
                    startActivity(intent);
                    finish();
                    Login.revertAnimation();
                }
            }
        };

    }
    private void SaveDataTorealTime() {
        Map<String,Object> profiles = new HashMap<>();
        profiles.put("VoterEmail",email);
        profiles.put("VoterId",auth.getCurrentUser().getUid());
        profiles.put("VotingState",VotingState);

        voterProfiles.child(auth.getCurrentUser().getUid()).updateChildren(profiles);
    }

    private void Validate(){
        email = Email.getText().toString().trim();
        password = Password.getText().toString().trim();

        if (TextUtils.isEmpty(email)){
            Toast.makeText(this,
                    "Please Enter Email",
                    Toast.LENGTH_SHORT).show();

        }else if (TextUtils.isEmpty(password)){
            Toast.makeText(this,
                    "Please enter Password",
                    Toast.LENGTH_SHORT).show();
        }


    }
    private void LoginVoter() {

        auth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (auth.getCurrentUser().isEmailVerified()){


                            Toast.makeText(voterLoginActivity.this,
                                    "Logged in Successfully",
                                    Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), VoterMainActivity.class);
                            startActivity(intent);

                        }else {
                            Toast.makeText(voterLoginActivity.this,
                                    "Please Verify your Email", Toast.LENGTH_SHORT).show();
                            Login.revertAnimation();
                        }


                    }
                });
    }


}