package com.vouta.plebliciteuserapp.organization.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vouta.plebliciteuserapp.R;
import com.vouta.plebliciteuserapp.organization.model.modelregister;

import java.util.ArrayList;
import java.util.Objects;

import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;


public class RegisterActivity extends AppCompatActivity {

    private TextView gotAnAccount;
    private EditText Email,Password,RegistrationNo;
    private CircularProgressButton circularProgressButton;
    private FirebaseAuth auth;
    private DatabaseReference Candidateref;

    ArrayList<modelregister> lists = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        gotAnAccount = findViewById(R.id.LoginTextOrg);
        circularProgressButton = findViewById(R.id.OrgBtnregister);
        Email = findViewById(R.id.OrgRegEmail);
        Password = findViewById(R.id.OrgRegPassword);
        RegistrationNo = findViewById(R.id.OrgRegNumber);

        auth = FirebaseAuth.getInstance();



        gotAnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
            }
        });
        circularProgressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
    }

    private void register() {

        String email = Email.getText().toString().trim();
        String password = Password.getText().toString().trim();
        String registrationNo = RegistrationNo.getText().toString().trim();
        if (TextUtils.isEmpty(email)) {
            Email.setError("Enter valid Email");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Password.setError("Password should be atleast length of 6!");
            return;
        }

        if (password.length() < 6) {
            Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
            return;
        }


        circularProgressButton.startAnimation();

        Candidateref = FirebaseDatabase.getInstance().getReference().child("Candidates");

        Candidateref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()){

                    modelregister list = snapshot1.getValue(modelregister.class);
                    lists.add(list);

                    for (modelregister i : lists){
                        if (i.getRegNum().equals(registrationNo)){
                            auth.createUserWithEmailAndPassword(email,password)
                                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            circularProgressButton.revertAnimation();

                                            if (task.isSuccessful()){
                                                Toast.makeText(RegisterActivity.this,
                                                        "Authetication success",
                                                        Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(getApplicationContext(),OrgCreateProfile.class);
                                                startActivity(intent);

                                            }else {
                                                Objects.requireNonNull(auth.getCurrentUser().sendEmailVerification());
                                                Toast.makeText(RegisterActivity.this,
                                                        "Verification Email sent check your mail",
                                                        Toast.LENGTH_SHORT).show();
                                            }

                                        }
                                    });
                        }
                    }

                    }
                }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(RegisterActivity.this,
                        error.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });

    }
}