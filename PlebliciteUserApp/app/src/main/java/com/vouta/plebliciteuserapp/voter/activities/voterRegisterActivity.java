package com.vouta.plebliciteuserapp.voter.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.vouta.plebliciteuserapp.R;
import com.vouta.plebliciteuserapp.organization.activities.RegisterActivity;
import com.vouta.plebliciteuserapp.voter.VoterMainActivity;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;

public class voterRegisterActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private EditText Email,Phone,Password;
    private String email,phoneNumber,password;
    private CircularProgressButton RegisterBtn;
    private TextView Login,forgotpassword;

    DatabaseReference voterProfiles;
    FirebaseUser VoterId;

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voter_register);

        Email = findViewById(R.id.RegEmail);
        Phone = findViewById(R.id.RegPhoneNumber);
        Password = findViewById(R.id.RegPassword);
        RegisterBtn = findViewById(R.id.VoterBtnregister);
        Login = findViewById(R.id.LoginText);

        voterProfiles = FirebaseDatabase.getInstance().getReference().child("VoterUsers");
        VoterId = FirebaseAuth.getInstance().getCurrentUser();
        if (VoterId != null){
            VoterId.getUid();
        }


        //Firebase
        auth = FirebaseAuth.getInstance();

        RegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterBtn.startAnimation();
                Validate();
                Register();
                verifyPhoneNumber();


            }
        });
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),voterLoginActivity.class);
                startActivity(intent);
            }
        });
    }



    public void Validate(){

         email = Email.getText().toString().trim();
         phoneNumber = Phone.getText().toString().trim();
         password = Password.getText().toString().trim();

        if (TextUtils.isEmpty(email)){
            Toast.makeText(this,
                    "Please Enter Email",
                    Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty(phoneNumber)){
            Toast.makeText(this,
                    "Please Enter Phone Number",
                    Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty(password)){
            Toast.makeText(this,
                    "Please enter Password",
                    Toast.LENGTH_SHORT).show();
        }

    }
    private void Register(){

        auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            auth.getCurrentUser().sendEmailVerification()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()){

                                                Toast.makeText(voterRegisterActivity.this,
                                                        "Registration Successful please check your email for verification code",
                                                        Toast.LENGTH_SHORT).show();

                                                Intent main = new Intent(getApplicationContext(), voterLoginActivity .class);
                                                startActivity(main);
                                                RegisterBtn.revertAnimation();

                                            }

                                        }
                                    });
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(voterRegisterActivity.this,
                        e.getMessage(),
                        Toast.LENGTH_SHORT).show();
                RegisterBtn.revertAnimation();

            }
        });

    }
    private void verifyPhoneNumber(){
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                Log.d("onVerificationCompleted ", "Verifcattion complete" + phoneAuthCredential);
                RegisterBtn.revertAnimation();
                signwithcredentials(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Log.d("verificationFailed","Verification Failed", e);
                RegisterBtn.revertAnimation();

                if (e instanceof FirebaseAuthInvalidCredentialsException){
                    Log.e("Exception:","FirebaseAuthInvalidCredentialsException" + e);

                }else if (e instanceof FirebaseTooManyRequestsException){
                    Log.e("Exception","FirebaseTooManyRequestsException" + e);
                }

                notifyUser("Verification Failed Try Again");

            }

            @Override
            public void onCodeAutoRetrievalTimeOut(@NonNull String s) {

                notifyUser("Verification Failed Try Again");
            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {

                Log.e("onCodeSent","onCodeSent" + s);
                Log.e("Verification code",  s);
            }
        };
    }

    private void notifyUser(String message) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setMessage(message);
        alert.setPositiveButton("ok",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                        startActivity(intent);
                    }
                });
        alert.setNegativeButton("cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                        startActivity(intent);
                    }
                });
        AlertDialog alertDialog = alert.create();
        alertDialog.show();
    }

    private void signwithcredentials(PhoneAuthCredential credential) {
        auth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        FirebaseUser user = task.getResult().getUser();
                        Intent intent = new Intent(getApplicationContext(), VoterMainActivity.class);
                        startActivity(intent);

                    }
                });
        }
    private void PhoneVerify(String Phonenumber){

        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(auth)
                .setPhoneNumber(Phonenumber)
                .setCallbacks(mCallbacks)
                .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = auth.getCurrentUser();
        if (user != null) {
            user.getUid();
        }

    }

}