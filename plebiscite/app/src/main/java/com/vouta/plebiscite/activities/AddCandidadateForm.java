package com.vouta.plebiscite.activities;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Message;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vouta.plebiscite.MainActivity;
import com.vouta.plebiscite.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;


public class AddCandidadateForm extends AppCompatActivity {


    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 100;
    private EditText NameOrg, Email, Phone;
    private CircularProgressButton submit;
    private DatabaseReference AddCandidateref, Candidateref;
    private String Uid;
    private FirebaseAuth mAuth;
    private String date, time, random, phone;
    Random rnd = new Random();
    String registrationnumber;

    String message = "you have been registered as a candidate here is your verification number " + registrationnumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_org);

        NameOrg = findViewById(R.id.txtName);
        Email = findViewById(R.id.textEmail);
        Phone = findViewById(R.id.txtMob);

        int number = rnd.nextInt(999999);
        registrationnumber = String.format("%06d", number);


        // this will convert any number sequence into 6 character.



        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            Uid = user.getUid();
        }


        AddCandidateref = FirebaseDatabase.getInstance().getReference().child("admin");
        Candidateref = FirebaseDatabase.getInstance().getReference().child("Candidates");


        Calendar caFordDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("dd-MMM-yyyy");
        date = currentDate.format(caFordDate.getTime());
        Calendar caFordTime = Calendar.getInstance();
        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss");
        time = currentTime.format(caFordTime.getTime());

        random = date + time;

        submit = findViewById(R.id.btnSubmit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //sendSms();
                AddCandidate();
            }
        });


    }

    private void validate() {

    }

    private void AddCandidate() {

        submit.startAnimation();


        AddCandidateref.child(Uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String name = NameOrg
                        .getText().toString().trim();
                String email = Email
                        .getText().toString().trim();
                phone = Phone
                        .getText().toString().trim();


                if (snapshot.exists()) {

                    HashMap OrgMap = new HashMap();
                    OrgMap.put("Uid", Uid);
                    OrgMap.put("Email", email);
                    OrgMap.put("firstName", name);
                    OrgMap.put("RegNum", registrationnumber);
                    OrgMap.put("Phone", phone);

                    Candidateref.child(Uid + random).setValue(OrgMap)
                            .addOnCompleteListener(new OnCompleteListener() {
                                @Override
                                public void onComplete(@NonNull Task task) {

                                    Intent intent = new Intent(getApplicationContext(), notify.class);
                                    startActivity(intent);
                                    Toast.makeText(getApplicationContext(),
                                            "Success",
                                            Toast.LENGTH_SHORT).show();
                                    submit.revertAnimation();
                                }
                            });


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AddCandidadateForm.this,
                        error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void sendSms() {
        phone = Phone.getText().toString();
        PendingIntent intent = null;
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(phone, null, message, intent, null);
        Toast.makeText(getApplicationContext(), "SMS sent.",
                Toast.LENGTH_LONG).show();


    }


}




