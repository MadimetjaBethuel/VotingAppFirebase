package com.vouta.plebiscite.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.EditText;
        import android.widget.Toast;

        import com.google.android.gms.tasks.OnCompleteListener;
        import com.google.android.gms.tasks.OnFailureListener;
        import com.google.android.gms.tasks.Task;
        import com.google.firebase.auth.AuthResult;
        import com.google.firebase.auth.FirebaseAuth;
        import com.google.firebase.auth.FirebaseUser;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
import com.vouta.plebiscite.MainActivity;
import com.vouta.plebiscite.R;

import java.util.HashMap;

import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;


public class AdminLogin extends AppCompatActivity {

    private CircularProgressButton circularProgressButton;

    private FirebaseAuth auth;
    private EditText editEmail,Password;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference Adminref;

    String Uid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);


        editEmail = findViewById(R.id.editTextEmail);
        Password = findViewById(R.id.editTextPassword);

        auth = FirebaseAuth.getInstance();
        Adminref = FirebaseDatabase.getInstance().getReference().child("admin");








        circularProgressButton = (CircularProgressButton) findViewById(R.id.btn_id);
        // circularProgressButton.startAnimation();
        //circularProgressButton.revertAnimation();


        circularProgressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Signin();

            }
        });

    }

    private void Signin() {
        String email = editEmail.getText().toString();
        String password = Password.getText().toString();

        circularProgressButton.startAnimation();

        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {


                if (task.isSuccessful()){
                    circularProgressButton.revertAnimation();

                    Toast.makeText(AdminLogin.this,
                            "Logged in",
                            Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    getUpdate();

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AdminLogin.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                circularProgressButton.revertAnimation();
            }
        });


    }

    public void getUpdate() {

        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        if (user!=null){
            Uid = user.getUid();
        }

        String email = editEmail.getText().toString();
        HashMap admin = new HashMap();
        admin.put("Email",email);

        Adminref.child(Uid).updateChildren(admin);
    }


}