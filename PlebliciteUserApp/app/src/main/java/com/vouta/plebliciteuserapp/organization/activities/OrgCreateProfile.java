package com.vouta.plebliciteuserapp.organization.activities;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.vouta.plebliciteuserapp.R;
import com.vouta.plebliciteuserapp.organization.OrgMainActivity;
import com.vouta.plebliciteuserapp.organization.model.CandidatesUsers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class OrgCreateProfile extends AppCompatActivity {


    private EditText Name,Email,Phone,Description;
    private ImageView profileP,changepp;
    private Button save;


    private UploadTask uploadTask;
    private StorageReference storageReference;
    private final FirebaseDatabase database =FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference;
    private final FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private DocumentReference documentReference;
    private static final int PICK_IMAGE = 10;
    private CandidatesUsers candidates;
    private String currentUserid;
    private Uri imageUri;
    private FirebaseAuth mAuth;

    private String name,email,phone,description;
    private int NumberOfVotes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bottomsheet_description);

        Name = findViewById(R.id.editName);
        Email = findViewById(R.id.editEmail);
        Phone = findViewById(R.id.editphone);
        Description = findViewById(R.id.editDescription);
        changepp = findViewById(R.id.changePP);
        save = findViewById(R.id.SaveprofBtn);
        profileP =findViewById(R.id.profilepic);

        mAuth = FirebaseAuth.getInstance();
        currentUserid = mAuth.getCurrentUser().getUid();
        NumberOfVotes = 0;

        documentReference = firebaseFirestore.collection("UserProfile").document(currentUserid);
        storageReference = FirebaseStorage.getInstance().getReference("Profile Images");
        databaseReference = database.getReference("All Users");

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Validate();
                uploaddata();
                UploadDP();


            }
        });

        changepp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"select picture"),PICK_IMAGE);

            }
        });


    }
    private void Validate(){
        name = Name
                .getText().toString().trim();
        email = Email
                .getText().toString().trim();
        phone = Phone
                .getText().toString().trim();
        description = Description
                .getText().toString().trim();

    }

    private void uploaddata() {
        Map<String,Object> profile = new HashMap<>();
        profile.put("NameProfile",name);
        profile.put("EmailProfile",email);
        profile.put("PhoneProfile",phone);
        profile.put("DescriptionProfile",description);
        profile.put("CandidateID",currentUserid);
        profile.put("NumberOfVotes",NumberOfVotes);


        databaseReference.child(currentUserid).updateChildren(profile).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(OrgCreateProfile.this, "uploaded"
                            , Toast.LENGTH_SHORT).show();
                }

            }
        });

        documentReference.update(profile).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(OrgCreateProfile.this,
                        "Uploaded", Toast.LENGTH_SHORT).show();

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(getApplicationContext(),
                                OrgMainActivity.class);
                        startActivity(intent);

                    }
                },2000);

            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE || requestCode == RESULT_OK ||
                data != null || data.getData() != null){

            imageUri = data.getData();
            Picasso.get().load(imageUri).into(profileP);
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                profileP.setImageBitmap(bitmap);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }


        }
    }

    private String getFileExt(Uri uri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }
    private void UploadDP() {

        if (imageUri != null) {
            final StorageReference reference = storageReference.child(System.currentTimeMillis() + "." +
                    "." + getFileExt(imageUri));
            uploadTask = reference.putFile(imageUri);

            Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {

                    if (task.isSuccessful()) {
                        throw task.getException();
                    }
                    return reference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {

                }
            });

        }else {
            Toast.makeText(this,
                    "Please fill all the fields",
                    Toast.LENGTH_SHORT).show();
        }
    }
}
