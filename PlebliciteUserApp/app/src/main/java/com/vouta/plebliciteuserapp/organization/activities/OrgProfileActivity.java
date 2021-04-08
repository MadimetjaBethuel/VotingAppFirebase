package com.vouta.plebliciteuserapp.organization.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;
import com.squareup.picasso.Picasso;

import com.vouta.plebliciteuserapp.R;
import com.vouta.plebliciteuserapp.organization.model.CandidatesUsers;
import com.vouta.plebliciteuserapp.voter.fragments.VoterHomefragment;
import com.vouta.plebliciteuserapp.voter.fragments.VoterNotificatioFragment;
import com.vouta.plebliciteuserapp.voter.fragments.VoterResultsFragment;

import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class OrgProfileActivity extends AppCompatActivity {


    private TextView editProfile, Name, Description, Phone, Email;
    EditText text;
    private ImageView profilePic;
    private ChipNavigationBar chipNavigationBar;
    private Fragment fragment;


    private UploadTask uploadTask;
    private StorageReference storageReference;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference;
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private DocumentReference documentReference;
    private String currentUserid;
    private FirebaseAuth mAuth;
    private Uri imageUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_org_profile);





        mAuth = FirebaseAuth.getInstance();
        currentUserid = mAuth.getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance()
                .getReference()
                .child("All Users")
                .child(currentUserid);


        documentReference = firebaseFirestore.collection("UserProfile").document();
        storageReference = FirebaseStorage.getInstance().getReference("Profile Images");
        Name = findViewById(R.id.Orgname);
        Description = findViewById(R.id.description);
        Phone = findViewById(R.id.phone);
        Email = findViewById(R.id.email);
        profilePic = findViewById(R.id.profilepic);

        getProfile();


    }

    private void getProfile(){

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()){
                    String name = snapshot.child("NameProfile").getValue().toString();
                    String email = snapshot.child("EmailProfile").getValue().toString();
                    String phone = snapshot.child("PhoneProfile").getValue().toString();
                    String bio = snapshot.child("DescriptionProfile").getValue().toString();


                    Name.setText(name);
                    Description.setText(bio);
                    Phone.setText(phone);
                    Email.setText(email);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }



}