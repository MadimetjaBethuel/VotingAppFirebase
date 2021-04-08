package com.vouta.plebiscite.activities;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vouta.plebiscite.MainActivity;
import com.vouta.plebiscite.R;

import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;

public class notify  extends AppCompatActivity {
    CircularProgressButton circularProgressButton;
    DatabaseReference Candidateref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notify);
        Candidateref = FirebaseDatabase.getInstance().getReference().child("Candidates");

        circularProgressButton = findViewById(R.id.btn_NotiyClose);

        circularProgressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);


            }
        });


    }

}
