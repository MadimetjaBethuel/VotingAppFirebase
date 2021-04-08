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
import com.vouta.plebliciteuserapp.R;
import com.vouta.plebliciteuserapp.organization.OrgMainActivity;

import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;

public class LoginActivity extends AppCompatActivity {

    private TextView forgotpass;
    private TextView noAccont;
    private CircularProgressButton circularProgressButton;
    private EditText Email,Password;
    FirebaseAuth  auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {

            if(auth.getCurrentUser().isEmailVerified()) {
                Intent intent = new Intent(LoginActivity.this, OrgMainActivity.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
            }else {
                Toast.makeText(this,
                        "Email not Verified", Toast.LENGTH_SHORT).show();
                FirebaseAuth.getInstance().signOut();
                finish();
            }
        }
        setContentView(R.layout.activity_login);

        forgotpass = findViewById(R.id.Orgforgotpassword);
        noAccont = findViewById(R.id.OrgRegisterText);
        Email = findViewById(R.id.OrgLogEmail);
        Password = findViewById(R.id.OrgLogPassword);
        circularProgressButton = findViewById(R.id.OrgVoterBtnLogin);

        auth = FirebaseAuth.getInstance();

        noAccont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),RegisterActivity.class);
                startActivity(intent);
            }
        });


        circularProgressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login();
            }
        });


    }

    private void Login() {
        String email = Email.getText().toString().trim();
        final String password = Password.getText().toString();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this,
                    "Enter Email",
                    Toast.LENGTH_SHORT).show();
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this,
                    "Enter password!",
                    Toast.LENGTH_SHORT).show();

        }

        circularProgressButton.startAnimation();
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        circularProgressButton.revertAnimation();
                        if (!task.isSuccessful()) {
                            // there was an error
                            if (password.length() < 6) {
                                Password.setError("password too short");
                            } else {
                                Toast.makeText(LoginActivity.this,
                                        "auth_failed",
                                        Toast.LENGTH_LONG).show();
                            }
                        } else {
                            if (auth.getCurrentUser().isEmailVerified()) {
                                startActivity(new Intent(LoginActivity.this, OrgMainActivity.class));
                                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                                finish();
                            } else {
                                Toast.makeText(LoginActivity.this,
                                        "email_unverified",
                                        Toast.LENGTH_SHORT).show();
                                FirebaseAuth.getInstance().signOut();
                            }
                        }
                    }
                });

    }
}